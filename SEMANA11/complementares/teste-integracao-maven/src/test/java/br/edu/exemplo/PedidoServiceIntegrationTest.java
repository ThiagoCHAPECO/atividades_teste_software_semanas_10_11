package br.edu.exemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceIntegrationTest {
    @Test
    void desafioDeveAceitarPedidoDoEstoqueExato() {
        EstoqueService estoque = new EstoqueService(3);
        PedidoService pedido = new PedidoService(estoque);
        assertTrue(pedido.realizarPedido(3));
        assertEquals(0, estoque.getQuantidade());
    }
    @Test
    void deveRealizarPedidoERetirarProdutoDoEstoque() {
        EstoqueService estoque = new EstoqueService(10);
        PedidoService pedido = new PedidoService(estoque);
        assertTrue(pedido.realizarPedido(3));
        assertEquals(7, estoque.getQuantidade());
    }

    @Test
    void naoDeveRealizarPedidoSemEstoqueSuficiente() {
        EstoqueService estoque = new EstoqueService(2);
        PedidoService pedido = new PedidoService(estoque);
        assertFalse(pedido.realizarPedido(3));
        assertEquals(2, estoque.getQuantidade());
    }
}
