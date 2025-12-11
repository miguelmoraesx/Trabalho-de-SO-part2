#ifndef HASH_UTILS_H
#define HASH_UTILS_H

// Calcula um hash simples para a string da senha
int hash_senha(const char *senha);

// Salva o hash em um arquivo de texto (ex.: "hash.txt")
int salvar_hash_em_arquivo(const char *nome_arquivo, int hash);

// Lê o hash de um arquivo de texto
int ler_hash_de_arquivo(const char *nome_arquivo, int *hash);

#endif
