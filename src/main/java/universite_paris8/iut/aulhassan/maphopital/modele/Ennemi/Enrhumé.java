package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.ActionsMouchoir;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.MouchoirEnrhumé;

public class Enrhumé extends Ennemi {
    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int VITESSE = 8;
    private static final int RECOMPENSE = 30;

    private int caseParcourues = 0;

    public Enrhumé() {

        super("Enrhumé", PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    @Override
    public void utiliserPouvoir(ActionsMouchoir actions) {
        if (estArriveSurNouvelleCase()) {
            caseParcourues++;

            if (caseParcourues % 10 == 0) {
                actions.ajouterMouchoir(new MouchoirEnrhumé(getX(), getY()));

            }
        }
    }
}
