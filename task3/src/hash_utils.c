#include "hash_utils.h"
#include <stdio.h>

// Hash bem simples, só pra fins didáticos
int hash_senha(const char *senha) {
    int h = 0;
    for (int i = 0; senha[i] != '\0'; i++) {
        h = h * 31 + (unsigned char) senha[i];
    }
    return h;
}

int salvar_hash_em_arquivo(const char *nome_arquivo, int hash) {
    FILE *f = fopen(nome_arquivo, "w");
    if (!f) {
        perror("Erro ao abrir arquivo para escrita");
        return 0;
    }
    fprintf(f, "%d\n", hash);
    fclose(f);
    return 1;
}

int ler_hash_de_arquivo(const char *nome_arquivo, int *hash) {
    FILE *f = fopen(nome_arquivo, "r");
    if (!f) {
        perror("Erro ao abrir arquivo para leitura");
        return 0;
    }
    if (fscanf(f, "%d", hash) != 1) {
        fprintf(stderr, "Erro ao ler hash do arquivo\n");
        fclose(f);
        return 0;
    }
    fclose(f);
    return 1;
}
