package universite_paris8.iut.aulhassan.maphopital.modele;

public class Projectile {
    private double x;
    private double y;
    private Ennemi cible;
    private int degats;
    private double vitesse = 15.0;
    private boolean actif = true;

    public Projectile(double startX, double startY, Ennemi cible, int degats) {
        this.x = startX;
        this.y = startY;
        this.cible = cible;
        this.degats = degats;
    }

    public void deplacer() {
        if (!actif) return;
        if (!cible.estVivant()) { actif = false; return; }

        double dx = cible.getX() - x;
        double dy = cible.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= vitesse) {
            cible.subirDegats(degats);
            actif = false;
        } else {
            x += vitesse * dx / dist;
            y += vitesse * dy / dist;
        }
    }

    public boolean estActif() { return actif; }
    public double getX()      { return x; }
    public double getY()      { return y; }
}