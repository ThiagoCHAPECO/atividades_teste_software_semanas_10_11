package br.edu.exemplo;

public class Produto {

    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        if (!Double.isFinite(preco) || preco < 0) {
            throw new IllegalArgumentException("Preco deve ser finito e nao negativo");
        }
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
