/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.loja_de_produtos_online.Interfaces;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Matheus
 */
public interface IDados<T> {
    void salvar(List<T> dados, String caminhoArquivo) throws IOException;
    List<T> carregar(String caminhoArquivo) throws IOException;
}
