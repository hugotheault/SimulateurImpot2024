package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

/**
 * Classe utilitaire pour appliquer le plafonnement du quotient familial.
 * <p>
 * Le plafonnement limite le bénéfice en impôt procuré par les parts fiscales
 * supplémentaires (au-delà des parts de base). Ce mécanisme vise à éviter
 * des réductions d’impôt excessives dues à un nombre élevé de parts.
 * </p>
 */
public class Plafonnement {

    private static final double DEMIPART = 0.5;

    /**
     * Applique le plafonnement du quotient familial.
     *
     * @param impotIndividuel Impôt théorique sans quotient familial (base : 1 part ou 2 parts selon le couple)
     * @param impotFoyer Impôt calculé avec le quotient familial réel (tenant compte de toutes les parts)
     * @param nbParts Nombre total de parts fiscales du foyer
     * @param nbPartsDecl Nombre de parts de base (1 pour célibataire, 2 pour couple)
     * @return Impôt plafonné, si l’avantage procuré par les parts supplémentaires dépasse le plafond autorisé
     */
    public static double appliquer(double impotIndividuel, double impotFoyer,
                                   double nbParts, double nbPartsDecl) {
        double ecartParts = nbParts - nbPartsDecl;

        // Calcul du plafond maximal de réduction lié aux parts supplémentaires
        double plafond = (ecartParts / DEMIPART) * ConstantesImpot.PLAFOND_DEMI_PART;

        // Réduction obtenue grâce au quotient familial
        double baisse = impotIndividuel - impotFoyer;

        // Si la réduction dépasse le plafond, on limite l’économie d’impôt
        if (baisse > plafond) {
            return Math.round(impotIndividuel - plafond);
        }

        // Sinon, on garde le calcul normal
        return Math.round(impotFoyer);
    }
}
