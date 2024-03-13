package PatternMatching;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import Auxiliary.FinalVariables;

public class Kmp {
    public static final String ARQUIVO = FinalVariables.ARQUIVO_DB;

    public static void findPatterns() {
        Scanner sc = new Scanner(System.in);

        try (RandomAccessFile file = new RandomAccessFile(ARQUIVO, "r")) {
            byte[] arquivo = new byte[(int) file.length()];
            file.readFully(arquivo);

            System.out.print("\n\nDigite o padrão a ser buscado: ");
            String strBuscada = sc.nextLine();

            byte[] padraoBuscado = strBuscada.getBytes();

            kmp(arquivo, padraoBuscado);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] calculatePrefix(byte[] padrao) {
        int tamPadrao = padrao.length;
        int[] prefixo = new int[tamPadrao];
        prefixo[0] = 0;

        int len = 0;
        int i = 1;

        while (i < tamPadrao) {
            if (padrao[i] == padrao[len]) {
                len++;
                prefixo[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = prefixo[len - 1];
                } else {
                    prefixo[i] = 0;
                    i++;
                }
            }
        }

        return prefixo;
    }

    public static void kmp(byte[] texto, byte[] padrao) {
        int tamTexto = texto.length;
        int tamPadrao = padrao.length;
        int[] prefixo = calculatePrefix(padrao);
        int i = 0;
        int j = 0;
        int operacoes = 0;

        List<Integer> posicoes = new ArrayList<>();
        List<Double> duracoes = new ArrayList<>();

        long tempoInicial = System.nanoTime();

        while (i < tamTexto) {
            operacoes++;

            if (padrao[j] == texto[i]) {
                j++;
                i++;
            }

            if (j == tamPadrao) {
                posicoes.add(i - j); // padrão encontrado
                j = prefixo[j - 1];

                duracoes.add((System.nanoTime() - tempoInicial) / 1_000_000.0); // Tempo atual - Tempo inicial

            } else if (i < tamTexto && padrao[j] != texto[i]) {
                if (j != 0) {
                    j = prefixo[j - 1];
                } else {
                    i++;
                }
            }
        }
        double duracaoTotal = (System.nanoTime() - tempoInicial) / 1_000_000.0;

        if (posicoes.size() != 0) {

            System.out.println("\n === Padrão Encontrado === \n");

            for (int k = 0; k < posicoes.size(); k++) {
                int posicao = posicoes.get(k);
                double duracao = duracoes.get(k);

                System.out.println("Padrão encontrado na posição: " + posicao + " no tempo de: " + duracao + " ms");
            }
            findId(posicoes);

        } else {
            System.out.println("\n === Padrão Não Encontrado ===");
        }

        System.err.println("\n\n ----------------------------------------");
        System.out.println(  "\nTotal encontrado: " + posicoes.size()       );
        System.out.println(  "Total de operações realizadas: " + operacoes  );
        System.out.println(  "Tempo total gasto: " + duracaoTotal + " ms"   );
    }

    static void findId(List<Integer> posicoes) {
        try {
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "r");

            int posicaoAtual;
            char lapide = 'a';
            int idAnt = 0;
            int id = 0;

            System.out.print("\n Padrão encontrado no(s) jogo(s) de Id: ");

            for (int i = 0; i < posicoes.size(); i++) {
                posicaoAtual = posicoes.get(i);

                arq.seek(posicaoAtual);

                do {
                    arq.seek(arq.getFilePointer() - 1);
                    lapide = arq.readChar();

                    if (lapide == ' ') {
                        id = getGame(arq);
                    }
                    arq.seek(arq.getFilePointer() - 2);
                } while (lapide != ' ' || id == 0);

                if (id != idAnt) {
                    System.out.printf(id + " ");
                }
                idAnt = id;
                id = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int getGame(RandomAccessFile arq) {
        int id = 0;

        try {
            long initialPosition = arq.getFilePointer();

            int tam = arq.readInt();
            id = arq.readInt();
            String name = arq.readUTF();
            Date data = new Date(arq.readLong());

            arq.seek(initialPosition);
        } catch (IOException | DateTimeException e) {
            return 0;
        }
        return id;
    }
}
