// src/services/soapClient.js
const soap = require("soap");

const WSDL_URL =
  process.env.WSDL_URL ||
  "http://localhost:8080/WS_CONUNI_SOAPJAVA_GRO01/WS_Conversion_Unidades?wsdl";

let _client = null;

async function getClient() {
  if (_client) return _client;
  return new Promise((resolve, reject) => {
    soap.createClient(WSDL_URL, (err, client) => {
      if (err) return reject(err);
      _client = client;
      resolve(client);
    });
  });
}

module.exports = {
  getClient,
  WSDL_URL,
};
