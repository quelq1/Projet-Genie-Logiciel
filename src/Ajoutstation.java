
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
    
    //TODO : Mettre l'écriture dans le fichier dans une fonction à part
    public static void ajoutstation(Plan plan) throws IOException {
        Set<Ligne> lignes ;
        lignes = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        boolean saisieOk = false ;
        double lati = -4 ;
        double longi = -4 ;
        int temps ;
        List<Station> stationterminus ; 

        System.out.println("Quel est le nom de la station que vous souhaitez ajouter ? ");
        Station saisieParUtil = new Station(sc.next());
        String nomstation = saisieParUtil.getNom() ;
        if(plan.getStations().contains(saisieParUtil)) {
            System.out.println("La station que vous souhaitez ajouter existe déjà.");
        }
        else {
           do {
               while(lati > 90 || lati < 0) {
                   System.out.println("A quelle latitude se trouve-t-elle ? ");
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
                    System.out.println("A quelle longitude se trouve-t-elle ? ");
                    try {
                        longi = Double.parseDouble(sc.next());
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
               }
            } while (!saisieOk);

            System.out.println("Sur quelle ligne se trouve-t-elle ? (Tapez la lettre correspondant à la ligne, pour créer une nouvelle ligne, tapez son nom)");
            Ligne ligne = new Ligne(sc.next());
            String str = ligne.getNom() ;
            
            if (!(plan.getLignes().contains(ligne))) {
                // cf Ndeye
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
                
                System.out.print("Vous avez la possibilite d'ajouter votre station soit avant "+ stationterminus.get(0) +" ou après "+ stationterminus.get(1)+". Quel est votre choix ? ");
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
                Coordonnee coordo = new Coordonnee(longi, lati) ;
                Station nouvellestation = new Station(nomstation, coordo);
                Fragment nouveaufragment = new Fragment(nouvellestation,stationexistante, temps) ;
                
                // Ajout dans le fichier plan
                Coordonnee coordonnees = stationexistante.getCoord() ; 
               
                Double latitude = coordonnees.getLatitude();
                Double longitude = coordonnees.getLongitude() ;
                
                //Ecriture dans le fichier
                FileWriter aecrire = null ;
                String texte = "\n"+nomstation+"\t"+lati+":"+longi+"\t"+stationexistante.getNom()+"\t"+latitude+":"+longitude+"\t"+temps+"\t"+str ;
                
                try{
                    aecrire = new FileWriter(fichier, true);
                    aecrire.write(texte);
                    System.out.println("Votre station a bien ete enregistree. ") ;
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                aecrire.close() ;
            }     
        }
    }
}
