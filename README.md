# 💻 Trabalho Prático 2 – Sistemas Operacionais (Threads)

Repositório público com a implementação do **Trabalho Prático 2** da disciplina de **Sistemas Operacionais – UFAM**.  
O foco do TP é experimentar **programação paralela com threads**, comparar com a versão sequencial e analisar o ganho de desempenho.

> Linguagens utilizadas:
> - **Java** (Questões 1 e 2 – vetores e matrizes, com `Thread`)
> - **C (pthread)** (Questão 3 – simulação de aplicação com threads)

---
## Como compilar e executar
Pré-requisitos:

* gcc com suporte a pthread

* make

* Java JDK 8+

### Compilar tudo
Compila os programas em C (sequencial e paralelo) e o programa em Java:
```bash
make
```

### Executar programas
Para o experimento 1 e 2, você pode:
* Compilar apenas Java (caso não tenha executado o `make`):

```bash
make java
```

* Executar

```bash
make run_java
```

As classes compiladas são geradas no diretório `out/` 

Para o experimento 3, você pode:
* Compilar (caso não tenha executado o `make`):

```bash
make c
```

* Executar versão sequencial:

```bash
make run_c_seq
```

* Executar versão paralela:
```bash
make run_c_par
```

Os binários gerados ficam em `task3/bin/`

Para remover os arquivos binários C e arquivos compilados Java, execute:
```bash
make clean
```

Se tiver alguma dúvida, execute:

```bash
make help
```

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

├── hash.txt
├── log_matriz.txt
├── log.txt
├── log_vetor.txt
├── makefile
├── README.md
├── task1_2
│   └── src
│       ├── experimentos
│       │   ├── ExperimentoMatriz.java
│       │   └── ExperimentoVetor.java
│       ├── gerador
│       │   └── GeradorDados.java
│       ├── Main.java
│       ├── matriz
│       │   ├── ProdutoMatrizPar.java
│       │   └── ProdutoMatrizSeq.java
│       ├── utils
│       │   └── Logger.java
│       └── vetor
│           ├── ProdutoEscalarPar.java
│           └── ProdutoEscalarSeq.java
│   
└── task3
    └── src
        ├── bruteforce_seq.c
        ├── bruteforce_threads.c
        ├── hash_utils.c
        └── hash_utils.h