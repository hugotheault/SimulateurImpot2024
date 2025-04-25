package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

public class Plafonnement {
    public static double appliquer(double impotIndividuel, double impotFoyer, double nbParts, double nbPartsDecl) {
        double ecartParts = nbParts - nbPartsDecl;
        double plafond = (ecartParts / 0.5) * ConstantesImpot.PLAFOND_DEMI_PART;
        double baisse = impotIndividuel - impotFoyer;

        if (baisse > plafond) {
            return Math.round(impotIndividuel - plafond);
        }

        return Math.round(impotFoyer);
    }
}