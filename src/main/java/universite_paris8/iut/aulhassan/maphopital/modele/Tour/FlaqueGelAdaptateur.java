package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import java.util.List;

public class FlaqueGelAdaptateur extends Projectile {

    private PiegeGelSol piegeGelSol;

    public FlaqueGelAdaptateur(double startX, double startY, int degats) {
        super(startX, startY, null, degats, true, "flaque.png", 72);

        this.piegeGelSol = new PiegeGelSol(startX, startY, degats);
    }

    public boolean estEncoreActive() {
        return piegeGelSol.estEncoreActif();
    }

    public void appliquerDegatsZone(List<Ennemi> ennemisActifs) {
        piegeGelSol.tickEtGererEffet(ennemisActifs);

        if (!piegeGelSol.estEncoreActif()) {
            this.setActif(false);
        }
    }

    @Override
    public void deplacer() {
        if (!piegeGelSol.estEncoreActif()) {
            this.setActif(false);
        }
    }

    @Override
    public double getX() {
        return piegeGelSol.getX();
    }

    @Override
    public double getY() {
        return piegeGelSol.getY();
    }
}