
import java.util.List;
import java.util.Scanner;


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
        return "Fragment{" + "depart=" + depart + ", arrivee=" + arrivee + ", tempsParcours=" + tempsParcours + ", incident=" + incident + '}';
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
    
    public static Fragment creationLiaisonFragment(Plan plan, Ligne ligne, Station s1, Scanner sc) {
        //Crée le fragment
        List<Station> stationterminus = plan.getStationExtremite(ligne);
        System.out.println("Vous avez la possibilite de lier votre station à :");
        System.out.println("\t1 - " + stationterminus.get(0).getNom());
        System.out.println("\t2 - " + stationterminus.get(1).getNom());
        System.out.println("Quel est votre choix ?");
        int choix = 0;
        boolean saisieOk = false;
        do {
            choix = Main.saisieInt(sc);

            if (0 < choix && choix < 3) {
                saisieOk = true;
            } else {
                System.out.println("Choix incorrect.");
            }
        } while (!saisieOk);
        Station s2 = stationterminus.get(choix - 1);
        
        //Création du fragment
        return saisieFragment(s1, s2, sc);
    }
    
    public static Fragment saisieFragment(Station s1, Station s2, Scanner sc) {        
        System.out.println("Entrer le temps de parcours entre " + s1.getNom() + " et " + s2.getNom() + " :");
        int tmpTrajet = Main.saisieInt(sc);

        return new Fragment(s1, s2, tmpTrajet);
    }
}