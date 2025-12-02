/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.User;

import com.mycompany.loja_de_produtos_online.Classes.User.CarrinhoDeCompras;
import com.mycompany.loja_de_produtos_online.Interfaces.PagamentoStrategy;

/**
 *
 * @author Matheus
 */
public class ProcessadorPedido {
    private PagamentoStrategy estrategiaPagamento;

    public void setEstrategiaPagamento(PagamentoStrategy estrategia) {
        this.estrategiaPagamento = estrategia;
    }

    public void finalizarPedido(CarrinhoDeCompras carrinho) {
        if (estrategiaPagamento == null) {
            System.err.println("[ERRO] Nenhuma estratégia de pagamento definida.");
            return;
        }

        double valorTotal = carrinho.getPrecoTotal();
        System.out.println("\n--- Processando Pedido ---");
        
        boolean sucesso = estrategiaPagamento.pagar(valorTotal);

        if (sucesso) {
            System.out.println("Pedido finalizado com sucesso!");
        } else {
            System.err.println("Falha ao finalizar o pedido. Tente novamente.");
        }
    }    
}
