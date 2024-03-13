package CRUD;

import java.io.IOException;
import java.io.RandomAccessFile;

import Auxiliary.FinalVariables;

public class Delete { 
    public static final char   EXCLUIDO = FinalVariables.EXCLUIDO;
    public static final String ARQUIVO  = FinalVariables.ARQUIVO_DB;
    public static final String INDEX    = FinalVariables.INDEX;

    public static void delete(int idBuscado) {

        try {
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            RandomAccessFile arqIndex = new RandomAccessFile(INDEX, "rw");

            arq.seek(0);
            arqIndex.seek(0);
            int ultimoId = arq.readInt();
            int id, pos;

            if (idBuscado > ultimoId) {
                System.out.println("Elemento Inexistente\n");
            } else {
                while (arqIndex.getFilePointer() < arqIndex.length()) {

                    id = arqIndex.readInt();
                    pos = arqIndex.readInt();

                    if (pos != -1 && id == idBuscado) {
                        arq.seek(pos);
                        arq.writeChar(EXCLUIDO);

                        arqIndex.seek(arq.getFilePointer() - 4);
                        arqIndex.writeInt(-1);

                        System.out.print("\n\n== Elemento excluido ==\n");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}