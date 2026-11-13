package universite_paris8.iut.aulhassan.maphopital.modele.BFS.deplacement;

import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;

import java.util.ArrayList;

public class DeplacementDirect implements StrategieDeplacement{

    @Override
    public void deplacer(Ennemi ennemi) {
        int cibleX = 23 * 32;
        int cibleY = 12 * 32;

        if (ennemi.getX() < cibleX)
            ennemi.setX(Math.min(ennemi.getX() + ennemi.getVitesse(), cibleX));

        else if (ennemi.getX() > cibleX)
            ennemi.setX(Math.max(ennemi.getX() - ennemi.getVitesse(), cibleX));

        if (ennemi.getY() < cibleY)
            ennemi.setY(Math.min(ennemi.getY() + ennemi.getVitesse(), cibleY));

        else if (ennemi.getY() > cibleY)
            ennemi.setY(Math.max(ennemi.getY() - ennemi.getVitesse(), cibleY));

    }

}