
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GOBIN
 */


import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

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
        Scanner sc = new Scanner(System.in);
        boolean saisieOk = false, choixOk = false;
        double lati = 0 ;
        double longi = 0 ;
        String stationexistante ;
        int temps ;
        do {
            System.out.print("Quel est le nom de la station que vous souhaitez ajouter ? ");
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
                String str = sc.next().toUpperCase() ;
                char nligne = str.charAt(0) ;
                
                
                switch(nligne) {
                    case 'A' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne A. Vous pouvez ajouter votre station avant la station Violette ou après la station Lavande. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Violette") == 0) {   
                            // saisieParUtil + Violette
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Lavande + saisieParUtil
                            stations.add(saisieParUtil) ;
                        }
                    case 'B' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne B. Vous pouvez ajouter votre station avant la station Myosotis ou après la station Jonquille. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Myosotis") == 0) {   
                            // saisieParUtil + Myosotis
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Jonquille + saisieParUtil
                            stations.add(saisieParUtil) ;
                        } 
                    case 'C' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne C. Vous pouvez ajouter votre station avant la station Jonquille ou après la station Orchidée. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Jonquille") == 0) {   
                            // saisieParUtil + Jonquille
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Orchidée + saisieParUtil
                            stations.add(saisieParUtil) ;
                        }
                    case 'D' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne D. Vous pouvez ajouter votre station avant la station Jonquille ou après la station Bleuet. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Jonquille") == 0) {   
                            // saisieParUtil + Jonquille
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Bleuet + saisieParUtil
                            stations.add(saisieParUtil) ;
                        }
                    case 'E' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne E. Vous pouvez ajouter votre station avant la station Narcisse ou après la station Tulipe. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Narcisse") == 0) {   
                            // saisieParUtil + Narcisse
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Tulipe + saisieParUtil
                            stations.add(saisieParUtil) ;
                        }
                    case 'F' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne F. Vous pouvez ajouter votre station avant la station Amaryllis ou après la station Pissenlit. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Amaryllis") == 0) {   
                            // saisieParUtil + Amaryllis
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Pissenlit + saisieParUtil
                            stations.add(saisieParUtil) ;
                        }
                    case 'G' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne G. Vous pouvez ajouter votre station avant la station Violette ou après la station Lavande. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Violette") == 0) {   
                            // saisieParUtil + Violette
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Lavande + saisieParUtil
                            stations.add(saisieParUtil) ;
                        }
                    case 'H' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne H. Vous pouvez ajouter votre station avant la station Violette ou après la station Pissenlit. Quel est votre choix ? (tapez le nom de la station) \n");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Violette") == 0) {   
                            // saisieParUtil + Violette
                            stations.add(saisieParUtil) ;
                        }
                        else {
                            // Pissenlit + saisieParUtil
                            stations.add(saisieParUtil) ;
                        }
                    default :
                        // création de la ligne, appel à la méthode de ndeye
                }
           }
        } while (!choixOk); 
    }
}
