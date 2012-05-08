
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Loïc Cimon
 */
public class FavorisUtilisateur implements Serializable {

    private static final String nomFich = "favoris.config";
    private static List<Station> favoris;
    private static Plan plan;
    private static Scanner sc;

    public static void menuGestionFavoris() {
        sc = new Scanner(System.in);
        boolean fin = false;
        while (!fin) {
            System.out.println("\t\t----------");
            System.out.println("\t\t Favoris");
            System.out.println("\t\t----------");
            System.out.println("");
            System.out.println("1 - Ajouter une station aux favorites");
            System.out.println("2 - Supprimer une station aux favorites");
            System.out.println("");
            System.out.println("0 - Quitter");
            System.out.println("");
            System.out.println("Entrez votre choix : ");

            Scanner sc = new Scanner(System.in);
            int choix = sc.nextInt();

            switch (choix) {
                case 0:
                    fin = true;
                    break;
                case 1:
                    addFavoris();
                    break;
                case 2:
                    supprimerFavoris();
                    break;
                default:
                    System.out.println("Choix incorrect...");
                    break;
            }
        }
    }

    public static void addFavoris() {
        boolean choixOk = false;
        Station choixStation;
        do {
            System.out.println("Entrez le nom de la station a ajouté aux favoris :");
            choixStation = new Station(sc.next());

            if (plan.getStations().contains(choixStation)) {
                choixStation = plan.getStations().get(plan.getStations().indexOf(choixStation));
                choixOk = true;
            } else {
                System.out.println("Erreur : la station saisie n'existe pas.");
            }
        } while (!choixOk);

        //On ajoute la station à la liste des favoris
        favoris.add(choixStation);
    }

    public static void afficheListeFavoris() {
        System.out.println("Favoris :");
        int i = 1;
        for (Station s : favoris) {
            System.out.println(i + " - " + s.getNom());
            i++;
        }
    }

    public static void supprimerFavoris() {
        if (favoris.size() <= 0) {
            System.out.println("Vous n'avez aucun favoris d'enregistrer.");
            return;
        }
        Scanner sc = new Scanner(System.in);

        afficheListeFavoris();
        System.out.println("");
        System.out.println("0 - Annuler");

        System.out.println("Entrez le numéro de la station a supprimer des favoris :");
        int choix = sc.nextInt();

        if (choix != 0) {
            favoris.remove(choix - 1);
        }
    }

    public static void sauvegarderFavoris() {
        if (favoris.size() > 0) {
            try {
                File f = new File(nomFich);
                f.createNewFile();
                FileOutputStream fichier = new FileOutputStream(f);
                try (ObjectOutputStream oos = new ObjectOutputStream(fichier)) {
                    oos.writeObject(favoris);
                    oos.flush();
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la sauvegarde des favoris.");
            }
        }
    }

    public static void chargerFavoris(Plan p) {
        plan = p;
        try {
            File f = new File(nomFich);
            FileInputStream fichier = new FileInputStream(nomFich);
            ObjectInputStream ois = new ObjectInputStream(fichier);
            favoris = (List<Station>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            favoris = new ArrayList<>();
        }
    }

    public static Station getFavoris(int i) {
        if (favoris.size() > i && i > 0) {
            return favoris.get(i);
        } else {
            return null;
        }
    }

    public static Station choixStation() {
        sc = new Scanner(System.in);
        Station station;
        boolean choixOk = false;
        System.out.print("Quel est son nom (tapez le numero correspondant à votre station) ? ");
        afficheListeFavoris();
        do {
            String choix = sc.next();


            try {
                station = FavorisUtilisateur.getFavoris(Integer.parseInt(choix) - 1);

                if (station != null) {
                    choixOk = true;
                } else {
                    System.out.println("Erreur : le favoris choisi n'existe pas.");
                }
            } catch (NumberFormatException e) {
                station = new Station(choix);

                if (plan.getStations().contains(station)) {
                    station = plan.getStations().get(plan.getStations().indexOf(station));
                    choixOk = true;
                } else {
                    System.out.println("Erreur : la station saisie n'existe pas.");
                }
            }
        } while (!choixOk);

        return station;
    }
}
