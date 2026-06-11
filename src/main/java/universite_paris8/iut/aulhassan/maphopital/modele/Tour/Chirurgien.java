package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class Chirurgien extends Tour {
    private static final int COUT    = 150;
    private static final int ATTAQUE = 50;
    private static final int VITESSE = 1;
    private static final int PORTEE  = 2;

    public Chirurgien() {
        super(COUT, ATTAQUE, VITESSE, PORTEE, "flaque.png", 64, true);
    }
}
