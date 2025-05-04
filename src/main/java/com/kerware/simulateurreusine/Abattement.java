package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

public class Abattement {
    public static double calculer(int revenu1, int revenu2, boolean couple) {
        int abt1 = calculAbattementParRevenu(revenu1);
        int abt2 = couple ? calculAbattementParRevenu(revenu2) : 0;
        return abt1 + abt2;
    }

    private static int calculAbattementParRevenu(int revenu) {
        int abt = (int) Math.round(revenu * ConstantesImpot.ABATTEMENT_TAUX);
        abt = Math.max(abt, ConstantesImpot.ABATTEMENT_MIN);
        abt = Math.min(abt, ConstantesImpot.ABATTEMENT_MAX);
        return abt;
    }
}