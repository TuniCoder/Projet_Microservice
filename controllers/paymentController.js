require('dotenv').config(); 
const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);
const History = require('../models/History');

// ✅ Créer une session de paiement Stripe et enregistrer l'historique
exports.createCheckoutSession = async (req, res) => {
  const { items, userId, dateCommande } = req.body;

  try {
    if (!Array.isArray(items) || items.length === 0) {
      return res.status(400).json({ error: 'Aucun item fourni pour la commande.' });
    }

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
          unit_amount: Math.round(Number(item.price) * 100),
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

    // ✅ Ajout de quantity et totalPrice dans chaque entrée de l'historique
    const historyData = items.map(item => ({
      userId: userId,
      productId: item.productId,
      quantity: item.quantity,
      totalPrice: Number(item.price) * item.quantity,
      orderDate: dateCommande || new Date()
    }));

    await History.insertMany(historyData);

    res.json({ url: session.url });

  } catch (error) {
    console.error("❌ Erreur Stripe ou historique:", error.message);
    res.status(500).json({ error: 'Erreur lors de la création de la session de paiement' });
  }
};

// ✅ Lire tout l'historique
exports.getAllHistories = async (req, res) => {
  try {
    const histories = await History.find();
    res.json(histories);
  } catch (error) {
    res.status(500).json({ error: 'Erreur lors de la récupération des historiques' });
  }
};

// ✅ Lire un historique par ID
exports.getHistoryById = async (req, res) => {
  try {
    const history = await History.findById(req.params.id);
    if (!history) {
      return res.status(404).json({ error: 'Historique non trouvé' });
    }
    res.json(history);
  } catch (error) {
    res.status(500).json({ error: 'Erreur lors de la récupération de l’historique' });
  }
};

// ✅ Mettre à jour un historique
exports.updateHistory = async (req, res) => {
  try {
    const updatedHistory = await History.findByIdAndUpdate(
      req.params.id,
      req.body,
      { new: true }
    );
    if (!updatedHistory) {
      return res.status(404).json({ error: 'Historique non trouvé' });
    }
    res.json(updatedHistory);
  } catch (error) {
    res.status(500).json({ error: 'Erreur lors de la mise à jour' });
  }
};

// ✅ Supprimer un historique
exports.deleteHistory = async (req, res) => {
  try {
    const deletedHistory = await History.findByIdAndDelete(req.params.id);
    if (!deletedHistory) {
      return res.status(404).json({ error: 'Historique non trouvé' });
    }
    res.json({ message: 'Historique supprimé avec succès' });
  } catch (error) {
    res.status(500).json({ error: 'Erreur lors de la suppression' });
  }
};
// Récupérer les historiques par ID utilisateur
exports.getHistoriesByUserId = async (req, res) => {
  const userId = req.params.userId;  // Récupérer l'userId depuis les paramètres de l'URL

  try {
    const histories = await History.find({ userId: userId }).sort({ orderDate: -1 }); // Trier par date descendante

    if (!histories.length) {
      return res.status(404).json({ error: 'Aucune commande trouvée pour cet utilisateur.' });
    }

    res.json(histories);
  } catch (error) {
    console.error("Erreur lors de la récupération des historiques :", error.message);
    res.status(500).json({ error: 'Erreur interne du serveur.' });
  }
};
