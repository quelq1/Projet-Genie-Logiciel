
import java.text.DecimalFormat;
import junit.framework.TestCase;

/**
 *
 * @author Loïc Cimon
 */
public class testGeolocalisation extends TestCase {
    
    /*
     * Test de la méthode distance de Coordonnee
     */
    public void testDistance() {
        double distance;
        DecimalFormat f;
        
        Coordonnee c1 = new Coordonnee(1., 1.);
        distance = c1.distance(null);
        
        distance = c1.distance(c1);
        assertEquals(0., distance);
        
        Coordonnee c2 = new Coordonnee(2., 2.);
        distance = c1.distance(c2);        
        f = new DecimalFormat();
        f.setMaximumFractionDigits(4);        
        assertEquals("1,4142", f.format(distance));
        
        distance = c2.distance(c1);        
        f = new DecimalFormat();
        f.setMaximumFractionDigits(4);        
        assertEquals("1,4142", f.format(distance));        
    }
}
