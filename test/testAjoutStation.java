
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
    
    private Plan attendu;
    private Plan resultat;

    @Override
    protected void setUp() {
        attendu = new Plan();
        resultat = new Plan();

        Station s1 = new Station("s1", new Coordonnee(50, 50));
        Station s2 = new Station("s2", new Coordonnee(41, 74));
        Station s3 = new Station("s3", new Coordonnee(11, 53));
        Station s4 = new Station("s4", new Coordonnee(12, 90));
        Station s5 = new Station("s5", new Coordonnee(35, 56));

        attendu.setStationUtil(s1);
        resultat.setStationUtil(s1);

        attendu.addStation(s1);
        attendu.addStation(s2);
        attendu.addStation(s3);
        
        resultat.addStation(s1);
        resultat.addStation(s2);
        resultat.addStation(s3);

        Fragment f1 = new Fragment(s1, s3, 2);
        Fragment f2 = new Fragment(s3, s2, 3);

        Ligne a = new Ligne("A");
        a.addFragment(f1);
        a.addFragment(f2);
        attendu.addLignes(a);
        resultat.addLignes(a);

        Fragment f3 = new Fragment(s4, s1, 4);
        Fragment f4 = new Fragment(s4, s3, 10);
        Ligne b = new Ligne("B");
        b.addFragment(f3);
        b.addFragment(f4);
        attendu.addLignes(b);
        resultat.addLignes(b);


        Fragment f5 = new Fragment(s4, s5, 1);
        Ligne c = new Ligne("C");
        c.addFragment(f5);
        attendu.addLignes(c);
        resultat.addLignes(c);
    }
    
    public void testAjoutStationExistante() {
        String data = "s1\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutStation.menuAjoutStation(resultat);
        
        assertEquals(attendu.toString(), resultat.toString());        
    }
    
    public void testAjoutStationLigneNonExistante() {
        String data = "s10\r\n-1\r\n91\r\n50\r\n50\r\n10\r\n20\r\nZ";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutStation.menuAjoutStation(resultat);
        
        assertEquals(attendu.toString(), resultat.toString());        
    }
    
    public void testAjoutStationNormal() {
        String data = "s10\r\n10\r\n20\r\nA\r\n0\r\n3\r\n1\r\n-1\r\n5\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutStation.menuAjoutStation(resultat);
        
        Station s10 = new Station("s10", new Coordonnee(10, 20));
        attendu.addStation(s10);
        Fragment f = new Fragment(new Station("s1"), s10, 5);
        Ligne ligne = new Ligne("A");
        for (Ligne l : attendu.getLignes()) {
            if (l.equals(ligne)) {
                ligne = l;
                break;
            }
        }
        ligne.addFragment(f);
        
        assertEquals(attendu.toString(), resultat.toString());        
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
