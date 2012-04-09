

/**
 *
 * @author Mami Sall
 */
public class Fragment {

    private int tempsParcours;
    private Incident incident;
    private Station depart, arrivee;
    
    public Fragment(Station sd, Station sa, int tp) {
        tempsParcours = tp;
        incident = null;
        depart = sd;
        arrivee = sa;
    }

    public Station getStationDepart() {
        return depart;
    }
    
    public Station getStationArrivee() {
        return arrivee;
    }
    
    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }    

    public int getTempsParcours() {
        return tempsParcours;
    }

    @Override
    public String toString() {
        return "tempsParcours=" + tempsParcours + ", depart=" + depart + ", arrivee=" + arrivee;
    }
}
