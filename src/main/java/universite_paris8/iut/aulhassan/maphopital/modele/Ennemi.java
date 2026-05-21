package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ennemi {

    private SimpleIntegerProperty x = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(0);

    private int pv;
    private int pvMax;
    private int attaque;
    private int vitesse;
    private int recompense;
    private boolean vivant;

    public Ennemi(int pv, int attaque, int vitesse, int recompense) {
        this.pv = pv;
        this.pvMax = pv;
        this.attaque = attaque;
        this.vitesse = vitesse;
        this.recompense = recompense;
        this.vivant = true;
    }
    public Ennemi() {
        this(80,5,1,15);
    }

    // fonction qui retourne true si l'ennemi vivant /false mort
    public boolean estVivant() {
        return vivant;
    }

    public int getPv(){
        return pv;
    }
    public int getPvMax(){
        return pvMax;
    }
    public int getAttaque(){
        return attaque;
    }
    public int getVitesse(){
        return vitesse;
    }
    public int getRecompense(){
        return recompense;
    }

    public int setPv(int pv) {
        return this.pv = pv;
    }
    public int setPvMax(int pvMax){
       return this.pvMax = pvMax;
    }
    public int setAttaque(int attaque){
        return this.attaque = attaque;
    }

    public int setVitesse(int vitesse){
        return this.vitesse = vitesse;
    }
    public int setRecompense(int recompense){
        return this.recompense = recompense;
    }




    public int getX() { return this.x.get(); }
    public void setX(int nouveauX) { this.x.set(nouveauX); }
    public SimpleIntegerProperty xProperty() { return this.x; }

    public int getY() { return this.y.get(); }
    public void setY(int nouveauY) { this.y.set(nouveauY); }
    public SimpleIntegerProperty yProperty() { return this.y; }
}


