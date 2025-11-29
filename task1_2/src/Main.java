package src;

import src.gerador.GeradorDados;
import src.vetor.ProdutoEscalarPar;
import src.vetor.ProdutoEscalarSeq;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int tamanhoVetor = 10_000_000;
        //System.out.print("Digite a quantidade de Threads: ");
        //int numThreads = s.nextInt();
        int numThreads = Runtime.getRuntime().availableProcessors();

        int[] a = GeradorDados.vetorAleatorio(tamanhoVetor);
        int[] b = GeradorDados.vetorAleatorio(tamanhoVetor);

        try {
            // sequencial
            long inicioSeq = System.nanoTime();
            long resultadoSeq = ProdutoEscalarSeq.calcula(a, b);
            long tempoSeq = System.nanoTime() - inicioSeq;

            // paralelo
            long inicioPar = System.nanoTime();
            long resultadoPar = ProdutoEscalarPar.calcula(a, b, numThreads);
            long tempoPar = System.nanoTime() - inicioPar;

            System.out.println("=== Produto Escalar ===");
            System.out.println("Tamanho: " + tamanhoVetor);
            System.out.println("Threads: " + numThreads);
            System.out.println("Resultado (seq): " + resultadoSeq);
            System.out.println("Resultado (par): " + resultadoPar);
            System.out.println("Tempo seq: " + tempoSeq / 1_000_000.0 + " ms");
            System.out.println("Tempo par: " + tempoPar / 1_000_000.0 + " ms");
            double speedup = (double) tempoSeq / tempoPar;
            System.out.println("Speedup: " + speedup);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
