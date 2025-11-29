SRC = task1_2/src
OUT = out

MAIN = src.Main

java_build:
	mkdir -p $(OUT)
	javac -d $(OUT) $(shell find $(SRC) -name "*.java")

java_run: java_build
	java -cp $(OUT) $(MAIN)

run: java_run

clean:
	rm -rf $(OUT)
