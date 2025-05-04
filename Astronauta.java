public class Astronauta {
    private String nome;
    private int saude;
    private boolean urgente;

    public Astronauta(String nome, int saude, boolean urgente) {
        this.nome = nome;
        this.saude = saude;
        this.urgente = urgente;
    }

    public String getNome() {
        return nome;
    }

    public int getSaude() {
        return saude;
    }

    public boolean isUrgente() {
        return urgente;
    }
}