package src.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private String tag;
    private final String msgPadrao = "Mensagem default.";
    private long tempoInicio;
    private long tempoTotal;

    private long tempoSeq;
    private long tempoPar;

    // Acumuladores para média paralela
    private long somaPar = 0;
    private int contPar = 0;

    private final File arquivo;
    private final int numThreads;

    public Logger(String tag, String arquivoPath, int numThreads) {
        this.tag = tag;
        this.arquivo = new File(arquivoPath);
        this.numThreads = numThreads;
    }

    private void criarArquivo() {
        try {
            if (!arquivo.exists()) {
                if (arquivo.createNewFile()) {
                    System.out.println("Arquivo de log criado: " + arquivo.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniciar() {
        this.tempoInicio = System.nanoTime();
    }

    public void finalizar(String msg) {
        if (msg == null || msg.isEmpty())
            msg = msgPadrao;

        this.tempoTotal = System.nanoTime() - tempoInicio;
        criarArquivo();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write(this.tag + ": " + msg + " | tempo = " + (this.tempoTotal / 1_000_000.0) + " ms");
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void separar() {
        criarArquivo();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write("----------------------------");
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void registrarTempoSequencial() {
        this.tempoSeq = tempoTotal;
    }

    // NÃO USE mais registrarTempoParalelo() isolado
    // agora apenas parcial + fecharRegistroParalelo()

    public void registrarTempoParcialParalelo() {
        somaPar += tempoTotal;
        contPar++;
    }

    public void fecharRegistroParalelo() {
        if (contPar > 0)
            this.tempoPar = somaPar / contPar;
    }

    public void relatorio(int... tamanhoDados) {
        criarArquivo();
        double seqMs = tempoSeq / 1_000_000.0;
        double parMs = tempoPar / 1_000_000.0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {

            bw.write("================================");
            bw.newLine();
            bw.write("=== Relatório de Experimento ===");
            bw.newLine();

            bw.write("Tamanho: ");
            if (tamanhoDados.length > 1)
                bw.write(tamanhoDados[0] + " x " + tamanhoDados[1]);
            else
                bw.write(String.valueOf(tamanhoDados[0]));
            bw.newLine();

            bw.write("Número de Threads: " + numThreads);
            bw.newLine();
            bw.write("Tempo seq: " + seqMs + " ms");
            bw.newLine();
            bw.write("Tempo par (média): " + parMs + " ms");
            bw.newLine();

            double speedup = tempoPar > 0 ? (double) tempoSeq / tempoPar : 0;
            bw.write("Speedup: " + speedup);
            bw.newLine();

            bw.write("================================");
            bw.newLine();

            // Console
            System.out.println("=== Relatório de Experimento ===");
            if (tamanhoDados.length > 1)
                System.out.println("Tamanho dos dados: " + tamanhoDados[0] + " x " + tamanhoDados[1]);
            else
                System.out.println("Tamanho dos dados: " + tamanhoDados[0]);

            System.out.println("Número de Threads: " + numThreads);
            System.out.println("Tempo seq: " + seqMs + " ms");
            System.out.println("Tempo par (média): " + parMs + " ms");
            System.out.println("Speedup: " + speedup);
            System.out.println("----------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
