package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

/**
 * Classe utilitaire permettant de calculer la Contribution Exceptionnelle sur les Hauts Revenus (CEHR),
 * en fonction du revenu fiscal de référence et du nombre de parts fiscales.
 */
public class ContributionExceptionnelle {

    /**
     * Calcule le montant de la contribution exceptionnelle sur les hauts revenus (CEHR).
     * Cette contribution est calculée selon des tranches et des taux spécifiques, qui varient
     * selon que le foyer est composé d'une seule part (célibataire) ou de plusieurs (couple).
     *
     * @param revenuRef Revenu fiscal de référence du foyer
     * @param nbPartsDecl Nombre de parts fiscales "de base" du foyer (1 pour une personne seule, 2 pour un couple)
     * @return Montant total de la contribution exceptionnelle (arrondi à l’euro)
     */
    public static double calculer(double revenuRef, double nbPartsDecl) {
        double[] taux = nbPartsDecl == 1
                ? ConstantesImpot.TAUX_CEHR_CELIBATAIRE
                : ConstantesImpot.TAUX_CEHR_COUPLE;

        int[] tranches = ConstantesImpot.TRANCHES_CEHR;

        double contrib = 0;
        for (int i = 0; i < taux.length; i++) {
            if (revenuRef >= tranches[i] && revenuRef < tranches[i + 1]) {
                // Portion du revenu dans la tranche courante
                contrib += (revenuRef - tranches[i]) * taux[i];
                break; // On arrête dès que la tranche est atteinte
            } else {
                // Revenu dépasse cette tranche entièrement
                contrib += (tranches[i + 1] - tranches[i]) * taux[i];
            }
        }

        return Math.round(contrib);
    }
}
