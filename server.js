const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const paymentRoutes = require('./routes/paymentRoutes');
require('dotenv').config();

const app = express();

// Vérifie que FRONTEND_URL est bien défini
const allowedOrigin = process.env.FRONTEND_URL || 'http://localhost:4200';

// Middlewares
app.use(cors({
  origin: allowedOrigin,
  methods: ['GET', 'POST', 'PUT', 'DELETE'],
  credentials: true // active si tu veux envoyer des cookies/tokens
}));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Routes
app.use('/api/payment', paymentRoutes);

// Démarrage du serveur
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`✅ Payment service running on port ${PORT}`);
});
