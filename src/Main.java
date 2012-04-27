
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    private static String fichier = "plan.txt";

    public static void main(String[] args) {
        Plan plan = new Plan(fichier);
        System.out.println(plan);
        
        boolean ok=true;

        System.out.println("BIENVENU que desirez vous faire ?\n");
        
	while (ok) {
		System.out.println("        MENU        ");
		System.out.println("1 - Vous localiser");
		System.out.println("2 - Connaître les incidents");
		System.out.println("3 - Signaler un incident");
		System.out.println("4 - Itineraire le plus rapide");
		System.out.println("5 - Connaitre l_itineraire avec le moins de changement");
		int reponse;
		Scanner sc=new Scanner(System.in);
		reponse=sc.nextInt();

		switch(reponse){
			case 1 :
                                Geolocalisation.geoloca(plan);
				break;
			case 2 :
				Incident.listeinci();
				break;
			case 3:
				Incident.incidentstat();
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			default:
				Geolocalisation.geoloca(plan);
				break;
		}

		System.out.println("\nAvez-vous quelque chose d'autre à faire ? ");
		String Rep;
		Rep=sc.next();
		if (Rep.compareTo("oui")==0 || Rep.compareTo("Oui")==0 || Rep.compareTo("OUI")==0) {
			ok=true;
		}
		else {
			ok=false;
		}
        }
   }  
}