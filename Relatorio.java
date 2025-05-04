import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Relatorio {
    public void gerarRelatorio(String arquivoSaida, RoboDeResgate robo) {
        try(FileWriter writer = new FileWriter(arquivoSaida)) {
            writer.write("-----------------------------\n");
            writer.write("|Todos os Caminhos Possiveis|\n");
            writer.write("-----------------------------\n");

            for(String passo : robo.getCaminhos()) {
                writer.write(passo + "\n");
            }

            writer.write("\nRESUMO FINAL:\n");
            writer.write("Total de Passos: " + robo.getPassos() + "\n");
            
            writer.write("\nAstronautas Resgatados:\n");
            for(Astronauta a : robo.getAstronautasResgatados()) {
                writer.write("- " + a.getNome() + 
                          " (Saude: " + a.getSaude() + 
                          ", Urgente: " + (a.isUrgente() ? "Sim" : "Nao") + ")\n");
            }

            writer.write("\nAstronautas Nao Resgatados:\n");
            List<Astronauta> naoResgatados = new ArrayList<>(robo.getEstacao().getAstronautas());
            naoResgatados.removeAll(robo.getAstronautasResgatados());
            
            if(naoResgatados.isEmpty()) {
                writer.write("Todos os astronautas foram resgatados!\n");
            } else {
                for(Astronauta a : naoResgatados) {
                    writer.write("- " + a.getNome() + " (Motivo: Inacessivel)\n");
                }
            }

        } catch(IOException e) {
            System.err.println("Erro ao gerar relatorio: " + e.getMessage());
        }
    }
}