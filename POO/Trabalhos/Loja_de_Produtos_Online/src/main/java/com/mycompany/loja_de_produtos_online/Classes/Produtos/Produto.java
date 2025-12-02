/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.Produtos;

import com.mycompany.loja_de_produtos_online.Interfaces.ICalculoPedido;
import com.mycompany.loja_de_produtos_online.Interfaces.ICarrinho;

/**
 *
 * @author Matheus
 */
public abstract class Produto implements ICalculoPedido, ICarrinho {
    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private static int proximoId = 1; 

    public Produto(String nome, double preco, String descricao) {
        this.id = proximoId++;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getId() {
        return id;
    }

    public double getPreco() {
        return preco;
    }
    
    public abstract String exibirDetalhes();
    
    @Override
    public double getPrecoTotal() {
        return this.preco;
    }
    
    @Override
    public void exibirItens() {
        System.out.println(this.toString()); 
    }
    
    @Override
    public String toString() {
        return String.format("[%d] %s - R$ %.2f", id, nome, preco);
    }
}
