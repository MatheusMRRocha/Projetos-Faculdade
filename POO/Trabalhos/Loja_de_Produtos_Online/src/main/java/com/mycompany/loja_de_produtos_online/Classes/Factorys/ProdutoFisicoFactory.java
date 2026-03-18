/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.Factorys;

import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.ProdutoFisico;
import com.mycompany.loja_de_produtos_online.Interfaces.IProdutoFactory;
import java.util.Map;

/**
 *
 * @author Matheus
 */
public class ProdutoFisicoFactory implements IProdutoFactory {

    @Override
    public Produto criar(String nome, double preco, String descricao, Map<String, Object> parametros) {
        
        double peso = (double) parametros.getOrDefault("peso", 0.0);
        String dimensao = (String) parametros.getOrDefault("dimensao", "N/A");
        
        return new ProdutoFisico(nome, preco, descricao, peso, dimensao);
    }
    
}
