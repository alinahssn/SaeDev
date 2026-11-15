package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.ActionsMouchoir;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Pouvoir.MouchoirEnrhumé;

public class Covidé extends Ennemi {

    private static final int PV_DEPART = 100;
    private static final int ATTAQUE = 10;
    private static final int  VITESSE = 8;
    private static final int RECOMPENSE = 30;

    public Covidé() {
        super("Covidé",PV_DEPART, ATTAQUE, VITESSE, RECOMPENSE);
    }

    public boolean estSurMouchoir(MouchoirEnrhumé mouchoir){
        return getX() == mouchoir.getX() && getY() == mouchoir.getY();
    }
    @Override
    public void utiliserPouvoir(ActionsMouchoir actions) {
        for (MouchoirEnrhumé mouchoir : actions.getMouchoirsActifs()) {
            if (estSurMouchoir(mouchoir)) {
                actions.transformerMouchoir(mouchoir, creerEnrhume());
            }
        }
    }

    private Enrhumé creerEnrhume() {
        Enrhumé enrhumé = new Enrhumé();
        enrhumé.setX(getSpawnX());
        enrhumé.setY(getSpawnY());
        enrhumé.setSpawn(getSpawnX(), getSpawnY());
        return enrhumé;
    }
}
