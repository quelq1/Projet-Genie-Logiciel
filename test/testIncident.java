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
public class testIncident extends TestCase {
    
    private Incident incident;
    private Plan plan;

    @Override
    protected void setUp() {
        incident = new Incident(5, "Commentaire");
        plan = new Plan();

        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Station s3 = new Station("s3");
        Station s4 = new Station("s4");
        Station s5 = new Station("s5");

        plan.setStationUtil(s1);

        plan.addStation(s1);
        plan.addStation(s2);
        plan.addStation(s3);

        Fragment f1 = new Fragment(s1, s3, 2);
        Fragment f2 = new Fragment(s3, s2, 3);

        Ligne a = new Ligne("A");
        a.addFragment(f1);
        a.addFragment(f2);
        plan.addLignes(a);

        Fragment f3 = new Fragment(s4, s1, 4);
        Fragment f4 = new Fragment(s4, s3, 10);
        Ligne b = new Ligne("B");
        b.addFragment(f3);
        b.addFragment(f4);
        plan.addLignes(b);


        Fragment f5 = new Fragment(s4, s5, 1);
        Ligne c = new Ligne("C");
        c.addFragment(f5);
        plan.addLignes(c);
    }
    
//    public void testIncident() {        
//        String data = "a\r\n-1\r\n1\r\nO\r\ns3\r\n-1\r\na\r\n5\r\nCommentaire\r\n";
//        System.setIn(new ByteArrayInputStream(data.getBytes()));
//        
//        AjoutIncident.menuAjoutIncident(plan);
//        
//        Station s = new Station("s3");
//        s.setIncident(incident);        
//        
//        for (Station s1 : plan.getStations()) {
//            if (s1.equals(s)) {
//                assertEquals(s.getIncident().toString(), s1.getIncident().toString());
//            }
//        }
//    }
    
    public void testFragment() {
        /*
         * Scénario 1 : Fragment existe pas
         */
        String data = "N\r\ns1\r\ns2\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutIncident.menuAjoutIncident(plan);
        
        Fragment sol = plan.getFragmentByStations("s1", "s2");
        assertEquals(null, sol);
        
        /*
         * Scénario 1 : Fragment existe pas
         */
        data = "N\r\ns1\r\ns3\r\n5\r\nCommentaire\r\n0\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutIncident.menuAjoutIncident(plan);
        sol = plan.getFragmentByStations("s1", "s3");
        assertEquals(incident.toString(), sol.getIncident().toString());
    }
}
