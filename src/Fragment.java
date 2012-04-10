package essaigit;

public class Fragment {
    public Station depart;
    public Station arrivee;
    public int temps_de_parcours;
    public Incidents incident;
    
    public Fragment(Station dep, Station arr, int tpsparc, Incidents inci) {
        depart=dep;
        arrivee=arr;
        temps_de_parcours=tpsparc;
        incident=inci;
    }
    
    //depart
    public Station getdep() {
        return depart;
    }
    
    public void setdep(Station newdep) {
        depart=newdep;
    }
    
    //arrivee
    public Station getarr() {
        return arrivee;
    }
    
    public void setarr(Station newarr){
        arrivee=newarr;
    }
    
    //temps de parcours 
    public int gettpsparc(){
        return temps_de_parcours;
    }
    
    public void settpsparc(int newtpsparc){
        temps_de_parcours=newtpsparc;
    }
    
    //incident
    public Incidents getinci(){
        return incident;
    }
    
    public void setinci(Incidents newinci) {
        incident=newinci;
    }
    
}
