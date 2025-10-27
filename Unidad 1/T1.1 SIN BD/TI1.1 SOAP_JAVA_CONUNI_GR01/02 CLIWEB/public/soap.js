// soap.js

async function apiPost(url, payload) {
  const res = await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.error || res.statusText);
  }
  return res.json();
}

/* ========== LOGIN ========== */
const loginForm = document.getElementById("loginForm");

if (loginForm) {
  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const msg = document.getElementById("loginMsg");

    msg.className = "toast";
    msg.textContent = "Autenticando...";

    try {
      const data = await apiPost("/api/login", { username, password });
      const respuesta = (data?.result || "").toString().trim().toUpperCase();

      // Verifica si el WS respondió OK o SUCCESS
      if (respuesta === "OK" || respuesta === "SUCCESS") {
        sessionStorage.setItem("logged", "1");
        msg.className = "toast success";
        msg.innerHTML = "<strong>Acceso correcto</strong>";

        // Redirige al conversor
        setTimeout(() => (window.location.href = "./convert.html"), 600);
      } else {
        // Si devuelve FAIL u otra cosa, no deja pasar
        sessionStorage.removeItem("logged");
        msg.className = "toast error";
        msg.innerHTML = "<strong>Acceso denegado</strong><br><small>Usuario o contraseña incorrectos.</small>";
      }
    } catch (err) {
      msg.className = "toast error";
      msg.textContent = "Error: " + err.message;
    }
  });
}

/* ========== CONVERSOR ========== */
const btnConvert = document.getElementById("btnConvert");

if (btnConvert) {
  // Requiere login
  if (sessionStorage.getItem("logged") !== "1") {
    window.location.href = "./index.html";
  }

  // Mapeo de nombres legibles
  const opNames = {
    celsiusAfahrenheit: "Fahrenheit",
    fahrenheitAcelsius: "Celsius",
    celsiusAkelvin: "Kelvin",
    metrosApies: "Pies",
    pulgadasAcentimetros: "Centímetros",
    gramosAonzas: "Onzas",
    kilometrosAmillas: "Millas",
    kilogramosAlibras: "Libras",
    librasAkilogramos: "Kilogramos",
  };

  btnConvert.addEventListener("click", async () => {
    const op = document.getElementById("op").value;
    const value = document.getElementById("value").value;
    const box = document.getElementById("convMsg");

    box.className = "msg";
    box.textContent = "Consultando...";

    try {
      const data = await apiPost("/api/convert", { op, value });
      const unidad = opNames[op] || "Resultado";
      box.className = "msg result";
      box.textContent = `${unidad}: ${data.result}`;
    } catch (err) {
      box.className = "toast error";
      box.textContent = "Error: " + err.message;
    }
  });

  const logout = document.getElementById("logout");
  if (logout) {
    logout.addEventListener("click", () => {
      sessionStorage.removeItem("logged");
    });
  }
}
c