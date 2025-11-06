/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generics.Classes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus
 */


// Deposito.java (A classe com o problema)
public class Deposito {
    private List itens = new ArrayList(); // Usa List sem tipo!

    public void adicionarItem(Object item) {
        this.itens.add(item);
    }

    public Object obterItem(int index) {
        return this.itens.get(index);
    }
}
