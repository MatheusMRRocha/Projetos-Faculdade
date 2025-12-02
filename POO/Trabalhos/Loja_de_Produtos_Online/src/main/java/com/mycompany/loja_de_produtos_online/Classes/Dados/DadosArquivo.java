/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Classes.Dados;

import com.mycompany.loja_de_produtos_online.Interfaces.IDados;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class DadosArquivo implements IDados {

    @Override
    public void salvar(List dados, String caminhoArquivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            for (Object item : dados) { 
                writer.println(item.toString());
            }
            System.out.printf("[Persistência] Dados salvos com sucesso em: %s\n", caminhoArquivo);
        } catch (IOException e) {
            System.err.printf("[Persistência] Erro ao salvar o arquivo: %s\n", e.getMessage());
            throw e;
        }
    }

    @Override
    public List carregar(String caminhoArquivo) throws IOException {
         List<String> linhas = new ArrayList<>();
        File file = new File(caminhoArquivo);
        
        if (!file.exists()) {
            System.out.printf("[Persistência] Arquivo nao encontrado: %s, Retornando lista vazia.\n", caminhoArquivo);
            return linhas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha);
                }
            }
            System.out.printf("Dados carregados com sucesso de: %s\n", caminhoArquivo);
        } catch (IOException e) {
            System.err.printf("Erro ao carregar o arquivo: %s\n", e.getMessage());
            throw e;
        }
        return linhas;
    }
    
}
