/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 *
 * @author Loïc Cimon
 */
public class testFavoris extends TestCase {

    private Plan plan;

    @Override
    protected void setUp() {
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

    /**
     * Test de la méthode menuGestionFavoris de FavorisUtilisateur.
     */
    @Test
    public void testMenuGestionFavoris() {
        /*
         * Scénario 1 : Cas tordu
         */
        String data = "A\r\n3\r\n-1\r\n1\r\n0\r\n2\r\n0\r\n0\r\n0";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        FavorisUtilisateur.menuGestionFavoris(plan);

        List<Station> res = new ArrayList<>();
        
        assertEquals(res, FavorisUtilisateur.getListeFavoris());

        /*
         * Scénario 2 :Ajout de la station s1
         */
        data = "1\r\n5\r\nc\r\n-1\r\ns1\r\n0\r\n0\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        FavorisUtilisateur.menuGestionFavoris(plan);

        res.add(new Station("s1"));


        assertEquals(res, FavorisUtilisateur.getListeFavoris());
        
        /*
         * Scénario 3 : Suppression de la station s1
         */
        
        data = "2\r\nc\r\n-1\r\n2\r\n1\r\n0\r\n0\r\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        FavorisUtilisateur.menuGestionFavoris(plan);

        res.remove(0);

        assertEquals(res, FavorisUtilisateur.getListeFavoris());
    }
}
