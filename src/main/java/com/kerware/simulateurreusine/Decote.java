package com.kerware.simulateurreusine;

import com.kerware.simulateurreusine.ConstantesImpot;

/**
 * Classe utilitaire pour le calcul de la décote de l'impôt sur le revenu.
 * La décote est un mécanisme de réduction de l'impôt pour les foyers fiscaux
 * dont le montant de l’impôt brut est inférieur à un certain seuil.
 */
public class Decote {

    /**
     * Calcule la décote applicable à un foyer fiscal en fonction du montant d'impôt brut
     * et du nombre de parts fiscales "de base".
     * La décote s’applique uniquement si le montant de l’impôt est inférieur à un seuil défini,
     * différent pour une personne seule ou un couple. Le calcul est plafonné pour ne pas
     * dépasser le montant de l’impôt initial.
     *
     * @param impot Montant de l’impôt brut avant décote
     * @param nbPartsDecl Nombre de parts fiscales "de base" (1 pour une personne seule, 2 pour un couple)
     * @return Montant de la décote applicable (arrondi à l’euro et plafonné à l’impôt)
     */
    public static double calculer(double impot, double nbPartsDecl) {
        double decote = 0;

        if (nbPartsDecl == 1 && impot < ConstantesImpot.SEUIL_DECOTE_SEUL) {
            decote = ConstantesImpot.DECOTE_MAX_SEUL - (impot * ConstantesImpot.TAUX_DECOTE);
        } else if (nbPartsDecl == 2 && impot < ConstantesImpot.SEUIL_DECOTE_COUPLE) {
            decote = ConstantesImpot.DECOTE_MAX_COUPLE - (impot * ConstantesImpot.TAUX_DECOTE);
        }

        decote = Math.round(decote);

        // La décote ne peut pas être supérieure au montant de l'impôt initial
        return Math.min(decote, impot);
    }
}
