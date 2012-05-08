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
public class testIncident extends TestCase{
    Plan p1= new Plan("plan.txt");
    String   data = "O\r\n1\r\n5\r\nabc\r\n";
    Incident i = new Incident(5, "abc");
          
    
    public void testIncident()
    {   System.setIn(new ByteArrayInputStream(data.getBytes()));
         
          
          Station s= new Station("Sauge");
          
          s.setIncident(i);
          
          
          p1.ajoutIncident();
          for(Station s1: p1.getStations())
          {
              if(s1.equals(s))
              
                  assertEquals(s.getIncident().toString(),s1.getIncident().toString());
           
          }
    }
    public void testFragment()
    {
          data = "N\r\n1\r\n2\r\n5\r\nabc\r\n";
          System.setIn(new ByteArrayInputStream(data.getBytes()));
          Station s1= new Station("Sauge");
          Station s2= new Station("Capucine");
          Fragment f= new Fragment(s1,s2,5);
          f.setIncident(i);
           //System.out.print(f.getIncident());
        p1.ajoutIncident();
         for(Ligne l:p1.getLignes())
          {
              for(Fragment f1: l.getListeFragments())
              {
                if(f1.getStationDep().equals(s1) && f1.getStationArr().equals(s2))
                 assertEquals(f.getIncident().toString(),f1.getIncident().toString());
              }
                 
                 
          }
         // System.setIn(new ByteArrayInputStream(data.getBytes()));
              
                
    }
    
}
