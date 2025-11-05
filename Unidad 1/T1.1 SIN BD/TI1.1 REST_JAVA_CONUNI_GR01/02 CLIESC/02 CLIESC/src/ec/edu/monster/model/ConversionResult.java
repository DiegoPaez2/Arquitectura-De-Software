package ec.edu.monster.model;

public class ConversionResult {
    private final String tipo;
    private final double entrada;
    private final double salida;

    public ConversionResult(String tipo, double entrada, double salida) {
        this.tipo = tipo;
        this.entrada = entrada;
        this.salida = salida;
    }

    public String getTipo() { return tipo; }
    public double getEntrada() { return entrada; }
    public double getSalida() { return salida; }

    @Override
    public String toString() {
        return String.format("%s: %.6f → %.6f", tipo, entrada, salida);
    }
}
