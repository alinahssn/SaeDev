package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class Grippé extends Ennemi {

    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int  VITESSE = 8;
    private static final int RECOMPENSE = 30;

    public Grippé() {

        super(PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    @Override
    public String toString(){
        return "Grippé : PV=" + getPv() + "/" + getPvMax() + " | VIVANT=" + estVivant();
    }


}


