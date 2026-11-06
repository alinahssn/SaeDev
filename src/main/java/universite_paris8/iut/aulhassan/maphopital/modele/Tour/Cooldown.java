package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class Cooldown {
    private int cooldownMax;
    private int cooldownActuel;
    private double multiplicateur;

    public Cooldown(int cooldownMax) {
        this.cooldownMax = cooldownMax;
        this.cooldownActuel = 0;
        this.multiplicateur = 1.0;
    }

    public void tick() {
        if (this.cooldownActuel > 0) {
            this.cooldownActuel--;
        }
    }

    public boolean estPret() {
        return this.cooldownActuel <= 0;
    }

    public void declencher() {
        this.cooldownActuel = (int) Math.round(this.cooldownMax * this.multiplicateur);
    }

    public void setMultiplicateur(double multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    public void setCooldownActuel(int valeur) {
        this.cooldownActuel = valeur;
    }

    public int getCooldownMax() {
        return cooldownMax;
    }

    public int getCooldownActuel() {
        return cooldownActuel;
    }
}