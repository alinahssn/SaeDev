package universite_paris8.iut.aulhassan.maphopital.modele;

public class Masquier extends Tour {
    private static final int COUT    = 50;
    private static final int ATTAQUE = 0;
    private static final int VITESSE = 0;
    private static final int PORTEE  = 1;

    public Masquier() {
        super(COUT, ATTAQUE, VITESSE, PORTEE, "flaque.png", 64, true);
    }

}
