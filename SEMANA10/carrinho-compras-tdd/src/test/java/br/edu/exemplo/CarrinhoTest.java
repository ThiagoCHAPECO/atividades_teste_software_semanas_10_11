package br.edu.exemplo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CarrinhoTest {
@Test void deveIniciarVazio() {
    assertEquals(0, new Carrinho().getTotal(), 0.001);
}
@Test void deveAdicionarProduto() {
    Carrinho carrinho = new Carrinho();
    carrinho.adicionar(new Produto("Teclado", 100));
    assertEquals(100, carrinho.getTotal(), 0.001);
}
@Test void deveSomarVariosProdutos() {
    Carrinho carrinho = new Carrinho();
    carrinho.adicionar(new Produto("Teclado", 100));
    carrinho.adicionar(new Produto("Mouse", 50));
    assertEquals(150, carrinho.getTotal(), 0.001);
}
@Test void deveRemoverProduto() {
    Carrinho carrinho = new Carrinho();
    Produto mouse = new Produto("Mouse", 50);
    carrinho.adicionar(new Produto("Teclado", 100));
    carrinho.adicionar(mouse);
    carrinho.remover(mouse);
    assertEquals(100, carrinho.getTotal(), 0.001);
}
@Test void deveRejeitarPrecoNegativo() {
    assertThrows(IllegalArgumentException.class, () -> new Produto("Teclado", -100));
}
@Test void deveAplicarDezPorCentoDeDesconto() {
    Carrinho carrinho = new Carrinho();
    carrinho.adicionar(new Produto("Monitor", 600));
    assertEquals(540, carrinho.getTotal(), 0.001);
}
}
