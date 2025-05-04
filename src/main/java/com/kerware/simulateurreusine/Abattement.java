package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

/**
 * Classe utilitaire pour le calcul des abattements fiscaux appliqués aux revenus nets.
 * L’abattement est calculé séparément pour chaque revenu et plafonné selon les limites fixées.
 */
public class Abattement {

    /**
     * Calcule l’abattement total applicable à un foyer fiscal.
     * L’abattement est appliqué individuellement à chaque revenu déclaré.
     *
     * @param revenu1 Revenu net du déclarant 1
     * @param revenu2 Revenu net du déclarant 2 (ignoré si le foyer n’est pas un couple)
     * @param couple true si les deux déclarants forment un couple (marié ou pacsé), false sinon
     * @return Montant total de l’abattement applicable
     */
    public static double calculer(int revenu1, int revenu2, boolean couple) {
        int abt1 = calculAbattementParRevenu(revenu1);
        int abt2 = couple ? calculAbattementParRevenu(revenu2) : 0;
        return abt1 + abt2;
    }

    /**
     * Calcule l’abattement individuel à partir d’un revenu donné,
     * en respectant les seuils minimum et maximum définis dans ConstantesImpot.
     *
     * @param revenu Revenu net individuel
     * @return Montant de l’abattement appliqué à ce revenu
     */
    private static int calculAbattementParRevenu(int revenu) {
        int abt = (int) Math.round(revenu * ConstantesImpot.ABATTEMENT_TAUX);
        abt = Math.max(abt, ConstantesImpot.ABATTEMENT_MIN);
        abt = Math.min(abt, ConstantesImpot.ABATTEMENT_MAX);
        return abt;
    }
}
