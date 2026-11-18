package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;

import java.util.List;

public abstract class Tour {
    private SimpleIntegerProperty x = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(0);

    private int cout;
    private int degat;
    private int vitesse;
    private int portee;

    private ConfigProjectile configProjectile;
    private Cooldown cooldown;
    private StrategieCiblage strategieCiblage = new CiblagePremierEnPortee();

    public Tour(int cout, int degat, int vitesse, int portee, ConfigProjectile configProjectile) {
        this.cout = cout;
        this.degat = degat;
        this.vitesse = vitesse;
        this.portee = portee;
        this.configProjectile = configProjectile;
        this.cooldown = new Cooldown(60);
    }

    public ConfigProjectile getConfigProjectile() {return configProjectile; }
    public String getNomImageProjectile() { return configProjectile.getNomImage(); }
    public int getTailleProjectile()      { return configProjectile.getTaille(); }
    public boolean getProjectileFixe()     { return configProjectile.isFixe(); }

    public int getCout()    { return cout; }
    public int getDegat()   { return degat; }
    public int getVitesse() { return vitesse; }
    public int getPortee()  { return portee; }

    public int getX() { return x.get(); }
    public int getY() { return y.get(); }
    public void setX(int nx) { x.set(nx); }
    public void setY(int ny) { y.set(ny); }

    public void tickCooldown() {
        cooldown.tick();
    }

    public boolean peutTirer(Ennemi cible) {
        if (cible == null || !cible.estVivant()) return false;
        if (!cooldown.estPret()) return false;

        double distance = Position.calculerDistance(this.getX(), this.getY(), cible.getX(), cible.getY());
        return distance <= (this.portee * 32);
    }

    public void setMultiplicateurCooldownProjectil(double multiplicateur) {
        cooldown.setMultiplicateur(multiplicateur);
    }

    public final Projectile agir(List<Ennemi> ennemisActifs) {
        tickCooldown();
        return executerAction(ennemisActifs);
    }

    protected abstract Projectile executerAction(List<Ennemi> ennemisActifs);

    public abstract int getNumCaseApresRetrait();

    public boolean estDetruit() { return false;}

    public void setStrategieCiblage(StrategieCiblage strategieCiblage) {
        this.strategieCiblage = strategieCiblage;
    }

    protected Ennemi choisirCible(List<Ennemi> ennemisActifs) {
        return strategieCiblage.choisir(this, ennemisActifs);
    }

    protected Projectile creerProjectile(Ennemi cible, int degats) {
        return new Projectile(getX() + 16, getY() + 16, cible, degats,getProjectileFixe(), getNomImageProjectile(), getTailleProjectile());
    }

    public int getPrixRevente() {
        return (int) (this.cout * 0.7);
    }

    public boolean estCooldownPret() {
        return cooldown.estPret();
    }

    public void setCooldownActuel(int valeur) {
        cooldown.setCooldownActuel(valeur);
    }
}