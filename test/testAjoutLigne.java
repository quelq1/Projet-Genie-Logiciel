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
        String data = "A\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutLigne.ajoutLigne(resultat);
        
        assertEquals(attendu.toString(), resultat.toString());
    }
    
    public void testAjoutLigneStationExistante() {
        String data = "Z\r\n-1\r\n0\r\n1\r\n2\r\ns1\r\ns1\r\na\r\nn\r\ns3";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        
        AjoutLigne.ajoutLigne(resultat);
        
        Ligne z = new Ligne("Z");
        Fragment f = attendu.getFragmentByStations("s1", "s3");
        z.addFragment(f);
        attendu.addLignes(z);
        assertEquals(attendu.toString(), resultat.toString());
    }
    
    public void testAjoutLigneCreationStation() {
        String data = "Z\r\n2\r\ns1\r\ns6\r\no\r\n22.2\r\n33.3\r\n";
    }
    
//    public void testAjoutLigne() {       
//        /**
//         * 
//         * premier scenario
//         */
//          String data = "test\r\n2\r\na\r\nb\r\n1\r\n";
//          System.setIn(new ByteArrayInputStream(data.getBytes()));
//        
//            // creation de deux plans p1 et p2 avec une ligne et deux stations avec la méthode classique d'ajout
//            
//            Ligne l= new Ligne("A");
//            Station sd= new Station("depart");
//            Station sa= new Station("arrivee");
//            Fragment f= new Fragment(sa, sd,2);
//            l.addFragment(f);
//            
//            Plan p1= new Plan();
//            p1.addLignes(l);
//            
//            Plan p2= new Plan();
//            p2.addLignes(l);
//        
//            // creation d'ue ligne test avec un fragment et ajout au plan 1 avec la méthode classique
//            
//            Ligne ltest= new Ligne("test");
//            Station s1=new Station("a");
//            Station s2=new Station("b");
//            Fragment ftest=new Fragment(s1,s2,1);
//            ltest.addFragment(ftest);
//            p1.addLignes(ltest);
//            
//            // creation de la même ligne et ajout au plan 2 avec la méthode AJOUTLIGNE
//            Ligne ltest1=new Ligne("test");
//            AjoutLigne.ajoutLigne(p2);
//           
//            
//        assertEquals(p1.getLignes(),p2.getLignes());
//        
//        /* 
//         * Scenario 2 test de l'ajout d'une ligne existante
//         * 
//         */
//         data = "A\r\n2\r\na\r\nb\r\n1\r\n";
//          System.setIn(new ByteArrayInputStream(data.getBytes()));
//            Plan p3= new Plan(); 
//            p3.addLignes(l);
//            Plan p4= new Plan();
//            p4.addLignes(l);
//            AjoutLigne.ajoutLigne(p4);
//            
//            assertEquals(p3.getLignes(),p4.getLignes());
//      
//         /*
//          * Scenario 3: test de l'ajout d'une ligne contenant moins de 2 stations
//          * 
//          */
//          data = "test\r\n1\r\na\r\nb\r\n1\r\n";
//          System.setIn(new ByteArrayInputStream(data.getBytes()));
//  
//            AjoutLigne.ajoutLigne(p4);
//   
//   
//            assertEquals(p3.getLignes(),p4.getLignes());
//   
//       
//        
//        
//        
//       
//    }
}
