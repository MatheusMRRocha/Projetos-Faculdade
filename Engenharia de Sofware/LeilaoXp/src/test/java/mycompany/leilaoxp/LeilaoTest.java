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
        leilao.propoe(new Lance(new Usuario("Steve"), 500.0)); 
        
        assertEquals(1, leilao.getLances().size()); 
        assertEquals(500.0, leilao.getMaiorLance(), 0.0001); 
    }
    @Test
    public void doisLancesMesmoUsuario() {
        Leilao leilao = new Leilao("Noetebook");
        Usuario steve = new Usuario("Steve");
        
        leilao.propoe(new Lance(steve, 2000.0));
        leilao.propoe(new Lance(steve, 3000.0));
        
        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getMaiorLance(), 0.0001);
    }

    @Test
    public void naoDeveAceitarLanceMenorOuIgualAoAnterior() {
        Leilao leilao = new Leilao("Celular");
        Usuario steve = new Usuario("Steve");
        Usuario matheus = new Usuario("Matheus");
        
        leilao.propoe(new Lance(steve, 5000.0));
        boolean aceitouMenor = leilao.propoe(new Lance(matheus, 4500.0));
        boolean aceitouIgual = leilao.propoe(new Lance(matheus, 5000.0));
        
        assertFalse(aceitouMenor, "Lance menor que o atual não aceito");
        assertFalse(aceitouIgual, "Lance igual ao atual não foi aceito");
        assertEquals(1, leilao.getLances().size());
        assertEquals(5000.0, leilao.getMaiorLance(), 0.0001);
    }
 
}
