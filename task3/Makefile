CC      = gcc
CFLAGS  = -Wall -Wextra -O2
LDFLAGS_SEQ =
LDFLAGS_PAR = -lpthread

SRC_DIR = src
BIN_DIR = bin

SEQ_SRC = $(SRC_DIR)/bruteforce_seq.c $(SRC_DIR)/hash_utils.c
PAR_SRC = $(SRC_DIR)/bruteforce_threads.c $(SRC_DIR)/hash_utils.c

all: dirs seq par

dirs:
	@mkdir -p $(BIN_DIR)

seq: $(SEQ_SRC)
	$(CC) $(CFLAGS) -o $(BIN_DIR)/bruteforce_seq $(SEQ_SRC) $(LDFLAGS_SEQ)

par: $(PAR_SRC)
	$(CC) $(CFLAGS) -o $(BIN_DIR)/bruteforce_threads $(PAR_SRC) $(LDFLAGS_PAR)

clean:
	rm -rf $(BIN_DIR)
