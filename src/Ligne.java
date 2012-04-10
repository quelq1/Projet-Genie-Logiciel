package essaigit;

import java.util.LinkedList;

public class Ligne {
    LinkedList fragment=new LinkedList();
    public String nom;
    
    public Ligne(LinkedList frag, String n){
        fragment=frag;
        nom=n;
    }
    
    //Fragment
    public LinkedList getfrag(){
        return fragment;
    }
    
    public void setfrag(LinkedList newfrag){
        fragment=newfrag;
    }
    
    //nom
    public String getnom(){
        return nom;
    }

    public void setnom(String newname) {
        nom=newname;
    }
            
}
