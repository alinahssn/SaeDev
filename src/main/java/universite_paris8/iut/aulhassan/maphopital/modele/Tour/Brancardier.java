package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class Brancardier extends Tour {

    private static final int COUT    = 150;
    private static final int ATTAQUE = 15;
    private static final int VITESSE = 2;
    private static final int PORTEE  = 5;
    public Brancardier() {
        super(COUT, ATTAQUE, VITESSE, PORTEE, "flaque.png", 64, true);
    }
}
