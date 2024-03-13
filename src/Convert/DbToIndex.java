package Convert;

import java.io.*;

import Auxiliary.FinalVariables;

public class DbToIndex {
    public static final String ARQUIVO = FinalVariables.ARQUIVO_DB;
    public static final String INDEX = FinalVariables.INDEX;

    public static void createIndex() {
        try {
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            RandomAccessFile arqIndex = new RandomAccessFile(INDEX, "rw");

            arqIndex.setLength(0);
            arq.seek(0);
            arq.skipBytes(4);

            int pos, tamanho, id;

            while (arq.getFilePointer() < arq.length()) {

                pos = (int) arq.getFilePointer();

                arq.readChar(); // lapide tam = 2
                tamanho = arq.readInt(); // tamanho tam = 4
                id = arq.readInt(); // id tam = 4

                arqIndex.writeInt(id);
                arqIndex.writeInt(pos);

                arq.seek(pos + tamanho + 6);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}