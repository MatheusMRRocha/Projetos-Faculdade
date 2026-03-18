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
    static private double MaiorLance = 0;
    static private List<Lance> lances = new ArrayList<>();
    
    public Leilao (String produto){
        this.produto = produto;
    }

    public List<Lance> getLances() {
        return lances;
    }

    public double getMaiorLance() {
        return MaiorLance;
    }
    
    public static void propoe(Lance lance){
        if (MaiorLance == 0){
            MaiorLance = lance.getValor();
            lances.add(new Lance(Usuario usuario, double valor));
        }
        
    }
    
}
