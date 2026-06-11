package universite_paris8.iut.aulhassan.maphopital.modele;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi;
import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Gastrique;

public class Vague {

    private EnvironnementJeu environnement;
    private int ennemisRestantsDansVague = 0;
    private int timerSpawn = 0;
    private int intervalleSpawn = 60;

    public Vague(EnvironnementJeu environnement) {
        this.environnement = environnement;
    }

    // Méthode appelée quand l'utilisateur clique sur le bouton de l'interface
    public void lancerVague() {
        if (ennemisRestantsDansVague == 0) {
            ennemisRestantsDansVague = 5;
            timerSpawn = 0;
            System.out.println("Vague initialisée dans le Modèle !");
        }
    }

    public Ennemi tickSpawn() {
        if (ennemisRestantsDansVague > 0) {
            timerSpawn++;
            if (timerSpawn >= intervalleSpawn) {
                timerSpawn = 0;
                ennemisRestantsDansVague--;

                // Création purement mathématique de l'objet universite_paris8.iut.aulhassan.maphopital.modele.universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi.universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.Ennemi
                Gastrique nouvelEnnemi = new Gastrique();
                nouvelEnnemi.setX(16 * 32);
                nouvelEnnemi.setY(0 * 32);

                // Ajout dans notre base de données (le modèle)
                environnement.getEnnemisActifs().add(nouvelEnnemi);

                // On retourne l'objet créé pour que la vue l'intercepte
                return nouvelEnnemi;
            }
        }
        return null; // Aucun ennemi ne doit apparaître à cette frame précise
    }
}