/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mami Sall
 */
public class Ligne {

    private String nom;
    private List<Fragment> l;

    public String getNom() {
        return nom;
    }

    public List<Fragment> getL() {
        return l;
    }

    public Ligne(String n) {
        nom = n;
        l = new LinkedList<Fragment>();
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
        if ((this.nom == null) ? (other.nom != null) : !this.nom.equals(other.nom)) {
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
        return nom;
    }
}
