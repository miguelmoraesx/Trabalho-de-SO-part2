package src;

import src.gerador.GeradorDados;
import src.matriz.MultiplicaPar;
import src.matriz.MultiplicaSeq;
import src.vetor.ProdutoEscalarPar;
import src.vetor.ProdutoEscalarSeq;

import java.io.IOException;
import java.util.Scanner;
import src.utils.Logger;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        //System.out.print("Digite a quantidade de Threads: ");
        //int numThreads = s.nextInt();
        int numThreads = Runtime.getRuntime().availableProcessors();
        Logger log = new Logger("VETOR", "log.txt", numThreads);
        int tamanhoVetor = 10_000_000;
        int tamanhoMatriz = 1_000;
        
        int[] a = GeradorDados.vetorAleatorio(tamanhoVetor);
        int[] b = GeradorDados.vetorAleatorio(tamanhoVetor);

        try {
            // sequencial
            log.iniciar();
            ProdutoEscalarSeq.calcula(a, b);
            log.finalizar("Produto escalar sequencial");
            log.registrarTempoSequencial();
            // paralelo
            log.iniciar();
            ProdutoEscalarPar.calcula(a, b, numThreads);
            log.finalizar("Produto escalar paralelo");
            log.registrarTempoParalelo();
            
            log.relatorio(tamanhoVetor);

            //matrizes
            int[][] d = GeradorDados.matrizAleatoria(tamanhoMatriz, tamanhoMatriz);
            int[][] e = GeradorDados.matrizAleatoria(tamanhoMatriz, tamanhoMatriz);

            log.setTag("MATRIZ");
            // sequencial
            log.iniciar();
            int[][] resultadoMatrizSeq = MultiplicaSeq.calcula(d, e);
            log.finalizar("Multiplicação de matrizes sequencial");
            log.registrarTempoSequencial();

            //paralelo
            log.iniciar();
            int[][] resultadoMatrizPar = MultiplicaPar.calcula(d, e, numThreads, 16);
            log.finalizar("Multiplicação de matrizes paralelo");
            log.registrarTempoParalelo();

            log.relatorio(tamanhoMatriz, tamanhoMatriz);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
