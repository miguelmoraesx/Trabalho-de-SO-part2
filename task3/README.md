# TP2 – Questão 3 – Brute Force com Hash (C + Threads)

Este diretório contém a implementação da questão 3 do TP2 de Sistemas Operacionais.

A aplicação simula um ataque de força bruta a um PIN de 4 dígitos (`0000`–`9999`)
utilizando apenas o **hash** da senha armazenado em arquivo. Há duas versões:

- `bruteforce_seq.c`: versão sequencial.
- `bruteforce_threads.c`: versão paralela usando `pthread`.

## Estrutura

```text
task3/
├── src/
│   ├── hash_utils.h        # Funções de hash e leitura/escrita em arquivo
│   ├── hash_utils.c
│   ├── bruteforce_seq.c    # Ataque brute force sequencial
│   └── bruteforce_threads.c# Ataque brute force com threads
├── Makefile
└── bin/                    # Binários gerados pelo make


# Compilar tudo
make          # gera bin/bruteforce_seq e bin/bruteforce_threads

# Versão sequencial
./bin/bruteforce_seq

# Versão paralela
./bin/bruteforce_threads
