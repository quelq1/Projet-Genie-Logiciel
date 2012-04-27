import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Incident {
    private int duree;
    private String commentaire;
    private static List<Incident> lIncident;
    
    public Incident(int d) {
        duree = d;
        commentaire = null;
        lIncident = new LinkedList<>();
    }
	
    public Incident(int d, String com){
        duree=d;
        commentaire=com;
    }
    
    //duree
    public int getDuree() {
        return duree;
    }
    
    public void setDuree(int newdu){
        duree=newdu;
    }
    
    //commentaire
    public String getCommentaire(){
        return commentaire;
    }
    
    public void setCommentaire(String newcom){
        commentaire=newcom;
    }    
    
    //Incident
    public List<Incident> getListeIncidents() {
        return lIncident;
    }

    public void setListeFragment(LinkedList newinci) {
        lIncident = newinci;
    }

    public boolean addIncident (Incident i) {
        return lIncident.add(i);
    }
    
    public static List<Incident> listeinci() {
        return lIncident;
    }
    
    public static void incidentstat() {
        
        System.out.println("Est-ce que l_incident a-t-il lieu sur une station ? ");
       
        String Reponse;
        Scanner sc = new Scanner(System.in);
	Reponse=sc.next();

	if (Reponse.compareTo("oui")==0 || Reponse.compareTo("Oui")==0 || Reponse.compareTo("OUI")==0) {
            System.out.println("Sur quelle station cela a-t-il lieu ?\n");
            //boucler sur liste station
            //reagrder si elle existe4 info incident ajout incident sur station
        }
	else {
            System.out.println("Est-ce que l_incident a-t-il lieu sur un rail ? ");
            String rep;
            rep=sc.next();

            if (rep.compareTo("oui")==0 || rep.compareTo("Oui")==0 || rep.compareTo("OUI")==0) {
                System.out.println("Sur quelle ligne se trouve cet incident ?\n");
                    
            }
	}
    }

}