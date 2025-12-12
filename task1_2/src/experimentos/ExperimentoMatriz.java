package src.experimentos;

import src.gerador.GeradorDados;
import src.utils.Logger;
import src.matriz.ProdutoMatrizSeq;
import src.matriz.ProdutoMatrizPar;

public class ExperimentoMatriz {

    public static void run(int numThreads, int repeticoes) {

        int n = 2_000;          // tamanho da matriz NxN
        int tamBloco = 16;    // bloco usado no paralelo
        int[][] A = GeradorDados.matrizAleatoria(n, n);
        int[][] B = GeradorDados.matrizAleatoria(n, n);

        Logger log = new Logger("MATRIZ", "log_matriz.txt", numThreads);

        try {
            // --- Sequencial (1 execução)
            log.iniciar();
            ProdutoMatrizSeq.calcula(A, B);
            log.finalizar("Produto matricial sequencial");
            log.registrarTempoSequencial();

            // --- Paralelo (N execuções acumuladas)
            for (int i = 0; i < repeticoes; i++) {
                log.iniciar();
                ProdutoMatrizPar.calcula(A, B, numThreads, tamBloco);
                log.finalizar("Produto matricial paralelo");
                log.registrarTempoParcialParalelo();
            }

            // calcula média
            log.fecharRegistroParalelo();

            // relatório final
            log.relatorio(n, n);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
