

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mami Sall
 */
public class Plan {

    private Set<Station> stations;
    private Set<Ligne> lignes;

    public Plan() {
        stations = new HashSet<>();
        lignes = new HashSet<>();
    }
        
    public Set<Ligne> getLignes() {
        return lignes;
    }

    public boolean addLignes(Ligne l) {
        return lignes.add(l);
    }    

    public Set<Station> getStations() {
        return stations;
    }
    
    public boolean setStations(Station s) {
        return this.stations.add(s);
    }
}
