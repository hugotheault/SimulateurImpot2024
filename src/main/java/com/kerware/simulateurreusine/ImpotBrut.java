package com.kerware.simulateurreusine;

public class ImpotBrut {
    public static double calculer(double revenuParPart, int[] tranches, double[] taux) {
        double impot = 0;
        for (int i = 0; i < taux.length; i++) {
            if (revenuParPart >= tranches[i] && revenuParPart < tranches[i + 1]) {
                impot += (revenuParPart - tranches[i]) * taux[i];
                break;
            } else {
                impot += (tranches[i + 1] - tranches[i]) * taux[i];
            }
        }
        return Math.round(impot);
    }
}