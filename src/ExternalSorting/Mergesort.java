package ExternalSorting;

import Auxiliary.Game;

public class Mergesort {

    public static void mergeSort(Game[] jogos, int inicio, int fim) {
        Game[] aux = new Game[jogos.length];
        mergeSort(jogos, aux, inicio, fim);
    }

    private static void mergeSort(Game[] jogos, Game[] aux, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(jogos, aux, inicio, meio);
            mergeSort(jogos, aux, meio + 1, fim);
            merge(jogos, aux, inicio, meio, fim);
        }
    }

    private static void merge(Game[] jogos, Game[] aux, int inicio, int meio, int fim) {
        int i = inicio;
        int j = meio + 1;
        int k = inicio;

        while (i <= meio && j <= fim) {
            if (jogos[i].id <= jogos[j].id) {
                aux[k++] = jogos[i++];
            } else {
                aux[k++] = jogos[j++];
            }
        }
        while (i <= meio) {
            aux[k++] = jogos[i++];
        }
        while (j <= fim) {
            aux[k++] = jogos[j++];
        }
        for (k = inicio; k <= fim; k++) {
            jogos[k] = aux[k];
        }
    }
}