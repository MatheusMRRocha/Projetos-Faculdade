/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.Factorys;

import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.ProdutoDigital;
import com.mycompany.loja_de_produtos_online.Interfaces.IProdutoFactory;
import java.util.Map;

/**
 *
 * @author Matheus
 */
public class ProdutoDigitalFactory implements IProdutoFactory {
    @Override
    public Produto criar(String nome, double preco, String descricao, Map<String, Object> parametros) {
        // Obtenção segura do parâmetro específico
        String downloadUrl = (String) parametros.getOrDefault("downloadUrl", "N/A");
        
        return new ProdutoDigital(nome, preco, descricao, downloadUrl);
    }
}
