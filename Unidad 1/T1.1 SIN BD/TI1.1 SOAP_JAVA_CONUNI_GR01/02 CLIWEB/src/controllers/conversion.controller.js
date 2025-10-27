// src/controllers/conversion.controller.js
const ws = require("../services/ws.service");

class ConversionController {
  static async convert(req, res) {
    const { op, value } = req.body || {};
    const num = Number(value);
    if (!op) return res.status(400).json({ error: "Falta 'op'." });
    if (Number.isNaN(num)) return res.status(400).json({ error: "'value' debe ser numérico." });

    try {
      const result = await ws.convert(op, num); // número double
      return res.json({ result });
    } catch (e) {
      return res.status(500).json({ error: String(e) });
    }
  }
}

module.exports = ConversionController;
