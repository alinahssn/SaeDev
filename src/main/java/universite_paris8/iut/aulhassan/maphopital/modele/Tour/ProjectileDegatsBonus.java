package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class ProjectileDegatsBonus extends ProjectileDecorator {

    private final int bonus;
    private boolean bonusApplique = false;

    public ProjectileDegatsBonus(Projectile enveloppe, int bonus) {
        super(enveloppe);
        this.bonus = bonus;
    }

    @Override
    public void deplacer() {
        super.deplacer();

        if (!bonusApplique && aTouche() && getCible().estVivant()) {
            getCible().subirDegats(bonus);
            bonusApplique = true;
        }
    }
}