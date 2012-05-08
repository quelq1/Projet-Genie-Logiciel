import java.io.Serializable;

public class Coordonnee implements Serializable {

    private double latitude;
    private double longitude;

    public Coordonnee(double lat, double longi) {
        latitude = lat;
        longitude = longi;
    }

    //latitude
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double nouvellelat) {
        latitude = nouvellelat;
    }
    
    //longitude
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double nouveaulong) {
        longitude = nouveaulong;
    }
    
    public double distance(Coordonnee c) {
        double lat = (this.getLatitude() - c.getLatitude()) * (this.getLatitude() - c.getLatitude());
        double lon = (this.getLongitude() - c.getLongitude()) * (this.getLongitude() - c.getLongitude());
        return Math.sqrt(lat + lon);
    }

    @Override
    public String toString() {
        return longitude + ":" + latitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordonnee other = (Coordonnee) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        return hash;
    }
}