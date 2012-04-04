

/**
 *
 * @author Mami Sall
 */
public class Fragment {

    private int tempsParcours;
    private Incident i;
    private Station depart, arrivee;
    private String ligne;

    public Station getArrivee() {
        return arrivee;
    }

    public Station getDepart() {
        return depart;
    }

    public Incident getI() {
        return i;
    }

    public String getLigne() {
        return ligne;
    }

    public int getTempsParcours() {
        return tempsParcours;
    }

    public Fragment() {
    }

    public Fragment(Station sd, Station sa, int tp, String l) {
        tempsParcours = tp;
        i = null;
        depart = sd;
        arrivee = sa;
        ligne = l;
    }

    @Override
    public String toString() {
        return "tempsParcours=" + tempsParcours + ", depart=" + depart + ", arrivee=" + arrivee + ", ligne=" + ligne;
    }
}
