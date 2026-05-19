package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Ennemi {

    private DoubleProperty x = new SimpleDoubleProperty(-200);
    private DoubleProperty y = new SimpleDoubleProperty(-200);



    public double getX() { return this.x.get(); }
    public void setX(double nouveauX) { this.x.set(nouveauX); }
    public DoubleProperty xProperty() { return this.x; }

    public double getY() { return this.y.get(); }
    public void setY(double nouveauY) { this.y.set(nouveauY); }
    public DoubleProperty yProperty() { return this.y; }
}


