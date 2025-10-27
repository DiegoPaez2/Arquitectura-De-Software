// src/controllers/auth.controller.js
const ws = require("../services/ws.service");

class AuthController {
  static async health(_req, res) {
    try {
      const ok = await ws.ping();
      res.json({ ok, wsdl: ws.getWsdl() });
    } catch (e) {
      res.status(500).json({ ok: false, error: String(e) });
    }
  }

  static async login(req, res) {
    const { username, password } = req.body || {};
    if (!username || !password) {
      return res.status(400).json({ error: "Faltan username y/o password" });
    }
    try {
      const result = await ws.login(username, password); // string del WS
      return res.json({ result });
    } catch (e) {
      return res.status(500).json({ error: String(e) });
    }
  }
}

module.exports = AuthController;
