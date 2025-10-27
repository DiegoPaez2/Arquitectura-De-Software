package ec.edu.monster.model;

public class ConversorModel {
    private String tipo;
    private double valor;
    private double resultado;

    public ConversorModel() {}
    public ConversorModel(String tipo, double valor) {
        this.tipo = tipo; this.valor = valor;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public double getResultado() { return resultado; }
    public void setResultado(double resultado) { this.resultado = resultado; }
}
