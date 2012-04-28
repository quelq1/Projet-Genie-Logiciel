
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */


import java.io.*;
import java.util.*;

public class Main {

    private static String fichier = "plan.txt";
   
    
    public static void main(String[] args) {
        Plan plan = new Plan(fichier);
        System.out.println(plan);

        System.out.println("");

        geolocalisation(plan);
        ajoutstation(plan) ;
    }

    public static void geolocalisation(Plan plan) {
        Scanner sc = new Scanner(System.in);
        Station util = null;
        boolean saisieOk = false, choixOk = false;
        do {
            System.out.print("Vous trouvez-vous dans une station (O : oui/N : non) ? ");
            String rep = sc.next();

            if (rep.toUpperCase().compareTo("O") == 0) {
                choixOk = true;
                //On affiche les stations dispo
                Iterator<Station> is = plan.getStations().iterator();
                int i = 1;
                while (is.hasNext()) {
                    System.out.println(i + " - " + is.next());
                    i++;
                }

                int nStation = 0;
                do {
                    System.out.print("Quel est son nom (tapez le numero correspondant à votre station) ? ");
                    try {
                        nStation = Integer.parseInt(sc.next());

                        if (nStation < 0 || nStation > plan.getStations().size()) {
                            throw new NumberFormatException();
                        }
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
                } while (!saisieOk);

                is = plan.getStations().iterator();
                i = 1;
                while (is.hasNext() && i <= nStation) {
                    if (i == nStation) {
                        util = is.next();
                    } else {
                        i++;
                        is.next();
                    }
                }

                System.out.println("Vous vous trouvez à " + util + ".");
                plan.setStationUtil(util);
            } else if (rep.toUpperCase().compareTo("N") == 0) {
                choixOk = true;
                double lat = 0, lon = 0;

                do {
                    System.out.println("A quelle latitude vous trouvez-vous ?");
                    try {
                        lat = Double.parseDouble(sc.next());
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
                } while (!saisieOk);

                do {
                    System.out.println("A quelle longitude vous trouvez-vous ?");
                    try {
                        lon = Double.parseDouble(sc.next());
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
                } while (!saisieOk);

                util = plan.getStationProche(new Coordonnee(lat, lon));
                plan.setStationUtil(util);
                System.out.println("La station la plus proche est " + util + ".");
            } else {
                System.out.println("Merci de respecter le format d'ecriture.");
            }
        } while (!choixOk);
    }
    public static void ajoutstation(Plan plan) {
        Set<Station> stations;
        stations = new HashSet<>();  
        Set<Ligne> lignes ;
        lignes = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        boolean saisieOk = false, choixOk = false;
        double lati = 0 ;
        double longi = 0 ;
        int temps ;
        ArrayList<Station> stationterminus ; 
        
        do {
            System.out.print("Quel est le nom de la station que vous souhaitez ajouter ? ");
            String nomstation = sc.next() ;
            Station saisieParUtil = new Station(sc.next());
            
            if(stations.contains(saisieParUtil )) {
                System.out.print("La station que vous souhaitez ajouter existe déjà.");
            }
            else {
                do {
                    System.out.print("A quelle latitude se trouve-t-elle ? ");
                    try {
                        lati = Double.parseDouble(sc.next());
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
                } while (!saisieOk);
                
                do {
                    System.out.println("A quelle longitude se trouve-t-elle ? ");
                    try {
                        longi = Double.parseDouble(sc.next());
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
                } while (!saisieOk);
                
                System.out.println("Sur quelle ligne se trouve-t-elle ? \n Tapez la lettre correspondant à la ligne, pour créer une nouvelle ligne, tapez son nom \n");
                String str = sc.next() ;
                
                Ligne l = new Ligne(str) ;
                if (!l.equals(lignes)) {
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
                    System.out.println("Vous avez la possibilite d'ajouter votre station soit avant "+ stationterminus.get(0) +" ou après "+ stationterminus.get(1)+". Quel est votre choix ? \n");
                    Station stationexistante = new Station(sc.next());
                    
                    System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                    temps = sc.nextInt() ; 

                    // Ajouts possibles 
                    Coordonnee coordo = new Coordonnee(longi, lati) ;
                    Station nouvellestation = new Station(nomstation, coordo, null); // manque incident
                    Fragment nouveaufragment = new Fragment(nouvellestation,stationexistante, temps) ;
                    
                    // Ajout dans le fichier plan
                    Coordonnee coordonnees = stationexistante.getCoord() ; 
                    Double latitude = coordonnees.getLatitude();
                    Double longitude = coordonnees.getLongitude() ;
                    FileWriter aecrire = null ;
                    String texte = nomstation+"\t"+lati+":"+longi+"\t"+stationexistante.getNom()+"\t"+latitude+":"+longitude+"\t"+temps+"\t"+str ;
                    try{
                        aecrire = new FileWriter("plan.txt", true);
                        aecrire.write(texte);
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                    System.out.println("Votre station a bien ete enregistree. ") ;
                }     
           }
        } while (!choixOk); 
    }
}
