package ec.edu.monster.model;

public class ConversionModel {
    private double valorEntrada;
    private double valorSalida;
    private String tipoConversion;

    public ConversionModel(double valorEntrada, String tipoConversion) {
        this.valorEntrada = valorEntrada;
        this.tipoConversion = tipoConversion;
    }

    public double getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(double valorEntrada) { this.valorEntrada = valorEntrada; }

    public double getValorSalida() { return valorSalida; }
    public void setValorSalida(double valorSalida) { this.valorSalida = valorSalida; }

    public String getTipoConversion() { return tipoConversion; }
    public void setTipoConversion(String tipoConversion) { this.tipoConversion = tipoConversion; }
}
