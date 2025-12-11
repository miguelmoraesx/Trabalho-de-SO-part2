# 💻 Trabalho Prático 2 – Sistemas Operacionais (Threads)

Repositório público com a implementação do **Trabalho Prático 2** da disciplina de **Sistemas Operacionais – UFAM**.  
O foco do TP é experimentar **programação paralela com threads**, comparar com a versão sequencial e analisar o ganho de desempenho.

> Linguagens utilizadas:
> - **Java** (Questões 1 e 2 – vetores e matrizes, com `Thread`)
> - **C (pthread)** (Questão 3 – simulação de aplicação com threads)

---

## 📚 Sumário

- [Arquitetura do repositório](#-arquitetura-do-repositório)
- [Questão 1 – Produto escalar de vetores](#-questão-1--produto-escalar-de-vetores)
- [Questão 2 – Multiplicação de matrizes](#-questão-2--multiplicação-de-matrizes)
- [Questão 3 – Simulação de brute force com hash (C + pthread)](#-questão-3--simulação-de-brute-force-com-hash-c--pthread)
- [Como compilar e executar](#-como-compilar-e-executar)
  - [Java – Questões 1 e 2](#java--questões-1-e-2)
  - [C – Questão 3](#c--questão-3)
- [Experimentos, métricas e speedup](#-experimentos-métricas-e-speedup)
- [Tecnologias utilizadas](#-tecnologias-utilizadas)
- [Autores](#-autores)
- [Licença](#-licença)

---

## 🧱 Arquitetura do repositório

```text
Trabalho-de-SO-part2/
├── task1_2/                    # Questões 1 e 2 (Java)
│   ├── src/
│   │   ├── gerador/
│   │   │   └── GeradorDados.java         # Geração de vetores/matrizes de teste
│   │   ├── vetor/
│   │   │   ├── ProdutoEscalarPar.java    # Produto escalar paralelo (threads)
│   │   │   └── ProdutoEscalarSeq.java    # Produto escalar sequencial
│   │   ├── matriz/
│   │   │   ├── MultiplicaPar.java        # Multiplicação de matrizes paralela
│   │   │   └── MultiplicaSeq.java        # Multiplicação de matrizes sequencial
│   │   ├── utils/
│   │   │   └── Logger.java               # Utilitário de logging / medição de tempo
│   │   └── Main.java                     # Menu principal das questões 1 e 2
│   ├── log.txt                           # Saídas/tempos de execução (exemplo)
│   ├── Makefile                          # (opcional) build/exec em Java
│   └── README.md                         # Detalhes específicos da task1_2 (se houver)
│
├── task3/                      # Questão 3 (C + pthread)
│   ├── src/
│   │   ├── hash_utils.h        # Assinaturas de hash e I/O de arquivo
│   │   ├── hash_utils.c        # Implementação de hash e leitura/escrita em arquivo
│   │   ├── bruteforce_seq.c    # Versão sequencial da aplicação (sem threads)
│   │   └── bruteforce_threads.c# Versão paralela com pthreads
│   ├── bin/                    # Binários gerados pelo Makefile (ignorado no Git)
│   ├── Makefile                # Compila as duas versões da aplicação
│   └── README.md               # Detalhes específicos da questão 3
│
├── SO_TP2_2025-2.pdf           # Enunciado do trabalho (cópia para referência)
├── .gitignore
└── README.md                   # (este arquivo) visão geral do repositório
