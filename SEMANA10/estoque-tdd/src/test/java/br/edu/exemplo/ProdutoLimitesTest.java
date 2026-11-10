package br.edu.exemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProdutoLimitesTest {
    @Test void deveIgnorarEntradaZero() {
        Produto produto = new Produto("Mouse");
        produto.adicionarEstoque(0);
        assertEquals(0, produto.getQuantidade());
    }
    @Test void deveIgnorarRetiradaZeroENegativa() {
        Produto produto = new Produto("Mouse");
        produto.adicionarEstoque(10);
        produto.retirarEstoque(0);
        produto.retirarEstoque(-3);
        assertEquals(10, produto.getQuantidade());
    }
    @Test void deveRetirarEstoqueExato() {
        Produto produto = new Produto("Mouse");
        produto.adicionarEstoque(10);
        produto.retirarEstoque(10);
        assertEquals(0, produto.getQuantidade());
    }
    @Test void devePreservarEstoqueAoExcederLimiteInteiro() {
        Produto produto = new Produto("Mouse");
        produto.adicionarEstoque(Integer.MAX_VALUE);
        produto.adicionarEstoque(1);
        assertEquals(Integer.MAX_VALUE, produto.getQuantidade());
    }
}
