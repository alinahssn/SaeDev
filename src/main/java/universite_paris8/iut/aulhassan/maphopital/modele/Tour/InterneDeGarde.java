package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class InterneDeGarde extends Tour {
    private static final int COUT    = 50;
    private static final int ATTAQUE = 20;
    private static final int VITESSE = 3;
    private static final int PORTEE  = 3;

    public InterneDeGarde() {
        super(COUT, ATTAQUE, VITESSE, PORTEE, "pillule.png",16, false);
    }
}