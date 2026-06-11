package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class Anesthésiste extends Tour {
    private static final int COUT    = 75;
    private static final int ATTAQUE = 0;
    private static final int VITESSE = 1;
    private static final int PORTEE  = 2;

    public Anesthésiste() {
        super(COUT, ATTAQUE, VITESSE, PORTEE, "flaque.png", 64, true);
    }
}

