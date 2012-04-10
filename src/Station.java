package essaigit;

public class Station {
    public String nom;
    public Coordonnees coordonnee;
    public int tps_darret=2;
    public Incidents incident;
    
    public Station(String n, Coordonnees coord, int tpsarr, Incidents inci) {
        nom=n;
        coordonnee=coord;
        tps_darret=tpsarr;
        incident=inci;
    }
    
    //nom
    public String getnom(){
        return nom;
    }

    public void setnom(String newname) {
        nom=newname;
    }
    
    //coordonnee
    public Coordonnees getcoord(){
        return coordonnee;
    }

    public void setcoord(Coordonnees newcoor) {
        coordonnee=newcoor;
    }
    
    //tps darret
    public int gettpsarr(){
        return tps_darret;
    }

    public void settpsarr(int newtpsarr) {
        tps_darret=newtpsarr;
    }
    
    //incident
    public Incidents getinci(){
        return incident;
    }
    
    public void setinci(Incidents newinci) {
        incident=newinci;
    }
    
}
