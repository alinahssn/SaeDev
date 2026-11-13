package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class Grippé extends Ennemi {

    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int VITESSE = 8;
    private static final int RECOMPENSE = 30;

    // Portée en cases dans laquelle le Grippé ralentit les tours
    public static final int PORTEE_RALENTISSEMENT = 3;

    public Grippé() {
        super("Grippé",PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

}