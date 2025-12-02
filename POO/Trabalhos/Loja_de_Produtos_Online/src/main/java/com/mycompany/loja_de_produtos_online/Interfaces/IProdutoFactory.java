/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Interfaces;

import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import java.util.Map;

/**
 *
 * @author Matheus
 */
public interface IProdutoFactory {
    Produto criar(String nome, double preco, String descricao, Map<String, Object> parametros);
}
