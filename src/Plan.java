
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Plan {

    private List<Station> stations;
    private Set<Ligne> lignes;
    private Station util;

    public Plan() {
        stations = new ArrayList<>();
        lignes = new HashSet<>();
        util = null;
    }

    public Plan(String fichier) {
        stations = new ArrayList<>();
        lignes = new HashSet<>();
        util = null;
        chargementPlan(fichier);
    }

    //Lignes
    public Set<Ligne> getLignes() {
        return lignes;
    }

    public boolean addLignes(Ligne l) {
        return lignes.add(l);
    }

    //Stations
    public List<Station> getStations() {
        return stations;
    }

    public boolean addStation(Station s) {
        return stations.add(s);
    }

    //Station utilisateur
    public Station getStationUtil() {
        return util;
    }

    public void setStationUtil(Station s) {
        util = s;
    }

    private void chargementPlan(String fichier) {
        //lecture du fichier texte	
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            String ligne;
            while ((ligne = br.readLine()) != null) {
                traitementLigne(ligne);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void traitementLigne(String chaine) {
        if (chaine != null) {
            String[] ligne = chaine.split("\t");

            //Station de départ
            String[] coord = ligne[1].split(":");
            Coordonnee cd = new Coordonnee(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
            Station sd = new Station(ligne[0], cd);

            //Station d'arrivée
            coord = ligne[3].split(":");
            Coordonnee ca = new Coordonnee(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
            Station sa = new Station(ligne[2], ca);

            if (!sd.equals(sa)) {

                //Ajout des stations de depart et d'arrivée si elle n'existe pas
                if (!stations.contains(sd)) {
                    stations.add(sd);
                }
                if (!stations.contains(sa)) {
                    stations.add(sa);
                }

                // creation du fragment 
                Fragment d = new Fragment(sd, sa, Integer.parseInt(ligne[4]));

                //creation d'une ligne 
                Ligne li = new Ligne(ligne[5].toUpperCase());

                //verification de l'existence de la ligne 
                if (lignes.contains(li)) {
                    Iterator<Ligne> it = lignes.iterator();
                    Ligne l;
                    while (it.hasNext()) {
                        l = it.next();

                        if (l.equals(li)) {
                            l.addFragment(d);
                        }
                    }
                } //si la ligne n'existe pas on l'ajoute
                else {
                    li.addFragment(d);
                    lignes.add(li);
                }
            }
        }
    }

    @Override
    public String toString() {
        String s = "* Plan :";
        s += "\n\t- Nombre de stations : " + stations.size();
        s += "\n\t- Nombre de lignes : " + lignes.size();
        s += "\n";
        s += "* Stations :";
        Iterator<Station> is = stations.iterator();
        while (is.hasNext()) {
            s += "\n\t- " + is.next();
        }
        s += "\n";
        s += "* Lignes :";
        Iterator<Ligne> il = lignes.iterator();
        while (il.hasNext()) {
            s += "\n\t- " + il.next();
        }
        return s;
    }

    public Station getStationProche(Coordonnee coord) {
        Station res = null, tmp;
        double min, distance;
        
        Iterator<Station> is = stations.iterator();
        if (is.hasNext()) {
            tmp = is.next(); 
            res = tmp;
            min = coord.distance(tmp.getCoord());
            while (is.hasNext()) {
                tmp = is.next();
                distance = coord.distance(tmp.getCoord());
                if (distance < min) {
                    res = tmp;
                    min = distance;
                }
            }
        }

        return res;
    }
    
    
    public void ajoutincident() {
        
        System.out.println("Est-ce que l'incident a lieu sur une station ? (O : oui/N : non) ");
        String reponse;
        Scanner sc = new Scanner(System.in);
	reponse=sc.next();
        
        if (reponse.compareToIgnoreCase("O") == 0) {
            int cpt=1;
            for (Station s : stations) {
                System.out.println(cpt+" "+s.getNom()); 
                cpt++;
            }
            
            int numstation;
            System.out.println("Quelle station ?");
            numstation=sc.nextInt();
                
            System.out.println("Quel est la durée de ce nouvel incident ?\n");
            int duree;
            duree=sc.nextInt();
                
            System.out.println("Ajoutez un commentaire : \n");
            String commentaire;
            commentaire=sc.next();

            Incident inc = new Incident(duree,commentaire);
            stations.get(numstation).setIncident(inc);
        }
        else {
            int cpt=1;
            for (Station s : stations) {
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
            System.out.println(stations.get(statdep)+" "+stations.get(statarriv));

            boolean ok=false;
            
            for (Ligne l : lignes)  {
                for (Fragment f : l.getListeFragments()) {
                    if (f.getStationDep()==stations.get(statdep) && f.getStationArr()==stations.get(statarriv)) {
                        System.out.println("Quel est la durée de ce nouvel incident ?\n");
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
}   
