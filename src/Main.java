
import java.util.Scanner;

public class Main {

    private static String fichier = "plan.txt";

    public static void main(String[] args) {
        Plan plan = new Plan(fichier);
        System.out.println(plan);

        boolean ok;

        System.out.println("Bienvenu...\n");
        Geolocalisation.geolocalisation(plan);

        do {
            System.out.println("\t\t---");
            System.out.println("\t\tMENU");
            System.out.println("\t\t---");
            System.out.println("1 - Ajouter une station");
            System.out.println("2 - Ajouter une ligne");
            System.out.println("3 - Signaler un incident");
            System.out.println("4 - Itineraire le plus rapide");
            System.out.println("5 - Connaitre l'itineraire avec le moins de changement");
            System.out.println("6 - Connaitre l'itineraire avec différentes etapes");
            System.out.println("");
            System.out.println("0 - Quitter");
            int reponse;
            Scanner sc = new Scanner(System.in);
            reponse = sc.nextInt();

            
            switch (reponse) {
                case 0:
                    System.out.println("Au revoir...");
                    ok = true;
                    break;
                case 1:
                Geolocalisation.geolocalisation(plan);
		
                    break;
                case 2:
                    plan.ajoutLigne();
                    break;
                case 3:plan.ajoutIncident();

                    break;
                case 4:
                    RechercheItineraire.menuChoixDestination(plan, 1);
                    
                    break;
                case 5:

                    break;
                case 6:break;

                default:
				System.out.println("Choix incorrect...");
				break;
		}
       
                System.out.println("\nAvez-vous quelque chose d'autre à faire  (O : oui/N : non) ? ");
		String rep;
		rep=sc.next();
                //permet de prendre en compte les minuscules et les majuscules
		if (rep.compareToIgnoreCase("O")!=0) {
			ok=false;
		}
		else {
			ok=true;
		}
        }while (ok);
   }  
                    
        
}
