import java.util.ArrayList;
import java.util.List;

public class EstacaoEspacial {
    private int N, M;
    private Modulo[][] modulos;
    private List<Astronauta> astronautas;
    private int[] posicaoRobo;

    public EstacaoEspacial(int N, int M) {
        this.N = N;
        this.M = M;
        this.modulos = new Modulo[N][M];
        this.astronautas = new ArrayList<>();
        this.posicaoRobo = null;
    }

    public void preencherLinha(int linha, String row) {
        for(int coluna = 0; coluna < M; coluna++) {
            char tipo = (coluna < row.length()) ? row.charAt(coluna) : '.';
            
            switch(tipo) {
                case 'S': modulos[linha][coluna] = new ModuloSeguranca(); break;
                case 'F': modulos[linha][coluna] = new ModuloComFogo(); break;
                case 'A': modulos[linha][coluna] = new Modulo('A'); break;
                case '#': modulos[linha][coluna] = new Modulo('#'); break;
                case '~': modulos[linha][coluna] = new Modulo('~'); break;
                default: modulos[linha][coluna] = new Modulo('.'); break;
            }
        }
    }

    public void removerAstronauta(int x, int y) {
        if(x >= 0 && x < N && y >= 0 && y < M) {
            if(modulos[x][y].isAstronauta()) {
                modulos[x][y] = new Modulo('.');
            }
        }
    }

    public String estacaoToString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(posicaoRobo != null && i == posicaoRobo[0] && j == posicaoRobo[1]) {
                    sb.append('R');
                } else {
                    sb.append(modulos[i][j].getTipo());
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void setPosicaoRobo(int[] pos) { posicaoRobo = pos; }
    public Modulo[][] getModulos() { return modulos; }
    public List<Astronauta> getAstronautas() { return astronautas; }
    public void adicionarAstronauta(Astronauta astronauta) { astronautas.add(astronauta); }
    public int getN() { return N; }
    public int getM() { return M; }
}