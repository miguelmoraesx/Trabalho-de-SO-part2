package src.matriz;

public class ProdutoMatrizSeq {

    /**
     * Calcula uma multiplicação de matrizes entre duas matrizes
     *
     * @param a Uma matriz A de inteiros
     * @param b Uma matriz B de inteiros
     * @return Uma matriz C resultante do calculo de A e B
     *
     */
    public static int[][] calcula(int[][] a, int[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("Dimensões incompatíveis: \n" +
                    "A é " +a.length +" x " +a[0].length +" e B é " +
                    b.length + " x " +b[0].length);
        }

        int[][] c = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                int soma = 0;
                for (int k = 0; k < a[0].length; k++) {
                    soma += a[i][k] * b[k][j];
                }
                c[i][j] = soma;
            }
        }

        return c;
    }
}
