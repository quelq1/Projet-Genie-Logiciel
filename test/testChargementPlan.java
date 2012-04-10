/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Loïc Cimon
 */
public class testChargementPlan {

    public testChargementPlan() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testEqualsStation() {
        assertFalse(new Station("rose").equals(null));
        
        assertTrue(new Station("rose").equals(new Station("rose")));
        assertFalse(new Station("rose").equals(new Station("tulipe")));
        
        assertTrue(new Station("rose", new Coordonnee(1.1, 2.1)).equals(new Station("rose")));
        assertFalse(new Station("rose", new Coordonnee(1.1, 2.1)).equals(new Station("tulipe")));
    }
    
    @Test
    public void testEqualsLigne() {
        assertFalse(new Ligne("A").equals(null));
        
        assertTrue(new Ligne("A").equals(new Ligne("A")));
        assertFalse(new Ligne("A").equals(new Ligne("B")));
    }
}
