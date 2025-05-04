package com.kerware.simulateurreusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;

/**
 * Adaptateur entre l'interface générique ICalculateurImpot et l'implémentation spécifique SimulateurReusine.
 * Ce pattern permet d'utiliser le simulateur existant dans un contexte plus générique.
 */
public class AdaptateurCalculateurImpotReusine implements ICalculateurImpot {

    private final SimulateurReusine simulateur;
    private int revenuNet1;
    private int revenuNet2;
    private SituationFamiliale situationFamiliale;
    private int nbEnfants;
    private int nbEnfantsHandicapes;
    private boolean parentIsole;

    /**
     * Constructeur par défaut. Initialise un simulateur Reusine.
     */
    public AdaptateurCalculateurImpotReusine() {
        this.simulateur = new SimulateurReusine();
    }

    /**
     * Définit le revenu net du déclarant 1.
     * @param rn Revenu net du déclarant 1
     */
    @Override
    public void setRevenusNetDeclarant1(int rn) {
        this.revenuNet1 = rn;
    }

    /**
     * Définit le revenu net du déclarant 2.
     * @param rn Revenu net du déclarant 2
     */
    @Override
    public void setRevenusNetDeclarant2(int rn) {
        this.revenuNet2 = rn;
    }

    /**
     * Définit la situation familiale du foyer fiscal.
     * @param sf Situation familiale (marié, pacsé, célibataire, etc.)
     */
    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        this.situationFamiliale = sf;
    }

    /**
     * Définit le nombre d’enfants à charge dans le foyer.
     * @param nbe Nombre d’enfants à charge
     */
    @Override
    public void setNbEnfantsACharge(int nbe) {
        this.nbEnfants = nbe;
    }

    /**
     * Définit le nombre d’enfants en situation de handicap dans le foyer.
     * @param nbesh Nombre d’enfants handicapés à charge
     */
    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsHandicapes = nbesh;
    }

    /**
     * Indique si le foyer est composé d’un parent isolé.
     * @param pi true si parent isolé, false sinon
     */
    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    /**
     * Déclenche le calcul de l'impôt sur le revenu net en utilisant les données fournies.
     */
    @Override
    public void calculImpotSurRevenuNet() {
        simulateur.calculerImpot(
                revenuNet1,
                revenuNet2,
                situationFamiliale,
                nbEnfants,
                nbEnfantsHandicapes,
                parentIsole
        );
    }

    /**
     * Retourne le revenu net du déclarant 1.
     * @return Revenu net du déclarant 1
     */
    @Override
    public int getRevenuNetDeclatant1() {
        return revenuNet1;
    }

    /**
     * Retourne le revenu net du déclarant 2.
     * @return Revenu net du déclarant 2
     */
    @Override
    public int getRevenuNetDeclatant2() {
        return revenuNet2;
    }

    /**
     * Retourne le montant de la contribution exceptionnelle.
     * @return Contribution exceptionnelle à l'impôt
     */
    @Override
    public double getContribExceptionnelle() {
        return simulateur.getContributionExceptionnelle();
    }

    /**
     * Retourne le revenu fiscal de référence.
     * @return Revenu fiscal de référence
     */
    @Override
    public int getRevenuFiscalReference() {
        return (int) simulateur.getRevenuFiscalReference();
    }

    /**
     * Retourne le montant de l’abattement appliqué.
     * @return Montant de l’abattement
     */
    @Override
    public int getAbattement() {
        return (int) simulateur.getAbattement();
    }

    /**
     * Retourne le nombre de parts fiscales du foyer.
     * @return Nombre de parts
     */
    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateur.getNombreParts();
    }

    /**
     * Retourne le montant de l'impôt avant décote.
     * @return Impôt brut avant décote
     */
    @Override
    public int getImpotAvantDecote() {
        return (int) simulateur.getImpotAvantDecote();
    }

    /**
     * Retourne le montant de la décote appliquée.
     * @return Montant de la décote
     */
    @Override
    public int getDecote() {
        return (int) simulateur.getDecote();
    }

    /**
     * Retourne le montant final de l’impôt sur le revenu net après décote.
     * @return Impôt net à payer
     */
    @Override
    public int getImpotSurRevenuNet() {
        return (int) simulateur.getImpotNet();
    }
}