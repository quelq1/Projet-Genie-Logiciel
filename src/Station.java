

/**
 *
 * @author Mami Sall
 */
public class Station {

    private String nom;
    private int TempsArret;
    private Incident incident;
    private Coordonnee coord;

    public Station(String name) {
        nom = name;
    }

    public int getTempsArret() {
        return TempsArret;
    }

    public Coordonnee getCoord() {
        return coord;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public String getNom() {
        return nom;
    }

    public Station(String name, Coordonnee c) {
        nom = name;
        incident = null;
        coord = c;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Station other = (Station) obj;
        if ((this.nom == null) ? (other.nom != null) : !this.nom.equals(other.nom)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.nom != null ? this.nom.hashCode() : 0);
        return hash;
    }
}
