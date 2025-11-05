package ec.edu.monster.ws;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/** Activa JAX-RS bajo /api */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    // vacío: Payara registra automáticamente @Path
}
