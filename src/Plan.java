
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
    }
    
    public String formatLigneFichier(Station s1, Station s2, int temps, Ligne ligne) {
        String texte = "\n" + s1.getNom() + "\t" + s1.getCoord().getLatitude() + ":" + s1.getCoord().getLongitude();
        texte += "\t" + s2.getNom() + "\t" + s2.getCoord().getLatitude() + ":" + s2.getCoord().getLongitude();
        texte += "\t" + temps + "\t" + ligne.getNom();
        
        return texte;
    }
}
