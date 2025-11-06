import { convertir } from "./model.js";
import { mostrarResultado, mostrarError } from "./view.js";

document.addEventListener("DOMContentLoaded", () => {
    if (localStorage.getItem("logged") !== "true") {
        window.location.href = "index.html";
        return;
    }

    document.getElementById("btnConvertir").addEventListener("click", async () => {
        const tipo = document.getElementById("conversion").value;
        const valor = document.getElementById("valor").value;
        if (!valor) {
            mostrarError({ message: "Ingrese un valor válido" });
            return;
        }
        try {
            const res = await convertir(tipo, valor);
            mostrarResultado(res);
        } catch (e) {
            mostrarError(e);
        }
    });
});

window.logout = function() {
    localStorage.removeItem("logged");
    window.location.href = "index.html";
};
