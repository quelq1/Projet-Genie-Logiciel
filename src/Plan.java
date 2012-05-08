
import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author Mami Sall
 */
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

    public void rmLignes(Ligne l) {
        this.lignes.remove(l);
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
            
            ips.close();
            ipsr.close();
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
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

    public ArrayList<Fragment> getDirections(Station s) {
        ArrayList<Fragment> res = new ArrayList<>();
        Fragment f;

        //Parcours des lignes
        Iterator<Ligne> ligne = lignes.iterator();
        Iterator<Fragment> frag;
        while (ligne.hasNext()) {
            //Parcours des fragments de la ligne
            frag = ligne.next().getListeFragments().iterator();
            while (frag.hasNext()) {
                f = frag.next();

                //Si le fragment contient la station et qu'elle n'est pas déjà dans la liste
                //On ajoute si :
                // - le fragment contient la station de départ
                // - le fragment n'est pas déjà contenu
                // - le fragment n'a pas d'incident
                // - la station d'arrivée n'a pas d'incident

                if (f.contientStation(s)
                        && !res.contains(f)
                        && f.getIncident() == null
                        && f.getDestination(s).getIncident() == null) {
                    res.add(f);
                }
            }
        }
        return res;
    }

    public void rechercheItineraires(Itineraire itineraire, Station s, Fragment fragPrec, ArrayList<Itineraire> sol) {
        if (itineraire.getArrivee().equals(s)) {
            //On fait une copie pour éviter les effets de bords
            Itineraire tmp = itineraire.clone();
            //On ne compte pas le temps d'arrêt à la station d'arrivée.
            tmp.rmDuree(s.getTempsArret());
            //ajoute l'itinéraire à la liste des solutions
            sol.add(tmp);
        } else {
            //On récupère les directions possibles
            ArrayList<Fragment> directions = this.getDirections(s);
            Station dest;

            //On boucle sur les stations possibles
            for (Fragment fragPossible : directions) {
                //On récupère la destination du fragment
                dest = fragPossible.getDestination(s);

                //Vérifie que la station n'a jamais été emprunté
                if (!itineraire.contains(dest)) {
                    //enregistrement de la station
                    itineraire.addStation(dest);
                    //ajout du temps de trajet
                    itineraire.addDuree(fragPossible.getTempsDeParcours() + dest.getTempsArret());

                    if (this.aChangement(fragPossible, fragPrec)) {
                        //incrémente le nombre de changement
                        itineraire.incrChangement();
                    }
                    //appel récursif
                    this.rechercheItineraires(itineraire, dest, fragPossible, sol);

                    //mise à jour des stations possibles
                    directions = this.getDirections(dest);

                    //décrémente le temps de parcours
                    itineraire.rmDuree(fragPossible.getTempsDeParcours() + dest.getTempsArret());
                    if (this.aChangement(fragPossible, fragPrec)) {
                        //décrément le nombre de changement
                        itineraire.decrChangement();
                    }
                    //suppression de la direction et le fragement précédent
                    itineraire.rmLastStation();
                }
            }
        }
    }

    public Itineraire getItinerairePlusRapide(Station dep, Station arr) {
        Itineraire itineraire = new Itineraire(dep, arr);
        ArrayList<Itineraire> solutions = new ArrayList<>();
        this.rechercheItineraires(itineraire, dep, null, solutions);

        //On parcours les chemins pour connaître le plus court
        Itineraire res = null;
        if (!solutions.isEmpty()) {
            int min = solutions.get(0).getDuree();
            for (Itineraire it : solutions) {
                if (it.getDuree() < min) {
                    res = it;
                }
            }
        }
        return res;
    }

    public boolean aChangement(Fragment fragPossible, Fragment prec) {
        if (prec == null || fragPossible == null) {
            return false;
        }

        for (Ligne l : lignes) {
            if (l.getListeFragments().contains(prec) && l.getListeFragments().contains(fragPossible)) {
                return false;
            }
        }
        return true;
    }

    //Ajout ligne
    public void ajoutLigne() {

        Scanner sc1 = new Scanner(System.in);

        System.out.println("Entrez le nom de la ligne à creer:");
        Ligne l = new Ligne(sc1.next());
        if (lignes.contains(l)) {
            System.out.println("la ligne existe déjà!!");
        } else {
            System.out.println("Entrer le nombre de stations à ajouter:");
            int nbreStation = sc1.nextInt();
            if (nbreStation >= 2) {
                ArrayList<Station> ListStationTmp = new ArrayList();
                while (nbreStation != 0) {
                    System.out.println("Entrer le nom de la station:");
                    String s = sc1.next();
                    Station stationTmp = new Station(s);
                    if (!ListStationTmp.contains(stationTmp)) {
                        if (this.stations.contains(stationTmp)) {
                            System.out.println("Station existante...");
                        }

                        ListStationTmp.add(stationTmp);
                        nbreStation--;
                    } else {
                        System.out.println("Vous avez déja saisi cette station!");
                    }

                }


                for (int i = 0; i <= ListStationTmp.size() - 2; i++) {

                    System.out.println("Entrer le temps de parcours entre " + ListStationTmp.get(i) + "et " + ListStationTmp.get(i + 1) + ":");
                    int tempsTmp = sc1.nextInt();
                    Fragment f = new Fragment(ListStationTmp.get(i), ListStationTmp.get(i + 1), tempsTmp);
                    l.addFragment(f);
                }
                lignes.add(l);
                System.out.println("La ligne a été ajoutée!");
            } else {
                System.out.println("Il faut au moins deux stations pour creer la ligne!");
            }

        }
    }

    //Stations
    public List<Station> getStations() {
        return stations;
    }

    public boolean addStation(Station s) {
        return stations.add(s);
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
    
    // ArrayList car station de début et station de fin       
    public List<Station> getStationExtremite(Ligne lig) {
        List<Station> tmp = new ArrayList<>();
        
        //Ajoute toutes les stations
        for (Fragment f : lig.getListeFragments()) {
            tmp.add(f.getStationArr());
            tmp.add(f.getStationDep());
        }
        
        //Supprime les stations contenues 2 fois
        List<Station> res = new ArrayList<>(tmp);
        for (Station s : tmp) {
            if (tmp.indexOf(s) != tmp.lastIndexOf(s)) {
                res.remove(s);
            }
        }        
        return res;
//        Station stationtmp = null ;
//        Station stationtmp2 = null ;
//        List<Station> lstationtmp = new ArrayList();
//        List<Station> lstationtmp1 = new ArrayList();
//        List<Station> lstationtmp2 = new ArrayList();
//        List<Station> stationextremite = new ArrayList() ; 
//        List<Station> stationimpossible = new ArrayList() ;
//        
//        // pour tous les fragments, on getStationDep et getStationArr qu'on stocke ds une variable dans 2 listes de stations temporaires
//        for (Fragment f:lig.getListeFragments()) {
//                lstationtmp1.add(f.getStationDep());
//                lstationtmp2.add(f.getStationArr());
//       }
//       // on concatène nos deux listes temporaires en une liste propre
//       lstationtmp.addAll(lstationtmp1) ; 
//       lstationtmp.addAll(lstationtmp2) ;
//       //pour toutes les stations appartenant à la liste, on regarde s'il y a des doublons -> si oui impossible que ça soit une extremité
//       for (int i = 0 ; i < lstationtmp.size() ; i++) {
//           for (int j=i+1 ; j < lstationtmp.size() ; j++) {
//               if (lstationtmp.get(i).getNom().compareTo(lstationtmp.get(j).getNom()) == 0){
//                   stationtmp = lstationtmp.get(j) ;
//                   stationimpossible.add(stationtmp);
//               }
//           }
//       }
//      
//      lstationtmp.removeAll(stationimpossible); 
//      // on compare notre liste impossible avec notre liste normale et la difference donne les extremités 
//      /*for (int k = 0 ; k < lstationtmp.size() ; k++) { 
//        for (int l = 0 ; l < stationimpossible.size() ; l++) {
//            if (!(lstationtmp.get(k).equals(stationimpossible.get(l)))) {
//                
//                stationtmp2 = lstationtmp.get(k) ;
//                stationextremite.add(stationtmp2);
//            }
//        }
//      } */
//      stationextremite = lstationtmp ;
//        return stationextremite ;
//        
    }    
}

