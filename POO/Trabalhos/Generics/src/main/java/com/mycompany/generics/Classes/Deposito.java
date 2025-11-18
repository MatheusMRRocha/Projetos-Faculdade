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


// Classe agora esta aceitando tipos genericos, representado por T
public class Deposito<T> {
    private List<T> itens = new ArrayList();

    public void adicionarItem(T item){
        this.itens.add(item);
    }

    public T obterItem(int index) {
        return this.itens.get(index);
    }
}
