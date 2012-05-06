/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;

public class testIncident extends TestCase{
    
    public void testIncident()
    {
     
        String   data = "1\r\n5\r\nabc\r\n";
          System.setIn(new ByteArrayInputStream(data.getBytes()));
          Plan p=new Plan("plan.txt");
          Station stest1= new Station("Sauge");
          Incident i= new Incident(5, "abc");
          stest1.setIncident(i);
          
          for(Station s:p.getStations()){
              if(s.equals(stest1))
                s.setIncident(i);
          }
         // Station stest2= new Station("Sauge");
          System.out.println();
        Station.ajoutIncidentStation();
          System.out.println(stest1.getIncident().toString());
          //System.out.println(stest2.toString());
          
          
    }
}
