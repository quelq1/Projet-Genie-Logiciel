
public class Station {

    private String nom;
    private Coordonnee coord;
    private static int tps_darret = 2;
    private Incident incident;

    public Station(String name) {
        nom = name;
    }

    public Station(String name, Coordonnee c) {
        nom = name;
        incident = null;
        coord = c;
    }

    public Station(String n, Coordonnee c, Incident inci) {
        nom = n;
        coord = c;
        incident = inci;
    }

    //nom
    public String getNom() {
        return nom;
    }

    public void setNom(String newname) {
        nom = newname;
    }

    //coordonnee
    public Coordonnee getCoord() {
        return coord;
    }

    public void setCoord(Coordonnee newcoor) {
        coord = newcoor;
    }

    //tps darret
    public int getTempsArret() {
        return tps_darret;
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
        final Station other = (Station) obj;
        if ((this.nom == null) ? (other.nom != null) : !this.nom.toUpperCase().equals(other.nom.toUpperCase())) {
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

    @Override
    public String toString() {
        String s = nom + " (" + coord + ")";
        if (incident != null) {
            s += " - " + incident;
        }
        return s;
    }

}
