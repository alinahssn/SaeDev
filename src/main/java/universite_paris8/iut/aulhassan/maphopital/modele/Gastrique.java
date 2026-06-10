package universite_paris8.iut.aulhassan.maphopital.modele;
/*
Description :
    Version plus faible de l'enrhumé, sans mouchoirs. PV réduits pour justifier la récompense moindre.

 */

public class Gastrique extends Ennemi {
    private static final int PV_DEPART = 80;
    private static final int ATTAQUE = 5;
    private static final int  VITESSE = 16;
    private static final int RECOMPENSE = 15;

    public Gastrique() {
        super(PV_DEPART,ATTAQUE,VITESSE,RECOMPENSE);
    }

    public String toString(){
        return "Gastrique : PV = "+getPv()+" | ATTAQUE = " + getAttaque()+" | VITESSE = " + getVitesse()+" | RECOMPENSE = " + getRecompense()+" | VIVANT = " + estVivant();
    }

}
