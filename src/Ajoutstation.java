
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elodie
 */
public class Ajoutstation {
    
    private static String fichier = "plan.txt";
    
    private int attribut1;
    private String attribut2;
    private boolean visible; // Attribut non-représentatif et donc ignoré
    public static double lati = -4 ;
    public static double longi = -4 ;
    
    @Override
    public boolean equals(Object obj) {
        // Vérification de l'égalité des références
        if (obj==this) {
            return true;
        }
        // Vérification du type du paramètre
        if (obj instanceof Ajoutstation) {
            // Vérification des valeurs des attributs
            Ajoutstation other = (Ajoutstation) obj;
            // Pour les attributs de type primitif
            // on compare directement les valeurs :
            if (this.attribut1 != other.attribut1) {
                return false; // les attributs sont différents 
            }
            // Pour les attributs de type objets 
            // on compare dans un premier temps les références 
            if (this.attribut2 != other.attribut2) {
                // Si les références ne sont pas identiques
                // on doit en plus utiliser equals()
                if (this.attribut2 == null || !this.attribut2.equals(other.attribut2)) {
                    return false; // les attributs sont différents 
                }
            }
            // Si on arrive ici c'est que tous les attributs sont égaux :
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.longi) ^ (Double.doubleToLongBits(this.longi) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.lati) ^ (Double.doubleToLongBits(this.lati) >>> 32));
        return hash;
    }
    
    
    public static void ajoutstation(Plan plan) {
        Set<Ligne> lignes ;
        lignes = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        boolean saisieOk = false ;
        int temps ;
        List<Station> stationterminus ; 

        System.out.print("Quel est le nom de la station que vous souhaitez ajouter ? ");
        String se= sc.next();
        Station saisieParUtil = new Station(se);
        String nomstation = saisieParUtil.getNom() ;
        
        if(plan.getStations().contains(saisieParUtil)) {
            System.out.println("La station que vous souhaitez ajouter existe déjà.");
        }
        else {
            do {
                while(lati > 90 || lati < 0) {
                    System.out.print("A quelle latitude se trouve-t-elle ? ");
                    try {
                            lati = Double.parseDouble(sc.next());
                            saisieOk = true;
                        } catch (NumberFormatException e) {
                            System.out.println("\nChoix incorrect.");
                        }
                }
            } while (!saisieOk) ;

            do {
                while (longi > 90 || longi < 0) {
                        System.out.print("A quelle longitude se trouve-t-elle ? ");
                        try {
                            longi = Double.parseDouble(sc.next());
                            saisieOk = true;
                        } catch (NumberFormatException e) {
                            System.out.println("\nChoix incorrect.");
                        }
                }
                } while (!saisieOk);
            Coordonnee coordo = new Coordonnee(lati, longi) ;
            //System.out.println(coordo);
           // System.out.println(plan.getStations().get(0).getCoord());
           // System.out.println(plan.getStations().get(2));
            // redéfinir Equals :/
            for (int x = 0 ; x < plan.getStations().size() ; x++  ) {
                if (plan.getStations().get(x).getCoord().equals(coordo) == true) {
                   System.out.println("Vous ne pouvez pas ajouter une nouvelle station avec les mêmes coordonnées qu'une déjà existante");
                   
               }
            }  
            
            System.out.print("Sur quelle ligne se trouve-t-elle ? (Tapez la lettre correspondant à la ligne, pour créer une nouvelle ligne, tapez son nom)");
            Ligne ligne = new Ligne(sc.next());
            String str = ligne.getNom() ;

            if (!(plan.getLignes().contains(ligne))) {
                plan.ajoutLigne() ;
            }
            else { 
                // permet de mettre la ligne entree par l'utilisateur en objet Ligne
                boolean trouve = false ;
                Ligne ltmp = null ;
                Iterator<Ligne> is = plan.getLignes().iterator() ;
                while (!trouve && is.hasNext()) {
                    ltmp = is.next() ;
                    if (ltmp.getNom().compareTo(str) == 0) {
                        trouve = true;
                    }
                }

                stationterminus = plan.getStationExtremite(ltmp) ;
                System.out.print("Vous avez la possibilite d'ajouter votre station soit avant "+stationterminus.get(0)+" ou après "+stationterminus.get(1)+". Quel est votre choix ? ");

                Station stationexistante = new Station(sc.next());

                List<Station> s = plan.getStations() ;
                for (int i = 0 ; i < s.size() ; i++ ) {
                    if (s.get(i).getNom().compareTo(stationexistante.getNom()) == 0) {
                        stationexistante = s.get(i) ;
                    }
                } 
                System.out.print("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                temps = sc.nextInt() ; 

                // Ajouts possibles 
                Station nouvellestation = new Station(nomstation, coordo);
                Fragment nouveaufragment = new Fragment(nouvellestation,stationexistante, temps) ;

                // Ajout dans le fichier plan
                Coordonnee coordonnees = stationexistante.getCoord() ; 
                String nomstat = stationexistante.getNom() ;
                Double latitude = coordonnees.getLatitude();
                Double longitude = coordonnees.getLongitude() ;

                //Ecriture dans le fichier
                String texte = "\n"+nomstation+"\t"+lati+":"+longi+"\t"+nomstat+"\t"+latitude+":"+longitude+"\t"+temps+"\t"+str ;
                ecriturefichier(texte) ;

                } 

            
        }
        
    }
    public static void ecriturefichier(String texte) {    
       FileWriter aecrire = null ;
       try{
            aecrire = new FileWriter(fichier, true);
            aecrire.write(texte);
            System.out.println("Votre station a bien ete enregistree. ") ;
            aecrire.close() ;
        } catch (IOException ex) {
            Logger.getLogger(Ajoutstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
