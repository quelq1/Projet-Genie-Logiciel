
public class Fragment {
    private Station depart, arrivee;
    private int temps_de_parcours;
    private Incident incident;
    
    public Fragment(Station dep, Station arr, int tpsparc, Incident inci) {
        depart=dep;
        arrivee=arr;
        temps_de_parcours=tpsparc;
        incident=inci;
    }
    
    //depart
    public Station getStationDep() {
        return depart;
    }
    
    public void setStationDep(Station newdep) {
        depart=newdep;
    }
    
    //arrivee
    public Station getStationArr() {
        return arrivee;
    }
    
    public void setStationArr(Station newarr){
        arrivee=newarr;
    }
    
    //temps de parcours 
    public int getTempsDeParcours(){
        return temps_de_parcours;
    }
    
    public void setTempsDeParcours(int newtpsparc){
        temps_de_parcours=newtpsparc;
    }
    
    //incident
    public Incident getIncident(){
        return incident;
    }
    
    public void setIncident(Incident newinci) {
        incident=newinci;
    }
    
}
