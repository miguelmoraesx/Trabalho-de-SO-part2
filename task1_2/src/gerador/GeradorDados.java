package src.gerador;

import java.util.Random;

/**
 * Classe responsável por gerar dados para vetores e matrizes
 *
 */
public class GeradorDados {
    private static final Random RANDOM = new Random();

    /**
     * Gera um vetor inteiro com dados aleatórios
     *
     * @param tam O tamanho do vetor
     * @return O vetor gerado com dados aleatórios
     *
     */
    public static int[] vetorAleatorio(int tam) {
        int[] vetor = new int[tam];

        for (int i = 0; i < tam; i++) {
            vetor[i] = RANDOM.nextInt();
        }
        return vetor;
    }

    /**
     * Gera um vetor inteiro com dados aleatórios, com valor minimo e máximo de um intervalo
     *
     * @param tam O tamanho do vetor
     * @param min O valor minimo gerado
     * @param max O valor máximo gerado
     * @return O vetor gerado com dados aleatórios no intervalo dado
     *
     */
    public static int[] vetorAleatorio(int tam, int min, int max) {
        int[] vetor = new int[tam];

        for (int i = 0; i < tam; i++) {
            vetor[i] = RANDOM.nextInt(max - min + 1) + min;
        }
        return vetor;
    }

    /**
     * Gera uma matriz inteiro com dados aleatórios
     * @param tamX O tamanho das linhas da matriz
     * @param tamY O tamanho das colunas da matriz
     * @return A matriz gerada com dados aleatórios
     */
    public static int[][] matrizAleatoria(int tamX, int tamY) {
        int[][] matriz = new int[tamX][tamY];
        for (int i = 0; i < tamX; i++) {
            for (int j = 0; j < tamY; j++) {
                matriz[i][j] = RANDOM.nextInt();
            }
        }
        return matriz;
    }

    /**
     * Gera uma matriz inteiro com dados aleatórios, com valores mínimos e máximos de um intervalo
     * @param tamX O tamanho das linhas da matriz
     * @param tamY O tamanho das colunas da matriz
     * @param min O valor mínimo gerado
     * @param max O valor máximo gerado
     * @return A matriz gerada com dados aleatórios, com intervalo dado
     */
    public static int[][] matrizAleatoria(int tamX, int tamY, int min, int max) {
        int[][] matriz = new int[tamX][tamY];
        for (int i = 0; i < tamX; i++) {
            for (int j = 0; j < tamY; j++) {
                matriz[i][j] = RANDOM.nextInt(max - min + 1) + min;
            }
        }
        return matriz;
    }
}
