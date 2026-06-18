package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.aulhassan.maphopital.modele.BFS.Sommet;
import universite_paris8.iut.aulhassan.maphopital.modele.Terrain;

import java.util.List;

public class VueTerrain {

    private TilePane tilehopital;
    private Terrain terrain;

    // Grille des ImageView pour pouvoir les modifier après coup (ex: spawns)
    private ImageView[][] cases;

    private Image imSolHopital;
    private Image imSolChambre;
    private Image imSolSpawn;

    public VueTerrain(TilePane tilehopital, Terrain terrain) {
        this.tilehopital = tilehopital;
        this.terrain = terrain;
    }

    public void dessinerCartographie() {
        imSolHopital  = ChargeurImage.charger("solhopital.png");
        imSolChambre  = ChargeurImage.charger("solchambre.png");
        imSolSpawn    = ChargeurImage.charger("solespawn.png");

        Image im2  = ChargeurImage.charger("mur.png");
        Image im3  = ChargeurImage.charger("lit.png");
        Image im4  = ChargeurImage.charger("chevet.png");
        Image im5  = ChargeurImage.charger("chaise.png");
        Image im6  = ChargeurImage.charger("machine.png");
        Image im7  = ChargeurImage.charger("bureau.png");
        Image im8  = ChargeurImage.charger("chaise2.png");
        Image im9  = ChargeurImage.charger("distrib.png");
        Image im10 = ChargeurImage.charger("plante.png");
        Image im11 = ChargeurImage.charger("lit2.png");

        cases = new ImageView[terrain.getHauteur()][terrain.getLargeur()];

        for (int i = 0; i < terrain.getHauteur(); i++) {
            for (int j = 0; j < terrain.getLargeur(); j++) {
                ImageView imv = new ImageView();
                imv.setFitWidth(32);
                imv.setFitHeight(32);
                switch (terrain.getMap()[i][j]) {
                    case 0:  imv.setImage(imSolChambre); break;
                    case 1:  imv.setImage(imSolHopital); break;
                    case 2:  imv.setImage(im2);  break;
                    case 3:  imv.setImage(im3);  break;
                    case 4:  imv.setImage(im4);  break;
                    case 5:  imv.setImage(im5);  break;
                    case 6:  imv.setImage(im6);  break;
                    case 7:  imv.setImage(im7);  break;
                    case 8:  imv.setImage(im8);  break;
                    case 9:  imv.setImage(im9);  break;
                    case 10: imv.setImage(im10); break;
                    case 11: imv.setImage(im11); break;
                    default: imv.setImage(imSolChambre); break;
                }
                cases[i][j] = imv;
                tilehopital.getChildren().add(imv);
            }
        }
    }


    public void afficherSpawns(List<Sommet> spawns, int[] spawnsActifs) {
        for (int i = 0; i < spawns.size(); i++) {
            Sommet s = spawns.get(i);
            boolean actif = false;
            for (int idx : spawnsActifs) if (idx == i) { actif = true; break; }
            cases[s.getY()][s.getX()].setImage(actif ? imSolSpawn : imSolChambre);//redessine pas la map à chaque fois
        }
    }
}