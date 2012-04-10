

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mami Sall
 */
public class Plan {

    private Set<Station> stations;
    private Set<Ligne> lignes;

    public Set<Ligne> getLignes() {
        return lignes;
    }

    public Set<Station> getStations() {
        return stations;
    }

    public Plan() {
        stations = new HashSet<Station>();
        lignes = new HashSet<Ligne>();


    }
    
    public void chargementPlan() {
        String chaine = "";
        String fichier = "C:/Users/Mami Sall/Desktop/plan.txt";
        String tab[] = new String[6];
        String tabdep[] = new String[2];
        String tabarr[] = new String[2];
        //lecture du fichier texte	
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;

            while ((ligne = br.readLine()) != null) {

                tab = ligne.split("\t");
                tabdep = tab[1].split(":");
                tabarr = tab[3].split(":");

                //creation des coordonnées et stations 
                Coordonnee cd = new Coordonnee(Double.parseDouble(tabdep[0]), Double.parseDouble(tabdep[1]));
                Station sd = new Station(tab[0], cd);
                Coordonnee ca = new Coordonnee(Double.parseDouble(tabarr[0]), Double.parseDouble(tabarr[1]));
                Station sa = new Station(tab[2], ca);

                //Ajout des stations de depart et d'arrivée si elle n'existe pas
                stations.add(sd);
                stations.add(sa);

                // creation du fragment 
                Fragment d = new Fragment(sd, sa, Integer.parseInt(tab[4]), tab[5]);
                //creation d'une ligne 
                Ligne li = new Ligne(tab[5]);
                //verification de l'existence de la ligne 

                if (lignes.contains(li)) {
                    for (Ligne l : lignes) {
                        if (l.equals(li)) {
                            l.getL().add(d);
                        }
                    }
                } //si la ligne n'existe pas on l'ajoute
                else {
                    lignes.add(li);
                    li.getL().add(d);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
