package ec.edu.monster.ws.filter;

import java.io.IOException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

/**
 * Filtro global CORS para habilitar peticiones desde orígenes distintos al del servidor.
 * Se ejecuta automáticamente en todas las respuestas del servicio REST (gracias a @Provider).
 * 
 * Este filtro:
 *  - Permite solicitudes desde cualquier origen (útil para desarrollo local).
 *  - Acepta los métodos HTTP más comunes.
 *  - Gestiona cabeceras personalizadas.
 *  - Responde correctamente a las peticiones OPTIONS (preflight).
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response)
            throws IOException {

        // 🌍 Orígenes permitidos (en producción puedes cambiar "*" por un dominio específico)
        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");

        // ✅ Métodos HTTP permitidos
        response.getHeaders().putSingle("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // 📦 Cabeceras que el cliente puede enviar
        response.getHeaders().putSingle("Access-Control-Allow-Headers",
                "Origin, Content-Type, Accept, Authorization, X-Requested-With");

        // 🔁 Permitir credenciales (cookies, headers con auth, etc.) — opcional
        response.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");

        // 🕒 Tiempo de cacheo del preflight (opcional)
        response.getHeaders().putSingle("Access-Control-Max-Age", "86400"); // 24 horas
    }
}
