package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

public abstract class ProjectileDecorator extends Projectile {

    protected final Projectile enveloppe;

    public ProjectileDecorator(Projectile enveloppe) {
        super(enveloppe.getX(), enveloppe.getY(), enveloppe.getCible(), 0,
                enveloppe.estFixe(), enveloppe.getNomImage(), enveloppe.getTaille());
        this.enveloppe = enveloppe;
    }

    @Override public void deplacer()          { enveloppe.deplacer(); }
    @Override public boolean estActif()       { return enveloppe.estActif(); }
    @Override public boolean aTouche()        { return enveloppe.aTouche(); }
    @Override public double getX()            { return enveloppe.getX(); }
    @Override public double getY()            { return enveloppe.getY(); }
    @Override public Ennemi getCible()        { return enveloppe.getCible(); }
    @Override public boolean estFixe()        { return enveloppe.estFixe(); }
    @Override public String getNomImage()     { return enveloppe.getNomImage(); }
    @Override public int getTaille()          { return enveloppe.getTaille(); }
    @Override public void setActif(boolean a) { enveloppe.setActif(a); }
}