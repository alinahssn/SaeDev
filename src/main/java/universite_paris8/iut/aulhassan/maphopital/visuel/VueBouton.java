package universite_paris8.iut.aulhassan.maphopital.visuel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import universite_paris8.iut.aulhassan.maphopital.modele.EnvironnementJeu;
import universite_paris8.iut.aulhassan.maphopital.modele.Vague;

public class VueBouton {

    public VueBouton(EnvironnementJeu environnement, Vague vague, Label labelPV, Label labelBudget, Label labelVague, Button btnInterne, Button btnGel, Button btnBranca, Button btnAne, Button btnMasque, Button btnChir, Button btnRevente,Button btnBonus) {

        // Budget
        labelBudget.setText(environnement.getBudget() + " €");
        environnement.budgetProperty().addListener((obs, ancien, nouveau) -> {
            labelBudget.setText(nouveau + " €");
        });

        // PV patient
        int pvMax = environnement.getPatient().getPvMax();
        labelPV.setText(environnement.getPatient().getPv() + " / " + pvMax);
        environnement.getPatient().pvProperty().addListener((obs, ancien, nouveau) -> {
            if (nouveau.intValue() <= 0) {
                labelPV.setText("MORT");
            } else {
                labelPV.setText(nouveau + " / " + pvMax);
            }
        });


        // Vague
        labelVague.setText("01 / 07");
        vague.numeroVagueProperty().addListener((obs, ancien, nouveau) -> {
            labelVague.setText(String.format("%02d", nouveau.intValue()) + " / 07");
        });

        // Tooltips
        creerTooltip(btnInterne, "Prix : 50€\nDégâts : 20");
        creerTooltip(btnGel,     "Prix : 75€\nDégâts : 5");
        creerTooltip(btnBranca,  "Prix : 100€\nDégâts : 20");
        creerTooltip(btnAne,     "Prix : 75€\nRalentit les ennemis");
        creerTooltip(btnMasque,  "Prix : 50€\nPV : 100 — Bloque les ennemis");
        creerTooltip(btnChir,    "Prix : 150€\nDégâts : 50");
        creerTooltip(btnRevente, "Revendre : récupère 70% du prix");
    }

    private void creerTooltip(Button b, String texte) {
        Tooltip t = new Tooltip(texte);
        t.setShowDelay(Duration.ZERO);
        t.setHideDelay(Duration.ZERO);
        b.setTooltip(t);
    }
}