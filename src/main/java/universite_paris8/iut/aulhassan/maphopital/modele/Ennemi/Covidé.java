package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class Covidé extends Ennemi {

    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int  VITESSE = 8;
    private static final int RECOMPENSE = 30;

    public Covidé() {
        super(PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    public boolean estSurMouchoir(MouchoirEnrhumé mouchoir){
        return getX() == mouchoir.getX() && getY() == mouchoir.getY();
    }
    public Enrhumé utiliserPouvoir(MouchoirEnrhumé mouchoir) {
        if(estSurMouchoir(mouchoir)) {
            Enrhumé enrhumé = new Enrhumé();
            enrhumé.setX(getSpawnX());
            enrhumé.setY(getSpawnY());
            enrhumé.setSpawn(getSpawnX(), getSpawnY());
            return enrhumé;
        }
        return null;
    }

    @Override
    public String toString(){
        return "Covidé : PV=" + getPv() + "/" + getPvMax() + " | VIVANT=" + estVivant();
    }
}
