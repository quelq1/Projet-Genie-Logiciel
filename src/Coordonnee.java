
import java.io.Serializable;


public class Coordonnee implements Serializable {
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
    
    public double distance(Coordonnee c) {
        double lat = (this.getLatitude()-c.getLatitude())*(this.getLatitude()-c.getLatitude());
        double lon = (this.getLongitude()-c.getLongitude())*(this.getLongitude()-c.getLongitude());
        return Math.sqrt(lat + lon);
    }

    @Override
    public String toString() {
        return longitude + ":" + latitude;
    }
}