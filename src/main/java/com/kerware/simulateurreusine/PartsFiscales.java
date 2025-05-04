package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Classe utilitaire permettant de calculer le nombre de parts fiscales
 * d’un foyer en fonction de la situation familiale, du nombre d’enfants,
 * d’enfants handicapés et du statut de parent isolé.
 * Le nombre de parts est un élément clé du calcul de l’impôt sur le revenu :
 * il permet de déterminer le revenu par part pour appliquer le barème progressif.
 */
public class PartsFiscales {

    private static final double DEMIPART = 0.5;

    /**
     * Calcule le nombre de parts fiscales pour un foyer donné.
     *
     * @param situation La situation familiale du déclarant (célibataire, marié, etc.)
     * @param nbEnfants Nombre total d'enfants à charge
     * @param nbEnfantsHandicapes Nombre d’enfants handicapés à charge
     * @param parentIsol Indique si le foyer est un parent isolé
     * @return Nombre total de parts fiscales (peut être décimal)
     */
    public static double calculer(SituationFamiliale situation, int nbEnfants,
                                  int nbEnfantsHandicapes, boolean parentIsol) {

        // Base selon situation familiale
        double parts = switch (situation) {
            case CELIBATAIRE, DIVORCE, VEUF -> 1.0;
            case MARIE, PACSE -> 2.0;
        };

        // Parts supplémentaires pour enfants
        if (nbEnfants <= 2) {
            parts += nbEnfants * DEMIPART; // 0.5 part par enfant pour les deux premiers
        } else {
            parts += 1.0 + (nbEnfants - 2); // 1 part pour le 3e enfant et plus
        }

        // Bonus pour chaque enfant handicapé
        parts += nbEnfantsHandicapes * DEMIPART;

        // Demi-part supplémentaire pour parent isolé avec enfants
        if (parentIsol && nbEnfants > 0) {
            parts += DEMIPART;
        }

        // Une part supplémentaire si veuf avec enfant à charge (cas spécifique)
        if (situation == SituationFamiliale.VEUF && nbEnfants > 0) {
            parts += 1;
        }

        return parts;
    }
}
