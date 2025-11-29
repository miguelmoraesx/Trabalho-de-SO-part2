package src.vetor;

public class ProdutoEscalarPar {

    private static class Tarefa implements Runnable {
        private final int[] a;
        private final int[] b;
        private final int inicio;
        private final int fim;
        private long somaParcial;

        /**
         * Construtor da classe interna de Thread
         *
         */
        Tarefa(int[] a, int[] b, int inicio, int fim) {
            this.a = a;
            this.b = b;
            this.inicio = inicio;
            this.fim = fim;
        }

        /**
         * Cada thread executa este trecho de código ao ser ativo,
         * pega um intervalo do vetor e executa uma soma parcial dentro
         * daquele intervalo
         *
         */
        @Override
        public void run() {
            long soma = 0;
            for (int i = inicio; i < fim; i++) {
                soma += (long) a[i] * b[i];
            }
            somaParcial = soma;
        }

        public long getSomaParcial() {
            return somaParcial;
        }
    }

    /**
     * Calcula um produto escalar entre dois vetores usando paralelismo
     *
     * @param a          Um vetor A de inteiros
     * @param b          Um vetor B de inteiros
     * @param numThreads A quantitade de threads a ser usada
     * @return O resultado do calculo do produto escalar de A e B
     *
     */
    public static long calcula(int[] a, int[] b, int numThreads) throws InterruptedException {
        int tamanho = a.length;
        Thread[] threads = new Thread[numThreads];
        Tarefa[] tarefas = new Tarefa[numThreads];

        int bloco = tamanho / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int inicio = i * bloco;
            int fim = (i == numThreads - 1) ? tamanho : inicio + bloco;

            tarefas[i] = new Tarefa(a, b, inicio, fim);
            threads[i] = new Thread(tarefas[i]);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long total = 0;
        for (Tarefa tarefa : tarefas) {
            total += tarefa.getSomaParcial();
        }
        return total;
    }
}
