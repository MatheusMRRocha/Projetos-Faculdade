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
        Deposito<Eletronico> depositoEletronico = new Deposito<>();
        Deposito<Alimento> depositoAlimento = new Deposito<>();
        
        depositoEletronico.adicionarItem(new Eletronico("Smartphone"));
        depositoEletronico.adicionarItem(new Eletronico("Notebook"));
        
        //Amazenamento com erro para apresentação
        //depositoEletronico.adicionarItem(new Alimento("Maçã"));
        //Amazenamento correto
        depositoAlimento.adicionarItem(new Alimento("Maca"));
        
        Eletronico primeiroItemE = depositoEletronico.obterItem(0);
        System.out.println("Item recuperado: " + primeiroItemE);

        Eletronico segundoItemE = depositoEletronico.obterItem(1);
        System.out.println("Item recuperado: " + segundoItemE);
        
        
        Alimento primeiroItemA = depositoAlimento.obterItem(0);
        System.out.println("Item recuperado: "+ primeiroItemA);
        
        /*Utilizado para teste de um lista que aceita qualquer classe
        
        Deposito<Produto> depositoGeral = new Deposito<>();

        depositoGeral.adicionarItem(new Eletronico("Smartphone"));
        depositoGeral.adicionarItem(new Eletronico("Notebook"));
        depositoGeral.adicionarItem(new Alimento("Maçã"));
        
        Produto primeiroItem = depositoGeral.obterItem(0);
        System.out.println("Item recuperado: " + primeiroItem);

        Produto itemProblematico = depositoGeral.obterItem(2);
        System.out.println("Item recuperado: " + itemProblematico);*/
    }
}
