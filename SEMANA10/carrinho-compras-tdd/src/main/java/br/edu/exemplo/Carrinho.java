package br.edu.exemplo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Carrinho {
    private final List<Produto> produtos = new ArrayList<>();

    public double getTotal() {
        double subtotal = produtos.stream().mapToDouble(Produto::getPreco).sum();
        return subtotal >= 500 ? subtotal * 0.9 : subtotal;
    }

    public void adicionar(Produto produto) {
        produtos.add(Objects.requireNonNull(produto, "Produto obrigatorio"));
    }

    public void remover(Produto produto) {
        produtos.remove(produto);
    }
}
