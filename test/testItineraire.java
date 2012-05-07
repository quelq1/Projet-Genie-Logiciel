/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Calendar;
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
        ArrayList<Fragment> res = RechercheItineraire.getDirections(s1);

        //Sans ligne existante
        assertEquals(new ArrayList<Station>(), res);

        //Sans voisine        
        Fragment f1 = new Fragment(new Station("Rose"), new Station("Narcisse"), 2);
        Fragment f2 = new Fragment(new Station("Narcisse"), new Station("Capucine"), 2);
        Ligne b = new Ligne("B");
        b.addFragment(f1);
        b.addFragment(f2);
        p.addLignes(b);
        res = RechercheItineraire.getDirections(s1);

        assertEquals(new ArrayList<Fragment>(), res);

        //Un voisin mais ligne différente
        Fragment f3 = new Fragment(s1, new Station("Rose"), 2);
        Ligne c = new Ligne("C");
        c.addFragment(f3);
        p.addLignes(c);
        res = RechercheItineraire.getDirections(s1);

        ArrayList<Fragment> attendu = new ArrayList<>();
        attendu.add(f3);
        assertEquals(attendu, res);

        //Doublon dans les fragments
        Ligne d = new Ligne("D");
        d.addFragment(f3);
        p.addLignes(d);
        res = RechercheItineraire.getDirections(s1);

        assertEquals(attendu, res);
    }

    /*
     * Test de la méthode equals d'Itineraire
     */
    public void testEqualsItineraire() {
        Calendar dateLancement = Calendar.getInstance();
        Itineraire it1 = new Itineraire(new Station("Rose"), new Station("Tulipe"), dateLancement);
        assertFalse(it1.equals(null));

        assertTrue(it1.equals(it1));

        Itineraire it2 = new Itineraire(new Station("Rose"), new Station("Tulipe"), dateLancement);
        assertTrue(it1.equals(it2));
        assertTrue(it2.equals(it1));

        it2 = new Itineraire(new Station("Tulipe"), new Station("Rose"), dateLancement);
        assertFalse(it1.equals(it2));
    }

    /*
     * Test de la méthode clone d'Itineraire
     */
    public void testClone() {
        Itineraire i1 = new Itineraire(new Station("s1"), new Station("s2"), Calendar.getInstance());
        assertEquals(i1, i1.clone());
    }

    public Calendar getHeure(int h, int m, int s) {
        Calendar date = Calendar.getInstance();
        date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), h, m, s);
        return date;
    }

    /*
     * Test de la méthode rechercheItineraires de Plan
     */
    public void testRechercheItineraires() {
        Plan p = new Plan();
        RechercheItineraire.initPlan(p, Calendar.getInstance());

        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        p.setStationUtil(s1);

        //Pas de solutions
        //----------------------------------
        ArrayList<Itineraire> sol = new ArrayList<>();
        Itineraire it = new Itineraire(s1, s2, this.getHeure(11, 00, 00));

        RechercheItineraire.rechercheItineraires(it, p.getStationUtil(), null, sol);

        assertEquals(new ArrayList<Itineraire>(), sol);

        //1 seul chemin possible pas d'impasse : s1 -> s3 -> s2
        //------------------------------------
        it = new Itineraire(s1, s2, this.getHeure(11, 00, 00));
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

        RechercheItineraire.rechercheItineraires(it, p.getStationUtil(), null, sol);

        //Résultat attendu
        ArrayList<Itineraire> attendu = new ArrayList<>();
        Itineraire i1 = new Itineraire(s1, s2, this.getHeure(11, 13, 00), 0);
        i1.addStation(s3);
        i1.addStation(s2);
        attendu.add(i1);

        assertEquals(attendu, sol);

        //1 seul chemin possible et impasse
        //-----------------------------------
        it = new Itineraire(s1, s2, this.getHeure(11, 00, 00));
        Station s4 = new Station("s4");
        Fragment f3 = new Fragment(s4, s1, 4);
        Ligne b = new Ligne("B");
        b.addFragment(f3);
        p.addLignes(b);
        sol.clear();

        RechercheItineraire.rechercheItineraires(it, p.getStationUtil(), null, sol);

        //Résultat attendu est le même
        assertEquals(attendu, sol);

        //2 seul chemins possibles sans impasse
        // s1->s3->s2
        // s1->s4->s3->s2
        //-------------------------------------
        it = new Itineraire(s1, s2, this.getHeure(11, 00, 00));
        Fragment f4 = new Fragment(s4, s3, 10);
        b.addFragment(f4);
        sol.clear();

        RechercheItineraire.rechercheItineraires(it, p.getStationUtil(), null, sol);

        //Résultat attendu
        Itineraire i2 = new Itineraire(s1, s2, this.getHeure(11, 33, 00), 1);
        i2.addStation(s4);
        i2.addStation(s3);
        i2.addStation(s2);
        attendu.clear();
        attendu.add(i2);
        attendu.add(i1);

        System.out.println("Att : " + attendu);
        System.out.println("Sol : " + sol);
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
        RechercheItineraire.initPlan(p, this.getHeure(11, 00, 00));

        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Station s3 = new Station("s3");
        Station s4 = new Station("s4");
        Station s5 = new Station("s5");

        p.setStationUtil(s4);

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

        Itineraire it = RechercheItineraire.getItinerairePlusRapide(p.getStationUtil(), s3, this.getHeure(11, 00, 00));

        //Résultat attendu
        Itineraire attendu = new Itineraire(s4, s3, this.getHeure(11, 7, 00), 1);
        attendu.addStation(s1);
        attendu.addStation(s3);

        assertEquals(attendu, it);
    }

    /*
     * Test de la méthode getItineraireParEtapes de Plan
     */
    public void testGetItineraireParEtapes() {
        //Création du plan
        Plan p = new Plan();
        RechercheItineraire.initPlan(p, this.getHeure(11, 0, 0));

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
        Ligne c = new Ligne("C");
        c.addFragment(f5);
        p.addLignes(c);

        //Sans étapes
        //------------------------------
        ArrayList<Station> etapes = new ArrayList<>();

        Itineraire it = RechercheItineraire.getItineraireParEtapes(etapes);

        assertEquals(null, it);

        //Sans étapes intermédiaire
        //------------------------------
        etapes.add(s1);
        etapes.add(s2);

        it = RechercheItineraire.getItineraireParEtapes(etapes);

        //Résultat attendu
        Itineraire attendu = new Itineraire(s1, s2, this.getHeure(11, 8, 00), 0);
        attendu.addStation(s3);
        attendu.addStation(s2);

        assertEquals(attendu, it);

        //Avec 1 seule étape : s1 -> s4 -> s3 sans changement de ligne
        //------------------------------
        etapes.clear();
        etapes.add(s1);
        etapes.add(s4);
        etapes.add(s3);

        System.out.println("____________________");
        it = RechercheItineraire.getItineraireParEtapes(etapes);

        //Résultat attendu
        attendu = new Itineraire(s1, s3, this.getHeure(11, 15, 00), 0);
        attendu.addStation(s4);
        attendu.addStation(s3);

        System.out.println("Att : " + attendu);
        System.out.println("iti : " + it);
        assertEquals(attendu, it);

        //Etape : s1 -> s3 -> s5 avec changement de ligne
        //------------------------------
        etapes.clear();
        etapes.add(s1);
        etapes.add(s3);
        etapes.add(s4);
        etapes.add(s5);

        it = RechercheItineraire.getItineraireParEtapes(etapes);

        //Résultat attendu
        attendu = new Itineraire(s1, s5, this.getHeure(11, 9, 00), 2);
        attendu.addStation(s3);
        attendu.addStation(s4);
        attendu.addStation(s5);

        assertEquals(attendu, it);

        //Etape : s1 -> s2 -> s4 avec impasse
        //------------------------------
        etapes.clear();
        etapes.add(s1);
        etapes.add(s2);
        etapes.add(s4);

        it = RechercheItineraire.getItineraireParEtapes(etapes);

        //Résultat attendu
        attendu = new Itineraire(s1, s4, this.getHeure(11, 16, 00), 1);
        attendu.addStation(s3);
        attendu.addStation(s2);
        attendu.addStation(s3);
        attendu.addStation(s4);

        assertEquals(attendu, it);
    }

    /*
     * Test de la méthode getItineraireMoinsChangement de Plan
     */
    public void testGetItineraireMoinsChangement() {
        //Création du plan
        Plan p = new Plan();
        RechercheItineraire.initPlan(p, this.getHeure(11, 0, 0));

        Station s1 = new Station("s1");
        Station s2 = new Station("s2");
        Station s3 = new Station("s3");
        Station s4 = new Station("s4");
        Station s5 = new Station("s5");

        p.setStationUtil(s1);

        p.addStation(s1);
        p.addStation(s2);
        p.addStation(s3);

        Fragment f1 = new Fragment(s1, s3, 3);
        Fragment f2 = new Fragment(s3, s2, 3);

        Ligne a = new Ligne("A");
        a.addFragment(f1);
        a.addFragment(f2);
        p.addLignes(a);

        Fragment f3 = new Fragment(s4, s1, 1);
        Fragment f4 = new Fragment(s4, s3, 1);
        Ligne b = new Ligne("B");
        b.addFragment(f3);
        b.addFragment(f4);
        p.addLignes(b);


        Fragment f5 = new Fragment(s4, s5, 1);
        Ligne c = new Ligne("C");
        c.addFragment(f5);
        p.addLignes(c);

        Itineraire it = RechercheItineraire.getItineraireMoinsChangement(p.getStationUtil(), s2);

        //Résultat attendu
        Itineraire attendu = new Itineraire(s1, s2, this.getHeure(11, 8, 0), 0);
        attendu.addStation(s3);
        attendu.addStation(s2);

        assertEquals(attendu, it);
    }
}
