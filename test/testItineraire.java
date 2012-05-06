/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author Loïc Cimon
 */
public class testItineraire extends TestCase {

    /*
     * Test de la méthode contientStation de Fragment
     */
    public void testContientStation() {
        Station s1 = new Station("Tulipe");
        Station s2 = new Station("Rose");

        Fragment f1 = new Fragment(s1, s2, 2);

        assertFalse(f1.contientStation(null));
        assertFalse(f1.contientStation(new Station("Coucou")));

        assertTrue(f1.contientStation(s1));
        assertTrue(f1.contientStation(s2));

        assertTrue(f1.contientStation(new Station("tulipe")));
        assertTrue(f1.contientStation(new Station("rose")));
    }

    /*
     * Test de la méthode getDirections de Plan
     */
    public void TestGetDirections() {
        Plan p = new Plan();
        Station s1 = new Station("Tulipe");
        ArrayList<Fragment> res = p.getDirections(s1);

        //Sans ligne existante
        assertEquals(new ArrayList<Station>(), res);

        //Sans voisine        
        Fragment f1 = new Fragment(new Station("Rose"), new Station("Narcisse"), 2);
        Fragment f2 = new Fragment(new Station("Narcisse"), new Station("Capucine"), 2);
        Ligne b = new Ligne("B");
        b.addFragment(f1);
        b.addFragment(f2);
        p.addLignes(b);
        res = p.getDirections(s1);

        assertEquals(new ArrayList<Fragment>(), res);

        //Un voisin mais ligne différente
        Fragment f3 = new Fragment(s1, new Station("Rose"), 2);
        Ligne c = new Ligne("C");
        c.addFragment(f3);
        p.addLignes(c);
        res = p.getDirections(s1);

        ArrayList<Fragment> attendu = new ArrayList<>();
        attendu.add(f3);
        assertEquals(attendu, res);

        //Doublon dans les fragments
        Ligne d = new Ligne("D");
        d.addFragment(f3);
        p.addLignes(d);
        res = p.getDirections(s1);

        assertEquals(attendu, res);
    }

    /*
     * Test de la méthode equals d'Itineraire
     */
    public void testEqualsItineraire() {
        Itineraire it1 = new Itineraire(new Station("Rose"), new Station("Tulipe"));
        assertFalse(it1.equals(null));

        assertTrue(it1.equals(it1));

        Itineraire it2 = new Itineraire(new Station("Rose"), new Station("Tulipe"));
        assertTrue(it1.equals(it2));
        assertTrue(it2.equals(it1));

        it2 = new Itineraire(new Station("Tulipe"), new Station("Rose"));
        assertFalse(it1.equals(it2));
    }

    /*
     * Test de la méthode clone d'Itineraire
     */
    public void testClone() {
        Itineraire i1 = new Itineraire(new Station("s1"), new Station("s2"));
        assertEquals(i1, i1.clone());
    }

