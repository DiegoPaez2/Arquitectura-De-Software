package ec.edu.monster.ws;

import ec.edu.monster.controlador.ConversionControlador;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/conversion")
@Produces(MediaType.APPLICATION_JSON)
public class ConversionResource {

    private final ConversionControlador ctrl = new ConversionControlador();

    // (opcional) ping para verificar mapeo
    @GET @Path("/ping")
    public Response ping() {
        return Response.ok("{\"ok\":true}").type(MediaType.APPLICATION_JSON).build();
    }

    // LONGITUD
    @GET @Path("/kilometros-a-millas")
    public Response kmToMi(@QueryParam("km") double km) {
        return Response.ok(ctrl.kilometrosAMillas(km)).build();
    }

    @GET @Path("/metros-a-pies")
    public Response mToFt(@QueryParam("m") double m) {
        return Response.ok(ctrl.metrosAPies(m)).build();
    }

    @GET @Path("/pulgadas-a-centimetros")
    public Response inToCm(@QueryParam("p") double p) {
        return Response.ok(ctrl.pulgadasACentimetros(p)).build();
    }

    // MASA
    @GET @Path("/kilogramos-a-libras")
    public Response kgToLb(@QueryParam("kg") double kg) {
        return Response.ok(ctrl.kilogramosALibras(kg)).build();
    }

    @GET @Path("/gramos-a-onzas")
    public Response gToOz(@QueryParam("g") double g) {
        return Response.ok(ctrl.gramosAOnzas(g)).build();
    }

    @GET @Path("/libras-a-kilogramos")
    public Response lbToKg(@QueryParam("lb") double lb) {
        return Response.ok(ctrl.librasAKilogramos(lb)).build();
    }

    // TEMPERATURA
    @GET @Path("/celsius-a-fahrenheit")
    public Response cToF(@QueryParam("c") double c) {
        return Response.ok(ctrl.celsiusAFahrenheit(c)).build();
    }

    @GET @Path("/fahrenheit-a-celsius")
    public Response fToC(@QueryParam("f") double f) {
        return Response.ok(ctrl.fahrenheitACelsius(f)).build();
    }

    @GET @Path("/celsius-a-kelvin")
    public Response cToK(@QueryParam("c") double c) {
        return Response.ok(ctrl.celsiusAKelvin(c)).build();
    }
}
