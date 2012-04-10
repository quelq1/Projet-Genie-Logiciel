
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Mami Sall
 */
public class Plan {

    private Set<Station> stations;
    private Set<Ligne> lignes;

    public Plan() {
        stations = new HashSet<>();
        lignes = new HashSet<>();
    }
        
    public Set<Ligne> getLignes() {
        return lignes;
    }

    public boolean addLignes(Ligne l) {
        return lignes.add(l);
    }    

    public Set<Station> getStations() {
        return stations;
    }
    
    public boolean setStations(Station s) {
        return this.stations.add(s);
    }

    public void chargementPlan() {
        String fichier = "C:/Users/Mami Sall/Desktop/plan.txt";
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
        String[] ligne = chaine.split("\t");

        //Station de départ
        String[] coord = ligne[1].split(":");
        Coordonnee cd = new Coordonnee(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
        Station sd = new Station(ligne[0], cd);

        //Station d'arrivée
        coord = ligne[3].split(":");
        Coordonnee ca = new Coordonnee(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
        Station sa = new Station(ligne[2], ca);

        //Ajout des stations de depart et d'arrivée si elle n'existe pas
        stations.add(sd);
        stations.add(sa);

        // creation du fragment 
        Fragment d = new Fragment(sd, sa, Integer.parseInt(ligne[4]), ligne[5]);

        //creation d'une ligne 
        Ligne li = new Ligne(ligne[5]);

        //verification de l'existence de la ligne 
        if (lignes.contains(li)) {
            Iterator<Ligne> it = lignes.iterator();
            Ligne l;
            while (it.hasNext()) {
                l = it.next();

                if (l.equals(li)) {
                    l.getL().add(d);
                }
            }
        } //si la ligne n'existe pas on l'ajoute
        else {
            li.getL().add(d);
            lignes.add(li);
        }
    }
}
