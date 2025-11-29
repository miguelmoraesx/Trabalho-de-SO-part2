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
     * Gera um vetor inteiro com dados aleatórios, com valor minimo e máximo do intervalo
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

}
