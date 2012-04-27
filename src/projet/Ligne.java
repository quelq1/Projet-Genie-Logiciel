/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

/**
 *
 * @author Mami Sall
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    public boolean equalsNom(Object obj) {
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ligne other = (Ligne) obj;
        
        if (!Objects.equals(this.lFragment, other.lFragment)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.lFragment);
        return hash;
    }

   

    @Override
    public String toString() {
        return nom + " : " + (lFragment.size()+1) + " stations.";
    }
}