package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

public class Projectile {
    private double x;
    private double y;
    private Ennemi cible;
    private int degats;
    private double vitesse = 15.0;
    protected boolean actif = true;
    private boolean fixe;
    private String nomImage;
    private int taille;

    public Projectile(double startX, double startY, Ennemi cible, int degats,boolean fixe, String nomImage, int taille) {
        this.x = startX;
        this.y = startY;
        this.cible = cible;
        this.degats = degats;
        this.fixe = fixe;
        this.nomImage = nomImage;
        this.taille = taille;
    }

    public void deplacer() {
        if (!actif) return;
        if (cible == null) return;
        if (!cible.estVivant()) {
            actif = false;
            return;
        }

        double dx = cible.getX() - x;
        double dy = cible.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);//distance avec le virus

        if (dist <= vitesse) { //projectile va l'atteindre
            cible.subirDegats(degats);
            actif = false;
        } else {
            x += vitesse * dx / dist;
            y += vitesse * dy / dist;//creation du vecteur unitaire
        }
    }

    public boolean estActif() { return actif; }
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean estFixe() { return fixe; }
    public Ennemi getCible() { return this.cible; }
    public void setActif(boolean actif) { this.actif = actif; }
    public String getNomImage() { return this.nomImage; }
    public int getTaille() { return this.taille; }
}