#define _POSIX_C_SOURCE 199309L

#include <stdio.h>
#include <time.h>
#include <string.h>
#include "hash_utils.h"

#define VERBOSE 0  // 0 = sem spam de logs, 1 = com logs

#if VERBOSE
    #define LOG(...) printf(__VA_ARGS__)
#else
    #define LOG(...)
#endif


#define ARQUIVO_HASH "hash.txt"

// Converte número 0..999999 para PIN de 6 dígitos (000000..999999)
void gera_pin(int numero, char *saida) {
    sprintf(saida, "%06d", numero);
}

double calcula_tempo_ms(struct timespec inicio, struct timespec fim) {
    long segundos = fim.tv_sec - inicio.tv_sec;
    long nanos    = fim.tv_nsec - inicio.tv_nsec;
    return (double) segundos * 1000.0 + (double) nanos / 1e6;
}

void limpar_stdin() {
    int ch;
    while ((ch = getchar()) != '\n' && ch != EOF) {}
}

void definir_senha() {
    char senha[16];

    printf("Digite a senha (PIN de 6 digitos, ex: 042089): ");
    if (scanf("%15s", senha) != 1) {
        printf("Erro de leitura da senha.\n");
        limpar_stdin();
        return;
    }

    int h = hash_senha(senha);
    printf("Hash gerado para a senha %s = %d\n", senha, h);

    if (salvar_hash_em_arquivo(ARQUIVO_HASH, h)) {
        printf("Hash salvo em '%s'.\n", ARQUIVO_HASH);
    } else {
        printf("Nao foi possivel salvar o hash em arquivo.\n");
    }
}

void ataque_sequencial() {
    int hash_alvo;
    if (!ler_hash_de_arquivo(ARQUIVO_HASH, &hash_alvo)) {
        printf("Nao foi possivel ler o hash. Defina uma senha primeiro.\n");
        return;
    }

    printf("Hash alvo lido de '%s' = %d\n", ARQUIVO_HASH, hash_alvo);

    char tentativa[16];
    int encontrado = 0;

    struct timespec inicio, fim;
    clock_gettime(CLOCK_MONOTONIC, &inicio);

    for (int i = 0; i < 1000000; i++) {
        gera_pin(i, tentativa);
        int h = hash_senha(tentativa);

        LOG("[SEQ] Testando senha %s (hash=%d)\n", tentativa, h);
        
        if (h == hash_alvo) {
            encontrado = 1;
            clock_gettime(CLOCK_MONOTONIC, &fim);
            double tempo_ms = calcula_tempo_ms(inicio, fim);

            printf("=== SEQUENCIAL ===\n");
            printf("Senha encontrada: %s\n", tentativa);
            printf("Tempo total: %.3f ms\n", tempo_ms);
            break;
        }
    }

    if (!encontrado) {
        clock_gettime(CLOCK_MONOTONIC, &fim);
        double tempo_ms = calcula_tempo_ms(inicio, fim);
        printf("Senha nao encontrada. Tempo total: %.3f ms\n", tempo_ms);
    }
}

int main() {
    int opcao;

    do {
        printf("\n=== BRUTE FORCE SEQUENCIAL ===\n");
        printf("1 - Definir senha e salvar hash\n");
        printf("2 - Executar ataque brute force sequencial\n");
        printf("0 - Sair\n");
        printf("Opcao: ");

        if (scanf("%d", &opcao) != 1) {
            printf("Entrada invalida. Encerrando.\n");
            limpar_stdin();
            break;
        }

        switch (opcao) {
            case 1:
                definir_senha();
                break;
            case 2:
                ataque_sequencial();
                break;
            case 0:
                printf("Saindo...\n");
                break;
            default:
                printf("Opcao invalida.\n");
        }
    } while (opcao != 0);

    return 0;
}
