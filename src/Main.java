
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Main {

    private static String fichier = "plan.txt";

    public static void main(String[] args) {
        Plan plan = new Plan(fichier);
        System.out.println(plan);
    }

    public static void geolocalisation() {
        //Demande localisation a utilisateur
        System.out.println("");
        String repneg = "N";
        String reppos = "O";
        String str = "";
        int station = 0;
        while (reppos.compareTo(str) != 0 || repneg.compareTo(str) != 0) {
            System.out.println("Vous trouvez-vous dans une station (O : oui/N : non) ?");
            Scanner sc = new Scanner(System.in);
            str = sc.nextLine();


            if (reppos.compareTo(str) == 0) {
                while (station < 1 || station > 16) {
                    System.out.println("Quel est son nom (tapez le numero correspondant à votre station) ?");
                    System.out.println("1. Amaryllis\n2. Bleuet\n3. Capucine\n4. Geranium\n5. Hortensia\n6. Iris\n7. Jonquille\n8. Lavande\n9. Myosotis\n10. Narcisse\n11. Orchidee\n12. Pissenlit\n13. Rose\n14. Sauge\n15. Tulipe\n16. Violette");
                    Scanner sc2 = new Scanner(System.in);
                    String str2 = sc2.nextLine();
                    // cast de string a int pour faire le switch

                    //aller dans la classe plan getName() pour le nom de la station, i pour son numéro qui est le meme que dans l'array list
                    station = Integer.parseInt(str2);
                    switch (station) {
                        case 1:
                            System.out.println("Vous vous trouvez à Amaryllis.");
                            break;
                        case 2:
                            System.out.println("Vous vous trouvez à Bleuet.");
                            break;
                        case 3:
                            System.out.println("Vous vous trouvez à Capucine.");
                            break;
                        case 4:
                            System.out.println("Vous vous trouvez à Geranium.");
                            break;
                        case 5:
                            System.out.println("Vous vous trouvez à Hortensia.");
                            break;
                        case 6:
                            System.out.println("Vous vous trouvez à Iris.");
                            break;
                        case 7:
                            System.out.println("Vous vous trouvez à Jonquille.");
                            break;
                        case 8:
                            System.out.println("Vous vous trouvez à Lavande.");
                            break;
                        case 9:
                            System.out.println("Vous vous trouvez à Myosotis.");
                            break;
                        case 10:
                            System.out.println("Vous vous trouvez à Narcisse.");
                            break;
                        case 11:
                            System.out.println("Vous vous trouvez à Orchidee.");
                            break;
                        case 12:
                            System.out.println("Vous vous trouvez à Pissenlit.");
                            break;
                        case 13:
                            System.out.println("Vous vous trouvez à Rose.");
                            break;
                        case 14:
                            System.out.println("Vous vous trouvez à Sauge.");
                            break;
                        case 15:
                            System.out.println("Vous vous trouvez à Tulipe.");
                            break;
                        case 16:
                            System.out.println("Vous vous trouvez à Violette.");
                            break;
                        default:
                            System.out.println("La station explicitee n'est pas repertoriee. Recommencez.");
                    } //fermeture switch
                } //fermeture while

            } //fermeture if
            else if (repneg.compareTo(str) == 0) {
                // demande des degres a l'utilisateur
                System.out.println("A quelle latitude vous trouvez-vous ?");
                Scanner sc3 = new Scanner(System.in);
                String str3 = sc3.nextLine();
                double latitude = Double.parseDouble(str3);
                System.out.println("Vous vous trouvez à la latitude " + latitude);
                System.out.println("A quelle longitude vous trouvez-vous ?");
                Scanner sc4 = new Scanner(System.in);
                String str4 = sc4.nextLine();
                double longitude = Double.parseDouble(str4);
                System.out.println("Vous vous trouvez à la longitude " + longitude);
                // c1 = new coord(x,y) ;
			/*
                 * min <- c1.calculdistance(station.getCoord) pour i de 1 à
                 * station.size() double d <-
                 * c1.calculdistance(station.getCoord) si d < min min <- d sdep
                 * <- station fsi fpour
                 */

            } else {
                System.out.println("Merci de respecter le format d'ecriture.");
            }
        }
    }
}