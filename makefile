CC      = gcc
CFLAGS  = -Wall -Wextra -O2
LDFLAGS_PAR = -lpthread

C_DIR   = task3
C_SRC   = $(C_DIR)/src
C_BIN   = $(C_DIR)/bin

SEQ_SRC = $(C_SRC)/bruteforce_seq.c $(C_SRC)/hash_utils.c
PAR_SRC = $(C_SRC)/bruteforce_threads.c $(C_SRC)/hash_utils.c

JAVA_SRC  = task1_2/src
JAVA_OUT  = out
JAVA_MAIN = src.Main

.PHONY: all clean c java run run_c run_java

all: c java ## Compila C e Java

$(C_BIN):
	mkdir -p $(C_BIN)

$(C_BIN)/bruteforce_seq: $(SEQ_SRC) | $(C_BIN)
	$(CC) $(CFLAGS) -o $@ $(SEQ_SRC)

$(C_BIN)/bruteforce_threads: $(PAR_SRC) | $(C_BIN)
	$(CC) $(CFLAGS) -o $@ $(PAR_SRC) $(LDFLAGS_PAR)


c: $(C_BIN)/bruteforce_seq $(C_BIN)/bruteforce_threads ## Compila programas C (sequencial e paralelo)

run_c_seq: ## Executa versão C sequencial
	./$(C_BIN)/bruteforce_seq

run_c_par: ## Executa versão C paralela
	./$(C_BIN)/bruteforce_threads

java: java_build ## Compila código Java

java_build: ## Compila fontes Java
	mkdir -p $(JAVA_OUT)
	javac -d $(JAVA_OUT) $(shell find $(JAVA_SRC) -name "*.java")

run_java: java ## Executa programa Java
	java -cp $(JAVA_OUT) $(JAVA_MAIN)

clean: ## Remove binários C e classes Java
	rm -rf $(C_BIN) $(JAVA_OUT)

.PHONY: help

help:
	@echo "Uso: make <alvo>"
	@echo ""
	@echo "Alvos disponíveis:"
	@grep -E '^[a-zA-Z0-9_-]+:.*## ' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS=":.*## "}; {printf "  %-15s %s\n", $$1, $$2}'