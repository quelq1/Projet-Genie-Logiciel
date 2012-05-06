/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.TestCase;

/**
 *
 * @author Loïc Cimon
 */
public class testChargementPlan extends TestCase {
   
    /*
     * Test de la méthode tequals de Station
     */
    public void testEqualsStation() {
        assertFalse(new Station("rose").equals(null));
        
        assertTrue(new Station("rose").equals(new Station("rose")));
        assertFalse(new Station("rose").equals(new Station("tulipe")));
        
        assertTrue(new Station("rose").equals(new Station("Rose")));
        assertFalse(new Station("rose").equals(new Station("Tulipe")));
        
        assertTrue(new Station("rose", new Coordonnee(1.1, 2.1)).equals(new Station("rose")));
        assertFalse(new Station("rose", new Coordonnee(1.1, 2.1)).equals(new Station("tulipe")));
    }
    
    /*
     * Test de la méthode equals de Ligne
     */
    public void testEqualsLigne() {
        assertFalse(new Ligne("A").equals(null));
        
        assertTrue(new Ligne("A").equals(new Ligne("A")));
        assertFalse(new Ligne("A").equals(new Ligne("B")));
        
        assertTrue(new Ligne("A").equals(new Ligne("a")));
        assertFalse(new Ligne("A").equals(new Ligne("b")));
    }
    
    /*
     * Test de la méthode traitementLigne de Plan
     */
    public void testTraitementLigne() {
        Plan p = new Plan();
        //p.traitementLigne(null);
        
        String chaine;
        //Ne doit rien créer, les stations sont identiques
        chaine = "Sauge	48.91:2.30	Sauge	48.89:2.35	2	A";
        p.traitementLigne(chaine);

        //Crée les stations, le fragment et la ligne
        chaine = "Sauge	48.91:2.30	Capucine	48.89:2.35	2	B";
        p.traitementLigne(chaine);
             
        //Ne crée rien tout existe déjà
        chaine = "Sauge	48.91:2.30	Capucine	48.89:2.35	2	B";
        p.traitementLigne(chaine);
        
        //Crée la station Myosotis et ajoute le fragment à la ligne B (attention à la casse)
        chaine = "Sauge	48.91:2.30	Myosotis	48.87:2.32	3	b";
        p.traitementLigne(chaine);
        
        //On crée le résultat attendu
        Plan res = new Plan();
        
        Station sauge = new Station("Sauge", new Coordonnee(48.91, 2.30));
        Station capucine = new Station("Capucine", new Coordonnee(48.89, 2.35));
        Station myosotis = new Station("Myosotis", new Coordonnee(48.87, 2.32));
        
        res.addStation(sauge);
        res.addStation(capucine);
        res.addStation(myosotis);
        
        assertEquals(res.getStations(), p.getStations());
        
        Fragment f1 = new Fragment(sauge, capucine, 2);
        Fragment f2 = new Fragment(sauge, myosotis, 2);
        Ligne b = new Ligne("B");
        b.addFragment(f1);
        b.addFragment(f2);
        res.addLignes(b);
        
        assertEquals(res.getLignes(), p.getLignes());
    }
}
