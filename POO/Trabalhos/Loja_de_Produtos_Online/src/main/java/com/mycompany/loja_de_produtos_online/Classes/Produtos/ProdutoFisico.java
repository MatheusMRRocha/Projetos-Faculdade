/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.Produtos;

import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;

/**
 *
 * @author Matheus
 */
public class ProdutoFisico extends Produto {
    private double peso;
    private String dimensao;

    public ProdutoFisico(String nome, double preco, String descricao, double peso, String dimensao) {
        super(nome, preco, descricao);
        this.peso = peso;
        this.dimensao = dimensao;
    }

    public double getPeso() {
        return peso;
    }

    public String getDimensao() {
        return dimensao;
    }    
    
    @Override
    public String exibirDetalhes(){
        return String.format("Peso: %.2f kg,Dimensoes: %s", peso, dimensao);
    }
}
