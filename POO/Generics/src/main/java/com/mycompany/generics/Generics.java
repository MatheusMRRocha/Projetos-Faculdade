/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.generics;

import com.mycompany.generics.Classes.Alimento;
import com.mycompany.generics.Classes.Deposito;
import com.mycompany.generics.Classes.Eletronico;
import com.mycompany.generics.Classes.Produto;

/**
 *
 * @author Matheus
 */
public class Generics {

// Main.java (Demonstração do problema)
    public static void main(String[] args) {
        Deposito<Produto> depositoGeral = new Deposito<>();

        depositoGeral.adicionarItem(new Eletronico("Smartphone"));
        depositoGeral.adicionarItem(new Eletronico("Notebook"));
        depositoGeral.adicionarItem(new Alimento("Maçã"));
        
        Produto primeiroItem = (Produto) depositoGeral.obterItem(0);
        System.out.println("Item recuperado: " + primeiroItem);

        Produto itemProblematico = (Produto) depositoGeral.obterItem(2);
        System.out.println("Item recuperado: " + itemProblematico);
    }
}
