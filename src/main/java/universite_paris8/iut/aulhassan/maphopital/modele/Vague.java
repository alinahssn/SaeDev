package universite_paris8.iut.aulhassan.maphopital.modele;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.*;

public class Vague {

    private EnvironnementJeu environnement;

    private int numeroVague = 0;
    private int ennemisASpawner = 0;
    private int compteurSpawn = 0;
    private int delaiSpawn = 90;

    public Vague(EnvironnementJeu environnement) {
        this.environnement = environnement;
    }

    public void lancerVague() {
        if (ennemisASpawner > 0) return;

        numeroVague++;

        switch (numeroVague) {
            case 1: ennemisASpawner = 5;  break; // 5 gastrique
            case 2: ennemisASpawner = 5;  break; // 3 gastrique + 2 enrhumé
            case 3: ennemisASpawner = 6;  break; // 3 enrhumé + 2 covidé + 1 grippé
            case 4: ennemisASpawner = 6;  break; // 1 enrhumé + 2 covidé + 2 grippé + 1 ebola
            case 5: ennemisASpawner = 8;  break; // 2 ebola + 1 rabique + 1 grippé + 4 gastrique
            case 6: ennemisASpawner = 1;  break; // SUJET ALPHA
            default:
                System.out.println("Plus de vagues !");
                numeroVague--;
        }

        compteurSpawn = 0;
        System.out.println("Vague " + numeroVague + " lancée !");
    }


    public Ennemi tickSpawn() {
        if (ennemisASpawner <= 0) return null;

        compteurSpawn++;
        if (compteurSpawn < delaiSpawn) return null;

        compteurSpawn = 0;
        ennemisASpawner--;

        // On calcule la position dans la vague pour savoir quel ennemi spawner
        // ennemisSpawnés = total de la vague - ennemisASpawner - 1
        int indexDansVague = getTailleVague(numeroVague) - ennemisASpawner - 1;

        return creerEnnemi(numeroVague, indexDansVague);
    }

    private int getTailleVague(int vague) {
        switch (vague) {
            case 1: return 5;
            case 2: return 5;
            case 3: return 6;
            case 4: return 6;
            case 5: return 8;
            case 6: return 1;
            default: return 0;
        }
    }

    private Ennemi creerEnnemi(int vague, int index) {
        switch (vague) {
            case 1:
                // 5 gastrique
                return new Gastrique();

            case 2:
                // index 0,1,2 gastrique | index 3,4 enrhumé
                if (index < 3) return new Gastrique();
                else           return new Enrhumé();

            case 3:
                // index 0,1,2 enrhumé | index 3,4 covidé | index 5 grippé
                if (index < 3)      return new Enrhumé();
                else if (index < 5) return new Covidé();
                else                return new Grippé();

            case 4:
                // index 0 enrhumé | index 1,2 covidé | index 3,4 grippé | index 5 ebola
                if (index == 0)     return new Enrhumé();
                else if (index < 3) return new Covidé();
                else if (index < 5) return new Grippé();
                else                return new Ebola();

            case 5:
                // index 0,1 ebola | index 2 rabique | index 3 grippé | index 4,5,6,7 gastrique
                if (index < 2)      return new Ebola();
                else if (index == 2) return new Rabique();
                else if (index == 3) return new Grippé();
                else                return new Gastrique();

            case 6:
                // SUJET ALPHA
                return new SujetAlpha();

            default:
                return new Gastrique();
        }
    }

    public boolean vagueEnCours() {
        return ennemisASpawner > 0;
    }

    public int getNumeroVague() {
        return numeroVague;
    }
}