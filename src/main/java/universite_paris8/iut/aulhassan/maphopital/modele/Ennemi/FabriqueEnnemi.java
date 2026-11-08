package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

import universite_paris8.iut.aulhassan.maphopital.modele.Ennemi.*;
public abstract class FabriqueEnnemi {

    public abstract Ennemi creerEnnemi();

    public static class FabGastrique extends FabriqueEnnemi {
        @Override public Ennemi creerEnnemi() { return new Gastrique(); }
    }
    public static class FabEnrhume extends FabriqueEnnemi {
        @Override public Ennemi creerEnnemi() { return new Enrhumé(); }
    }
    public static class FabCovide extends FabriqueEnnemi {
        @Override public Ennemi creerEnnemi() { return new Covidé(); }
    }
    public static class FabGrippe extends FabriqueEnnemi {
        @Override public Ennemi creerEnnemi() { return new Grippé(); }
    }
    public static class FabEbola extends FabriqueEnnemi {
        @Override public Ennemi creerEnnemi() { return new Ebola(); }
    }
    public static class FabRabique extends FabriqueEnnemi {
        @Override public Ennemi creerEnnemi() { return new Rabique(); }
    }
    public static class FabSujetAlpha extends FabriqueEnnemi {
        @Override public Ennemi creerEnnemi() { return new SujetAlpha(); }
    }
}