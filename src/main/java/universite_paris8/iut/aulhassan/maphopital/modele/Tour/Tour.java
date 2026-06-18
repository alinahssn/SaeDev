package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.List;

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
    public int getCooldownMax() { return cooldownMax; }

    // cooldownActuel : compteur qui décompte de cooldownMax jusqu'à 0
    // quand il vaut 0, la tour est prête à tirer
    protected int cooldownActuel = 0;

    // multiplicateur appliqué au cooldown (1.0 = normal, 2.0 = deux fois plus lent)
    // mis à jour chaque frame selon les ennemis Grippé à portée
    private double multiplicateurCooldown = 1.0;

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
    public boolean getProjectileFixe()     { return projFixe; }

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
        if (cible == null || !cible.estVivant()) return false;//pas de cible ou deja morte non
        if (cooldownActuel > 0) return false;//encore en train de recharger non

        int dx = this.x.get() - cible.getX();
        int dy = this.y.get() - cible.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance <= (this.portee * 32);

    }//calcul distance pixel entre tour et virus -> si inférieur ou = à la portée -> tour convertie en pixel portee*32 et renvoie true -> peutTirer

    // Fonction pour le Grippé (ralentissemnt des projectils)
    public void setMultiplicateurCooldownProjectil(double multiplicateur) {
        this.multiplicateurCooldown = multiplicateur;
    }

    public Projectile agir(List<Ennemi> ennemisActifs) {
        tickCooldown();

        for(Ennemi e :  ennemisActifs) {
            if(peutTirer(e)) {
                Projectile proj = new Projectile(getX() + 16, getY() + 16, e, getDegat(), getProjectileFixe(),getNomImageProjectile(), getTailleProjectile());
                this.cooldownActuel = (int) Math.round(this.cooldownMax * this.multiplicateurCooldown);
                return proj;
            }
        }
        return null;
    }

    public int getPrixRevente() {
        return (int) (this.cout * 0.7);
    }
}