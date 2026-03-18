/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.User;

import com.mycompany.loja_de_produtos_online.Classes.Produtos.Produto;
import com.mycompany.loja_de_produtos_online.Interfaces.ICarrinho;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class CarrinhoDeCompras  implements ICarrinho{
    private final List<ICarrinho> itens;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
    }


    public void adicionarItem(ICarrinho item) {
        this.itens.add(item);
    }

    public void removerItem(ICarrinho item) {
        this.itens.remove(item);
    }

    public void esvaziarCarrinho() {
        this.itens.clear();
        System.out.println("Carrinho de compras esvaziado.");
    }

    @Override
    public double getPrecoTotal() {
        double total = 0.0;
        for (ICarrinho item : itens) {
            total += item.getPrecoTotal();
        }
        return total;
    }

    @Override
    public void exibirItens() {
        System.out.println("\n--- Conteudo do Carrinho ---");
        if (itens.isEmpty()) {
            System.out.println("O carrinho esta vazio.");
            return;
        }
        for (ICarrinho item : itens) {
            if (item instanceof Produto) {
                Produto p = (Produto) item;
                System.out.printf("- %s (ID: %d) | Preço: R$ %.2f\n", p.getNome(), p.getId(), p.getPreco());
            } else {
                item.exibirItens(); 
            }
        }
        System.out.printf("Total Geral: R$ %.2f\n", getPrecoTotal());
        System.out.println("---------------------------");
    }
}

