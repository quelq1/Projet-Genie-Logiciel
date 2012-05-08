
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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
    
    public void testAjoutStation() {
        /*
         * Scénario 1 normal
         */
        String data = "C\r\n0\r\n1\r\nligne\r\nB\r\n2\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Plan test = new Plan();
        Ligne l = new Ligne("ligne");
        test.addLignes(l);
        Station s1 = new Station("A", new Coordonnee(0, 1));
        test.addStation(s1);
        Station s2 = new Station("B", new Coordonnee(0, 2));
        test.addStation(s2);
        Fragment f = new Fragment(s1, s2, 2);
        l.addFragment(f);
        
        Ajoutstation.ajoutStation(test);
        
        //Solution attendu
        Plan attendu = new Plan();
        attendu.addLignes(l);
        attendu.addStation(s1);
        attendu.addStation(s2);
        Station s3 = new Station("C", new Coordonnee(0, 1));
        Fragment f1 = new Fragment(s3, s2, 2);
        l.addFragment(f1);
        
        assertEquals(test.getStations(), attendu.getStations());
      
        
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
    
    /**
     * Test de la méthode getStationExtremite de Plan
     */
    public void testGetStationExtremite() {
        Plan plan = new Plan();
        Ligne ligne = new Ligne("A");
        
        //Pas de station
        List<Station> res = plan.getStationExtremite(ligne);
        List<Station> attendu = new ArrayList<>();

        assertEquals(res, attendu);
        
        //1 fragment dans la ligne
        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Fragment f1 = new Fragment(s1, s2, 1);
        ligne.addFragment(f1);
        
        res = plan.getStationExtremite(ligne);
        
        attendu.add(s2);
        attendu.add(s1);
        
        assertEquals(res, attendu);
        
        //2 station dans la ligne
        Station s3 = new Station("s3");
        Fragment f2 = new Fragment(s1, s3, 3);
        ligne.addFragment(f2);
        
        res = plan.getStationExtremite(ligne);
        
        attendu.clear();
        attendu.add(s2);
        attendu.add(s3);
        
        assertEquals(attendu, res);
    }
}
