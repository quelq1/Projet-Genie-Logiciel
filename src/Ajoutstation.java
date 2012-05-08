
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Elodie
 */
public class Ajoutstation {
    private static Scanner sc = new Scanner(System.in);

    public static void ajoutStation(Plan plan) {
        //Saisie nom
        System.out.println("Quel est le nom de la station que vous souhaitez ajouter ? ");
        String nomStation = sc.next();
        Station aAjoute = new Station(nomStation);

        if (plan.getStations().contains(aAjoute)) {
            System.out.println("La station que vous souhaitez ajouter existe déjà.");
            return;
        }

        //Coordonnée
        Coordonnee c = saisieCoord();

        //Vérifie qu'une station n'a pas déjà ces coords
        for (Station s : plan.getStations()) {
            if (s.getCoord().equals(c)) {
                System.out.println("Vous ne pouvez pas ajouter une nouvelle station avec les mêmes coordonnées qu'une déjà existante");
                return;
            }
        }
        aAjoute.setCoord(c);

        //Saisie ligne
        System.out.println("Sur quelle ligne se trouve-t-elle ? (tapez son nom)");
        String nomLigne = sc.next();
        Ligne ligne = new Ligne(nomLigne);


        if (!plan.getLignes().contains(ligne)) {
            System.out.println("La ligne n'existe pas.");
            return;
        }

        //Récupère la ligne
        for (Ligne l : plan.getLignes()) {
            if (l.equals(ligne)) {
                ligne = l;
                break;
            }
        }

        //Crée le fragment
        List<Station> stationterminus = plan.getStationExtremite(ligne);
        System.out.print("Vous avez la possibilite d'ajouter votre station :");
        System.out.println("\t1 - " + stationterminus.get(0).getNom());
        System.out.println("\t2 - " + stationterminus.get(1).getNom());
        System.out.println("Quel est votre choix ?");
        int choix = 0;
        boolean saisieOk = false;
        do {
            try {
                choix = Integer.parseInt(sc.next());

                if (0 < choix || choix < 3) {
                    saisieOk = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nChoix incorrect.");
            }
        } while (!saisieOk);
        Station extremite = stationterminus.get(choix - 1);

        int tmpTrajet = 0;
        saisieOk = false;
        do {
            try {
                tmpTrajet = Integer.parseInt(sc.next());
                saisieOk = true;
            } catch (NumberFormatException e) {
                System.out.println("\nVeuillez saisir un entier.");
            }
        } while (!saisieOk);

        Fragment frag = new Fragment(aAjoute, extremite, tmpTrajet);
        ligne.addFragment(frag);
        plan.addStation(aAjoute);
        
        //On écrit dans le fichier
        ecriturefichier(aAjoute, extremite, tmpTrajet, ligne);
    }

    public static Coordonnee saisieCoord() {
        //Saisie coord
        boolean saisieOk = false;
        double[] coord = new double[2];
        String[] nomCoord = new String[]{"latitude", "longiture"};
        for (int i = 0; i < 2; i++) {
            do {
                System.out.println("A quelle " + nomCoord[i] + " se trouve-t-elle ? ");
                try {
                    coord[i] = Double.parseDouble(sc.next());

                    if (0 < coord[i] || coord[i] < 90) {
                        saisieOk = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nChoix incorrect.");
                }
            } while (!saisieOk);
        }

        return new Coordonnee(coord[0], coord[1]);
    }

    public static void ecriturefichier(Station s1, Station s2, int temps, Ligne ligne) {
        String texte = "\n" + s1.getNom() + "\t" + s1.getCoord().getLatitude() + ":" + s1.getCoord().getLongitude();
        texte += "\t" + s2.getNom() + "\t" + s2.getCoord().getLatitude() + ":" + s2.getCoord().getLongitude();
        texte += "\t" + temps + "\t" + ligne.getNom();
        
        FileWriter writer = null;
        try {
            writer = new FileWriter(Main.getFichierPlan(), true);
            writer.write(texte, 0, texte.length());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    //    private int attribut1;
//    private String attribut2;
//    private boolean visible; // Attribut non-représentatif et donc ignoré
//    public static double lati = -4;
//    public static double longi = -4;
//
//    @Override
//    public boolean equals(Object obj) {
//        // Vérification de l'égalité des références
//        if (obj == this) {
//            return true;
//        }
//        // Vérification du type du paramètre
//        if (obj instanceof Ajoutstation) {
//            // Vérification des valeurs des attributs
//            Ajoutstation other = (Ajoutstation) obj;
//            // Pour les attributs de type primitif
//            // on compare directement les valeurs :
//            if (this.attribut1 != other.attribut1) {
//                return false; // les attributs sont différents 
//            }
//            // Pour les attributs de type objets 
//            // on compare dans un premier temps les références 
//            if (this.attribut2 != other.attribut2) {
//                // Si les références ne sont pas identiques
//                // on doit en plus utiliser equals()
//                if (this.attribut2 == null || !this.attribut2.equals(other.attribut2)) {
//                    return false; // les attributs sont différents 
//                }
//            }
//            // Si on arrive ici c'est que tous les attributs sont égaux :
//            return true;
//        }
//        return false;
//    }
    
//    public static void ajoutstation(Plan plan) {
//        Scanner sc = new Scanner(System.in);
//        boolean saisieOk = false;
//        int temps;
//        List<Station> stationterminus;
//
//        System.out.print("Quel est le nom de la station que vous souhaitez ajouter ? ");
//        String se = sc.next();
//        Station saisieParUtil = new Station(se);
//        String nomstation = saisieParUtil.getNom();
//
//        if (plan.getStations().contains(saisieParUtil)) {
//            System.out.println("La station que vous souhaitez ajouter existe déjà.");
//        } else {
//            do {
//                while (lati > 90 || lati < 0) {
//                    System.out.print("A quelle latitude se trouve-t-elle ? ");
//                    try {
//                        lati = Double.parseDouble(sc.next());
//                        saisieOk = true;
//                    } catch (NumberFormatException e) {
//                        System.out.println("\nChoix incorrect.");
//                    }
//                }
//            } while (!saisieOk);
//
//            do {
//                while (longi > 90 || longi < 0) {
//                    System.out.print("A quelle longitude se trouve-t-elle ? ");
//                    try {
//                        longi = Double.parseDouble(sc.next());
//                        saisieOk = true;
//                    } catch (NumberFormatException e) {
//                        System.out.println("\nChoix incorrect.");
//                    }
//                }
//            } while (!saisieOk);
//            Coordonnee coordo = new Coordonnee(lati, longi);
//            //System.out.println(coordo);
//            // System.out.println(plan.getStations().get(0).getCoord());
//            // System.out.println(plan.getStations().get(2));
//            // redéfinir Equals :/
//            for (int x = 0; x < plan.getStations().size(); x++) {
//                if (plan.getStations().get(x).getCoord().equals(coordo) == true) {
//                    System.out.println("Vous ne pouvez pas ajouter une nouvelle station avec les mêmes coordonnées qu'une déjà existante");
//
//                }
//            }
//
//            System.out.print("Sur quelle ligne se trouve-t-elle ? (Tapez la lettre correspondant à la ligne, pour créer une nouvelle ligne, tapez son nom)");
//            Ligne ligne = new Ligne(sc.next());
//            String str = ligne.getNom();
//
//            if (!(plan.getLignes().contains(ligne))) {
//                plan.ajoutLigne();
//            } else {
//                // permet de mettre la ligne entree par l'utilisateur en objet Ligne
//                boolean trouve = false;
//                Ligne ltmp = null;
//                Iterator<Ligne> is = plan.getLignes().iterator();
//                while (!trouve && is.hasNext()) {
//                    ltmp = is.next();
//                    if (ltmp.getNom().compareTo(str) == 0) {
//                        trouve = true;
//                    }
//                }
//
//                stationterminus = plan.getStationExtremite(ltmp);
//                System.out.print("Vous avez la possibilite d'ajouter votre station soit avant " + stationterminus.get(0) + " ou après " + stationterminus.get(1) + ". Quel est votre choix ? ");
//
//                Station stationexistante = new Station(sc.next());
//
//                List<Station> s = plan.getStations();
//                for (int i = 0; i < s.size(); i++) {
//                    if (s.get(i).getNom().compareTo(stationexistante.getNom()) == 0) {
//                        stationexistante = s.get(i);
//                    }
//                }
//                System.out.print("Combien de temps mettez-vous d'une station à l'autre ? (en minutes) ");
//                temps = sc.nextInt();
//
//                // Ajouts possibles 
//                Station nouvellestation = new Station(nomstation, coordo);
//                Fragment nouveaufragment = new Fragment(nouvellestation, stationexistante, temps);
//
//                // Ajout dans le fichier plan
//                Coordonnee coordonnees = stationexistante.getCoord();
//                String nomstat = stationexistante.getNom();
//                Double latitude = coordonnees.getLatitude();
//                Double longitude = coordonnees.getLongitude();
//
//                //Ecriture dans le fichier
//                String texte = "\n" + nomstation + "\t" + lati + ":" + longi + "\t" + nomstat + "\t" + latitude + ":" + longitude + "\t" + temps + "\t" + str;
//                ecriturefichier(texte);
//
//            }
//
//
//        }
//
//    }
}
