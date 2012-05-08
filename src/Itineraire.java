
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Loïc Cimon
 */
public class Itineraire {

    private ArrayList<Station> trajet;
    private Station depart;
    private Station arrivee;
    private Calendar dateArr;
    private int nbChangement;

    public Itineraire(Station dep, Station arr, Calendar date) {
        this.trajet = new ArrayList<>();
        //On ajoute la station de départ
        trajet.add(dep);
        depart = dep;
        arrivee = arr;
        dateArr = date;
        nbChangement = 0;
    }

    /*
     * Constructeur pour la recherche d'itinéraire par étapes (sans ville
     * d'arrivée)
     */
    public Itineraire(Station depart, Calendar date) {
        this.trajet = new ArrayList<>();
        this.depart = depart;
        dateArr = date;
        nbChangement = 0;
    }

    public Itineraire(Station dep, Station arr, Calendar date, int nb) {
        this.trajet = new ArrayList<>();
        //On ajoute la station de départ
        trajet.add(dep);
        depart = dep;
        arrivee = arr;
        dateArr = date;
        nbChangement = nb;
    }

    public boolean addStation(Station s) {
        return trajet.add(s);
    }

    @Override
    public Itineraire clone() {
        Itineraire it = new Itineraire(depart, arrivee, dateArr);
        it.trajet = (ArrayList<Station>) this.trajet.clone();
        it.dateArr = (Calendar) this.dateArr.clone();
        it.nbChangement = this.nbChangement;
        return it;
    }

    //Useless
    public boolean contains(Station s) {
        return trajet.contains(s);
    }

    //Useless
    public boolean rmLastStation() {
        Station last = trajet.get(trajet.size() - 1);
        return trajet.remove(last);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Itineraire other = (Itineraire) obj;
        if (!this.trajet.equals(other.trajet)) {
            return false;
        }

        if (!this.arrivee.equals(other.arrivee)) {
            return false;
        }

        if (!this.depart.equals(other.depart)) {
            return false;
        }

        if (this.dateArr.get(Calendar.YEAR) != other.dateArr.get(Calendar.YEAR)
                || this.dateArr.get(Calendar.MONTH) != other.dateArr.get(Calendar.MONTH)
                || this.dateArr.get(Calendar.DAY_OF_MONTH) != other.dateArr.get(Calendar.DAY_OF_MONTH)
                || this.dateArr.get(Calendar.HOUR) != other.dateArr.get(Calendar.HOUR)
                || this.dateArr.get(Calendar.MINUTE) != other.dateArr.get(Calendar.MINUTE)
                || this.dateArr.get(Calendar.SECOND) != other.dateArr.get(Calendar.SECOND)
                ) {
            return false;
        }

        if (this.nbChangement != other.nbChangement) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.trajet);
        hash = 89 * hash + Objects.hashCode(this.depart);
        hash = 89 * hash + Objects.hashCode(this.arrivee);
        return hash;
    }

    public void addDuree(int i) {
        dateArr.add(Calendar.MINUTE, i);
    }

    @Override
    public String toString() {
        String s = "De " + depart + " à " + arrivee + "[ Arrivée : " + dateArr.getTime() + " - Nombre de changement : " + nbChangement + "] : ";
        for (Station station : trajet) {
            s += station.getNom() + ", ";
        }
        return s;
    }

    public Station getArrivee() {
        return arrivee;
    }

    public Calendar getDateArrivee() {
        return dateArr;
    }

    public int getNbChangement() {
        return nbChangement;
    }

    public void incrChangement() {
        nbChangement++;
    }

    public void decrChangement() {
        nbChangement--;
    }

    public void concatItineraire(Itineraire i, Plan p) {
        this.arrivee = i.arrivee;
        
        this.dateArr = (Calendar) i.dateArr.clone();
        
        this.nbChangement += i.nbChangement;
        //Vérifie si y a un changement de ligne entre les 2 itinéraires
        Fragment prec = p.getFragmentByStations(this.trajet.get(this.trajet.size() - 1).getNom(), this.trajet.get(this.trajet.size() - 2).getNom());
        Fragment suiv = p.getFragmentByStations(i.trajet.get(0).getNom(), i.trajet.get(1).getNom());
        if (p.aChangement(prec, suiv)) {
            this.nbChangement++;
        }
        
        i.trajet.remove(0);
        this.trajet.addAll(i.trajet);

//        if (!this.trajet.contains(s)) {
//            this.trajet.add(s);
//        } else {
//            this.duree += s.getTempsArret();
//            Fragment prec = p.getFragmentByStations(s.getNom(), this.trajet.get(this.trajet.size() - 2).getNom());
//            Fragment suiv = p.getFragmentByStations(s.getNom(), i.trajet.get(1).getNom());
//            
//            if (p.aChangement(prec, suiv)) {
//                this.nbChangement++;
//            }
//        }

    }

    public Station getStation(int i) {
        if (trajet.size() <= i) {
            return null;
        }
        return trajet.get(i);
    }

    public int getSize() {
        return trajet.size();
    }

    public ArrayList<Station> getTrajet() {
        return trajet;
    }
}
