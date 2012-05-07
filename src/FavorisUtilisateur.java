
import java.util.List;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Loïc Cimon
 */
public class FavorisUtilisateur {
    
    private List<Station> favoris;
    
    public void menuGestionFavoris() {
        System.out.println("\t\t------------");
        System.out.println("\t\t  Favoris");
        System.out.println("\t\t------------");
        System.out.println("");
        System.out.println("1 - Ajouter une station aux favorites");
        System.out.println("2 - Supprimer une station aux favorites");
        System.out.println("");
        System.out.println("0 - Quitter");
        System.out.println("");
        System.out.println("Entrez votre choix : ");
        
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();
    }
    
}
