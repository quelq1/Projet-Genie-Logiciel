
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
}
