package essaigit;

public class Main {
    
    public static void main(String[] args) {
        Incidents inci = new Incidents(20,"suicide");
        Coordonnees coord = new Coordonnees(48,2);
        Station station = new Station("Myosotis",coord,2,inci);
        Station station2 = new Station("Violette",coord,2,inci);
       
        Incidents inci2 = new Incidents(10,"panne");
        Incidents inci3 = new Incidents(20,"greve");
        
        Station station3 = new Station("Jonquille",coord,2,inci2);
        
        Fragment frag = new Fragment(station,station2,20,inci);
        Fragment frag2 = new Fragment(station,station3,20,inci2);
        
        
    }
}
