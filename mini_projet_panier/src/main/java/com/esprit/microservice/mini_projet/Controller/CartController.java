package com.esprit.microservice.mini_projet.Controller;

import com.esprit.microservice.mini_projet.Entities.Cart;
import com.esprit.microservice.mini_projet.Entities.CartItem;
import com.esprit.microservice.mini_projet.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
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
        return ResponseEntity.ok(cartService.addToCart(userId, cartItem));
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
}
