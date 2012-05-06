
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import junit.framework.TestCase;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Elodie
 */
public class testAjoutStation extends TestCase {

    public void testAjoutStation() throws IOException {
        /*
         * Scénario 1
         */
        String data = "C\r\n0\r\n1\r\nligne\r\nB\r\n2\r\n"; //scenario normal
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Plan p1 = new Plan();
        Ligne l = new Ligne("ligne");
        p1.addLignes(l);
        Station s1 = new Station("A", new Coordonnee(0, 1));
        p1.addStation(s1);
        Station s2 = new Station("B", new Coordonnee(0, 2));
        p1.addStation(s2);
        Fragment f = new Fragment(s1, s2, 2);
        l.addFragment(f);
        Ajoutstation.ajoutstation(p1);
        
        Plan p2 = new Plan();
        p2.addLignes(l);
        p2.addStation(s1);
        p2.addStation(s2);
        Station s3 = new Station("C", new Coordonnee(0, 1));
        Fragment f1 = new Fragment(s3, s2, 2);
        l.addFragment(f1);
        assertEquals(p1.getStations(), p2.getStations());
      
        
        /* /*
         * Scénario 2
         */
        /*
         * data = "A\r\n0.00\r\n1\r\nA\r\n1\r\n" ; //latitude en double
         * System.setIn(new ByteArrayInputStream(data.getBytes()));
         * Ajoutstation.ajoutstation(p) ; assertEquals(s, p.getStationUtil());
         *
         * /*
         * Scénario 3
         */
        /*
         * data = "A\r\n0\r\n1.00\r\nA\r\n1\r\n" ; //longitude en double
         * System.setIn(new ByteArrayInputStream(data.getBytes()));
         * Ajoutstation.ajoutstation(p) ; assertEquals(s, p.getStationUtil());
         *
         * /*
         * Scénario 4
         */
        /*
         * data = "A\r\n0\r\n1.000\r\nA\r\n1\r\n" ; //longitude incorrecte
         * System.setIn(new ByteArrayInputStream(data.getBytes()));
         * Ajoutstation.ajoutstation(p) ; assertFalse(s2, p.getStationUtil());
         *
         * /*
         * Scénario 5
         */
        /*
         * data = "A\r\n0.000\r\n1.00\r\nA\r\n1\r\n" ; //latitude incorrecte
         * System.setIn(new ByteArrayInputStream(data.getBytes()));
         * Ajoutstation.ajoutstation(p) ; assertFalse(s2, p.getStationUtil());
         */
    }
}
