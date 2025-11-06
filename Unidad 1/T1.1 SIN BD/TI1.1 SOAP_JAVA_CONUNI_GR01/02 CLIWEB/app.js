// app.js
const express = require("express");
const cors = require("cors");
const path = require("path");

const apiRoutes = require("./src/routes/api.routes");

const app = express();
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// FRONTEND estático
app.use(express.static(path.join(__dirname, "public")));

// API (MVC)
app.use("/api", apiRoutes);

// Fallback: sirve index.html solo para rutas sin extensión y fuera de /api
app.use((req, res, next) => {
  const isGet = req.method === "GET";
  const isApi = req.path.startsWith("/api/");
  const hasExt = /\.[a-z0-9]+$/i.test(req.path);
  if (isGet && !isApi && !hasExt) {
    return res.sendFile(path.join(__dirname, "public", "index.html"));
  }
  next();
});

const PORT = process.env.PORT || 3000;
const HOST = process.env.HOST || "0.0.0.0";

app.listen(PORT, HOST, () => {
  console.log(`✅ Servidor listo: http://${HOST}:${PORT}`);
  console.log(
    `🔗 WSDL: ${process.env.WSDL_URL || "http://10.9.6.53:8080/WS_CONUNI_SOAPJAVA_GRO01/WS_Conversion_Unidades?wsdl"}`
  );
});

module.exports = app;
