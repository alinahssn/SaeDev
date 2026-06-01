package universite_paris8.iut.aulhassan.maphopital.modele;

import java.util.LinkedList;

public class BFS {

    private int[][] distMap;
    private Terrain terrain;

    public BFS(Terrain terrain, int cibleCol, int cibleLigne) {
        this.terrain = terrain;
        this.distMap = new int[terrain.getHauteur()][terrain.getLargeur()];

        // On remplit tout à -1 (= non visité)
        for (int[] ligne : distMap)
            java.util.Arrays.fill(ligne, -1);

        calculer(cibleCol, cibleLigne);
    }

    private void calculer(int cibleCol, int cibleLigne) {
        LinkedList<int[]> file = new LinkedList<>();

        distMap[cibleLigne][cibleCol] = 0;
        file.add(new int[]{cibleCol, cibleLigne});

        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

        while (!file.isEmpty()) {
            int[] actuel = file.poll();
            int c = actuel[0], l = actuel[1];

            for (int[] dir : directions) {
                int nc = c + dir[0];
                int nl = l + dir[1];

                // Hors grille ?
                if (nc < 0 || nc >= terrain.getLargeur()) continue;
                if (nl < 0 || nl >= terrain.getHauteur()) continue;
                // Déjà visité ?
                if (distMap[nl][nc] != -1) continue;
                // Pas une case praticable ?
                if (terrain.getMap()[nl][nc] != 0) continue;

                distMap[nl][nc] = distMap[l][c] + 1;
                file.add(new int[]{nc, nl});
            }
        }
    }

    public int[][] getDistMap() { return distMap; }
}