package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

public class Ebola extends Ennemi {
    private static final int PV_DEPART = 150;
    private static final int RECOMPENSE = 60;

    private static final int SEUIL_2 = 100;
    private static final int SEUIL_3 = 50;

    private int phase = 1;
    public Ebola() {
        super(PV_DEPART, 5,8, RECOMPENSE);
    }
    public int getPhase() {
        return phase;
    }
    @Override
    public void subirDegats(int degat){
        super.subirDegats(degat);
        mettreAJourPhase();

    }

    public void mettreAJourPhase(){
        int pv = getPv();
        int nouvellePhase;

        if (pv >= SEUIL_2){
            nouvellePhase = 1;
        }
        else if (pv >= SEUIL_3){
            nouvellePhase = 2;
        }
        else if (pv > 0){
            nouvellePhase = 3;
        }
        else{
            nouvellePhase = 4;
        }

        if(phase != nouvellePhase){
            phase = nouvellePhase;
            switch (phase){
                case 2 -> {setAttaque(10); setVitesse(16);}
                case 3 -> {setAttaque(15); setVitesse(32);}
                case 4 -> {setAttaque(20); setVitesse(64);}
            }
        }
    }
}
