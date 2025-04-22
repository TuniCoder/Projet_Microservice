package com.esprit.microservice.mini_projet.Controller;

import com.esprit.microservice.mini_projet.Entities.Cart;
import com.esprit.microservice.mini_projet.Entities.CartItem;
import com.esprit.microservice.mini_projet.Service.CartService;
import com.esprit.microservice.mini_projet.Service.EmailDto;
import com.esprit.microservice.mini_projet.Service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final EmailService emailService;

    public CartController(CartService cartService, EmailService emailService) {
        this.cartService = cartService;
        this.emailService = emailService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartTotal(userId));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartItem> addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        CartItem addedItem = cartService.addToCart(userId, cartItem);

        // Si nécessaire, vous pouvez envoyer un email après l'ajout d'un article
        // emailService.sendOrderConfirmationEmail(userEmail, orderId, totalAmount);

        return ResponseEntity.ok(addedItem);
    }

    @PutMapping("/{userId}/items/{itemId}/quantity")
    public ResponseEntity<CartItem> updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItemQuantity(userId, itemId, quantity));
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        cartService.removeFromCart(userId, itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long userId, @RequestBody Cart cart) {
        if (!userId.equals(cart.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cartService.updateCart(cart));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<Void> deleteCart(@PathVariable Long userId) {
        cartService.deleteCart(userId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{userId}/checkout")
    public ResponseEntity<String> checkout(@PathVariable Long userId, @RequestBody EmailDto email) {


        // Récupérer le panier de l'utilisateur
        Cart cart = cartService.getCartByUserId(userId);

        // Vérifiez que le panier n'est pas vide
        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Le panier est vide.");
        }

        // Générer un ID de commande et le montant total
        String orderId = "ORDER-" + cart.getId();
        String total = cart.getTotalPrice() +" DT";
        try {
            System.out.println(email);
            // Envoyer un e-mail de confirmation avec les détails de la commande
            emailService.sendOrderConfirmationEmail(email.getEmail(), orderId,total);
            return ResponseEntity.ok("Commande validée, e-mail de confirmation envoyé.");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'e-mail.");
        }
    }
    @PostMapping("/{userId}/apply-promo")
    public ResponseEntity<Cart> applyPromo(@PathVariable Long userId, @RequestParam String code) {
        try {
            cartService.applyPromoCode(userId, code);
            Cart updatedCart = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}