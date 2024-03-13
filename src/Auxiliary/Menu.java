package Auxiliary;

public class Menu {
    public static void showMenu(){
        System.out.print("\n\n=====================================\n");
        System.out.print("\t   ===   Menu   ===   \n"                 );
        System.out.print("\n   Opção 0 - Sair"                        );
        System.out.print("\n   Opção 1 - Create"                      );
        System.out.print("\n   Opção 2 - Read"                        );
        System.out.print("\n   Opção 3 - Update"                      );
        System.out.print("\n   Opção 4 - Delete"                      );
        System.out.print("\n   Opção 5 - Ordenar"                     );
        System.out.print("\n   Opção 6 - Compactar banco de dados"    );
        System.out.print("\n   Opção 7 - Descompactar banco de dados" );
        System.out.print("\n   Opção 8 - Sobrescrever banco de dados" );
        System.out.print("\n   Opção 9 - Casamento de padrões"        );
        System.out.print("\n\n=====================================\n");
    }

    public static void overwriteMenu(){
        System.out.print("\n\n------------------------------------------------\n");
        System.out.print("\t   ---   Menu Sobrescrever  ---   \n"                );          
        System.out.print("\n   Opção 1 - Sobrescrever com os Dados Originais"    );
        System.out.print("\n   Opção 2 - Sobrescrever com os Dados de um Arquivo Compactado já existente" );
        System.out.print("\n\n------------------------------------------------\n");
    }
}