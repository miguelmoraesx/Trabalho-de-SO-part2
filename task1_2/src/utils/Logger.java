package src.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger{
    private String tag;
    private String msg;
    private long tempoInicio;
    private long tempoTotal;
    private long tempoSeq;
    private long tempoPar;
    private final File arquivo;
    private int numThreads;

    public Logger(String tag, String arquivoPath, int numThreads){
        this.tag = tag;
        this.msg = new String("Mensagem default.");
        this.tempoInicio = System.nanoTime();
        this.arquivo = new File(arquivoPath);
        this.numThreads = numThreads;
    }

    private boolean criarArquivo(){
        try {
            if(!arquivo.exists())
                if(arquivo.createNewFile())
                    System.out.println("Arquivo de log criado: " +arquivo.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void iniciar(){
        this.tempoInicio = System.nanoTime();
    }

    public void finalizar(String msg) throws IOException{
        if(msg == null || msg.isEmpty())
            msg = this.msg;

        this.tempoTotal = System.nanoTime() - tempoInicio;
        
        try {
            BufferedWriter bw;
            if(criarArquivo()){
                bw = new BufferedWriter(new FileWriter(arquivo, true));
                bw.write(this.tag +": " +this.msg +" | tempo = " +this.tempoTotal/1_000_000.0 +"ms");
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void separar(){
        if(criarArquivo()){
            try {
                BufferedWriter bw;
                if(criarArquivo()){
                    bw = new BufferedWriter(new FileWriter(arquivo, true));
                    bw.write("----------------------------");
                    bw.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public void registrarTempoSequencial(){
        this.tempoSeq = tempoTotal;
    }

    public void registrarTempoParalelo(){
        this.tempoPar = tempoTotal;
    }

    public void relatorio(int... tamanhoDados) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write("================================");
            bw.newLine();
            bw.write("=== Relatório de Experimento ===");
            bw.newLine();
            bw.write("Tamanho: ");
            if(tamanhoDados.length > 1)
                bw.write(tamanhoDados[0] +" x " +tamanhoDados[1]);
            else
                bw.write(tamanhoDados[0]);
            bw.newLine();
            bw.write("Número de Threads: " + numThreads);
            bw.newLine();
            bw.write("Tempo seq: " + tempoSeq / 1_000_000.0 + " ms");
            bw.newLine();
            bw.write("Tempo par: " + tempoPar / 1_000_000.0 + " ms");
            bw.newLine();
            double speedup = (double) tempoSeq / tempoPar;
            bw.write("Speedup: " + speedup);
            bw.newLine();
            bw.write("================================");
            bw.newLine();
            
            System.out.println("=== Relatório de Experimento ===");
            if(tamanhoDados.length >1)
                System.out.println("Tamanho dos dados:" +tamanhoDados[0] +" x " +tamanhoDados[1]);
            else
                System.out.println("Tamanho dos dados: " + tamanhoDados[0]);
            System.out.println("Número de Threads: " + numThreads);
            System.out.println("Tempo seq: " + tempoSeq / 1_000_000.0 + " ms");
            System.out.println("Tempo par: " + tempoPar / 1_000_000.0 + " ms");
            System.out.println("Speedup: " + speedup);
            System.out.println("----------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}