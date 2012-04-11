
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    private static String fichier = "plan.txt";

    public static void main(String[] args) {
//        Plan plan = new Plan(fichier);
//        System.out.println(plan);
//
//        System.out.println("");
//
//        plan.setStationUtil(geolocalisation(plan));
//        
//        System.out.println("Vous vous trouvez à " + plan.getStationUtil() + ".");
        
        String data = "Hello, World!\r\n";
  System.setIn(new ByteArrayInputStream(data.getBytes()));
  Scanner scanner = new Scanner(System.in);
  System.out.println(scanner.nextLine());


    }

    public static Station geolocalisation(Plan plan) {
        Scanner sc = new Scanner(System.in);
        Station util = null;

        System.out.print("Vous trouvez-vous dans une station (O : oui/N : non) ? ");
        String rep = sc.next();

        if (rep.toUpperCase().compareTo("O") == 0) {
            //On affiche les stations dispo
            Iterator<Station> is = plan.getStations().iterator();
            int i = 1;
            while (is.hasNext()) {
                System.out.println(i + " - " + is.next());
                i++;
            }
            System.out.print("Quel est son nom (tapez le numero correspondant à votre station) ? ");
            rep = sc.next();
            
            is = plan.getStations().iterator();
            i = 1;
            while (is.hasNext() && i <= Integer.valueOf(rep)) {
                if (i == Integer.valueOf(rep)) {
                    util = is.next();
                }
                i++;
                is.next();
            }
        }
        else {
            double lat, lon;
            System.out.println("A quelle latitude vous trouvez-vous ?");
            lat = Double.valueOf(sc.next());
            
            System.out.println("A quelle longitude vous trouvez-vous ?");
            lon = Double.valueOf(sc.next());
            
            util = plan.getStationProche(new Coordonnee(lat, lon));            
        }

        return util;
    }
}