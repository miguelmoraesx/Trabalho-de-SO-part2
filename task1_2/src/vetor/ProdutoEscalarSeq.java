package src.vetor;

public class ProdutoEscalarSeq {

    /**
     * Calcula um produto escalar entre dois vetores
     *
     * @param a Um vetor A de inteiros
     * @param b Um vetor B de inteiros
     * @return O resultado do calculo do produto escalar de A e B
     *
     */
    public static long calcula(int[] a, int[] b) {
        long soma = 0;
        for (int i = 0; i < a.length; i++) {
            soma += (long) a[i] * b[i];
        }
        return soma;
    }
}
