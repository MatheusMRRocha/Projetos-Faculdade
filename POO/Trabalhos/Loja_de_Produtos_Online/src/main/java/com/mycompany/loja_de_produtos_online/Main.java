/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loja_de_produtos_online;

import com.mycompany.loja_de_produtos_online.Classes.Dados.Catalogo;
import com.mycompany.loja_de_produtos_online.Classes.Dados.DadosArquivo;
import com.mycompany.loja_de_produtos_online.Classes.Factorys.ProdutoFactory;
import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import com.mycompany.loja_de_produtos_online.Classes.Terminal;
import com.mycompany.loja_de_produtos_online.Interfaces.IDados;

/**
 *
 * @author Matheus
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("--- Ligando Loja Online ---");

        ProdutoFactory factory = new ProdutoFactory();

        IDados<String> dados = new DadosArquivo(); 
        
        Catalogo<Produto> catalogo = new Catalogo<>(factory, dados);

        Terminal terminal = new Terminal(catalogo, factory);
        
        terminal.iniciar();
    }
}
