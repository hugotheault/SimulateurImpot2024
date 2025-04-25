package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

public class Decote {
    public static double calculer(double impot, double nbPartsDecl) {
        double decote = 0;
        if (nbPartsDecl == 1 && impot < ConstantesImpot.SEUIL_DECOTE_SEUL) {
            decote = ConstantesImpot.DECOTE_MAX_SEUL - (impot * ConstantesImpot.TAUX_DECOTE);
        } else if (nbPartsDecl == 2 && impot < ConstantesImpot.SEUIL_DECOTE_COUPLE) {
            decote = ConstantesImpot.DECOTE_MAX_COUPLE - (impot * ConstantesImpot.TAUX_DECOTE);
        }

        decote = Math.round(decote);
        return Math.min(decote, impot);
    }
}