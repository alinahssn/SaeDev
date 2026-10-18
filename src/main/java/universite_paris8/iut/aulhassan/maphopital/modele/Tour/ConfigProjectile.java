package universite_paris8.iut.aulhassan.maphopital.modele.Tour;

public class ConfigProjectile {
    private String nomImage;
    private int taille;
    private boolean fixe;

    public ConfigProjectile(String nomImage, int taille, boolean fixe) {
        this.nomImage = nomImage;
        this.taille = taille;
        this.fixe = fixe;
    }

    public String getNomImage() { return nomImage; }
    public int getTaille()      { return taille; }
    public boolean isFixe()     { return fixe; }
}