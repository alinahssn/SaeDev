package universite_paris8.iut.aulhassan.maphopital.modele.BFS;

public class Sommet {
    private int x;
    private int y;

    public Sommet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.x;
        result = 31 * result + this.y;
        return result;
    }
//
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Sommet s = (Sommet) obj;
        return this.x == s.x && this.y == s.y;
    }

    public String toString() {
        return "Sommet [" + this.x + ", " + this.y + ", p= ]" ;}



}