    /*
     * Test de la méthode rechercheItineraires de Plan
     */
    public void testRechercheItineraires() {
        Plan p = new Plan();
        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        p.setStationUtil(s1);

        //Pas de solutions
        //----------------------------------
        ArrayList<Itineraire> sol = new ArrayList<>();
        Itineraire it = new Itineraire(s1, s2);
        p.rechercheItineraires(it, p.getStationUtil(), null, sol);

        assertEquals(new ArrayList<Itineraire>(), sol);

        //1 seul chemin possible pas d'impasse
        //------------------------------------
        Station s3 = new Station("s3");
        p.addStation(s1);
        p.addStation(s2);
        p.addStation(s3);
        Fragment f1 = new Fragment(s1, s3, 2);
        Fragment f2 = new Fragment(s3, s2, 3);
        Ligne a = new Ligne("A");
        a.addFragment(f1);
        a.addFragment(f2);
        p.addLignes(a);
//        p.rechercheItineraires(it, p.getStationUtil(), sol);

        //Résultat attendu
        ArrayList<Itineraire> attendu = new ArrayList<>();
        Itineraire i1 = new Itineraire(s1, s2, 7, 0);
        i1.addStation(s3);
        i1.addStation(s2);
        attendu.add(i1);

//        assertEquals(attendu, sol);

        //1 seul chemin possible et impasse
        //-----------------------------------
        Station s4 = new Station("s4");
        Fragment f3 = new Fragment(s4, s1, 4);
        Ligne b = new Ligne("B");
        b.addFragment(f3);
        p.addLignes(b);
        sol.clear();
        p.rechercheItineraires(it, p.getStationUtil(), null, sol);

        //Résultat attendu est le même
        assertEquals(attendu, sol);

        //2 seul chemins possibles sans impasse
        //-------------------------------------
        Fragment f4 = new Fragment(s4, s3, 10);
        b.addFragment(f4);
        sol.clear();
        p.rechercheItineraires(it, p.getStationUtil(), null, sol);
        
        //Résultat attendu
        Itineraire i2 = new Itineraire(s1, s2, 21, 1);
        i2.addStation(s4);
        i2.addStation(s3);
        i2.addStation(s2);
        attendu.clear();
        attendu.add(i2);
        attendu.add(i1);

        assertEquals(attendu, sol);

        //2 seul chemins possibles et impasse
        //-------------------------------------
        Station s5 = new Station("s5");
        Fragment f5 = new Fragment(s4, s5, 1);
        Ligne c = new Ligne("c");
        c.addFragment(f5);
        p.addLignes(c);

        //Résultat attendu est le même
        assertEquals(attendu, sol);
    }

    /*
     * Test de la méthode aChangement de Plan
     */
    public void testAChangement() {
        Plan p = new Plan();

        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Station s3 = new Station("s3");
        Station s4 = new Station("s4");

        p.addStation(s1);
        p.addStation(s2);
        p.addStation(s3);
        p.addStation(s4);

        Fragment f1 = new Fragment(s1, s2, 2);
        Fragment f2 = new Fragment(s3, s4, 4);

        //Cas null
        assertFalse(p.aChangement(null, null));
        assertFalse(p.aChangement(f1, null));
        assertFalse(p.aChangement(null, f1));

        // Cas sans changement
        Ligne l1 = new Ligne("A");
        l1.addFragment(f1);
        l1.addFragment(f2);
        p.addLignes(l1);

        assertFalse(p.aChangement(f1, f2));

        // Cas changement avec fragement unique dans les lignes
        p.rmLignes(l1);

        l1 = new Ligne("A");
        l1.addFragment(f1);
        p.addLignes(l1);

        Ligne l2 = new Ligne("B");
        l2.addFragment(f2);
        p.addLignes(l2);

        assertTrue(p.aChangement(f1, f2));

        //Cas changement avec fragment non unique dans les lignes
        l2.addFragment(f1);

        assertFalse(p.aChangement(f1, f2));

    }

    /*
     * Test de la méthode getItinerairePlusRapide de Plan
     */
    public void testGetItinerairePlusRapide() {
        //Création du plan
        Plan p = new Plan();
        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Station s3 = new Station("s3");
        Station s4 = new Station("s4");
        Station s5 = new Station("s5");

        p.setStationUtil(s1);

        p.addStation(s1);
        p.addStation(s2);
        p.addStation(s3);

        Fragment f1 = new Fragment(s1, s3, 2);
        Fragment f2 = new Fragment(s3, s2, 3);

        Ligne a = new Ligne("A");
        a.addFragment(f1);
        a.addFragment(f2);
        p.addLignes(a);

        Fragment f3 = new Fragment(s4, s1, 4);
        Fragment f4 = new Fragment(s4, s3, 10);
        Ligne b = new Ligne("B");
        b.addFragment(f3);
        b.addFragment(f4);
        p.addLignes(b);


        Fragment f5 = new Fragment(s4, s5, 1);
        Ligne c = new Ligne("c");
        c.addFragment(f5);
        p.addLignes(c);

        Itineraire it = p.getItinerairePlusRapide(p.getStationUtil(), s2);

        //Résultat attendu
        Itineraire attendu = new Itineraire(s1, s2, 7, 0);
        attendu.addStation(s3);
        attendu.addStation(s2);

        System.out.println("Sol : " + it);
        System.out.println("Attendu : " + attendu);
        assertEquals(attendu, it);
    }
}