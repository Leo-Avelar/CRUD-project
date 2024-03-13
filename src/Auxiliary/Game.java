package Auxiliary;

import java.util.Date;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Game {
    // lápide - tamanho - id - nome - data - tag - tempoDeJogo - Preço
    public char lapide;
    public int tamanho;
    public int id;
    public String nome;
    public Date data;
    public String tags;
    public int tempo_medio;
    public float preco;
    public byte[] registro;

    public Game(char lapide, int tamanho, int id, String nome, Date data, String tags, int tempo_medio, float preco) { //Com data Date
        this.lapide = lapide;
        this.tamanho = tamanho;
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.tags = tags;
        this.tempo_medio = tempo_medio;
        this.preco = preco;
        this.registro = null;
    }

    public Game(char lapide, int tamanho, int id, String nome, long data, String tags, int tempo_medio, float preco) { //Com data long
        this.lapide = lapide;
        this.tamanho = tamanho;
        this.id = id;
        this.nome = nome;
        this.data = new Date(data);
        this.tags = tags;
        this.tempo_medio = tempo_medio;
        this.preco = preco;
        this.registro = null;
    }

    public Game(char lapide, int tamanho, int id, byte[] registro ) { //Com data long
        this.lapide = lapide;
        this.tamanho = tamanho;
        this.id = id;
        this.nome = null;
        this.data = null;
        this.tags = null;
        this.tempo_medio = -1;
        this.preco = -1;
        this.registro = registro;
    }

    public Game() { //vazio
        this.lapide = ' ';
        this.tamanho = -1;
        this.id = -1;
        this.nome = "";
        this.data = new Date(0);
        this.tags = "";
        this.tempo_medio = -1;
        this.preco = 0F;
    }

    public byte[] createByteArray() throws IOException { //PARA ESCRITA
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        lapide = ' ';
        id = 0;
        tamanho = 0;

        //Id e tamanho serão alterados no Create:
        dos.writeChar(lapide);//lapide
        dos.writeInt(tamanho);
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeLong(data.getTime());
        dos.writeUTF(tags);
        dos.writeInt(tempo_medio);
        dos.writeFloat(preco);
        
        return baos.toByteArray();
    }

    public void readByteArray(byte[] ba) throws IOException { //PARA LEITURA
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        
        id = dis.readInt();
        nome = dis.readUTF();
        data = new Date(dis.readLong());
        tags = dis.readUTF();
        tempo_medio = dis.readInt();
        preco = dis.readFloat();
    }
}


