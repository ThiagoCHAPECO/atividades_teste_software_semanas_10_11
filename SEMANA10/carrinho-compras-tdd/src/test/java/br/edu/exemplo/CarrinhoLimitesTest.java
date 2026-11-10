package br.edu.exemplo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class CarrinhoLimitesTest {
    @ParameterizedTest
    @CsvSource({"499.99,499.99", "500.00,450.00", "500.01,450.009"})
    void deveRespeitarFronteiraDoDesconto(double subtotal, double esperado) {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Compra", subtotal));
        assertEquals(esperado, carrinho.getTotal(), 0.000001);
    }
    @Test void deveRecalcularDescontoAposRemocao() {
        Carrinho carrinho = new Carrinho();
        Produto mouse = new Produto("Mouse", 100);
        carrinho.adicionar(new Produto("Monitor", 400));
        carrinho.adicionar(mouse);
        assertEquals(450, carrinho.getTotal(), 0.001);
        carrinho.remover(mouse);
        assertEquals(400, carrinho.getTotal(), 0.001);
    }
    @Test void removerProdutoAusenteNaoAlteraTotal() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Mouse", 80));
        carrinho.remover(new Produto("Outro", 10));
        assertEquals(80, carrinho.getTotal(), 0.001);
    }
    @Test void deveRejeitarProdutoNulo() {
        Carrinho carrinho = new Carrinho();
        assertThrows(NullPointerException.class, () -> carrinho.adicionar(null));
        assertEquals(0, carrinho.getTotal());
    }
    @Test void deveAceitarPrecoZero() {
        Carrinho carrinho = new Carrinho();
        carrinho.adicionar(new Produto("Brinde", 0));
        assertEquals(0, carrinho.getTotal());
    }
}
