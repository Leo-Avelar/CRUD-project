package Convert;

import java.io.*;
import java.util.Date;
import java.text.ParseException;
import org.apache.commons.csv.*;

import Auxiliary.FinalVariables;
import Auxiliary.StringToDate;

public class CsvToDb {
    public static final String ARQUIVO_DB = FinalVariables.ARQUIVO_DB;
    public static final String ARQUIVO_CSV = FinalVariables.ARQUIVO_CSV;

    public static void createDB() {
        try {

            RandomAccessFile arq = new RandomAccessFile(ARQUIVO_DB, "rw");// Criar o arquivo DB
            
            Reader in = new FileReader(ARQUIVO_CSV);// Abrir o arquivo CSV para leitura
            CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withHeader("name", "release_date", "steamspy_tags", "median_playtime", "price");
            Iterable<CSVRecord> records = format.parse(in);

            int idFinal=0;
            
            arq.setLength(0);
            arq.writeInt(idFinal);

            for (CSVRecord record : records) {
                String name = record.get("name");
                Date date = StringToDate.parseDate(record.get("release_date"));
                String tags = record.get("steamspy_tags");
                int medianPlaytime = Integer.parseInt(record.get("median_playtime"));
                float price = Float.parseFloat(record.get("price"));

                int id = ++idFinal;
                char lapide = ' ';
                int tamanho = 0;

                // lápide - tamanho - id - nome - data - tag - tempoDeJogo - Preço
                arq.writeChar ( lapide         );
                long posInicial = arq.getFilePointer();
                arq.writeInt  ( tamanho        );
                arq.writeInt  ( id             );
                arq.writeUTF  ( name           );
                arq.writeLong ( date.getTime() );
                arq.writeUTF  ( tags           );
                arq.writeInt  ( medianPlaytime );
                arq.writeFloat( price          );

                //Para alterar o valor do tamanho:
                long posFinal = arq.getFilePointer();
                tamanho = (int) (posFinal - posInicial -4); // -4 pra tirar a quantidade de bytes de tamanho
                arq.seek(posInicial);
                arq.writeInt(tamanho);
                arq.seek(posFinal); //Volta para o final do arquivo
            }

            arq.seek(0);//Alterar o valor do ultimo id usado:
            arq.writeInt(idFinal);
            arq.close();//arquivo db
            in.close();//arquivo csv

            System.out.print("\nDados gravados com sucesso no arquivo.db");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}