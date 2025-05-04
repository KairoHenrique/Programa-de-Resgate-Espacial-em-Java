import java.util.*;

public class RoboDeResgate {
    private EstacaoEspacial estacao;
    private int[][] direcoes = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    private List<Astronauta> astronautasResgatados;
    private int passos;
    private List<String> caminhos;
    private int[] posicaoAtualRobo;

    public RoboDeResgate(EstacaoEspacial estacao) {
        this.estacao = estacao;
        this.astronautasResgatados = new ArrayList<>();
        this.passos = 0;
        this.caminhos = new ArrayList<>();
        this.posicaoAtualRobo = encontrarModuloSeguranca();
        
        if(posicaoAtualRobo == null) {
            throw new RuntimeException("Modulo de seguranca nao encontrado!");
        }
        estacao.setPosicaoRobo(posicaoAtualRobo);
    }

    public void iniciarResgate() {
        List<Astronauta> astronautas = new ArrayList<>(estacao.getAstronautas());
        astronautas.sort((a1, a2) -> {
            if(a1.isUrgente() != a2.isUrgente()) return a2.isUrgente() ? 1 : -1;
            return Integer.compare(a1.getSaude(), a2.getSaude());
        });

        System.out.println("\nPrioridade de Resgate:");
        astronautas.forEach(a -> 
            System.out.println("- " + a.getNome() + 
                            " (Urgente: " + (a.isUrgente() ? "Sim" : "Nao") + 
                            ", Saude: " + a.getSaude() + ")"));

        for(Astronauta astronauta : astronautas) {
            int[] posAstronauta = encontrarAstronauta(astronauta);
            if(posAstronauta != null) {
                if(bfsResgatar(posAstronauta, astronauta)) {
                    astronautasResgatados.add(astronauta);
                    estacao.removerAstronauta(posAstronauta[0], posAstronauta[1]);
                    bfsVoltarParaBase();
                }
            }
        }
    }

    private boolean bfsResgatar(int[] destino, Astronauta astronauta) {
        Queue<int[]> fila = new LinkedList<>();
        boolean[][] visitado = new boolean[estacao.getN()][estacao.getM()];
        Map<String, int[]> predecessor = new HashMap<>();

        fila.add(posicaoAtualRobo);
        visitado[posicaoAtualRobo[0]][posicaoAtualRobo[1]] = true;
        boolean destinoAlcancado = false;

        while(!fila.isEmpty() && !destinoAlcancado) {
            int[] atual = fila.poll();

            for(int[] dir : direcoes) {
                int x = atual[0] + dir[0];
                int y = atual[1] + dir[1];

                if(x >= 0 && x < estacao.getN() && y >= 0 && y < estacao.getM()) {
                    Modulo modulo = estacao.getModulos()[x][y];
                    if(modulo.isTransitavel() && !visitado[x][y]) {
                        visitado[x][y] = true;
                        predecessor.put(x + "," + y, atual);
                        fila.add(new int[]{x,y});

                        if(x == destino[0] && y == destino[1]) {
                            destinoAlcancado = true;
                            break;
                        }
                    }
                }
            }
        }

        if(destinoAlcancado) {
            reconstruirCaminhoResgate(predecessor, destino, astronauta);
            return true;
        } else {
            String msg = "Nao foi possivel resgatar " + astronauta.getNome();
            System.out.println(msg);
            caminhos.add(msg);
            return false;
        }
    }

    private void reconstruirCaminhoResgate(Map<String, int[]> predecessor, int[] destino, Astronauta astronauta) {
        LinkedList<int[]> caminho = new LinkedList<>();
        int[] atual = destino;

        while(atual != null) {
            caminho.addFirst(atual);
            atual = predecessor.get(atual[0] + "," + atual[1]);
        }

        if(caminho.size() <= 1) {
            String msg = "Astronauta " + astronauta.getNome() + " nao pode ser resgatado!";
            System.out.println(msg);
            caminhos.add(msg);
            return;
        }

        for(int[] pos : caminho) {
            if(Arrays.equals(pos, posicaoAtualRobo)) continue;

            posicaoAtualRobo = pos.clone();
            estacao.setPosicaoRobo(posicaoAtualRobo);
            passos++;

            String estado = estacao.estacaoToString();
            caminhos.add(estado);
            caminhos.add("Numero de Passos: " + passos);
            
            System.out.println(estado);
            System.out.println("Numero de Passos: " + passos);
        }

        String info = "Resgatado: " + astronauta.getNome() + 
                    " (Saude: " + astronauta.getSaude() + 
                    ", Urgente: " + (astronauta.isUrgente() ? "Sim" : "Nao") + ")";
        caminhos.add(info);
        System.out.println(info);
    }

    private void bfsVoltarParaBase() {
        int[] base = encontrarModuloSeguranca();
        if(base == null) return;

        Queue<int[]> fila = new LinkedList<>();
        boolean[][] visitado = new boolean[estacao.getN()][estacao.getM()];
        Map<String, int[]> predecessor = new HashMap<>();

        fila.add(posicaoAtualRobo);
        visitado[posicaoAtualRobo[0]][posicaoAtualRobo[1]] = true;
        boolean destinoAlcancado = false;

        while(!fila.isEmpty() && !destinoAlcancado) {
            int[] atual = fila.poll();

            for(int[] dir : direcoes) {
                int x = atual[0] + dir[0];
                int y = atual[1] + dir[1];

                if(x >= 0 && x < estacao.getN() && y >= 0 && y < estacao.getM()) {
                    Modulo modulo = estacao.getModulos()[x][y];
                    if(modulo.isTransitavel() && !visitado[x][y]) {
                        visitado[x][y] = true;
                        predecessor.put(x + "," + y, atual);
                        fila.add(new int[]{x,y});

                        if(x == base[0] && y == base[1]) {
                            destinoAlcancado = true;
                            break;
                        }
                    }
                }
            }
        }

        if(destinoAlcancado) {
            reconstruirCaminhoRetorno(predecessor, base);
        }
    }

    private void reconstruirCaminhoRetorno(Map<String, int[]> predecessor, int[] destino) {
        LinkedList<int[]> caminho = new LinkedList<>();
        int[] atual = destino;

        while(atual != null) {
            caminho.addFirst(atual);
            atual = predecessor.get(atual[0] + "," + atual[1]);
        }

        for(int[] pos : caminho) {
            if(Arrays.equals(pos, posicaoAtualRobo)) continue;

            posicaoAtualRobo = pos.clone();
            estacao.setPosicaoRobo(posicaoAtualRobo);
            passos++;

            String estado = estacao.estacaoToString();
            caminhos.add(estado);
            caminhos.add("Numero de Passos: " + passos);
            
            System.out.println(estado);
            System.out.println("Numero de Passos: " + passos);
        }
    }

    private int[] encontrarModuloSeguranca() {
        for(int i = 0; i < estacao.getN(); i++) {
            for(int j = 0; j < estacao.getM(); j++) {
                if(estacao.getModulos()[i][j].isSeguranca()) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    private int[] encontrarAstronauta(Astronauta astronauta) {
        for(int i = 0; i < estacao.getN(); i++) {
            for(int j = 0; j < estacao.getM(); j++) {
                if(estacao.getModulos()[i][j].isAstronauta()) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public List<String> getCaminhos() { return caminhos; }
    public int getPassos() { return passos; }
    public List<Astronauta> getAstronautasResgatados() { return astronautasResgatados; }
    public EstacaoEspacial getEstacao() { return estacao; }
}