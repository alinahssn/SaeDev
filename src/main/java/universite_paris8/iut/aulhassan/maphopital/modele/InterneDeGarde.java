package universite_paris8.iut.aulhassan.maphopital.modele;

public class InterneDeGarde extends Tour{
    private static final int cout = 75;
    private static final int attaque = 20;
    private static final int  vitesse = 3;
    private static final int portee = 2;

    public InterneDeGarde(int cout, int attaque, int vitesse, int portee) {
        super(cout, attaque, vitesse, portee);
    }

    /*public String toString() {
        return "InterneDeGarde : COUT = " + getCout() + " | ATTAQUE = " + getAttaque() + " | VITESSE = " + getVitesse() + " | PORTEE = "  + getPortee() + " | ACTIVE = "  + estActive();
    }
*/

}
