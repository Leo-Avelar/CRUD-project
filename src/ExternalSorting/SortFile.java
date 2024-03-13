package ExternalSorting;

import java.io.RandomAccessFile;
import Auxiliary.Game;
import Auxiliary.FileOperations;
import Auxiliary.FinalVariables;

public class SortFile {
    public static final char   EXCLUIDO      = FinalVariables.EXCLUIDO;
    public static final int    MAX_REGISTROS = FinalVariables.MAX_REGISTROS;
    public static final String PRINCIPAL     = FinalVariables.ARQUIVO_DB;
    public static final String TEMPORARIO1   = FinalVariables.TEMPORARIO1;
    public static final String TEMPORARIO2   = FinalVariables.TEMPORARIO2;
    public static final String TEMPORARIO3   = FinalVariables.TEMPORARIO3;
    public static final String TEMPORARIO4   = FinalVariables.TEMPORARIO4;

    public static void sort() {
        try {
            RandomAccessFile arq   = new RandomAccessFile(PRINCIPAL,   "rw");
            RandomAccessFile arqT1 = new RandomAccessFile(TEMPORARIO1, "rw");
            RandomAccessFile arqT2 = new RandomAccessFile(TEMPORARIO2, "rw");
            RandomAccessFile arqT3 = new RandomAccessFile(TEMPORARIO3, "rw");
            RandomAccessFile arqT4 = new RandomAccessFile(TEMPORARIO4, "rw");

            arqT1.setLength(0);
            arqT2.setLength(0);
            arqT3.setLength(0);
            arqT4.setLength(0);

            createPaths(arq, arqT1, arqT2);

            boolean apenasUm = true; // Verifica se os registros foram escritos em apenas um arquivo ou não
            boolean inverteArquivosLeituraEscrita = false; 

            do {
                if (inverteArquivosLeituraEscrita == false) {
                    apenasUm = intercalation(arqT1, arqT2, arqT3, arqT4, apenasUm);
                    if(apenasUm == true){
                        TransferData(arq, arqT3);
                    }

                } else {
                    apenasUm = intercalation(arqT3, arqT4, arqT1, arqT2, apenasUm);
                    if(apenasUm == true){
                        TransferData(arq, arqT1);
                    }
                }
                inverteArquivosLeituraEscrita = !inverteArquivosLeituraEscrita;

            } while (apenasUm != true);

            arqT1.close();
            arqT2.close();
            arqT3.close();
            arqT4.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createPaths(RandomAccessFile arq, RandomAccessFile arqT1, RandomAccessFile arqT2) throws Exception {
        int cont = -1;
        Game jogos[] = new Game[MAX_REGISTROS];
        boolean changeFiles = true;
        char lapide;
        int tamanho, id;
    
        arq.seek(4);
        RandomAccessFile arqT;
        while (arq.getFilePointer() < arq.length()) {
            lapide = arq.readChar();
            tamanho = arq.readInt();
            long pos = arq.getFilePointer();
            id = arq.readInt();
    
            if (lapide != EXCLUIDO) {
                byte[] registro = new byte[tamanho - 4];
                arq.read(registro);
                cont++;
                jogos[cont] = new Game(lapide, tamanho, id, registro);
            }
                
            arq.seek(pos + tamanho);
                
            if ((cont == MAX_REGISTROS - 1) || (arq.getFilePointer() == arq.length() && cont >= 0)) {
                Mergesort.mergeSort(jogos, 0, cont);
    
                if (changeFiles == false) {
                    arqT = arqT1;
                } else {
                    arqT = arqT2;
                }
                changeFiles = !changeFiles;
    
                arqT.seek(arqT.length());
    
                for (int i = 0; i <= cont; i++) {
                    FileOperations.writeGame(jogos[i], arqT);
                }
                cont = -1;
            }
        }
    }

    public static boolean intercalation(RandomAccessFile arqLeitura1, RandomAccessFile arqLeitura2, RandomAccessFile arqEscrita1, RandomAccessFile arqEscrita2,  boolean apenasUm) throws Exception {
        int idAntigo1 = 0;
        int idAntigo2 = 0;

        //Os jogos serão lidos desses arquivos:
        arqLeitura1.seek(0);
        arqLeitura2.seek(0);

        //Os jogos serão escritos nesses arquivos:
        arqEscrita1.seek(0);
        arqEscrita2.seek(0);

        Game jogo1 = FileOperations.readGame(arqLeitura1);
        Game jogo2 = FileOperations.readGame(arqLeitura2);

        RandomAccessFile arqT = arqEscrita1; //arqT é o arquivo de escrita no momento

        boolean idAnteriorMaiorQueAtual1 = false;
        boolean idAnteriorMaiorQueAtual2 = false;

        boolean inverte = true; //Na mudança de "inverte" é trocado qual arquivo de escrita está sendo usado (arqT) 

        boolean FimDoArqLeitura1 = false;
        boolean FimDoArqLeitura2 = false;
        
        apenasUm = true; //Condição de parada, a ordenação somente acaba quando todos os dados estiverem em apenas um arquivo.

        while (FimDoArqLeitura1 != true || FimDoArqLeitura2 != true) {

            if(FimDoArqLeitura1 == true){
                jogo1.id = 2147483647; //Sentinela
            }
            if(FimDoArqLeitura2 == true){
                jogo2.id = 2147483647; //Sentinela
            }
            if(FimDoArqLeitura1 == false){
                if(jogo1.id > idAntigo1){
                    if((jogo1.id < jogo2.id) || (idAnteriorMaiorQueAtual2 == true)){
                        FileOperations.writeGame(jogo1, arqT);
                        idAntigo1 = jogo1.id;

                        if(arqLeitura1.getFilePointer() == arqLeitura1.length()){
                            FimDoArqLeitura1 = true;
                        }
                        else{
                            jogo1 = FileOperations.readGame(arqLeitura1);
                        }
                    }
                }
                else{
                    idAnteriorMaiorQueAtual1 = true;
                }
            }
            if(FimDoArqLeitura2 == false){
                if(jogo2.id > idAntigo2){
                    if((jogo2.id < jogo1.id) || (idAnteriorMaiorQueAtual1 == true)){

                        FileOperations.writeGame(jogo2, arqT);
                        idAntigo2 = jogo2.id;

                        if(arqLeitura2.getFilePointer() == arqLeitura2.length()){
                            FimDoArqLeitura2 = true;
                        }
                        else{
                            jogo2 = FileOperations.readGame(arqLeitura2);
                        }
                    }
                }
                else{
                    idAnteriorMaiorQueAtual2 = true;
                }
            }
            if((idAnteriorMaiorQueAtual1==true && idAnteriorMaiorQueAtual2==true) || (idAnteriorMaiorQueAtual1==true && FimDoArqLeitura2==true) || (idAnteriorMaiorQueAtual2==true && FimDoArqLeitura1==true)){
                if(inverte == false) {
                    arqT = arqEscrita1;
                }else{
                    arqT = arqEscrita2;
                }
                inverte = !inverte;
                apenasUm = false;

                if(FimDoArqLeitura1 == false){
                    idAntigo1 = 0;
                }
                if(FimDoArqLeitura2 == false){
                    idAntigo2 = 0;
                }
                idAnteriorMaiorQueAtual1 = false;
                idAnteriorMaiorQueAtual2 = false;
            }
        }
        return apenasUm;
    }

    
    public static void TransferData(RandomAccessFile arq, RandomAccessFile arqT) throws Exception {
        arq.seek(0);
        int ultimoId = arq.readInt();
        arq.setLength(0);
        arq.writeInt(ultimoId);

        arqT.seek(0);

        while( arqT.getFilePointer() < arqT.length()){

            arq.writeChar( arqT.readChar() ); //lapide
            int tamanho = arqT.readInt();
            arq.writeInt( tamanho );          //tamanho
            long pos = arqT.getFilePointer();
            arq.writeInt( arqT.readInt() );   //id
            byte[] registro = new byte[tamanho - 4];
            arqT.read(registro);
            arq.write(registro);              //resto das informações

            arqT.seek(pos + tamanho);
        }
        System.out.print("== Arquivo Ordenado ==\n");
    }
}