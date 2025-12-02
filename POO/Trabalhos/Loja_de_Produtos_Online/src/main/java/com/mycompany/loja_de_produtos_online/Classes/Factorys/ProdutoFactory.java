/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.Factorys;

import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.TiposProduto;
import com.mycompany.loja_de_produtos_online.Interfaces.IProdutoFactory;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matheus
 */
public class ProdutoFactory {
    private final Map<TiposProduto, IProdutoFactory> criadores = new HashMap<>();

    public ProdutoFactory() {
        registrarCriador(TiposProduto.FISICO, new ProdutoFisicoFactory());
        registrarCriador(TiposProduto.DIGITAL, new ProdutoDigitalFactory());
    }

    public void registrarCriador(TiposProduto tipo, IProdutoFactory factoryMethod) {
        this.criadores.put(tipo, factoryMethod);
        System.out.printf("[Factory] Factory Method de %s registrado com sucesso.\n", tipo.name());
    }

    public Produto criarProduto(TiposProduto tipo, String nome, double preco, String descricao, Map<String, Object> parametros) {
        IProdutoFactory factoryMethod = criadores.get(tipo);
        
        if (factoryMethod == null) {
            throw new IllegalArgumentException("Tipo de produto invalido ou nao registrado: " + tipo);
        }

        return factoryMethod.criar(nome, preco, descricao, parametros);
    }
}
