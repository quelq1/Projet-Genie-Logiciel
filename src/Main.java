import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static String fichier = "plan.txt";

    public static void main(String[] args) {
        Plan plan = new Plan(fichier);
        System.out.println(plan);
        
        boolean ok;

        System.out.println("BIENVENUE que desirez vous faire ?\n");
        
	do {
		System.out.println("        MENU        ");
		System.out.println("1 - Vous localiser");
                System.out.println("2 - Ajouter une station");
                System.out.println("3 - Ajouter une ligne");
<<<<<<< HEAD
		System.out.println("4 - Signaler un incident sur une station");
=======
		System.out.println("4 - Signaler un incident sur une ligne");
>>>>>>> c3f484b3d78a13ef7615fd3c58f51a8312993f05
                System.out.println("5 - Signaler un incident sur un fragment");
		System.out.println("6 - Itineraire le plus rapide");
		System.out.println("7 - Connaitre l'itineraire avec le moins de changement");
                System.out.println("8 - Connaitre l'itineraire avec différentes etapes");
<<<<<<< HEAD
		int reponse;
		Scanner sc=new Scanner(System.in);
		reponse=sc.nextInt();
=======
                int reponse;
                Scanner sc=new Scanner(System.in);
                reponse=sc.nextInt();
>>>>>>> c3f484b3d78a13ef7615fd3c58f51a8312993f05

		switch(reponse){
			case 1 :
                                Geolocalisation.geolocalisation(plan);
				break;
			case 2:
				
				break;
			case 3:
<<<<<<< HEAD
                                plan.ajoutLigne();
				break;
			case 4:
				Station.ajoutIncidentStation();
=======
                                
				break;
			case 4:
				
>>>>>>> c3f484b3d78a13ef7615fd3c58f51a8312993f05
				break;
                        case 5:
                               Fragment.ajoutIncidentFragment();
                                break;
                        case 6:
                                
                                break;
                        case 7 :
                                
                                break;
<<<<<<< HEAD
                        case 8 : 
=======
                        case 8 :
>>>>>>> c3f484b3d78a13ef7615fd3c58f51a8312993f05
                                
                                break;
			default:
				Geolocalisation.geolocalisation(plan);
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
