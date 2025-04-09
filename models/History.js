const mongoose = require('mongoose');

const historySchema = new mongoose.Schema({
  userId: {
    type: Number,
    required: true
  },
  productId: {
    type: Number,
    required: true
  },
  orderDate: {
    type: Date,
    default: Date.now
  }
});

module.exports = mongoose.model('History', historySchema);
