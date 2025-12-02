/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.Pagamento;

import com.mycompany.loja_de_produtos_online.Interfaces.PagamentoStrategy;

/**
 *
 * @author Matheus
 */
public class PagamentoCartao implements PagamentoStrategy {
    private final String nomeTitular;
    private final String numeroCartao; // Simplificado

    public PagamentoCartao(String nomeTitular, String numeroCartao) {
        this.nomeTitular = nomeTitular;
        this.numeroCartao = numeroCartao;
    }

    @Override
    public boolean pagar(double valor) {
        System.out.printf("[Pagamento Cartão] Processando debito de R$ %.2f...\n", valor);
        String numeroFormatado = numeroCartao.length() >= 4 ? numeroCartao.substring(0, 4) + " **** **** ****" : "ERRO";
        System.out.printf("Titular: %s | Cartão: %s\n", nomeTitular, numeroFormatado);
        System.out.println("Transação aprovada com sucesso via Cartao de Credito.");
        return true;
    }
    
    
}
