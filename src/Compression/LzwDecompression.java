package Compression;
import java.io.*;
import java.util.*;
import Auxiliary.CheckType;
import Auxiliary.FinalVariables;

public class LzwDecompression {
    public static final int TAMANHO_MAX = FinalVariables.TAMANHO_MAX;


    public static String decompressFile() {
        String arquivoComprimido = CheckType.getFileName("\nDigite o nome do Arquivo COMPACTADO (.db): ", "\n--- Nome  inválido ---");
        String arquivoDescomprimido = CheckType.getFileName("\nDigite o nome que você deseja para o Arquivo DESCOMPACTADO (.db): ", "\n--- Nome  inválido ---");

        decompress(arquivoComprimido, arquivoDescomprimido);
        return arquivoDescomprimido;
    }
    

    public static void decompress(String arquivoComprimido, String arquivoDescomprimido) {

        try (DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(arquivoComprimido)));
             RandomAccessFile output = new RandomAccessFile(arquivoDescomprimido, "rw")) {

            List<byte[]> dicionario = new ArrayList<>();
            for (int i = 0; i < 256; i++) {
                dicionario.add(new byte[]{(byte) i});
            }

            int tamDicio = 256;
            int tamBit = 9;
            int maxCode = (int) Math.pow(2, tamBit) - 1;

            int currentCode;
            byte[] sequenciaAnterior;

            try {
                currentCode = input.readInt();
                sequenciaAnterior = dicionario.get(currentCode);
                output.write(sequenciaAnterior);
            } catch (EOFException e) {
                // Quando chega no fim do arquivo
                System.out.println("== O Arquivo Descomprimido Foi Criado 2 ==");
                return;
            }

            while (true) {
                try {
                    currentCode = input.readInt();
                } catch (EOFException e) {
                    // Quando chega no fim do arquivo
                    break;
                }

                byte[] sequenciaAtual;

                if (currentCode == tamDicio) {
                    sequenciaAtual = concatenateSequences(sequenciaAnterior, sequenciaAnterior[0]);
                } else if (currentCode < tamDicio) {
                    sequenciaAtual = dicionario.get(currentCode);
                } else {
                    throw new IllegalStateException("== Erro ==\n - Codigo Invalido encontrado -");
                }

                output.write(sequenciaAtual);
                if (tamDicio < TAMANHO_MAX) {
                    byte[] novaSequencia = concatenateSequences(sequenciaAnterior, sequenciaAtual[0]);
                    dicionario.add(novaSequencia);
                    tamDicio++;

                    if (tamDicio > maxCode) {
                        if (tamBit < 12) {
                            tamBit++;
                            maxCode = (int) Math.pow(2, tamBit) - 1;
                        }
                    }
                }
                sequenciaAnterior = sequenciaAtual;
            }

            System.out.println("== O Arquivo Descomprimido Foi Criado ==");
        } catch (FileNotFoundException e) {
            System.out.println("\n-- O arquivo Compactado não foi encontrado --");
        } catch (IOException e) {
            System.out.println("\n-- Ocorreu um erro ao manipular o arquivo --");
            e.printStackTrace();
        }
    }

    private static byte[] concatenateSequences(byte[] sequencia1, byte sequencia2) {
        byte[] resultado = new byte[sequencia1.length + 1];
        System.arraycopy(sequencia1, 0, resultado, 0, sequencia1.length);
        resultado[resultado.length - 1] = sequencia2;
        return resultado;
    }
}
