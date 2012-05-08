
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class Station implements Serializable {

    private final int nbRameHeure = 12;
    private final int tmpChgmt = 2;
    private int tmpAttenteStation;
    private String nom;
    private Coordonnee coord;
    private transient Incident incident;

    public Station(String name) {
        nom = name;
        incident = null;
        coord = null;
        tmpAttenteStation = 0;
    }

    public Station(String name, Coordonnee c) {
        nom = name;
        incident = null;
        coord = c;
        tmpAttenteStation = 0;
    }

    public Station(String n, Coordonnee c, Incident inci) {
        nom = n;
        coord = c;
        incident = inci;
        tmpAttenteStation = 0;
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
    public int getTempsAttenteStation(Calendar d) {
        int minute = d.get(Calendar.MINUTE);
        int attente = (60 / nbRameHeure) - minute % (60 / nbRameHeure);
 
        if (attente < tmpChgmt) {
            attente = (60 / nbRameHeure) + attente;
        }
        tmpAttenteStation = attente;
        return attente;
    }
    
    public void setTempsAttenteStation(int i) {
        tmpAttenteStation = i;
    }

    //incident
    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident newinci) {
        incident = newinci;
    }

    public boolean equalsIncident(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Station other = (Station) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;

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
    
    public int getTmpArret() {
        return tmpAttenteStation;
    }
}