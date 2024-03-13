package Auxiliary;

import java.io.*;
import java.util.Date;

import ColumnarTranspositionCipher.Decrypt;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class FileOperations {

    public static void writeGame(Game jogo, RandomAccessFile arq){  
        try{
            arq.writeChar(jogo.lapide);
            arq.writeInt(jogo.tamanho);
            arq.writeInt(jogo.id);
            arq.write(jogo.registro);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Game readGame(RandomAccessFile arq){ 
        try{
            char lapide = arq.readChar();
            int tamanho = arq.readInt();
            int id = arq.readInt();
            byte[] registro  = new byte[tamanho - 4];
            arq.read(registro);
    
            Game jogo = new Game(lapide, tamanho, id, registro);
            return jogo;
                
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void readRegister(byte[] ba, int tamanho, int id) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        
        String nome = dis.readUTF();
        Date data = new Date(dis.readLong());
        String tags = dis.readUTF();
        int tempo_medio = dis.readInt();
        float preco = dis.readFloat();

        nome = Decrypt.decryptName(id);
        
        System.out.print("\n\n=== Entidade encontrada ===\n");
        System.out.print("\nId: " + id);
        System.out.print("\nNome: " + nome);
        System.out.print("\nData de lançamento: " + new SimpleDateFormat("yyyy-MM-dd").format(data));
        System.out.print("\nTags: " + tags);
        System.out.print("\nTempo médio de Jogo: " + tempo_medio);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.print("\nPreço: " + df.format(preco));
        
        int lixo = tamanho - (nome.length() + 8 + tags.length() + 4 + 4);
        
        if (lixo > 0) {
            byte[] resto = new byte[lixo];
            dis.read(resto);
        }
    }
}
