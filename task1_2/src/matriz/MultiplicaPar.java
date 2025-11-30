package src.matriz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiplicaPar {

    private static class Tarefa implements Runnable {
        private final int[][] a;
        private final int[][] b;
        private final int[][] c;
        private final int inicioLinha;
        private final int fimLinha;
        private final int inicioColuna;
        private final int fimColuna;
        public Tarefa(int[][] a, int[][] b, int[][] c,
                      int inicioLinha, int fimLinha,
                      int inicioColuna, int fimColuna) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.inicioLinha = inicioLinha;
            this.fimLinha = fimLinha;
            this.inicioColuna = inicioColuna;
            this.fimColuna = fimColuna;
        }
        @Override
        public void run() {
            for (int i = inicioLinha; i < fimLinha; i++) {
                for (int j = inicioColuna; j < fimColuna; j++) {
                    int soma = 0;
                    for (int k = 0; k < a[0].length; k++) {
                        soma += a[i][k] * b[k][j];
                    }
                    c[i][j] = soma;
                }
            }
        }
    }

    /**
     * Multiplicação paralela de matrizes baseado no CUDA
     * Mais detalhes em <a href="https://kharshit.github.io/blog/2024/06/07/matrix-multiplication-cuda">este blog</a>,
     * há também a <a href="https://docs.nvidia.com/cuda/cuda-c-programming-guide/">documentação oficial da NVIDIA CUDA</a> e
     * baseado junto com o repositório com <a href="https://github.com/NVIDIA/cuda-samples">exemplos de CUDA</a>
     *
     * O uso de blocos simula a estratégia de CUDA onde cada bloco calcula uma submatriz de C.
     * Em GPUs isso reduz acessos na memória global e melhora o paralelismo, aqui a ideia é parecida: cada {@link Tarefa}
     * é responsável por calcular um bloco da matriz resultado C. Isso permite dividir o trabalho em pedaços menores
     * e distribuir entre as threads.
     * Esse modelo de blocos facilita o paralelismo e imita a estratégia de GPUs,
     * só que adaptado para CPU usando um pool fixo de threads.
     *
     * @param a Matriz A
     * @param b Matriz B
     * @param tamBloco Tamanho do bloco quadrado (ex: 16 = 16x16)
     * @return Matriz resultado C
     * @throws InterruptedException Se a espera pela conclusão das threads for interrompida.
     * @throws IllegalArgumentException Se as dimensões de A e B não forem compatíveis (colunas de A != linhas de B).
     */
    public static int[][] calcula(int[][] a, int[][] b, int numThreads, int tamBloco) throws InterruptedException {
        int M = a.length, K = a[0].length, N = b[0].length;

        if(K != b.length)
            throw new IllegalArgumentException("A quantidade de colunas de A deve ser igual as linhas de B");

        int[][] c = new  int[M][N];
        int gridLinhas = (int) Math.ceil((double) M / tamBloco);
        int gridColunas = (int) Math.ceil((double) N / tamBloco);

        ExecutorService pool = Executors.newFixedThreadPool(numThreads);
        List<Runnable> tarefas = new ArrayList<Runnable>();

        for (int by = 0; by < gridLinhas; by++) {
            for(int bx = 0; bx < gridColunas; bx++) {
                int inicioLinha = by * tamBloco;
                int fimLinha = Math.min(inicioLinha + tamBloco, M);
                int inicioColuna = bx * tamBloco;
                int fimColuna = Math.min(inicioColuna + tamBloco, N);
                tarefas.add(new Tarefa(a, b, c, inicioLinha, fimLinha, inicioColuna, fimColuna));

            }
        }

        for(Runnable tarefa : tarefas)
            pool.execute(tarefa);
        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return c;
    }
}
