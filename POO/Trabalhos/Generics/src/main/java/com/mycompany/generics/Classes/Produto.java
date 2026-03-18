/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.generics.Classes;

/**
 *
 * @author Matheus
 */

// Produto.java (Classe base)
public abstract class Produto{
    private String nome;
    
    public Produto(String nome){
        this.nome = nome; 
    }
    public String getNome(){
        return nome;
    }
    @Override
    public String toString(){
        return "Produto: " + nome; 
    }
}
