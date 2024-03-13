package Auxiliary;

public class FinalVariables {
    public static final String ARQUIVO_CSV   = "arquivos/InitialFile/jogos.csv";    // Initial File
    public static final String ARQUIVO_DB    = "arquivos/banco.db";                 // File created after transforming the initial .csv file into .db (main file)
    public static final String INDEX         = "arquivos/index.db";                 // Index created from .db file 
    public static final String TEMPORARIO1   = "arquivos/TemporaryFiles/temp1.db";  // Temporary file 1 used in sorting
    public static final String TEMPORARIO2   = "arquivos/TemporaryFiles/temp2.db";  // Temporary file 2 used in sorting
    public static final String TEMPORARIO3   = "arquivos/TemporaryFiles/temp3.db";  // Temporary file 3 used in sorting
    public static final String TEMPORARIO4   = "arquivos/TemporaryFiles/temp4.db";  // Temporary file 4 used in sorting
    public static final String KEY           = "password";                          // The key is used to encrypt and decrypt
    public static final String PATH          = "arquivos/LzwFiles/";                // path used when creating compressed and decompressed files
    public static final char   EXCLUIDO      = '*';                                 // Symbol used to mark deleted games
    public static final int    TAMANHO_MAX   = 4096;                                // Used in LzwCompression and LzwDecompression
    public static final int    MAX_REGISTROS = 10;                                  // Used in Sort
}
