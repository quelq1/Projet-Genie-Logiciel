
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
        String[] ligne = chaine.split("\t");
        Set<Station> stations;
        stations = new HashSet<>();
        Station nvellestation = new Station(ligne[0].toUpperCase()) ;
        
        Scanner sc = new Scanner(System.in);
        Station util = null;
        boolean saisieOk = false, choixOk = false;
        double lati = 0 ;
        double longi = 0 ;
        String stationexistante ;
        ObjectOutputStream ecritfichier ;
        int temps ;
        do {
            System.out.print("Quel est le nom de la station que vous souhaitez ajouter ?");
            String nomstation = sc.next();
            
            Iterator<Station> is = stations.iterator();
            while (is.hasNext()) {
                
            } 
            
            /*
              Ligne li = new Ligne(ligne[5].toUpperCase());
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
            
             */
            if(nomstation.toUpperCase().contains(nvellestation)) {
                System.out.print("La station que vous souhaitez ajouter existe déjà.");
            }
            else {
                do {
                    System.out.print("A quelle latitude se trouve-t-elle ?");
                    try {
                        lati = Double.parseDouble(sc.next());
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
                } while (!saisieOk);
                
                do {
                    System.out.println("A quelle longitude se trouve-t-elle ?");
                    try {
                        longi = Double.parseDouble(sc.next());
                        saisieOk = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\nChoix incorrect.");
                    }
                } while (!saisieOk);
                
                System.out.println("Sur quelle ligne se trouve-t-elle ? \n Tapez la lettre correspondant à la ligne, pour créer une nouvelle ligne, tapez son nom");
                String str = sc.next().toUpperCase() ;
                char nligne = str.charAt(0) ;
                
                // creation du fragment 
                Fragment d = new Fragment(sd, sa, Integer.parseInt(ligne[4]));
                
                switch(nligne) {
                    case 'A' :
                        System.out.println("Vous souhaitez l'ajouter à la ligne A. Vous pouvez ajouter votre station avant la station Violette ou après la station Lavande. Quel est votre choix ?");
                        stationexistante = sc.next().toUpperCase() ;
                        System.out.println("Combien de temps mettez-vous d'une station à l'autre ?") ;
                        temps = sc.nextInt() ;
                        if (stationexistante.compareTo("Violette") == 0) {
                            // écriture dans le fichier texte = ajout
                            
                           /* try {
                                ecritfichier = new ObjectOutputStream(
                                                new BufferedOutputStream(
                                                    new FileOutputStream(
                                                        new File(fichier))));
                                ecritfichier.write(new Fragment(nomstation,lati+":"+longi,"Violette","48.84:2.33",temps,"A"));    
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }*/
                           /* nvellestation.addFragment(d);
                    lignes.add(li);*/
                        }
                        else {
                            try {
                                ecritfichier = new ObjectOutputStream(
                                                new BufferedOutputStream(
                                                    new FileOutputStream(
                                                        new File(fichier))));
                                ecritfichier.writeObject(new Fragment("Lavande","48.84:2.37",nomstation,lati+":"+longi,temps,"A"));    
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                       
                    case 'B' :
                    case 'C' :
                    case 'D' :
                    case 'E' :
                    case 'F' :
                    case 'G' :
                    case 'H' :
                    default :
                        // création de la ligne, appel à la méthode de ndeye
                  
                    
                }
            
                       
               
                
           }
        } while (!choixOk); 
    }
}
