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
    
    public void testAjoutLigne() {
        
        
        /****
         * 
         * premier scenario
         */
          String   data = "test\r\n2\r\na\r\nb\r\n1\r\n";
          System.setIn(new ByteArrayInputStream(data.getBytes()));
        
            // creation de deux plans p1 et p2 avec une ligne et deux stations avec la méthode classique d'ajout
            
            Ligne l= new Ligne("A");
            Station sd= new Station("depart");
            Station sa= new Station("arrivee");
            Fragment f= new Fragment(sa, sd,2);
            l.addFragment(f);
            
            Plan p1= new Plan();
            p1.addLignes(l);
            
            Plan p2= new Plan();
            p2.addLignes(l);
        
            // creation d'ue ligne test avec un fragment et ajout au plan 1 avec la méthode classique
            
            Ligne ltest= new Ligne("test");
            Station s1=new Station("a");
            Station s2=new Station("b");
            Fragment ftest=new Fragment(s1,s2,1);
            ltest.addFragment(ftest);
            p1.addLignes(ltest);
            
            // creation de la même ligne et ajout au plan 2 avec la méthode AJOUTLIGNE
            Ligne ltest1=new Ligne("test");
            p2.ajoutLigne();
           
            
        assertEquals(p1.getLignes(),p2.getLignes());
        
        /* 
         * Scenario 2 test de l'ajout d'une ligne existante
         * 
         */
         data = "A\r\n2\r\na\r\nb\r\n1\r\n";
          System.setIn(new ByteArrayInputStream(data.getBytes()));
            Plan p3= new Plan(); 
            p3.addLignes(l);
            Plan p4= new Plan();
            p4.addLignes(l);
            p4.ajoutLigne();
      
         /*
          * Scenario 3: test de l'ajout d'une ligne contenant moins de 2 stations
          * 
          */
          data = "test\r\n1\r\na\r\nb\r\n1\r\n";
          System.setIn(new ByteArrayInputStream(data.getBytes()));
  
            p4.ajoutLigne();
   
   
            assertEquals(p3.getLignes(),p4.getLignes());
   
       
        
        
        
       
    }
}
