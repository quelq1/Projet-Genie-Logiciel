
import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import junit.framework.TestCase;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elodie
 */
public class testAjoutStation extends TestCase {
    public void testAjoutStation() {
        Plan p = new Plan() ;
        Main.ajoutstation(p) ;
    }
}
