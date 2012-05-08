
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
         * Scénario 1 normal
         */
        String data = "C\r\n0\r\n1\r\nligne\r\nB\r\n2\r\n";
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
        Ajoutstation.ajoutStation(p1);
        
        Plan p2 = new Plan();
        p2.addLignes(l);
        p2.addStation(s1);
        p2.addStation(s2);
        Station s3 = new Station("C", new Coordonnee(0, 1));
        Fragment f1 = new Fragment(s3, s2, 2);
        l.addFragment(f1);
        assertEquals(p1.getStations(), p2.getStations());
      
        
        /* 
         * Scénario 2 nom déjà existant
         */
        
         data = "C\r\n0\r\n1\r\nligne\r\nB\r\n2\r\n";
         System.setIn(new ByteArrayInputStream(data.getBytes()));
         Plan p3= new Plan(); 
         p3.addLignes(l);
         Plan p4= new Plan();
         p4.addLignes(l);
         p4.ajoutLigne();
         
         /*
          * Scénario 3 la latitude et la longitude existent déjà
          */
         
         /*
          * 
          */
    }
}
