package ColumnarTranspositionCipher;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Auxiliary.FinalVariables;

public class Encrypt {
    public static final String ARQUIVO  = FinalVariables.ARQUIVO_DB;
    public static final String INDEX    = FinalVariables.INDEX;
    public static final String KEY      = FinalVariables.KEY;

    public static void encryptFile() {

        try{
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            arq.seek(0);
            int ultimoId = arq.readInt();
            
            while(arq.getFilePointer() < arq.length()){

                char lapide = arq.readChar();
                int tamanho = arq.readInt();
                long pos1 = arq.getFilePointer();
                int id = arq.readInt();
                long pos2 = arq.getFilePointer();
                String nome = arq.readUTF();

                String nomeCrip = encrypt(nome);
                arq.seek(pos2);
                arq.writeUTF(nomeCrip); //Substitui o nome pelo nome criptografado

                arq.seek(pos1 + tamanho);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    public static String encrypt(String mensagem) {
        int numColunas = KEY.length();
        int numLinhas = Math.max((int) Math.ceil((double) mensagem.length() / numColunas), 1);

        // Preenchendo a mensagem com caracteres de preenchimento, se necessário
        if (mensagem.length() < numLinhas * numColunas) {
            int numCharsExtra = numLinhas * numColunas - mensagem.length();
            mensagem = mensagem + "#".repeat(numCharsExtra);
        }

        // Criando uma lista de colunas
        List<StringBuilder> colunas = new ArrayList<>();
        for (int i = 0; i < numColunas; i++) {
            colunas.add(new StringBuilder());
        }

        // Preenchendo as colunas com os caracteres da mensagem
        for (int i = 0; i < mensagem.length(); i++) {
            int column = i % numColunas;
            colunas.get(column).append(mensagem.charAt(i));
        }

        // Ordenando as colunas com base na chave
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < numColunas; i++) {
            order.add(i);
        }
        Collections.sort(order, (a, b) -> Character.compare(KEY.charAt(a), KEY.charAt(b)));

        // Construindo a mensagem criptografada a partir das colunas na ordem correta
        StringBuilder mensagemCriptografada = new StringBuilder();
        for (int col : order) {
            for (int linha = 0; linha < numLinhas; linha++) {
                mensagemCriptografada.append(colunas.get(col).charAt(linha));
            }
        }

        // Removendo os caracteres "#" da mensagem criptografada
        String mensagemFinal = mensagemCriptografada.toString().replace("#", "");
        return mensagemFinal;
    }



    public static void encryptName(int idBuscado){
        try{
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            RandomAccessFile arqIndex = new RandomAccessFile(INDEX, "rw");

            arqIndex.seek(0);

            while(arqIndex.getFilePointer() < arqIndex.length()){

                int id = arqIndex.readInt();
                int pos = arqIndex.readInt();

                if (pos!=-1 && id == idBuscado){
                    arq.seek(pos);

                    char lapide = arq.readChar();
                    int tamanho = arq.readInt();
                    long pos1 = arq.getFilePointer();
                    int id2 = arq.readInt();
                    long pos2 = arq.getFilePointer();
                    String nome = arq.readUTF();

                    String nomeCrip= encrypt(nome);
                    arq.seek(pos2);
                    arq.writeUTF(nomeCrip); //Substitui o nome pelo nome criptografado

                    arq.seek(pos1 + tamanho);
                    
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
