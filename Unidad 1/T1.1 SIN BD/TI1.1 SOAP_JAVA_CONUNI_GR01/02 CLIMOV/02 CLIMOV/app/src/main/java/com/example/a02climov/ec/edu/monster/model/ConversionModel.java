package ec.edu.monster.model;

public class ConversionModel {
    private final double valorEntrada;
    private final String tipoConversion;
    private double valorSalida;

    public ConversionModel(double valorEntrada, String tipoConversion) {
        this.valorEntrada = valorEntrada;
        this.tipoConversion = tipoConversion;
    }

    public double getValorEntrada() { return valorEntrada; }
    public String getTipoConversion() { return tipoConversion; }
    public double getValorSalida() { return valorSalida; }
    public void setValorSalida(double valorSalida) { this.valorSalida = valorSalida; }
}

