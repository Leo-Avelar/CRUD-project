
import java.util.*;
import CRUD.*;
import ColumnarTranspositionCipher.*;
import Auxiliary.*;
import Convert.*;
import Compression.*;
import PatternMatching.*;
import ExternalSorting.SortFile;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CsvToDb.createDB();
        DbToIndex.createIndex();
        Encrypt.encryptFile();

        int op;
        int overwriteOption;
        int idBuscado;

        do {
            Menu.showMenu();
            op = CheckType.getInt("\nDigite a opção desejada: ", "\n--- Opção INVÁLIDA ---");

            switch (op) {
                case 0:
                    System.out.print("\n --- Você Saiu --- \n\n");
                    break;

                case 1:
                    System.out.print("\n --- Create --- \n");
                    Create.create();
                    break;

                case 2:
                    System.out.print("\n --- Read --- \n");
                    idBuscado = CheckType.getInt("\nDigite o id a ser buscado: ", "\n== Id Inválido ==");
                    Read.read(idBuscado);
                    break;

                case 3:
                    System.out.print("\n --- Update --- \n");
                    idBuscado = CheckType.getInt("\nDigite o id a ser atualizado: ", "\n== Id Inválido ==");
                    Update.update(idBuscado);
                    break;

                case 4:
                    System.out.print("\n --- Delete --- \n");
                    idBuscado = CheckType.getInt("\nDigite o id a ser deletado: ", "\n== Id Inválido ==");
                    Delete.delete(idBuscado);
                    break;

                case 5:
                    System.out.print("\n --- Ordenar --- \n");
                    SortFile.sort();
                    DbToIndex.createIndex();
                    break;

                case 6:
                    System.out.print("\n --- Compactar banco de dados --- \n");
                    LzwCompression.compressFile();
                    break;

                case 7:
                    System.out.print("\n --- Descompactar banco de dados --- \n");
                    LzwDecompression.decompressFile();
                    break;

                case 8:
                    Menu.overwriteMenu();
                    overwriteOption = CheckType.getInt("\nDigite a opção desejada: ", "\n--- Opção INVÁLIDA ---");

                    switch (overwriteOption) {
                        case 0:
                            break;

                        case 1:
                            CsvToDb.createDB();
                            DbToIndex.createIndex();
                            Encrypt.encryptFile();
                            break;

                        case 2:
                            Overwrite.menuOverwrite();
                            DbToIndex.createIndex();
                            break;

                        default:
                            System.out.print("\n      === ERRO === \n --- Opção INVÁLIDA ---\n");
                            break;
                    }
                    break;

                case 9:
                    Decrypt.decryptFile();
                    System.out.print("\n --- Casamento de padrões --- \n");
                    Kmp.findPatterns();
                    Encrypt.encryptFile();
                    break;

                default:
                    System.out.print("\n      === ERRO === \n --- Opção INVÁLIDA ---\n");
                    break;
            }
        } while (op != 0);
        sc.close();
    }
}