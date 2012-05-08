
import java.io.Serializable;
import java.util.Scanner;

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
        return latitude + ":" + longitude;
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

    public static Coordonnee saisieCoord(Plan plan, Scanner sc) {
        //Saisie coord
        double[] coord = new double[2];
        String[] nomCoord = new String[]{"latitude", "longitude"};
        Coordonnee res;

        boolean saisieOk;
        for (int i = 0; i < nomCoord.length; i++) {
            saisieOk = false;
            do {
                System.out.println("A quelle " + nomCoord[i] + " se trouve-t-elle ? ");
                try {
                    coord[i] = Double.parseDouble(sc.next());

                    if (0. <= coord[i] && coord[i] <= 90.) {
                        saisieOk = true;
                    } else {
                        System.out.println("Choix incorrect : la " + nomCoord[i] + " doit être entre 0 et 90.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Choix incorrect.");
                }
            } while (!saisieOk);
        }

        res = new Coordonnee(coord[0], coord[1]);

        return res;
    }
}