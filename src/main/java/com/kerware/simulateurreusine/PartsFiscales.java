package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

public class PartsFiscales {

    private static final double DEMIPART = 0.5;

    public static double calculer(SituationFamiliale situation, int nbEnfants,
                                  int nbEnfantsHandicapes, boolean parentIsol) {
        double parts = switch (situation) {
            case CELIBATAIRE, DIVORCE, VEUF -> 1.0;
            case MARIE, PACSE -> 2.0;
        };

        if (nbEnfants <= 2) {
            parts += nbEnfants * DEMIPART;
        } else {
            parts += 1.0 + (nbEnfants - 2);
        }

        parts += nbEnfantsHandicapes * DEMIPART;

        if (parentIsol && nbEnfants > 0) {
            parts += DEMIPART;
        }

        if (situation == SituationFamiliale.VEUF && nbEnfants > 0) {
            parts += 1;
        }

        return parts;
    }
}