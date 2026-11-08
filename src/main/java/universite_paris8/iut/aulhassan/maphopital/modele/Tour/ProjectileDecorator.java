package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public abstract class ProjectileDecorator extends Projectile {

    protected final Projectile enveloppe;

    public ProjectileDecorator(Projectile enveloppe) {
        super(enveloppe.getX(), enveloppe.getY(), enveloppe.getCible(), 0, enveloppe.estFixe(), enveloppe.getNomImage(), enveloppe.getTaille());
        this.enveloppe = enveloppe;
    }

    @Override public void deplacer()          { enveloppe.deplacer(); }
    @Override public boolean estActif()       { return enveloppe.estActif(); }
    @Override public double getX()            { return enveloppe.getX(); }
    @Override public double getY()            { return enveloppe.getY(); }
    @Override public void setActif(boolean a) { enveloppe.setActif(a); }
}