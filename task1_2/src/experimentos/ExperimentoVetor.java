package src.experimentos;

import src.gerador.GeradorDados;
import src.utils.Logger;
import src.vetor.ProdutoEscalarPar;
import src.vetor.ProdutoEscalarSeq;

public class ExperimentoVetor {

    public static void run(int numThreads, int repeticoes) {

        int tamanhoVetor = 10_000_000;
        int[] a = GeradorDados.vetorAleatorio(tamanhoVetor);
        int[] b = GeradorDados.vetorAleatorio(tamanhoVetor);

        Logger log = new Logger("VETOR", "log_vetor.txt", numThreads);

        try {
            // sequencial - execução única
            log.iniciar();
            ProdutoEscalarSeq.calcula(a, b);
            log.finalizar("Produto escalar sequencial");
            log.registrarTempoSequencial();

            // paralelo - N execuções armazenadas internamente
            for (int i = 0; i < repeticoes; i++) {
                log.iniciar();
                ProdutoEscalarPar.calcula(a, b, numThreads);
                log.finalizar("Produto escalar paralelo");
                log.registrarTempoParcialParalelo();
            }

            log.fecharRegistroParalelo();

            // gera relatorio final
            log.relatorio(tamanhoVetor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
