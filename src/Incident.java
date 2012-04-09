

/**
 *
 * @author Mami Sall
 */
public class Incident {

    private int duree;
    private String commentaire;

    public Incident(int d) {
        duree = d;
        commentaire = null;
    }
    
    public Incident(int d, String c) {
        duree = d;
        commentaire = c;
    }
}
