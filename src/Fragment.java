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