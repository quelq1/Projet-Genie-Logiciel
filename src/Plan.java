

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mami Sall
 */
public class Plan {

    private Set<Station> stations;
    private Set<Ligne> lignes;

    public Set<Ligne> getLignes() {
        return lignes;
    }

    public Set<Station> getStations() {
        return stations;
    }

    public Plan() {
        stations = new HashSet<Station>();
        lignes = new HashSet<Ligne>();


    }
}
