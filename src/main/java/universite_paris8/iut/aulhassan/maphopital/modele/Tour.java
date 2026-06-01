package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Tour {
    private SimpleIntegerProperty x = new SimpleIntegerProperty(40);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(40);

    private int cout;
    private int degat;
    private int vitesse;
    private int portee;
    private Ennemi cible;
    private int xPortee;
    private int yPortee;

    public Tour(int cout ,int degat, int vitesse, int portee) {
        this.cout = cout;
        this.degat = degat;
        this.vitesse = vitesse;
        this.portee = portee;
        this.cible = new Ennemi();
    }

    public int getCout() {
        return cout;
    }

    //public boolean dansPortee (Ennemi cible){
    //}

    public boolean attaquable ( ){
        if (this.cible==null){
            return false;
        }
        int dx = this.x.get() - this.cible.getX();
        int dy = this.y.get() - this.cible.getY();
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance <= this.portee;
    }



    public int action(Ennemi cible) {
        this.cible = cible;
        if (attaquable()){
            cible.subirDegats(this.degat);
            return this.degat;
        }
        return 0;
    }





}
