package com.kerware.simulateurreusine;

/**
 * Classe utilitaire permettant de calculer l'impôt brut sur le revenu
 * en fonction du revenu imposable par part, selon les tranches d'imposition.
 */
public class ImpotBrut {

    /**
     * Calcule l'impôt brut en appliquant un barème progressif sur le revenu par part.
     * Le calcul utilise les tranches et les taux correspondants, de manière cumulative,
     * jusqu'à atteindre la tranche dans laquelle se situe le revenu.
     *
     * @param revenuParPart Revenu imposable par part fiscale
     * @param tranches Tableau des seuils des tranches d'imposition (doit être de taille n+1)
     * @param taux Tableau des taux d'imposition correspondants à chaque tranche (de taille n)
     * @return Montant total de l’impôt brut arrondi à l’euro
     */
    public static double calculer(double revenuParPart, int[] tranches, double[] taux) {
        double impot = 0;

        for (int i = 0; i < taux.length; i++) {
            if (revenuParPart >= tranches[i] && revenuParPart < tranches[i + 1]) {
                // Revenu partiellement dans cette tranche
                impot += (revenuParPart - tranches[i]) * taux[i];
                break;
            } else {
                // Tranche entièrement remplie
                impot += (tranches[i + 1] - tranches[i]) * taux[i];
            }
        }

        return Math.round(impot);
    }
}
