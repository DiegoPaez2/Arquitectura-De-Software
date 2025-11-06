export function mostrarResultado(valor) {
    document.getElementById("resultado").textContent = `Resultado: ${valor}`;
}

export function mostrarError(err) {
    document.getElementById("resultado").textContent = `❌ Error: ${err.message}`;
}
