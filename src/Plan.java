
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

    public Ligne getLigneCommune(Fragment f1, Fragment f2) {
        if (f1 == null || f2 == null) {
            return null;
        }
        for (Ligne l : lignes) {
            if (l.getListeFragments().contains(f1) && l.getListeFragments().contains(f2)) {
                return l;
            }
        }
        return null;
    }

    public boolean aChangement(Fragment fragPossible, Fragment prec) {
        if (prec == null || fragPossible == null) {
            return false;
        }

        if (this.getLigneCommune(fragPossible, prec) == null) {
            return true;
        } else {
            return false;
        }
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

    public void ajoutIncident() {

        System.out.println("Est-ce que l'incident a lieu sur une station ? (O : oui/N : non) ");
        String reponse;
        Scanner sc = new Scanner(System.in);
        reponse = sc.next();

        if (reponse.compareToIgnoreCase("O") == 0) {
            int cpt = 1;
            for (Station s : stations) {
                System.out.println(cpt + " " + s.getNom());
                cpt++;
            }

            int numstation;
            System.out.println("Quelle station ?");
            numstation = sc.nextInt();

            System.out.println("Quel est la durée de ce nouvel incident ?\n");
            int duree;
            duree = sc.nextInt();

            System.out.println("Ajoutez un commentaire : \n");
            String commentaire;
            commentaire = sc.next();

            Incident inc = new Incident(duree, commentaire);
            stations.get(numstation - 1).setIncident(inc);

        } else {
            int cpt = 1;
            for (Station s : stations) {
                System.out.println(cpt + " " + s.getNom());
                cpt++;
            }

            int statdep;
            int statarriv;
            System.out.println("Quelles sont les stations?");
            System.out.println("Donner la station de départ puis la station d'arrivée");
            statdep = sc.nextInt();
            statarriv = sc.nextInt();

            System.out.println(statdep + " " + statarriv);
            System.out.println(stations.get(statdep - 1) + " " + stations.get(statarriv - 1));

            boolean ok = false;

            for (Ligne l : lignes) {
                for (Fragment f : l.getListeFragments()) {
                    if (f.getStationDep() == stations.get(statdep - 1) && f.getStationArr() == stations.get(statarriv - 1)) {
                        System.out.println("Quel est la durée de ce nouvel incident ?\n");
                        int duree;
                        duree = sc.nextInt();

                        System.out.println("Ajoutez un commentaire : \n");
                        String commentaire;
                        commentaire = sc.next();

                        Incident inc = new Incident(duree, commentaire);
                        f.setIncident(inc);
                        ok = true;
                    }
                }
            }
            if (ok == false) {
                System.out.println("Il n'existe pas de fragment entre ces deux stations");
            }
        }
    }

    public Fragment getFragmentByStations(String s1, String s2) {
        for (Ligne l : lignes) {
            for (Fragment f : l.getListeFragments()) {
                if ((f.getStationArr().getNom().compareTo(s1) == 0 || f.getStationArr().getNom().compareTo(s2) == 0)
                        && ((f.getStationDep().getNom().compareTo(s1) == 0) || f.getStationDep().getNom().compareTo(s2) == 0)) {
                    return f;
                }
            }
        }
        return null;
    }

    public List<Ligne> getLigneByFragment(Fragment f) {
        List<Ligne> res = new ArrayList<>();
        for (Ligne l : lignes) {
            if (l.getListeFragments().contains(f)) {
                res.add(l);
            }
        }
        return res;
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
