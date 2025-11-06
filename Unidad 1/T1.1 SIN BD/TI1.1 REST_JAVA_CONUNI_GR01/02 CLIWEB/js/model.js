const API_BASE = "http://localhost:8080/WS_CONUNI_RESTJAVA_GRO01/api/conversion";

export async function convertir(tipo, valor) {
    let param = "";

    // Mapeo correcto del parámetro según el endpoint
    if (tipo === "kilometros-a-millas") param = `?km=${valor}`;
    else if (tipo === "metros-a-pies") param = `?m=${valor}`;
    else if (tipo === "pulgadas-a-centimetros") param = `?p=${valor}`;
    else if (tipo === "kilogramos-a-libras") param = `?kg=${valor}`;
    else if (tipo === "gramos-a-onzas") param = `?g=${valor}`;
    else if (tipo === "libras-a-kilogramos") param = `?lb=${valor}`;
    else if (tipo === "celsius-a-fahrenheit" || tipo === "celsius-a-kelvin") param = `?c=${valor}`;
    else if (tipo === "fahrenheit-a-celsius") param = `?f=${valor}`;
    else throw new Error("Tipo de conversión no válido");

    const url = `${API_BASE}/${tipo}${param}`;
    console.log("🔥 Fetching:", url);

    try {
        const response = await fetch(url, {
            method: "GET",
            headers: { "Accept": "application/json" }
        });

        if (!response.ok) {
            const text = await response.text();
            throw new Error(`Error ${response.status}: ${text}`);
        }

        // Algunos servicios devuelven texto, otros JSON. Probemos ambos:
        const text = await response.text();
        return text || "Sin respuesta";
    } catch (error) {
        console.error("💀 Error en fetch:", error);
        throw error;
    }
}
