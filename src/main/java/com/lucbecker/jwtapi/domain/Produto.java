package com.lucbecker.jwtapi.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Produto {

    private Integer id;
    private String nome;
    private Double preco;

    private List<Produto> produtos = new ArrayList<>();

    public Produto() {
    }

    public Produto(Integer id, String nome, Double preco, List<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.produtos = produtos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
