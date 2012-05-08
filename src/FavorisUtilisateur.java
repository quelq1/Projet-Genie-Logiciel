
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
    private static List<Station> favoris = new ArrayList<>();
    private static Scanner sc;

    public static void menuGestionFavoris(Plan plan) {
        sc = new Scanner(System.in);
        boolean fin = false;
        while (!fin) {
            System.out.println("\t\t----------");
            System.out.println("\t\t Favoris");
            System.out.println("\t\t----------");
            System.out.println("");
            System.out.println("1 - Ajouter une station aux favoris");
            System.out.println("2 - Supprimer une station aux favoris");
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
                    addFavoris(plan);
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

    public static void addFavoris(Plan plan) {
        boolean choixOk = false;
        Station choixStation;
        String choix;
        do {
            System.out.println("Entrez le nom de la station a ajouté aux favoris [0 : annuler] : ");
            choix = sc.next();


            //Si annuler, on coupe tout
            if (choix.compareTo("0") == 0) {
                return;
            }

            choixStation = new Station(choix);
            if (plan.getStations().contains(choixStation)) {
                choixStation = plan.getStations().get(plan.getStations().indexOf(choixStation));
                choixOk = true;
            } else {
                System.out.println("Erreur : la station saisie n'existe pas.");
            }
        } while (!choixOk);

        //On ajoute la station à la liste des favoris
        favoris.add(choixStation);
        System.out.println(choixStation.getNom() + " a été ajoutée aux favoris.");
    }

    public static void afficheListeFavoris() {
        if (favoris != null && !favoris.isEmpty()) {
            System.out.println("Favoris :");
            int i = 1;
            for (Station s : favoris) {
                System.out.println("\t" + i + " - " + s.getNom());
                i++;
            }
        }
    }

    public static void supprimerFavoris() {
        if (favoris == null || favoris.size() <= 0) {
            System.out.println("Vous n'avez aucun favoris d'enregistrer.");
            return;
        }

        afficheListeFavoris();
        System.out.println("");
        System.out.println("0 - Annuler");

        System.out.println("Entrez le numéro de la station a supprimer des favoris :");
        boolean saisieOk = false;
        int choix;
        do {
            choix = Main.saisieInt(sc);

            if (choix == 0) {
                return;
            }

            if (0 < choix && choix <= favoris.size()) {
                favoris.remove(choix - 1);
                saisieOk = true;
            } else {
                System.out.println("Choix incorrect.");
            }
        } while (!saisieOk);
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

    public static void chargerFavoris() {
        try {
            File f = new File(nomFich);
            FileInputStream fichier = new FileInputStream(nomFich);
            ObjectInputStream ois = new ObjectInputStream(fichier);
            favoris = (List<Station>) ois.readObject();
            fichier.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            favoris = new ArrayList<>();
        }
    }

    public static Station getFavoris(int i) {
        if (favoris != null && favoris.size() > i && i >= 0) {
            return favoris.get(i);
        } else {
            return null;
        }
    }

    public static Station choixStation(Plan plan, Scanner sc) {
        Station station;
        String choix;
        boolean choixOk = false;
        afficheListeFavoris();
        do {
            choix = sc.next();

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

    public static List<Station> getListeFavoris() {
        return favoris;
    }
}
