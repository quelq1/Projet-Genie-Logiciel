
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Loïc Cimon
 */
public class AjoutLigne {

    private static Scanner sc;

    public static void menuGestionLigne(Plan plan) {
        sc = new Scanner(System.in);
        boolean fin = false;
        while (!fin) {
            System.out.println("\t\t--------------------");
            System.out.println("\t\t Gestion des lignes");
            System.out.println("\t\t--------------------");
            System.out.println("");
            System.out.println("1 - Ajouter une station existante à une ligne");
            System.out.println("2 - Supprimer une station d'une ligne");
            System.out.println("3 - Créer une ligne");
            System.out.println("");
            System.out.println("0 - Quitter");
            System.out.println("");
            System.out.println("Entrez votre choix : ");

            int choix = Main.saisieInt(sc);

            switch (choix) {
                case 0:
                    fin = true;
                    break;
                case 1:
                    
                    break;
                case 2:
                    rmStation(plan);
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Choix incorrect...");
                    break;
            }
        }
    }
    
    public static void addStation(Plan plan) {
        System.out.println("Entrez le nom de ligne à modifier :");
        Ligne ligneModif = rechercheLigneByNom(sc.next(), plan);
        
        if (ligneModif == null) {
            System.out.println("La ligne n'existe pas.");
            return;
        }
        
        System.out.println("Entrez la station a ajouter :");
        Station aAjouter = FavorisUtilisateur.choixStation(plan, sc);
        
        //Crée le fragment
        Fragment f = Fragment.creationLiaisonFragment(plan, ligneModif, aAjouter, sc);
        
        //Ajoute le fragment à la ligne
        ligneModif.addFragment(f);
        
        System.out.println("La station " + aAjouter.getNom() + " a été ajoutée à la ligne " + ligneModif.getNom());
    }

    public static void rmStation(Plan plan) {
        System.out.println("Entrez le nom de ligne à modifier :");
        Ligne ligneModif = rechercheLigneByNom(sc.next(), plan);
        
        if (ligneModif == null) {
            System.out.println("La ligne n'existe pas.");
            return;
        }
        
        List<Station> stationterminus = plan.getStationExtremite(ligneModif);
        System.out.println("Station a supprimer :");
        System.out.println("\t1 - " + stationterminus.get(0).getNom());
        System.out.println("\t2 - " + stationterminus.get(1).getNom());
        System.out.println("");
        System.out.println("0 - Annuler");
        System.out.println("Quel est votre choix ?");

        int choix = 0;
        boolean saisieOk = false;
        do {
            choix = Main.saisieInt(sc);

            //Si annuler, on coupe tout
            if (choix == 0) {
                return;
            }
            if (0 < choix && choix < 3) {
                saisieOk = true;
            } else {
                System.out.println("Choix incorrect.");
            }
        } while (!saisieOk);
        Station aSupp = stationterminus.get(choix - 1);
       
        
        
        //On récupère le fragment
        boolean trouve = false;
        Iterator<Fragment> iFrag = ligneModif.getListeFragments().iterator();
        Fragment f = null;
        while(iFrag.hasNext() && !trouve) {
            f  = iFrag.next();
            if (f.contientStation(aSupp)) {
                trouve = true;
            }
        }
        
        //TODO Vérifier le nombre de ligne du : si nb < 2 -> impossible, sinon on supprimer
        List<Ligne> lignesDuFrag = plan.getLigneByFragment(f);
        if (lignesDuFrag.size() < 2) {
            System.out.println("La station ne peut être supprimée de la ligne. C'est la seule ligne passant par cette station");
        }
        else {
            ligneModif.getListeFragments().remove(f);
            System.out.println("La station " + aSupp.getNom() + " a été supprimée de la ligne " + ligneModif.getNom());
        }
    }

    public static void ajoutLigne(Plan plan) {
        System.out.println("Entrez le nom de la ligne à creer : ");
        Ligne l = new Ligne(sc.next());
        if (plan.getLignes().contains(l)) {
            System.out.println("La ligne existe déjà!!");
            return;
        }

        System.out.println("Entrer le nombre de stations à ajouter : ");
        int nbreStation;

        boolean saisieOk = false;
        do {
            nbreStation = sc.nextInt();
            if (nbreStation >= 2) {
                saisieOk = true;
            } else {
                System.out.println("Il faut au moins deux stations pour creer la ligne !");
            }
        } while (!saisieOk);

        ArrayList<Station> listStationTmp = new ArrayList();

        //TODO Affiche les stations et favoris

        while (nbreStation != 0) {
            System.out.println("Entrer le nom de la station :");
            String s = sc.next();
            Station stationTmp = new Station(s);

            //Vérifie si la station n'a pas déjà été saisie
            if (!listStationTmp.contains(stationTmp)) {

                //Vérifie si la station n'existe pas déjà dans le plan
                if (!plan.getStations().contains(stationTmp)) {
                    System.out.println("La station n'existe pas, voulez vous la créer ? [O/N]");
                    String choix = sc.next();
                    if (choix.compareToIgnoreCase("o") == 0) {
                        Coordonnee coord = Coordonnee.saisieCoord(plan, sc);
                        stationTmp.setCoord(coord);
                    } else {
                        //Passe à l'itération suivante
                        continue;
                    }
                } else {
                    //On récupère la station existante dans le plan
                    stationTmp = plan.getStations().get(plan.getStations().indexOf(stationTmp));
                }

                listStationTmp.add(stationTmp);
                nbreStation--;
            } else {
                System.out.println("Vous avez déja saisi cette station!");
            }
        }

        Fragment f;
        for (int i = 0; i <= listStationTmp.size() - 2; i++) {
            //Vérifie que le fragement n'existe pas déjà
            f = plan.getFragmentByStations(listStationTmp.get(i).getNom(), listStationTmp.get(i + 1).getNom());
            if (f == null) {
                //Si null, on le crée
                f = Fragment.saisieFragment(listStationTmp.get(i), listStationTmp.get(i + 1), sc);
            }
            l.addFragment(f);
        }

        plan.getLignes().add(l);
        System.out.println("La ligne a été ajoutée !");
    }
    
    private static Ligne rechercheLigneByNom(String nom, Plan plan) {
        Ligne ligneModif = new Ligne(nom);
        
        //On récupère la ligne
        Iterator<Ligne> it = plan.getLignes().iterator();
        Ligne res = null;
        boolean trouve = false;
        while(it.hasNext() && !trouve) {
            res = it.next();
            if (res.equals(ligneModif)) {
                ligneModif = res;
                trouve = true;
            }
        }
        if (!trouve) {
            res = null;
        }
        return res;
    }
}
