
import java.util.Objects;

public class Fragment {

    private Station depart, arrivee;
    private int tempsParcours;
    private Incident incident;

    public Fragment(Station sd, Station sa, int tp) {
        tempsParcours = tp;
        incident = null;
        depart = sd;
        arrivee = sa;
    }

    public Fragment(Station dep, Station arr, int tpsparc, Incident inci) {
        depart = dep;
        arrivee = arr;
        tempsParcours = tpsparc;
        incident = inci;
    }

    //depart
    public Station getStationDep() {
        return depart;
    }

    public void setStationDep(Station newdep) {
        depart = newdep;
    }

    //arrivee
    public Station getStationArr() {
        return arrivee;
    }

    public void setStationArr(Station newarr) {
        arrivee = newarr;
    }

    //temps de parcours 
    public int getTempsDeParcours() {
        return tempsParcours;
    }

    public void setTempsDeParcours(int newtpsparc) {
        tempsParcours = newtpsparc;
    }

    //incident
    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident newinci) {
        incident = newinci;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fragment other = (Fragment) obj;
        if (!Objects.equals(this.depart.getNom(), other.depart.getNom())) {
            return false;
        }
        if (!Objects.equals(this.arrivee.getNom(), other.arrivee.getNom())) {
            return false;
        }
        if (!Objects.equals(this.incident, other.incident)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.depart);
        hash = 97 * hash + Objects.hashCode(this.arrivee);
        hash = 97 * hash + this.tempsParcours;
        hash = 97 * hash + Objects.hashCode(this.incident);
        return hash;
    }

    @Override
    public String toString() {
        return "tempsParcours=" + tempsParcours + ", depart=" + depart + ", arrivee=" + arrivee;
    }

    boolean contientStation(Station s) {
        return depart.equals(s) || arrivee.equals(s);
    }

    public Station getDestination(Station station) {
        if (depart.equals(station)) {
            return arrivee;
        } else {
            return depart;
        }
    }
}