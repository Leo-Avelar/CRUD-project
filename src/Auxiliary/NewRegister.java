package Auxiliary;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class NewRegister {
    public static byte[] createGame() {
        try {
            Scanner sc = new Scanner(System.in);

            String nome = CheckType.getString("\nDigite o nome do jogo: ","\n== Nome Invalido ==" );

            Date data = CheckType.getDate("\nDigite a data de lançamento (ano/mês/dia): ", "\n== Data Invalida ==");

            System.out.println("\nDigite as tags ( separe com: , ):");
            String tags = sc.nextLine();
            tags = tags.replaceAll("\\||\\-|/|\\\\|_,", ";");

            int tempoJogo = CheckType.getInt("\nDigite o tempo médio de jogo (em horas): ", "\n== Tempo Invalido ==");
            float preco = CheckType.getFloat("\nDigite o preço: ", "\n== Preço Invalido ==");

            Game jogo = new Game(' ', 0, 0, nome, data, tags, tempoJogo, preco);

            byte[] novoRegistro;
            novoRegistro = jogo.createByteArray();
            return novoRegistro;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int setId() throws IOException {
        RandomAccessFile arqId = new RandomAccessFile("arquivos/banco.db", "rw");

        arqId.seek(0);
        int id = arqId.readInt() + 1;

        arqId.seek(0);
        arqId.writeInt(id);

        arqId.close();
        return id;
    }
}