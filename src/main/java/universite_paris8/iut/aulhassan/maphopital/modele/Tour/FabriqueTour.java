package universite_paris8.iut.aulhassan.maphopital.modele.Tour;


public abstract class FabriqueTour {

    public abstract Tour creerTour();

    public static class FabInterneDeGarde extends FabriqueTour {
        @Override public Tour creerTour() { return new InterneDeGarde(); }
    }
    public static class FabInfirmier extends FabriqueTour {
        @Override public Tour creerTour() { return new Infirmier(); }
    }
    public static class FabBrancardier extends FabriqueTour {
        @Override public Tour creerTour() { return new Brancardier(); }
    }
    public static class FabAnesthesiste extends FabriqueTour {
        @Override public Tour creerTour() { return new Anesthésiste(); }
    }
    public static class FabMasquier extends FabriqueTour {
        @Override public Tour creerTour() { return new Masquier(); }
    }
    public static class FabChirurgien extends FabriqueTour {
        @Override public Tour creerTour() { return new Chirurgien(); }
    }
}