// src/routes/api.routes.js
const { Router } = require("express");
const AuthController = require("../controllers/auth.controller");
const ConversionController = require("../controllers/conversion.controller");

const router = Router();

// Health
router.get("/health", AuthController.health);

// Auth
router.post("/login", AuthController.login);

// Conversiones
router.post("/convert", ConversionController.convert);

module.exports = router;
