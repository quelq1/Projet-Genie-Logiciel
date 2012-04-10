package essaigit;

public class Coordonnees {
    public double longitude;
    public double latitude;
    
    public Coordonnees(int longi, int lat) {
        longitude=longi;
        latitude=lat;
    }
    
    //longitude
    public double getlong(){
        return longitude;
    }

    public void setlong(double nouveaulong) {
        longitude=nouveaulong;
    }
    
    //latitude
    public double getlati(){
        return latitude;
    }

    public void setlati(double nouvellelat) {
        latitude=nouvellelat;
    }
    
}
