package Compression;
import java.io.*;
import java.util.Scanner;
import Auxiliary.CheckType;
import Auxiliary.FinalVariables;

public class Overwrite {
    public static final String ARQUIVO_PRINCIPAL = FinalVariables.ARQUIVO_DB;

    public static void overwriteFileDB(String nomeArqDescompact) {
        try {
            RandomAccessFile arqPrinc = new RandomAccessFile(ARQUIVO_PRINCIPAL, "rw");
            RandomAccessFile arq = new RandomAccessFile(nomeArqDescompact, "rw");

            arqPrinc.setLength(0);
            arq.seek(0);

            // Copiar os dados do novo arquivo para o arquivo do banco de dados
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = arq.read(buffer)) != -1) {
                arqPrinc.write(buffer, 0, bytesRead);
            }
            System.out.println("O arquivo do banco de dados foi sobrescrito com sucesso.");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao sobrescrever o arquivo do banco de dados:");
        }
    }

    public static void menuOverwrite() {
        Scanner sc = new Scanner(System.in);
        String nomeDescom = "";
        String op = "";

        do {
            System.out.print("\nO arquivo já está descompactado ? (s/n): ");
            op = sc.nextLine();

            switch (op) {
                case "s":
                    nomeDescom = CheckType.getFileName("\nDigite o nome do Arquivo DESCOMPACTADO (.db): ",
                            "--- Nome  inválido ---");
                    overwriteFileDB(nomeDescom);
                    break;

                case "n":
                    LzwDecompression.decompressFile();
                    overwriteFileDB(nomeDescom);
                    break;

                default:
                    System.out.println("\n --- Resposta Inválida --- ");
                    System.out.println(" teste op: " + op);
                    break;
            }
        } while (op.equals("s") && op.equals("n"));
    }
}
