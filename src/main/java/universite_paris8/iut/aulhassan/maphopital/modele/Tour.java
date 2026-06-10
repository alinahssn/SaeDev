package universite_paris8.iut.aulhassan.maphopital.modele;

import javafx.beans.property.SimpleIntegerProperty;

public class Tour {
    private SimpleIntegerProperty x = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty y = new SimpleIntegerProperty(0);

    private int cout;
    private int degat;
    private int vitesse;
    private int portee;

    private String nomImageProj;
    private int tailleProj;
    private boolean projFixe;


    // cooldownMax : nombre de frames à attendre entre chaque tir
    // la game loop tourne à ~60 frames/sec, donc 60 = 1 tir par seconde
    private int cooldownMax = 60;

    // cooldownActuel : compteur qui décompte de cooldownMax jusqu'à 0
    // quand il vaut 0, la tour est prête à tirer
    private int cooldownActuel = 0;

    public Tour(int cout, int degat, int vitesse, int portee, String nomImageProj, int tailleProj, boolean projFixe) {
        this.cout = cout;
        this.degat = degat;
        this.vitesse = vitesse;
        this.portee = portee;

        this.nomImageProj = nomImageProj;
        this.tailleProj = tailleProj;
        this.projFixe = projFixe;
    }

    public String getNomImageProjectile() { return nomImageProj; }
    public int getTailleProjectile()      { return tailleProj; }
    public boolean isProjectileFixe()     { return projFixe; }

    public int getCout()    { return cout; }
    public int getDegat()   { return degat; }
    public int getVitesse() { return vitesse; }
    public int getPortee()  { return portee; }

    public int getX() { return x.get(); }
    public int getY() { return y.get(); }
    public void setX(int nx) { x.set(nx); }
    public void setY(int ny) { y.set(ny); }

    // appelé à chaque frame dans la game loop
    // décrémente le compteur de 1 jusqu'à ce qu'il atteigne 0
    public void tickCooldown() {
        if (cooldownActuel > 0) cooldownActuel--;
    }

    public boolean peutTirer(Ennemi cible) {
        if (cible == null || !cible.estVivant()) return false;

        // si le cooldown n'est pas terminé, on ne peut pas tirer
        if (cooldownActuel > 0) return false;

        int dx = this.x.get() - cible.getX();
        int dy = this.y.get() - cible.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= this.portee * 32) {
            // on tire : on remet le cooldown au maximum pour attendre avant le prochain tir
            cooldownActuel = cooldownMax;
            return true;
        }
        return false;
    }
}