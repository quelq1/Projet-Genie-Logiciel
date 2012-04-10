
public class Coordonnee {
    private double longitude;
    private double latitude;
    
    public Coordonnee(double longi, double lat) {
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

    @Override
    public String toString() {
        return longitude + ":" + latitude;
    }
}