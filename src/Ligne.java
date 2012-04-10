
import java.util.LinkedList;

public class Ligne {
    private String nom;
    private LinkedList<Fragment> fragment;
    
    public Ligne(String n){
        nom=n;
        fragment=new LinkedList();
    }
    
    //Fragment
    public LinkedList getListeFragment(){
        return fragment;
    }
    
    public void setListeFragment(LinkedList newfrag){
        fragment=newfrag;
    }
    
    //nom
    public String getNom(){
        return nom;
    }

    public void setNom(String newname) {
        nom=newname;
    }
            
}
