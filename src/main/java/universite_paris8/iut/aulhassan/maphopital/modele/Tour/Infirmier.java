package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class Infirmier extends Tour {

    private static final int COUT    = 75;
    private static final int ATTAQUE = 5;
    private static final int VITESSE = 1;
    private static final int PORTEE  = 2;

    public Infirmier() {
        super(COUT, ATTAQUE, VITESSE, PORTEE, "flaque.png", 72, true);
    }
}
