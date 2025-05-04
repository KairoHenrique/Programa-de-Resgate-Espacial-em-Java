# 🚀 Programa de Resgate Espacial 🚀

- **Aluno:** Kairo Henrique Ferreira Martins  
- **Professor:** Eduardo Habib Bechelane Maia  
- **Disciplina:** Programação Orientada a Objetos  

---

## Introdução

Este projeto implementa um simulador de resgate em uma estação espacial representada por uma matriz de módulos. Um robô de resgate deve navegar pelos corredores, evitar fogo e obstáculos, localizar e resgatar astronautas com diferentes níveis de saúde e prioridade de urgência, e registrar todo o processo em relatórios de saída.

---

## Objetivos

- Modelar o ambiente da estação espacial com módulos de diferentes tipos (corredor, obstáculo, segurança, fogo, astronauta).  
- Carregar cenários a partir de arquivos de entrada (`entradaX.txt`) e gerar relatórios em arquivos de saída (`saidaX.txt`).  
- Simular a busca de trajetórias e o resgate de astronautas, priorizando casos urgentes e otimizando o número de passos.  
- Produzir logs e estatísticas sobre passos percorridos, astronautas resgatados e não resgatados.

---

## Funcionalidades Principais

1. **Representação da Estação (`EstacaoEspacial`)**  
   - Matriz de módulos de dimensão N×M.  
   - Cada célula pode ser:
     - `.` Corredor livre  
     - `#` Obstáculo  
     - `~` Área de segurança  
     - `F` Módulo em chamas  
     - `A` Astronauta (removido após resgate)  

2. **Leitura de Cenários**  
   - Arquivos `entradaX.txt` contendo:
     1. Duas inteiros N (linhas) e M (colunas).  
     2. N linhas de M caracteres cada, definindo o layout da estação.  
     3. Seção `Astronautas:` com linhas no formato `Nome,Saúde,Urgente` (1=sim, 0=não).  

3. **Robô de Resgate (`RoboDeResgate`)**  
   - Seleciona o próximo alvo por prioridade: primeiro por `urgente`, em empate por maior `saúde`.  
   - Calcula caminho mínimo usando BFS, evitando módulos `F` e `#`.  
   - Move-se passo a passo, registra cada posição e conta o total de passos.  
   - Após cada resgate, retorna à base antes de buscar o próximo.

4. **Geração de Relatórios (`Relatorio`)**  
   - Cria arquivos `saidaX.txt` contendo:  
     - **Todos os Caminhos Possíveis:** visualização do mapa com a trajetória do robô em cada passo.  
     - **Número de Passos** até cada resgate.  
     - **Resumo Final:** lista de astronautas resgatados (nome, saúde, urgência) e não resgatados.

---

## Diagrama de Classes

<details>
  <summary>Clique para ver o Diagrama de Classes</summary>

  ![Diagrama de Classes](diagrama%20de%20classe.png)
</details>

---

## Metodologia de Execução

Para cada cenário `entradaX.txt` (X = 1…10):

1. **Inicialização**  
   - `Main` lê N e M, instancia `EstacaoEspacial` e preenche a matriz.  
   - Cria instâncias de `Astronauta` conforme a seção `Astronautas:`.

2. **Simulação de Resgate**  
   - `RoboDeResgate.iniciarResgate()`:
     1. Enquanto existirem astronautas não resgatados:
        - Seleciona próximo alvo.
        - Executa BFS até o alvo.
        - Move-se, registrando posição e incrementando passos.
        - Marca astronauta como resgatado.
        - Retorna à base.

3. **Geração de Relatório**  
   - `Relatorio.gerar(saidaX.txt, robo)` escreve:
     - Seção **Todos os Caminhos Possíveis** com mapas de cada passo.
     - **Número de Passos** por resgate.
     - Linhas `Resgatado: Nome (Saúde: S, Urgente: Sim/Não)`.

---

## Estrutura do Projeto

├── Astronauta.java
├── EstacaoEspacial.java
├── Modulo.java
├── ModuloComFogo.java
├── ModuloSeguranca.java
├── RoboDeResgate.java
├── Relatorio.java
├── Main.java
├── diagrama de classe.png
├── entrada1.txt … entrada10.txt
└── saida1.txt … saida10.txt


---

## Exemplo de Caso de Teste

### `entrada1.txt`

10 10
S.........
.###..#..A
.~~~..F..A
... (restante do cenário)
Astronautas:
Yuri Gagarin,85,0
Neil Armstrong,60,1
...


### Trecho de `saida1.txt`
<details>
  <summary>🔍 Clique para ver um trecho</summary>

----------------------------- |Todos os Caminhos Possíveis| -----------------------------
S.R........
.###..#..A
...
Número de Passos: 1

SR........
.###..#..A
...
Resgatado: Neil Armstrong (Saúde: 60, Urgente: Sim)
...

</details>

---

## Conclusão

- Validação de conceitos de POO em Java: herança, encapsulamento e modularização.  
- Uso de BFS para navegação em grade.  
- Geração de relatórios detalhados para análise de desempenho.

### Possíveis Melhorias

- Implementar heurística A* para trajetórias mais eficientes.  
- Paralelizar o processamento de múltiplos cenários.  
- Adicionar interface gráfica para visualização em tempo real.
- O código dá muitas voltas
- O código não impede corretamente que o robô entre em áreas inseguras, o robô deveria evitar células que estejam a até duas casas do fogo, não apenas uma.
- Falta de validações de consistência no arquivo.
---

## Compilação e Execução

**Requisitos:** Java 8 ou superior

```bash
# Compilar
javac *.java

# Executar
java Main

Os relatórios serão gerados em saida1.txt … saida10.txt.
Autor

    Kairo Henrique Ferreira Martins