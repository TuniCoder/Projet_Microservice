const express = require('express');
const router = express.Router();
const paymentController = require('../controllers/paymentController');

router.post('/create-checkout-session', paymentController.createCheckoutSession);
router.get('/history/:userId', async (req, res) => {
    try {
      const userId = parseInt(req.params.userId);
      const history = await History.find({ userId });
      res.json(history);
    } catch (error) {
      console.error('Erreur lors de la récupération de l\'historique:', error);
      res.status(500).json({ error: 'Erreur serveur' });
    }
  });
router.get('/', paymentController.getAllHistories);
router.get('/:id', paymentController.getHistoryById);
router.put('/:id', paymentController.updateHistory);
router.delete('/:id', paymentController.deleteHistory);
router.get('/history/user/:userId', paymentController.getHistoriesByUserId);

module.exports = router;
