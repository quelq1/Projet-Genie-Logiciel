
public class Coordonnee {
    private double latitude;
    private double longitude;
    
    public Coordonnee(double lat, double longi) {
        latitude=lat;
        longitude=longi;
    }
    
    //latitude
    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double nouvellelat) {
        latitude=nouvellelat;
    }
    
    
    //longitude
    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double nouveaulong) {
        longitude=nouveaulong;
    }
    
    public double distance(Coordonnee c) {
        double lat = (this.getLatitude()-c.getLatitude())*(this.getLatitude()-c.getLatitude());
        double lon = (this.getLongitude()-c.getLongitude())*(this.getLongitude()-c.getLongitude());
        return Math.sqrt(lat + lon);
    }

    @Override
    public String toString() {
        return latitude + ":" + longitude;
    }

}