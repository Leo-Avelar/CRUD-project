package ColumnarTranspositionCipher;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Auxiliary.FinalVariables;

public class Decrypt {
    public static final String ARQUIVO  = FinalVariables.ARQUIVO_DB;
    public static final String INDEX    = FinalVariables.INDEX;
    public static final String KEY      = FinalVariables.KEY;

    public static String decrypt(String mensagemCriptograda) {
        int numColunas = KEY.length();
        int numLinhas = Math.max((int) Math.ceil((double) mensagemCriptograda.length() / numColunas), 1);

        // Criando uma lista de colunas vazias
        List<StringBuilder> Colunas = new ArrayList<>();
        for (int i = 0; i < numColunas; i++) {
            Colunas.add(new StringBuilder());
        }

        int index = 0;

        if( mensagemCriptograda.length() % KEY.length() == 0 ){
            for (int col : sortColumns(KEY)) {
                for (int linha = 0; linha < numLinhas; linha++) {
                    if (index < mensagemCriptograda.length()) {
                        Colunas.get(col).append(mensagemCriptograda.charAt(index));
                        index++;
                    } else {
                        Colunas.get(col).append('#');
                    }
                }
            }
        }
        else{
            for (int col : sortColumns(KEY)) {
                for (int linha = 0; linha < numLinhas; linha++) {
                    if (linha < numLinhas - 1) {
                        Colunas.get(col).append(mensagemCriptograda.charAt(index));
                        index++;
                    } else if (linha == numLinhas - 1 && col < mensagemCriptograda.length() % numColunas) {
                        Colunas.get(col).append(mensagemCriptograda.charAt(index));
                        index++;
                    } else {
                        Colunas.get(col).append('#');
                    }
                }
            }
        }

        // Construindo a mensagem original a partir das colunas
        StringBuilder mensagemDescrip = new StringBuilder();
        for (int linha = 0; linha < numLinhas; linha++) {
            for (int col = 0; col < numColunas; col++) {
                mensagemDescrip.append(Colunas.get(col).charAt(linha));
            }
        }

        // Removendo os caracteres "#" da mensagem original
        String mensagemFinal = mensagemDescrip.toString().replace("#", "");
        return mensagemFinal;
    }

    private static List<Integer> sortColumns(String KEY) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < KEY.length(); i++) {
            order.add(i);
        }
        Collections.sort(order, (a, b) -> Character.compare(KEY.charAt(a), KEY.charAt(b)));
        return order;
    }

    public static void decryptFile() {
        try {
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            arq.seek(0);
            int ultimoId = arq.readInt();

            while (arq.getFilePointer() < arq.length()) {
                char lapide = arq.readChar();
                int tamanho = arq.readInt();
                long pos1 = arq.getFilePointer();
                int id = arq.readInt();
                long pos2 = arq.getFilePointer();
                String nome = arq.readUTF();

                String nomeDescrip = decrypt(nome);
                arq.seek(pos2);
                arq.writeUTF(nomeDescrip); // Substitui o nome criptografado pelo nome descriptografado

                arq.seek(pos1 + tamanho);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String decryptName(int idBuscado){
        String nomeDescrip = "";
        try{
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            RandomAccessFile arqI = new RandomAccessFile(INDEX, "rw");

            arqI.seek(0);

            while(arqI.getFilePointer() < arqI.length()){

                int id = arqI.readInt();
                int pos = arqI.readInt();

                if (pos!=-1 && id == idBuscado){

                    arq.seek(pos);

                    char lapide = arq.readChar();
                    int tamanho = arq.readInt();
                    int id2 = arq.readInt();
                    String nome = arq.readUTF();

                    nomeDescrip = decrypt(nome);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nomeDescrip;
    }
}
