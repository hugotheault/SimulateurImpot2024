package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

public class ContributionExceptionnelle {
    public static double calculer(double revenuRef, double nbPartsDecl) {
        double[] taux = nbPartsDecl == 1 ? ConstantesImpot.TAUX_CEHR_CELIBATAIRE : ConstantesImpot.TAUX_CEHR_COUPLE;
        int[] tranches = ConstantesImpot.TRANCHES_CEHR;

        double contrib = 0;
        for (int i = 0; i < taux.length; i++) {
            if (revenuRef >= tranches[i] && revenuRef < tranches[i + 1]) {
                contrib += (revenuRef - tranches[i]) * taux[i];
                break;
            } else {
                contrib += (tranches[i + 1] - tranches[i]) * taux[i];
            }
        }
        return Math.round(contrib);
    }
}