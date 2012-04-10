
public class Main {
    
    public static void main(String[] args) {
        Incident inci = new Incident(20,"suicide");
        Coordonnee coord = new Coordonnee(48,2);
        Station station = new Station("Myosotis",coord,inci);
        Station station2 = new Station("Violette",coord,inci);
       
        Incident inci2 = new Incident(10,"panne");
        Incident inci3 = new Incident(20,"greve");
        
        Station station3 = new Station("Jonquille",coord,inci2);
        
        Fragment frag = new Fragment(station,station2,20,inci);
        Fragment frag2 = new Fragment(station,station3,20,inci2);
        
        
    }
}