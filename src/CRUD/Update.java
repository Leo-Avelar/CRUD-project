package CRUD;

import java.io.IOException;
import java.io.RandomAccessFile;

import Auxiliary.FinalVariables;
import Auxiliary.NewRegister;
import ColumnarTranspositionCipher.Encrypt;

public class Update {

    public static final char EXCLUIDO = FinalVariables.EXCLUIDO;
    public static final String ARQUIVO = FinalVariables.ARQUIVO_DB;
    public static final String INDEX = FinalVariables.INDEX;

    public static void update(int idBuscado) {
        try {
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            RandomAccessFile arqIndex = new RandomAccessFile(INDEX, "rw");
            int ultimoId = arq.readInt();

            if (idBuscado > ultimoId) {
                System.out.println("Elemento Inexistente\n");

            } else {
                arqIndex.seek(0);
                while (arqIndex.getFilePointer() < arqIndex.length()) {
                    int id = arqIndex.readInt();
                    int pos = arqIndex.readInt();

                    if (pos != -1 && id == idBuscado) {

                        arq.seek(pos);

                        long pos2 = arq.getFilePointer();
                        char lapide = arq.readChar();
                        int tamanho = arq.readInt();
                        id = arq.readInt();

                        System.out.print("\n== Criando o novo registro ==");
                        byte[] novoRegistro = NewRegister.createGame();

                        if (novoRegistro.length <= tamanho) {
                            arq.seek(pos2);
                            arq.write(novoRegistro);
                            arq.seek(pos2 + 2);
                            arq.writeInt(tamanho);
                            arq.writeInt(id);
                            Encrypt.encryptName(id);

                            System.out.print("\n== Registro atualizado ==");
                            return;
                        } else {
                            arq.seek(pos2);
                            arq.writeChar(EXCLUIDO);
                            arq.seek(arq.length());
                            long voltar = arq.getFilePointer();
                            arq.write(novoRegistro);
                            arq.seek(voltar + 2);
                            arq.writeInt(novoRegistro.length - 6);
                            arq.writeInt(id);

                            arqIndex.seek(arqIndex.getFilePointer() - 4);
                            arqIndex.writeInt((int) voltar);
                            Encrypt.encryptName(id);

                            System.out.print("\n== Registro atualizado ==");
                            return;
                        }
                    }
                }
                System.out.println("-- Id não encontrado --");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
