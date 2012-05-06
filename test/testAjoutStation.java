
import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import junit.framework.TestCase;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elodie
 */
public class testAjoutStation extends TestCase {
    public void testAjoutStation() {
        Plan p = new Plan() ;
        Station s = new Station("Sauge", new Coordonnee(48.91, 2.30)) ;
        Station s2 = new Station("Sauge", new Coordonnee(48.912, 2.302)) ;
        /*
         * Scénario 1
        */ 
        String data = "A\r\n0\r\n1\r\nA\r\n1\r\n" ; //scenario normal
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.ajoutstation(p) ;
        assertEquals(s, p.getStationUtil());
        
        /*
         * Scénario 2
        */ 
        data = "A\r\n0.00\r\n1\r\nA\r\n1\r\n" ; //latitude en double
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.ajoutstation(p) ;
        assertEquals(s, p.getStationUtil());
        
        /*
         * Scénario 3
        */ 
        data = "A\r\n0\r\n1.00\r\nA\r\n1\r\n" ; //longitude en double
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.ajoutstation(p) ;
        assertEquals(s, p.getStationUtil());
        
        /*
         * Scénario 4
         */
        data = "A\r\n0\r\n1.000\r\nA\r\n1\r\n" ; //longitude incorrecte
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.ajoutstation(p) ;
        assertFalse(s2, p.getStationUtil());
        
        /*
         * Scénario 5
         */
        data = "A\r\n0.000\r\n1.00\r\nA\r\n1\r\n" ; //latitude incorrecte
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Main.ajoutstation(p) ;
        assertFalse(s2, p.getStationUtil());
    }
}
