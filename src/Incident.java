
public class Incident {
    private int duree;
    private String commentaire;
    
    public Incident(int d) {
        duree = d;
        commentaire = null;
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
    

}