#define _POSIX_C_SOURCE 199309L

#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>
#include "hash_utils.h"

#define VERBOSE 0  // 0 = sem prints internos, 1 = com prints

#if VERBOSE
    #define LOG(...) printf(__VA_ARGS__)
#else
    #define LOG(...)
#endif

#define ARQUIVO_HASH "hash.txt"

typedef struct {
    int id_thread;
    int inicio;
    int fim;
} ThreadArgs;

// Variáveis globais compartilhadas
pthread_mutex_t mutex_encontrou;
int  hash_alvo      = 0;
int  encontrou      = 0;
char senha_encontrada[16];

void gera_pin(int numero, char *saida) {
    // numero entre 0 e 9999
    sprintf(saida, "%04d", numero);
}

double calcula_tempo_ms(struct timespec inicio, struct timespec fim) {
    long segundos = fim.tv_sec - inicio.tv_sec;
    long nanos    = fim.tv_nsec - inicio.tv_nsec;
    return (double) segundos * 1000.0 + (double) nanos / 1e6;
}

void *thread_bruteforce(void *arg) {
    ThreadArgs *args = (ThreadArgs *) arg;
    char tentativa[16];

    printf("[Thread %d] Iniciando busca de %d ate %d\n",
           args->id_thread, args->inicio, args->fim);

    for (int i = args->inicio; i <= args->fim; i++) {

        // Verifica se outra thread já encontrou a senha
        pthread_mutex_lock(&mutex_encontrou);
        if (encontrou) {
            pthread_mutex_unlock(&mutex_encontrou);
            printf("[Thread %d] Encerrando (senha ja encontrada)\n",
                   args->id_thread);
            break;
        }
        pthread_mutex_unlock(&mutex_encontrou);

        gera_pin(i, tentativa);
        int h = hash_senha(tentativa);

        LOG("[Thread %d] Testando senha %s (hash=%d)\n",
               args->id_thread, tentativa, h);

        if (h == hash_alvo) {
            pthread_mutex_lock(&mutex_encontrou);
            if (!encontrou) {
                encontrou = 1;
                strcpy(senha_encontrada, tentativa);
                printf("[Thread %d] ENCONTROU! Senha = %s\n",
                       args->id_thread, senha_encontrada);
            }
            pthread_mutex_unlock(&mutex_encontrou);
            break;
        }

        // usleep(50000); // opcional: 50 ms pra saída ficar mais legível
    }

    return NULL;
}

void definir_senha() {
    char senha[16];

    printf("Digite a senha (PIN de 4 digitos, ex: 0420): ");
    if (scanf("%15s", senha) != 1) {
        printf("Erro de leitura da senha.\n");
        // limpa stdin básico
        int ch;
        while ((ch = getchar()) != '\n' && ch != EOF) {}
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

void ataque_paralelo() {
    int num_threads;

    printf("Informe o numero de threads: ");
    if (scanf("%d", &num_threads) != 1 || num_threads <= 0) {
        printf("Numero de threads invalido.\n");
        int ch;
        while ((ch = getchar()) != '\n' && ch != EOF) {}
        return;
    }

    if (!ler_hash_de_arquivo(ARQUIVO_HASH, &hash_alvo)) {
        printf("Nao foi possivel ler o hash. Defina uma senha primeiro.\n");
        return;
    }

    printf("Hash alvo lido de '%s' = %d\n", ARQUIVO_HASH, hash_alvo);

    pthread_t  threads[num_threads];
    ThreadArgs args[num_threads];

    int total = 10000;               // 0000..9999
    int bloco = total / num_threads; // tamanho base de cada bloco
    int resto = total % num_threads; // sobra para distribuir

    encontrou = 0;
    memset(senha_encontrada, 0, sizeof(senha_encontrada));
    pthread_mutex_init(&mutex_encontrou, NULL);

    struct timespec inicio, fim;
    clock_gettime(CLOCK_MONOTONIC, &inicio);

    int atual = 0;
    for (int i = 0; i < num_threads; i++) {
        args[i].id_thread = i;
        args[i].inicio    = atual;

        int tamanho_bloco = bloco + (i < resto ? 1 : 0);
        args[i].fim       = atual + tamanho_bloco - 1;
        atual             = args[i].fim + 1;

        pthread_create(&threads[i], NULL, thread_bruteforce, &args[i]);
    }

    for (int i = 0; i < num_threads; i++) {
        pthread_join(threads[i], NULL);
    }

    clock_gettime(CLOCK_MONOTONIC, &fim);
    double tempo_ms = calcula_tempo_ms(inicio, fim);

    if (encontrou) {
        printf("=== PARALELO ===\n");
        printf("Senha encontrada: %s\n", senha_encontrada);
        printf("Tempo total: %.3f ms\n", tempo_ms);
    } else {
        printf("Senha nao encontrada. Tempo total: %.3f ms\n", tempo_ms);
    }

    pthread_mutex_destroy(&mutex_encontrou);
}

int main() {
    int opcao;

    do {
        printf("\n=== BRUTE FORCE PARALELO ===\n");
        printf("1 - Definir senha e salvar hash\n");
        printf("2 - Executar ataque brute force paralelo\n");
        printf("0 - Sair\n");
        printf("Opcao: ");

        if (scanf("%d", &opcao) != 1) {
            printf("Entrada invalida. Encerrando.\n");
            int ch;
            while ((ch = getchar()) != '\n' && ch != EOF) {}
            break;
        }

        switch (opcao) {
            case 1:
                definir_senha();
                break;
            case 2:
                ataque_paralelo();
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
