package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;

public class Tour {
    private SimpleIntegerProperty x = new SimpleIntegerProperty(40);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(40);

    private int cout;
    private int attaque;
    private int vitesse;
    private int portee;

    public Tour(int cout, int attaque, int vitesse, int portee) {
        this.cout = cout;
        this.attaque = attaque;
        this.vitesse = vitesse;
        this.portee = portee;

    }



}
