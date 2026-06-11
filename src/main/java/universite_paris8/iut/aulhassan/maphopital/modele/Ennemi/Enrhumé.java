package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class Enrhumé extends Ennemi {
    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int  VITESSE = 2;
    private static final int RECOMPENSE = 30;

    public Enrhumé() {

        super(PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    @Override
    public String toString(){
        return "Enrhumé : PV=" + getPv() + "/" + getPvMax() + " | VIVANT=" + estVivant();
    }
}
