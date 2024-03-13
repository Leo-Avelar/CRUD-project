package Compression;
import java.io.*;
import java.util.*;
import Auxiliary.CheckType;
import Auxiliary.FinalVariables;

public class LzwCompression {
    public static final String ARQUIVO_ORIGINAL = FinalVariables.ARQUIVO_DB;
    public static final int    TAMANHO_MAX      = FinalVariables.TAMANHO_MAX;

    public static void compressFile() {

        String fileName = CheckType.getFileName("\nDigite o nome que vc deseja para o Arquivo COMPACTADO (.db): ", "\n--- Nome  inválido ---");

        try (RandomAccessFile input = new RandomAccessFile(ARQUIVO_ORIGINAL, "r");
             DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {

            // Leitura prévia do arquivo para preencher a lista/dicionário
            List<byte[]> dicionario = new ArrayList<>();
            for (int i = 0; i < 256; i++) {
                dicionario.add(new byte[]{(byte) i});
            }

            int tamDicio = 256;
            int tamBit = 9;
            int maxCode = (int) Math.pow(2, tamBit) - 1;

            ByteArrayOutputStream bytesAtuais = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    bytesAtuais.write(buffer[i]);
                    byte[] sequenciaAtual = bytesAtuais.toByteArray();
                    if (!isSequenceInDictionary(dicionario, sequenciaAtual)) {
                        // Grava o código da sequencia anterior no arquivo de saída
                        int sequenciaAnterior = getCodeFromDictionary(dicionario, Arrays.copyOfRange(sequenciaAtual, 0, sequenciaAtual.length - 1));
                        output.writeInt(sequenciaAnterior);

                        if (tamDicio < TAMANHO_MAX) {
                            // Adiciona a nova sequencia ao dicionário
                            dicionario.add(sequenciaAtual);
                            tamDicio++;

                            if (tamDicio > maxCode) {
                                if (tamBit < 12) { // Limita o tamanho do bit a 12
                                    tamBit++;
                                    maxCode = (int) Math.pow(2, tamBit) - 1;
                                }
                            }
                        }

                        bytesAtuais.reset();
                        bytesAtuais.write(buffer[i]);
                    }
                }
            }

            if (bytesAtuais.size() > 0) {
                // Grava o código da última sequência no arquivo de saída
                int lastCode = getCodeFromDictionary(dicionario, bytesAtuais.toByteArray());
                output.writeInt(lastCode);
            }

            System.out.println("== O Arquivo Comprimido Foi Criado ==");

        } catch (IOException e) {
            System.out.println("-- Ocorreu um erro ao manipular o arquivo --");
            e.printStackTrace();
        }
    }


    private static boolean isSequenceInDictionary(List<byte[]> dicionario, byte[] sequencia) {
        for (byte[] entry : dicionario) {
            if (Arrays.equals(entry, sequencia)) {
                return true;
            }
        }
        return false;
    }

    private static int getCodeFromDictionary(List<byte[]> dicionario, byte[] sequencia) {
        for (int i = 0; i < dicionario.size(); i++) {
            if (Arrays.equals(dicionario.get(i), sequencia)) {
                return i;
            }
        }
        throw new IllegalStateException("== Erro == \n-Não encontrado no dicionário -");
    }
}
