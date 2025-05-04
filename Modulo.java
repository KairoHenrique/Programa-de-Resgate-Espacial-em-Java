public class Modulo {
    private char tipo;

    public Modulo(char tipo) {
        this.tipo = tipo;
    }

    public char getTipo() {
        return tipo;
    }

    public boolean isTransitavel() {
        return tipo == '.' || tipo == 'A' || tipo == 'S';
    }

    public boolean isAstronauta() {
        return tipo == 'A';
    }

    public boolean isSeguranca() {
        return tipo == 'S';
    }

    public boolean isFogo() {
        return tipo == 'F';
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
}