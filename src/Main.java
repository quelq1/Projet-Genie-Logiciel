
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static String fichier = "plan.txt";
   
    
    public static void main(String[] args) throws IOException {
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
    
    /* FIXME : Variable "nouveaufragment" non utilisée */
    
    //TODO : Mettre l'écriture dans le fichier dans une fonction à part
    public static void ajoutstation(Plan plan) throws IOException {
        Set<Ligne> lignes ;
        Set<Ligne> l ;
        lignes = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        boolean saisieOk = false ;
        double lati = 0 ;
        double longi = 0 ;
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
                System.out.println("A quelle latitude se trouve-t-elle ? ");
                try {
                    lati = Double.parseDouble(sc.next());
                    saisieOk = true;
                } catch (NumberFormatException e) {
                    System.out.println("\nChoix incorrect.");
                }
            
                System.out.println("A quelle longitude se trouve-t-elle ? ");
                try {
                    longi = Double.parseDouble(sc.next());
                    saisieOk = true;
                } catch (NumberFormatException e) {
                    System.out.println("\nChoix incorrect.");
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
                //System.out.println(nouvellestation);
                Fragment nouveaufragment = new Fragment(nouvellestation,stationexistante, temps) ;
                //System.out.println(nouveaufragment);
                
                // Ajout dans le fichier plan
                Coordonnee coordonnees = stationexistante.getCoord() ; 
               
                Double latitude = coordonnees.getLatitude();
                Double longitude = coordonnees.getLongitude() ;
                
                FileWriter aecrire = null ;
                String texte = "\n"+nomstation+"\t"+lati+":"+longi+"\t"+stationexistante.getNom()+"\t"+latitude+":"+longitude+"\t"+temps+"\t"+str ;
                
                try{
                    aecrire = new FileWriter(fichier, true);
                    aecrire.write(texte);
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                System.out.println("Votre station a bien ete enregistree. ") ;
                aecrire.close() ;
            }     
        }
    }
}
