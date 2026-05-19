package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Ennemi {

    private DoubleProperty x = new SimpleDoubleProperty(50);
    private DoubleProperty y = new SimpleDoubleProperty(50);

    public void bouger() {
        int choix = (int) (Math.random() * 4);// 0, 1, 2 ou 3

        if (choix == 0) this.setX(this.getX() + 32);
        if (choix == 1) this.setX(this.getX() - 32);
        if (choix == 2) this.setY(this.getY() + 32);
        if (choix == 3) this.setY(this.getY() - 32);
    }

    public double getX() { return this.x.get(); }
    public void setX(double nouveauX) { this.x.set(nouveauX); }
    public DoubleProperty xProperty() { return this.x; }

    public double getY() { return this.y.get(); }
    public void setY(double nouveauY) { this.y.set(nouveauY); }
    public DoubleProperty yProperty() { return this.y; }
}


