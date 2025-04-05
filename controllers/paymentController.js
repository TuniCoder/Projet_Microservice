require('dotenv').config(); 
const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

exports.createCheckoutSession = async (req, res) => {
  const { items } = req.body;

  try {
    // Sécurité : vérifier que chaque item a un name, price, et quantity valides
    if (!Array.isArray(items) || items.length === 0) {
      return res.status(400).json({ error: 'Aucun item fourni pour la commande.' });
    }

    const lineItems = items.map(item => {
      if ( !item.price || !item.quantity) {
        throw new Error('Un des items est mal formé');
      }

      return {
        price_data: {
          currency: 'usd',
          product_data: {
            name: "produit",
          },
          unit_amount: Math.round(Number(item.price) * 100), // en cents
        },
        quantity: item.quantity,
      };
    });

    const session = await stripe.checkout.sessions.create({
      payment_method_types: ['card'],
      line_items: lineItems,
      mode: 'payment',
      success_url: `${process.env.FRONTEND_URL}/success`,
      cancel_url: `${process.env.FRONTEND_URL}/cancel`,
    });

    res.json({ url: session.url });
  } catch (error) {
    console.error("Erreur Stripe:", error);
    res.status(500).json({ error: 'Erreur lors de la création de la session de paiement' });
  }
};
