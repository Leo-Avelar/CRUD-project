package CRUD;

import java.io.*;

import Auxiliary.FinalVariables;
import Auxiliary.NewRegister;
import ColumnarTranspositionCipher.Encrypt;

public class Create {
    public static final String ARQUIVO = FinalVariables.ARQUIVO_DB;
    public static final String INDEX = FinalVariables.INDEX;

    public static void create() {
        try {
            RandomAccessFile arq = new RandomAccessFile(ARQUIVO, "rw");
            RandomAccessFile arqI = new RandomAccessFile(INDEX, "rw");

            byte[] novoRegistro = NewRegister.createGame();
            int id = NewRegister.setId();

            arq.seek(arq.length());
            long posInicial = arq.getFilePointer();

            arq.write(novoRegistro);
            int tamanho = novoRegistro.length;

            arq.seek(posInicial + 2); // Pula a lapide e sobrescreve o tamanho anterior que era 0000
            arq.writeInt(tamanho - 6); // Menos 6 já que a lapide é um char (tem tamanho 2) e o proprio tamanho que tem
                                       // tamanho 4
            arq.writeInt(id);

            arqI.seek(arqI.length());
            arqI.writeInt(id);
            arqI.writeInt((int) posInicial);

            Encrypt.encryptName(id);

            System.out.print("\n== Entidade criada com sucesso (id = " + id + ") ==\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}