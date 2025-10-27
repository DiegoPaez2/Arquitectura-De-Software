// src/services/ws.service.js
const { getClient, WSDL_URL } = require("./soapClient");

const OPS = new Set([
  "celsiusAfahrenheit",
  "fahrenheitAcelsius",
  "celsiusAkelvin",
  "metrosApies",
  "pulgadasAcentimetros",
  "gramosAonzas",
  "kilometrosAmillas",
  "kilogramosAlibras",
  "librasAkilogramos",
]);

async function ping() {
  await getClient(); // si crea el cliente, el WSDL responde
  return true;
}

function getWsdl() {
  return WSDL_URL;
}

async function login(username, password) {
  const client = await getClient();
  return new Promise((resolve, reject) => {
    client.login({ arg0: username, arg1: password }, (err, res) => {
      if (err) return reject(err);
      resolve(res?.return ?? "");
    });
  });
}

async function convert(op, value) {
  if (!OPS.has(op)) {
    throw new Error("Operación inválida");
  }
  const client = await getClient();
  return new Promise((resolve, reject) => {
    client[op]({ arg0: value }, (err, res) => {
      if (err) return reject(err);
      resolve(res?.return);
    });
  });
}

module.exports = {
  ping,
  getWsdl,
  login,
  convert,
};
