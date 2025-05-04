package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

public class Plafonnement {

    private static final double DEMIPART = 0.5;

    public static double appliquer(double impotIndividuel, double impotFoyer,
                                   double nbParts, double nbPartsDecl) {
        double ecartParts = nbParts - nbPartsDecl;
        double plafond = (ecartParts / DEMIPART) * ConstantesImpot.PLAFOND_DEMI_PART;
        double baisse = impotIndividuel - impotFoyer;

        if (baisse > plafond) {
            return Math.round(impotIndividuel - plafond);
        }

        return Math.round(impotFoyer);
    }
}