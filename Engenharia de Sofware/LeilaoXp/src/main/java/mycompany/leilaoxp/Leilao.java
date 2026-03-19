/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.leilaoxp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class Leilao {

    private String produto;
    private double maiorLance = 0;
    private List<Lance> lances = new ArrayList<>();
    
    public Leilao (String produto){
        this.produto = produto;
    }

    public List<Lance> getLances() {
        return lances;
    }

    public double getMaiorLance() {
        return maiorLance;
    }
    
    public boolean propoe(Lance lance){
        if (lance.getValor() <= maiorLance) {
            return false;
        }
        if (!lances.isEmpty()) {
            Usuario ultimoUsuario = lances.get(lances.size() - 1).getUsuario();
            if (ultimoUsuario.getNome().equals(lance.getUsuario().getNome())) {
                return false;
            }
        }
        this.maiorLance = lance.getValor();
        this.lances.add(lance);
        return true;
    }   
    private boolean podeDarLance(Usuario usuario) {
        if (lances.isEmpty()){ 
            return false;
        }else{
            Lance ultimoLance = lances.get(lances.size() - 1);
            return ultimoLance.getUsuario().getNome().equals(usuario.getNome());
        }
    }
}
