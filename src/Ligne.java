import java.util.LinkedList;
import java.util.List;

public class Ligne {

    private String nom;
    private List<Fragment> lFragment;

    public Ligne(String n) {
        nom = n;
        lFragment = new LinkedList<>();
    }

    //nom
    public String getNom() {
        return nom;
    }

    public void setNom(String newname) {
        nom = newname;
    }

    //Fragment
    public List<Fragment> getListeFragments() {
        return lFragment;
    }

    public void setListeFragment(LinkedList newfrag) {
        lFragment = newfrag;
    }

    public boolean addFragment(Fragment f) {
        return lFragment.add(f);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Ligne other = (Ligne) obj;
        if ((this.nom == null) ? (other.nom != null) : !this.nom.toUpperCase().equals(other.nom.toUpperCase())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.nom != null ? this.nom.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return nom + " : " + (lFragment.size()+1) + " stations : \n"+lFragment;
    }
    
    public boolean contientStation(Station s) {
        for (Fragment f : lFragment) {
            if (f.contientStation(s)) {
                return true;
            }
        }
        return false;
    }
}