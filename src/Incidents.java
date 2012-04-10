package essaigit;

public class Incidents {
    public int duree;
    public String commentaire;
    
    public Incidents(int d, String com){
        duree=d;
        commentaire=com;
    }
    
    //duree
    public int getduree() {
        return duree;
    }
    
    public void setduree(int newdu){
        duree=newdu;
    }
    
    //commentaire
    public String getcom(){
        return commentaire;
    }
    
    public void setcom(String newcom){
        commentaire=newcom;
    }    
    
}
