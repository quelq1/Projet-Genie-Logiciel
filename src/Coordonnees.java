
public class Coordonnees {
    private double longitude;
    private double latitude;
    
    public Coordonnees(int longi, int lat) {
        longitude=longi;
        latitude=lat;
    }
    
    //longitude
    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double nouveaulong) {
        longitude=nouveaulong;
    }
    
    //latitude
    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double nouvellelat) {
        latitude=nouvellelat;
    }
    
}
