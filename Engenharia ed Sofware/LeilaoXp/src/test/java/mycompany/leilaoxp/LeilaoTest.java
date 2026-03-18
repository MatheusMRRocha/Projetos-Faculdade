/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package mycompany.leilaoxp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Matheus
 */
public class LeilaoTest {
    private Leilao leilao;
    private Lance lance;
    private Usuario usuario;
    @Test 
    public void deveReceberUmLance(){ 
        Leilao leilao = new Leilao("Playstation 5"); 
        Leilao.propoe(new Lance(new Usuario("Steve"), 500.0)); 
        assertEquals(1, leilao.getLances().size()); 
        assertEquals(500.0, leilao.getMaiorLance(), 0.0001); 
    }
}
