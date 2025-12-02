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

    /**
     * Remove um item do carrinho.
     * @param item O item a ser removido.
     */
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
            // Chamada recursiva: Se for um Composite, ele somará seus sub-itens.
            // Se for uma Folha (Produto), ele retornará seu preço.
            total += item.getPrecoTotal();
        }
        return total;
    }

    /**
     * Implementação do método do Componente (ICarrinho) para exibir os itens.
     */
    @Override
    public void exibirItens() {
        System.out.println("\n--- Conteúdo do Carrinho ---");
        if (itens.isEmpty()) {
            System.out.println("O carrinho está vazio.");
            return;
        }
        for (ICarrinho item : itens) {
            // Verifica se o item é um Produto para exibir detalhes formatados.
            if (item instanceof Produto) {
                Produto p = (Produto) item;
                System.out.printf("- %s (ID: %d) | Preço: R$ %.2f\n", p.getNome(), p.getId(), p.getPreco());
            } else {
                // Se for outro Composite (ex: um Combo), chama recursivamente o exibirItens.
                item.exibirItens(); 
            }
        }
        System.out.printf("Total Geral: R$ %.2f\n", getPrecoTotal());
        System.out.println("---------------------------");
    }
}

