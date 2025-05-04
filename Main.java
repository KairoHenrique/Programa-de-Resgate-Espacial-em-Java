import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        for(int i = 1; i <= 10; i++) {
            String entrada = "entrada" + i + ".txt";
            String saida = "saida" + i + ".txt";

            try {
                File file = new File(entrada);
                Scanner scanner = new Scanner(file);
                
                System.out.println("\n=== PROCESSANDO: " + entrada + " ===");

                int N = scanner.nextInt();
                int M = scanner.nextInt();
                scanner.nextLine();

                EstacaoEspacial estacao = new EstacaoEspacial(N, M);
                for(int linha = 0; linha < N; linha++) {
                    String row = scanner.nextLine();
                    estacao.preencherLinha(linha, row);
                }

                System.out.println("\nConfiguracao Inicial:");
                System.out.println(estacao.estacaoToString());

                List<Astronauta> todosAstronautas = new ArrayList<>();
                while(scanner.hasNextLine() && !scanner.nextLine().equals("Astronautas:")) {}
                
                while(scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    if(linha.startsWith("Posicoes")) break;
                    if(linha.isBlank()) continue;

                    String[] dados = linha.split(",");
                    if(dados.length == 3) {
                        String nome = dados[0].trim();
                        int saude = Integer.parseInt(dados[1].trim());
                        boolean urgente = dados[2].trim().equals("1");
                        Astronauta astronauta = new Astronauta(nome, saude, urgente);
                        estacao.adicionarAstronauta(astronauta);
                        todosAstronautas.add(astronauta);
                    }
                }

                RoboDeResgate robo = new RoboDeResgate(estacao);
                robo.iniciarResgate();

                Relatorio relatorio = new Relatorio();
                relatorio.gerarRelatorio(saida, robo);

                System.out.println("\n=== RESUMO ===");
                System.out.println("Passos: " + robo.getPassos());
                
                System.out.println("\nResgatados:");
                robo.getAstronautasResgatados().forEach(a -> 
                    System.out.println("- " + a.getNome()));
                
                System.out.println("\nNao Resgatados:");
                todosAstronautas.removeAll(robo.getAstronautasResgatados());
                todosAstronautas.forEach(a -> 
                    System.out.println("- " + a.getNome()));

                scanner.close();


            } catch(FileNotFoundException e) {
                System.out.println("Arquivo nao encontrado: " + entrada);
            }
        }
    }
}