require('dotenv').config(); 
const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);
const History = require('../models/History');

exports.createCheckoutSession = async (req, res) => {
  const { items, userId,dateCommande } = req.body;

  try {
    // ✅ Validation de base
    if (!Array.isArray(items) || items.length === 0) {
      return res.status(400).json({ error: 'Aucun item fourni pour la commande.' });
    }

    // ✅ Préparation des articles pour Stripe
    const lineItems = items.map(item => {
      if (!item.name || !item.price || !item.quantity) {
        throw new Error('Un des items est mal formé');
      }

      return {
        price_data: {
          currency: 'usd',
          product_data: {
            name: item.name,
          },
          unit_amount: Math.round(Number(item.price) * 100), // en cents
        },
        quantity: item.quantity,
      };
    });

    // ✅ Création de la session Stripe
    const session = await stripe.checkout.sessions.create({
      payment_method_types: ['card'],
      line_items: lineItems,
      mode: 'payment',
      success_url: `${process.env.FRONTEND_URL}/success`,
      cancel_url: `${process.env.FRONTEND_URL}/cancel`,
    });

    // ✅ Sauvegarde de l’historique d’achat
    const historyData = items.map(item => ({
      userId: userId,
      productId: item.productId,
      orderDate: dateCommande || new Date()
    }));

    await History.insertMany(historyData);

    // ✅ Réponse
    res.json({ url: session.url });
    
  } catch (error) {
    console.error("❌ Erreur Stripe ou historique:", error.message);
    res.status(500).json({ error: 'Erreur lors de la création de la session de paiement' });
  }
};
