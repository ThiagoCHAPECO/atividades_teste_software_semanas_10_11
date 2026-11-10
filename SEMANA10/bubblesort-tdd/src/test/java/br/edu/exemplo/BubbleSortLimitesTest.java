package br.edu.exemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BubbleSortLimitesTest {
    @Test void deveAceitarVetorVazio() { conferir(new int[]{}, new int[]{}); }
    @Test void deveAceitarUmElemento() { conferir(new int[]{7}, new int[]{7}); }
    @Test void deveOrdenarNegativosEZero() {
        conferir(new int[]{0, -1, 4, -9}, new int[]{-9, -1, 0, 4});
    }
    @Test void deveOrdenarOrdemInversa() {
        conferir(new int[]{4, 3, 2, 1}, new int[]{1, 2, 3, 4});
    }
    @Test void deveOrdenarExtremosSemOverflow() {
        conferir(new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE},
                new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE});
    }
    private void conferir(int[] entrada, int[] esperado) {
        BubbleSort algoritmo = new BubbleSort(entrada);
        algoritmo.ordenar();
        assertArrayEquals(esperado, algoritmo.getNumeros());
        algoritmo.ordenar();
        assertArrayEquals(esperado, algoritmo.getNumeros());
    }
}
