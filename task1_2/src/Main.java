package src;

import src.experimentos.ExperimentoMatriz;
import src.experimentos.ExperimentoVetor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Escolha o experimento:");
        System.out.println("1 - Produto Escalar");
        System.out.println("2 - Multiplicação de Matrizes");
        int escolha = s.nextInt();

        System.out.print("Digite a quantidade de threads: ");
        int numThreads = s.nextInt();

        int repeticoes = 3;

        switch (escolha) {
            case 1: ExperimentoVetor.run(numThreads, repeticoes); break;
            case 2: ExperimentoMatriz.run(numThreads, repeticoes); break;
            default: System.out.println("Opção inválida.");
        }

    }
}
