package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

public class ProjectileDegatsBonus extends ProjectileDecorator {

    private final int bonus;

    public ProjectileDegatsBonus(Projectile enveloppe, int bonus) {
        super(enveloppe);
        this.bonus = bonus;
    }

    @Override
    public void deplacer() {
        Ennemi cible = getCible();
        boolean vivantAvant = cible != null && cible.estVivant();

        super.deplacer();

        if (vivantAvant && !estActif() && cible.estVivant()) {
            cible.subirDegats(bonus);
        }
    }
}