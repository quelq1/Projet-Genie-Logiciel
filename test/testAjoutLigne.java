/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import junit.framework.TestCase;



/**
 *
 * @author Mami Sall
 */
public class testAjoutLigne extends TestCase{
    
    private Plan attendu;
    private Plan resultat;

    @Override
    protected void setUp() {
        attendu = new Plan();
        resultat = new Plan();

        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Station s3 = new Station("s3");
        Station s4 = new Station("s4");
        Station s5 = new Station("s5");

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
    
    public void testAjoutLigneExistante() {
        String data = "3\r\nA\r\n0\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutLigne.menuGestionLigne(resultat);
        
        assertEquals(attendu.toString(), resultat.toString());
    }
    
    public void testAjoutLigneStationExistante() {
        String data = "3\r\nZ\r\n-1\r\n0\r\n1\r\n2\r\ns1\r\ns1\r\na\r\nn\r\ns3\r\n0\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutLigne.menuGestionLigne(resultat);
        
        Ligne z = new Ligne("Z");
        Fragment f = attendu.getFragmentByStations("s1", "s3");
        z.addFragment(f);
        attendu.addLignes(z);
        assertEquals(attendu.toString(), resultat.toString());
    }
}
