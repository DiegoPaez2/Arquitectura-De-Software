package ec.edu.monster.model;

public class ConversionResult {
    private String tipo;
    private double entrada;
    private double salida;

    public ConversionResult(String tipo, double entrada, double salida) {
        this.tipo = tipo;
        this.entrada = entrada;
        this.salida = salida;
    }

    @Override
    public String toString() {
        return String.format("Conversión: %s\nValor de entrada: %.3f\nResultado: %.3f\n",
                tipo, entrada, salida);
    }
}

