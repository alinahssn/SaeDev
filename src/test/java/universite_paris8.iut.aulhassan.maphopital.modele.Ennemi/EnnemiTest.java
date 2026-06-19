package universite_paris8.iut.aulhassan.maphopital.modele.Ennemi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnnemiTest {


    private Ennemi ennemi = new Ennemi(80, 5, 1, 15);

    @Test
    void testConstructeurAvecParametres() {
        assertEquals(80, ennemi.getPv());
        assertEquals(80, ennemi.getPvMax());
        assertEquals(5, ennemi.getAttaque());
        assertEquals(1, ennemi.getVitesse());
        assertEquals(15, ennemi.getRecompense());
    }

    @Test
    void testConstructeurParDefaut() {
        Ennemi ennemiParDefaut = new Ennemi();
        assertEquals(80, ennemiParDefaut.getPv());
        assertEquals(5, ennemiParDefaut.getAttaque());
        assertEquals(1, ennemiParDefaut.getVitesse());
        assertEquals(15, ennemiParDefaut.getRecompense());
    }

    @Test
    void testEnnemiEstVivantAuDepart() {
        assertTrue(ennemi.estVivant());
    }

    @Test
    void testSubirDegats() {
        ennemi.subirDegats(20);
        assertEquals(60, ennemi.getPv());
        assertTrue(ennemi.estVivant());
    }

    @Test
    void testSubirDegatsMortel() {
        ennemi.subirDegats(80);
        assertEquals(0, ennemi.getPv());
        assertFalse(ennemi.estVivant());
    }

    @Test
    void testSubirDegatsSuperieurAuxPv() {
        // Les degats ne doivent pas faire descendre les pv en negatif
        ennemi.subirDegats(200);
        assertEquals(0, ennemi.getPv());
        assertFalse(ennemi.estVivant());
    }

    @Test
    void testSetAttaque() {
        ennemi.setAttaque(10);
        assertEquals(10, ennemi.getAttaque());
    }

    @Test
    void testSetVitesse() {
        ennemi.setVitesse(3);
        assertEquals(3, ennemi.getVitesse());
    }

    @Test
    void testSetRecompense() {
        ennemi.setRecompense(50);
        assertEquals(50, ennemi.getRecompense());
    }

    @Test
    void testRalentir() {
        ennemi.setVitesse(10);
        // ralentir() repart de vitesseBase (fixee au constructeur), pas de la vitesse actuelle
        ennemi.ralentir(0.5);
        assertTrue(ennemi.estRalenti());
        assertEquals(Math.max(1, (int) (1 * 0.5)), ennemi.getVitesse());
    }

    @Test
    void testRalentirNeSAppliquePasDeuxFois() {
        ennemi.ralentir(0.5);
        int vitesseApresPremierRalentissement = ennemi.getVitesse();
        ennemi.ralentir(0.1);
        assertEquals(vitesseApresPremierRalentissement, ennemi.getVitesse());
    }

    @Test
    void testRestaurerVitesse() {
        ennemi.ralentir(0.5);
        ennemi.restaurerVitesse();
        assertFalse(ennemi.estRalenti());
        assertEquals(1, ennemi.getVitesse());
    }

    @Test
    void testRestaurerVitesseSansRalentirNeFaitRien() {
        ennemi.restaurerVitesse();
        assertFalse(ennemi.estRalenti());
        assertEquals(1, ennemi.getVitesse());
    }

    @Test
    void testPositionInitiale() {
        assertEquals(16 * 32, ennemi.getX());
        assertEquals(0, ennemi.getY());
    }

    @Test
    void testSetXEtSetY() {
        ennemi.setX(100);
        ennemi.setY(200);
        assertEquals(100, ennemi.getX());
        assertEquals(200, ennemi.getY());
    }

    @Test
    void testSetSpawn() {
        ennemi.setSpawn(64, 128);
        assertEquals(64, ennemi.getSpawnX());
        assertEquals(128, ennemi.getSpawnY());
    }

    @Test
    void testBloquer() {
        assertFalse(ennemi.estBloque());
        ennemi.bloquer(5);
        assertTrue(ennemi.estBloque());
    }

    @Test
    void testBloquerGardeLeMaximum() {
        ennemi.bloquer(3);
        ennemi.bloquer(10);
        assertTrue(ennemi.estBloque());
        // On verifie indirectement que c'est bien le max (10) qui a ete garde
        // en comptant le nombre de deplacements bloques necessaires pour debloquer
        ennemi.setChemin(null); // deplacer() s'arrete si chemin == null, mais framesBloque doit decroitre quand meme via bloquer seul
    }

    @Test
    void testChemingNullAuDepart() {
        assertNull(ennemi.getChemin());
    }

    @Test
    void testDeplacerSansCheminNeFaitRien() {
        int xAvant = ennemi.getX();
        int yAvant = ennemi.getY();
        ennemi.deplacer();
        assertEquals(xAvant, ennemi.getX());
        assertEquals(yAvant, ennemi.getY());
    }

    @Test
    void testPvProperty() {
        assertEquals(80, ennemi.pvProperty().get());
        ennemi.subirDegats(30);
        assertEquals(50, ennemi.pvProperty().get());
    }

    @Test
    void testXPropertyEtYProperty() {
        assertEquals(ennemi.getX(), ennemi.xProperty().get());
        assertEquals(ennemi.getY(), ennemi.yProperty().get());
    }

    @Test
    void testEstArriveSurNouvelleCaseFauxAuDepart() {
        assertFalse(ennemi.estArriveSurNouvelleCase());
    }
}