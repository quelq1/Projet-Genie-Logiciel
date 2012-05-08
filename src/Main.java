
import java.util.Scanner;

public class Main {

    private static String fichier = "plan.txt";

    public static void main(String[] args) {
        System.out.println("   *********************");
        System.out.println("     Application metro");
        System.out.println("   *********************");
        
        Plan plan = new Plan(fichier);
        Geolocalisation.geolocalisation(plan);

        boolean fin = false;
        do {
            System.out.println("\t\t------");
            System.out.println("\t\t MENU");
            System.out.println("\t\t------");
            System.out.println("1 - Actualiser ma position");
            System.out.println("2 - Ajouter une station");
            System.out.println("3 - Ajouter une ligne");
            System.out.println("4 - Signaler un incident");
            System.out.println("5 - Itinéraire le plus rapide");
            System.out.println("6 - Itinéraire avec le moins de changements");
            System.out.println("7 - Itinéraire avec différentes etapes");
            System.out.println("");
            System.out.println("0 - Quitter");
            int reponse;
            Scanner sc = new Scanner(System.in);
            reponse = sc.nextInt();

            switch (reponse) {
                case 0:
                    System.out.println("Au revoir...");
                    fin = true;
                    break;
                case 1:
                    Geolocalisation.geolocalisation(plan);
                    break;
                case 2:
                    Ajoutstation.ajoutStation(plan);
                    break;
                case 3:
                    plan.ajoutLigne();
                    break;
                case 4:
                    plan.ajoutIncident();
                    break;
                case 5:
                    RechercheItineraire.menuChoixDestination(plan, 1);
                    break;
                case 6:
                    RechercheItineraire.menuChoixDestination(plan, 2);
                    break;
                case 7:
                    RechercheItineraire.menuChoixDestination(plan, 3);
                    break;
                default:
                    System.out.println("Choix incorrect...");
                    break;
            }
        } while (!fin);
    }

    public static String getFichierPlan() {
        return fichier;
    }
}
