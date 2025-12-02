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
public class PagamentoBoleto implements PagamentoStrategy {
    private final String cpf;

    public PagamentoBoleto(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean pagar(double valor) {
        System.out.printf("[Pagamento Boleto] Gerando boleto de R$ %.2f...\n", valor);
        System.out.printf("CPF do Cliente: %s\n", cpf);
        System.out.println("Boleto gerado com sucesso. O produto será liberado apos a compensação bancaria.");
        return true;
    }
    
}
