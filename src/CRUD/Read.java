package CRUD;

import java.io.*;

import Auxiliary.*;

public class Read {
    public static final char EXCLUIDO  = FinalVariables.EXCLUIDO;
    public static final String ARQUIVO = FinalVariables.ARQUIVO_DB;
    public static final String INDEX   = FinalVariables.INDEX;

    public static void read(int idBuscado) {
        try {
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            RandomAccessFile arqIndex = new RandomAccessFile(INDEX, "rw");

            boolean encontrado = false;

            arq.seek(0);
            arqIndex.seek(0);
            int ultimoId = arq.readInt();

            if (idBuscado > ultimoId) {
                System.out.println("-- Elemento Inexistente -- \n");
            } else {
                char lapide = EXCLUIDO;
                int id = -1;
                int tamanho = -1;
                int pos;

                while (arqIndex.getFilePointer() < arqIndex.length()) {

                    id = arqIndex.readInt();
                    pos = arqIndex.readInt();

                    if (pos != -1 && id == idBuscado) {
                        encontrado = true;

                        arq.seek(pos);
                        lapide = arq.readChar();

                        if (lapide != EXCLUIDO) {
                            tamanho = arq.readInt();
                            id = arq.readInt();

                            byte[] registro = new byte[tamanho - 4];
                            arq.read(registro);

                            FileOperations.readRegister(registro, tamanho, id);
                        }
                    }
                }
                if (encontrado == false) {
                    System.out.println("-- Id não encontrado -- \n");
                }
            }
            arq.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}