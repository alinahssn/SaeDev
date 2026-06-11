module universite_paris8.iut.aulhassan.maphopital {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens universite_paris8.iut.aulhassan.maphopital to javafx.fxml;
    exports universite_paris8.iut.aulhassan.maphopital;
    exports universite_paris8.iut.aulhassan.maphopital.modele;
    opens universite_paris8.iut.aulhassan.maphopital.modele to javafx.fxml;
    exports universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;
    opens universite_paris8.iut.aulhassan.maphopital.modele.Ennemi to javafx.fxml;
    exports universite_paris8.iut.aulhassan.maphopital.modele.Tour;
    opens universite_paris8.iut.aulhassan.maphopital.modele.Tour to javafx.fxml;
}