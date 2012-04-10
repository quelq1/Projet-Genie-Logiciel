
public class Station {
    private String nom;
    private Coordonnees coordonnee;
    private static int tps_darret=2;
    private Incident incident;
    
    public Station(String n, Coordonnees coord, Incident inci) {
        nom=n;
        coordonnee=coord;
        incident=inci;
    }
    
    //nom
    public String getNom(){
        return nom;
    }

    public void setNom(String newname) {
        nom=newname;
    }
    
    //coordonnee
    public Coordonnees getCoord(){
        return coordonnee;
    }

    public void setCoord(Coordonnees newcoor) {
        coordonnee=newcoor;
    }
    
    //tps darret
    public int getTempsArret(){
        return tps_darret;
    }
    
    //incident
    public Incident getIncident(){
        return incident;
    }
    
    public void setIncident(Incident newinci) {
        incident=newinci;
    }    
}
