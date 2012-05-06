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
    

    public static void ajoutIncidentFragment() {
            int cpt=1;
            Plan p = new Plan("plan.txt");
            Scanner sc= new Scanner(System.in);
            for (Station s : p.getStations()) {
                System.out.println(cpt+" "+s.getNom()); 
                cpt++;
            }

            int statdep;
            int statarriv;
            System.out.println("Quelles sont les stations?");
            System.out.println("Donner la station de départ puis la station d'arriver");
            statdep=sc.nextInt();
            statarriv=sc.nextInt();

            System.out.println(statdep+" "+statarriv);
            System.out.println(p.getStations().get(statdep)+" "+p.getStations().get(statarriv));

            boolean ok=false;
            String nom=null;
        
            for (Ligne l : p.getLignes())  {
                for (Fragment f : l.getListeFragments()) {
                    if (f.getStationDep()==p.getStations().get(statdep) && f.getStationArr()==p.getStations().get(statarriv)) {
                        System.out.println("Quelle est la durée de ce nouvel incident ?\n");
                        int duree;
                        duree=sc.nextInt();

                        System.out.println("Ajoutez un commentaire : \n");
                        String commentaire;
                        commentaire=sc.next();

                        Incident inc = new Incident(duree,commentaire);
                        f.setIncident(inc);
                        ok=true;
                    }
               }
           }
           if (ok==false) {
               System.out.println("Il n'esiste pas de fragment entre ces deux stations");
           }  
    }

}