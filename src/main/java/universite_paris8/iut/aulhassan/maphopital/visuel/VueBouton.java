package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;

public class VueBouton {

    public VueBouton(EnvironnementJeu environnement, Label labelPV, Label labelBudget) {
        labelBudget.setText("Budget : " + environnement.getBudget());

        environnement.getPatient().pvProperty().addListener((obs, ancienPv, nouveauPv) -> {
            if (nouveauPv.intValue() <= 0) {
                labelPV.setText("0 / MORT");
            } else {
                labelPV.setText(nouveauPv + " / " + environnement.getPatient().getPvMax());
            }
        });

        environnement.budgetProperty().addListener((obs, ancienBudget, nouveauBudget) -> {
            labelBudget.setText("Budget : " + nouveauBudget + "€");
            if (nouveauBudget.intValue() < 100) {
                labelBudget.setTextFill(Color.RED);
            } else {
                labelBudget.setTextFill(Color.WHITE);
            }
        });
    }
}