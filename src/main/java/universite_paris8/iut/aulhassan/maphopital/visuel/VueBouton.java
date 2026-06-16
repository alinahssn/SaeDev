package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;

public class VueBouton {

    public VueBouton(EnvironnementJeu environnement, Label labelPV, Label labelBudget,
                     Button btnInterne, Button btnGel, Button btnBranca,
                     Button btnAne, Button btnMasque, Button btnChir, Button btnRevente, Button btnBonus) {

        // 1. Ton code de base (on n'y touche pas)
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

        creerTooltip(btnInterne, "Prix : 50€\nDégâts : 20");
        creerTooltip(btnGel, "Prix : 75€\nDégâts : 5");
        creerTooltip(btnBranca, "Prix : 100€\nDégâts : 20");
        creerTooltip(btnAne, "Prix : 75€\nDégâts : 0");
        creerTooltip(btnMasque, "Prix : 50€\nPV : 100");
        creerTooltip(btnChir, "Prix : 150€\nDégâts : 50");
        creerTooltip(btnRevente, "Revendre : Recupère 70% du prix");
        creerTooltip(btnBonus, "Prix : 50€\nSoigne le patient de 5 PV");
    }

    private void creerTooltip(Button b, String texte) {
        Tooltip t = new Tooltip(texte);
        t.setShowDelay(Duration.ZERO);
        t.setHideDelay(Duration.ZERO);
        b.setTooltip(t);
    }
}