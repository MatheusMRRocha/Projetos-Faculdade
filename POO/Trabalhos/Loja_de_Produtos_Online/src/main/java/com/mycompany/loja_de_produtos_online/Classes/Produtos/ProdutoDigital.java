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
public class ProdutoDigital extends Produto {
    private String download;

    public ProdutoDigital(String nome, double preco, String descricao, String download) {
        super(nome, preco, descricao);
        this.download = download;
    }

    public String getDownload() {
        return download;
    }
    
    @Override
    public String exibirDetalhes() {
        return String.format("Local de Download: %s", download);
    }
}
