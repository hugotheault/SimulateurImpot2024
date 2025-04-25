package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

public class SimulateurReusine {

    private double contribExceptionnelle = 0;
    private double revenuRef = 0;
    private double abattement = 0;
    private double nbParts = 0;
    private double impotFoyer = 0;
    private double decote = 0;
    private int impotNet = 0;

    public int calculerImpot(int revenu1, int revenu2, SituationFamiliale situation, int nbEnfants, int nbEnfantsHandicapes, boolean parentIsol) {
        verifierDonnees(revenu1, revenu2, situation, nbEnfants, nbEnfantsHandicapes, parentIsol);

        boolean couple = situation == SituationFamiliale.MARIE || situation == SituationFamiliale.PACSE;
        this.abattement = Abattement.calculer(revenu1, revenu2, couple);
        this.revenuRef = Math.max(0, revenu1 + revenu2 - abattement);

        double nbPartsDecl = (couple ? 2 : 1);
        this.nbParts = PartsFiscales.calculer(situation, nbEnfants, nbEnfantsHandicapes, parentIsol);

        double impotIndiv = ImpotBrut.calculer(revenuRef / nbPartsDecl, ConstantesImpot.TRANCHES_REVENUS, ConstantesImpot.TAUX_IMPOSITION) * nbPartsDecl;
        this.impotFoyer = ImpotBrut.calculer(revenuRef / nbParts, ConstantesImpot.TRANCHES_REVENUS, ConstantesImpot.TAUX_IMPOSITION) * nbParts;

        double impotPlafonne = Plafonnement.appliquer(impotIndiv, impotFoyer, nbParts, nbPartsDecl);
        this.decote = Decote.calculer(impotPlafonne, nbPartsDecl);

        this.contribExceptionnelle = ContributionExceptionnelle.calculer(revenuRef, nbPartsDecl);
        this.impotNet = (int) Math.round(impotPlafonne - decote + contribExceptionnelle);
        return impotNet;
    }

    private void verifierDonnees(int revenu1, int revenu2, SituationFamiliale situation, int nbEnfants, int nbEnfantsHandicapes, boolean parentIsol) {
        if (revenu1 < 0 || revenu2 < 0) throw new IllegalArgumentException("Revenu ne peut pas être négatif");
        if (nbEnfants < 0 || nbEnfantsHandicapes < 0) throw new IllegalArgumentException("Nombre d'enfants invalide");
        if (situation == null) throw new IllegalArgumentException("Situation familiale requise");
        if (nbEnfantsHandicapes > nbEnfants) throw new IllegalArgumentException("Enfants handicapés > enfants totaux");
        if (nbEnfants > 7) throw new IllegalArgumentException("Nombre d'enfants > 7 non supporté");
        if (parentIsol && (situation == SituationFamiliale.MARIE || situation == SituationFamiliale.PACSE)) throw new IllegalArgumentException("Parent isolé ne peut pas être marié ou pacsé");
        if ((situation == SituationFamiliale.CELIBATAIRE || situation == SituationFamiliale.VEUF || situation == SituationFamiliale.DIVORCE) && revenu2 > 0) throw new IllegalArgumentException("Déclarant seul ne peut pas avoir revenu 2");
    }

    public double getContributionExceptionnelle(){
        return this.contribExceptionnelle;
    }

    public double getRevenuFiscalReference(){
        return this.revenuRef;
    }

    public double getAbattement(){
        return this.abattement;
    }

    public double getNombreParts(){
        return this.nbParts;
    }

    public double getImpotNet(){
        return this.impotNet;
    }

    public double getDecote(){
        return this.decote;
    }

    public double getImpotAvantDecote(){
        return this.impotFoyer;
    }
}