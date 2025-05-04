package simulateur;

import com.kerware.simulateur.AdaptateurSimulateur;
import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.AdaptateurCalculateurImpotReusine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsSimulateur {

    private static ICalculateurImpot simulateurReusine;
    private static ICalculateurImpot simulateur;

    @BeforeAll
    public static void setUp() {
        simulateurReusine = new AdaptateurCalculateurImpotReusine();
        simulateur = new AdaptateurSimulateur();
    }

    public static void preparer(int revenusNetDeclarant1, int revenusNetDeclarant2,
                                SituationFamiliale situationFamiliale, int nbEnfantsACharge,
                                int nbEnfantsSituationHandicap, boolean parentIsole) {
        simulateurReusine.setRevenusNetDeclarant1(revenusNetDeclarant1);
        simulateurReusine.setRevenusNetDeclarant2(revenusNetDeclarant2);
        simulateurReusine.setSituationFamiliale(situationFamiliale);
        simulateurReusine.setNbEnfantsACharge(nbEnfantsACharge);
        simulateurReusine.setNbEnfantsSituationHandicap(nbEnfantsSituationHandicap);
        simulateurReusine.setParentIsole(parentIsole);
        simulateurReusine.calculImpotSurRevenuNet();
    }

    public static Stream<Arguments> donneesPartsFoyerFiscal() {
        return Stream.of(
                Arguments.of(24000, "CELIBATAIRE", 0, 0, false, 1),
                Arguments.of(24000, "CELIBATAIRE", 1, 0, false, 1.5),
                Arguments.of(24000, "CELIBATAIRE", 2, 0, false, 2),
                Arguments.of(24000, "CELIBATAIRE", 3, 0, false, 3),
                Arguments.of(24000, "MARIE", 0, 0, false, 2),
                Arguments.of(24000, "PACSE", 0, 0, false, 2),
                Arguments.of(24000, "MARIE", 3, 1, false, 4.5),
                Arguments.of(24000, "DIVORCE", 2, 0, true, 2.5),
                Arguments.of(24000, "VEUF", 3, 0, true, 4.5)
        );

    }

    @Test
    public void testCalculImpot_Normal() {

        preparer(35000, 15000,
                SituationFamiliale.MARIE, 1, 0, false);
        int impot = simulateurReusine.getImpotSurRevenuNet();

        assertTrue(impot > 0);
        assertEquals(35000, simulateurReusine.getRevenuNetDeclatant1());
        assertEquals(15000, simulateurReusine.getRevenuNetDeclatant2());
        assertEquals(0, simulateurReusine.getContribExceptionnelle());
        assertEquals(45000, simulateurReusine.getRevenuFiscalReference());
        assertEquals(5000, simulateurReusine.getAbattement());
        assertEquals(609, simulateurReusine.getDecote());
        assertEquals(1845, simulateurReusine.getImpotAvantDecote());
        assertEquals(2.5, simulateurReusine.getNbPartsFoyerFiscal(), 0.01);

        preparer(15000, 0,
                SituationFamiliale.CELIBATAIRE, 1, 0, true);
        impot = simulateurReusine.getImpotSurRevenuNet();

        preparer(15000, 0,
                SituationFamiliale.VEUF, 1, 0, true);
        impot = simulateurReusine.getImpotSurRevenuNet();
    }

    @Test
    public void testCalculImpot_EnfantsMax() {

        preparer(60000, 30000, SituationFamiliale.MARIE, 7, 2, false);

        int impot = simulateurReusine.getImpotSurRevenuNet();

        assertTrue(impot >= 0);
        assertEquals(9, simulateurReusine.getNbPartsFoyerFiscal(), 0.1);
    }

    @Test
    public void testCalculImpot_Exception_EnfantsDepasse() {
        assertThrows(IllegalArgumentException.class, () ->
                preparer(50000, 20000, SituationFamiliale.MARIE, 8, 0, false));
    }

    @Test
    public void testCalculImpot_Exception_EnfantsHandicapesSuperieur() {
        assertThrows(IllegalArgumentException.class, () ->
                preparer(50000, 0, SituationFamiliale.CELIBATAIRE, 2, 3, false));
    }

    @Test
    public void testCalculImpot_Exception_RevenuNegatif() {
        assertThrows(IllegalArgumentException.class, () ->
                preparer(-1000, 0, SituationFamiliale.VEUF, 1, 0, false));
    }

    @Test
    public void testCalculImpot_Exception_ParentIsoleMarie() {
        assertThrows(IllegalArgumentException.class, () ->
                preparer(20000, 20000, SituationFamiliale.MARIE, 1, 0, true));
    }

    @Test
    public void testCalculImpot_Exception_DeclarantSeulAvecRevenu2() {
        assertThrows(IllegalArgumentException.class, () ->
                preparer(25000, 15000, SituationFamiliale.CELIBATAIRE, 0, 0, false));
    }

    // COUVERTURE EXIGENCE : EXG_IMPOT_03
    @DisplayName("Tests du calcul des parts pour différents foyers fiscaux")
    @ParameterizedTest
    @MethodSource( "donneesPartsFoyerFiscal" )
    public void testNombreDeParts( int revenuNetDeclarant1, String situationFamiliale, int nbEnfantsACharge,
                                   int nbEnfantsSituationHandicap, boolean parentIsole, double nbPartsAttendu) {

        // Arrange
        simulateur.setRevenusNetDeclarant1( revenuNetDeclarant1 );
        simulateur.setRevenusNetDeclarant2( 0);
        simulateur.setSituationFamiliale( SituationFamiliale.valueOf(situationFamiliale) );
        simulateur.setNbEnfantsACharge( nbEnfantsACharge );
        simulateur.setNbEnfantsSituationHandicap( nbEnfantsSituationHandicap );
        simulateur.setParentIsole( parentIsole );

        // Act
        simulateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(   nbPartsAttendu, simulateur.getNbPartsFoyerFiscal());

    }


    public static Stream<Arguments> donneesAbattementFoyerFiscal() {
        return Stream.of(
                Arguments.of(4900, "CELIBATAIRE", 0, 0, false, 495), // < 495 => 495
                Arguments.of(12000, "CELIBATAIRE", 0, 0, false, 1200), // 10 %
                Arguments.of(200000, "CELIBATAIRE", 0, 0, false, 14171) // > 14171 => 14171
        );

    }

    // COUVERTURE EXIGENCE : EXG_IMPOT_03
    @DisplayName("Tests des abattements pour les foyers fiscaux")
    @ParameterizedTest
    @MethodSource( "donneesAbattementFoyerFiscal" )
    public void testAbattement( int revenuNetDeclarant1, String situationFamiliale, int nbEnfantsACharge,
                                int nbEnfantsSituationHandicap, boolean parentIsole, int abattementAttendu) {

        // Arrange
        simulateur.setRevenusNetDeclarant1( revenuNetDeclarant1 );
        simulateur.setRevenusNetDeclarant2( 0);
        simulateur.setSituationFamiliale( SituationFamiliale.valueOf(situationFamiliale) );
        simulateur.setNbEnfantsACharge( nbEnfantsACharge );
        simulateur.setNbEnfantsSituationHandicap( nbEnfantsSituationHandicap );
        simulateur.setParentIsole( parentIsole );

        // Act
        simulateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(   abattementAttendu, simulateur.getAbattement());
    }


    public static Stream<Arguments> donneesRevenusFoyerFiscal() {
        return Stream.of(
                Arguments.of(12000, "CELIBATAIRE", 0, 0, false, 0), // 0%
                Arguments.of(20000, "CELIBATAIRE", 0, 0, false, 199), // 11%
                Arguments.of(35000, "CELIBATAIRE", 0, 0, false, 2736 ), // 30%
                Arguments.of(95000, "CELIBATAIRE", 0, 0, false, 19284), // 41%
                Arguments.of(200000, "CELIBATAIRE", 0, 0, false, 60768) // 45%
        );

    }

    // COUVERTURE EXIGENCE : EXG_IMPOT_04
    @DisplayName("Tests des différents taux marginaux d'imposition")
    @ParameterizedTest
    @MethodSource( "donneesRevenusFoyerFiscal" )
    public void testTrancheImposition( int revenuNet, String situationFamiliale, int nbEnfantsACharge,
                                       int nbEnfantsSituationHandicap, boolean parentIsole, int impotAttendu) {

        // Arrange
        simulateur.setRevenusNetDeclarant1( revenuNet );
        simulateur.setRevenusNetDeclarant2( 0);
        simulateur.setSituationFamiliale( SituationFamiliale.valueOf(situationFamiliale) );
        simulateur.setNbEnfantsACharge( nbEnfantsACharge );
        simulateur.setNbEnfantsSituationHandicap( nbEnfantsSituationHandicap );
        simulateur.setParentIsole( parentIsole );

        // Act
        simulateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(   impotAttendu, simulateur.getImpotSurRevenuNet());
    }



    public static Stream<Arguments> donneesRobustesse() {
        return Stream.of(
                Arguments.of(-1, 0,"CELIBATAIRE", 0, 0, false), // 0%
                Arguments.of(20000,0, null , 0, 0, false), // 11%
                Arguments.of(35000,0, "CELIBATAIRE", -1, 0, false ), // 30%
                Arguments.of(95000,0, "CELIBATAIRE", 0, -1, false), // 41%
                Arguments.of(200000,0, "CELIBATAIRE", 3, 4, false, 60768),
                Arguments.of(200000,0, "MARIE", 3, 2, true),
                Arguments.of(200000,0, "PACSE", 3, 2, true),
                Arguments.of(200000,0, "MARIE", 8, 0, false),
                Arguments.of(200000,10000, "CELIBATAIRE", 8, 0, false),
                Arguments.of(200000,10000, "VEUF", 8, 0, false),
                Arguments.of(200000,10000, "DIVORCE", 8, 0, false)
        );
    }

    // COUVERTURE EXIGENCE : Robustesse
    @DisplayName("Tests de robustesse avec des valeurs interdites")

    @ParameterizedTest( name ="Test avec revenuNetDeclarant1={0}, revenuDeclarant2={1}, situationFamiliale={2}, nbEnfantsACharge={3}, nbEnfantsSituationHandicap={4}, parentIsole={5}")
    @MethodSource( "donneesRobustesse" )
    public void testRobustesse( int revenuNetDeclarant1, int revenuNetDeclarant2 , String situationFamiliale, int nbEnfantsACharge,
                                int nbEnfantsSituationHandicap, boolean parentIsole) {

        // Arrange
        simulateur.setRevenusNetDeclarant1( revenuNetDeclarant1 );
        simulateur.setRevenusNetDeclarant2( revenuNetDeclarant2 );
        if ( situationFamiliale == null )
            simulateur.setSituationFamiliale( null  );
        else
            simulateur.setSituationFamiliale( SituationFamiliale.valueOf( situationFamiliale ));
        simulateur.setNbEnfantsACharge( nbEnfantsACharge );
        simulateur.setNbEnfantsSituationHandicap( nbEnfantsSituationHandicap );
        simulateur.setParentIsole( parentIsole );

        // Act & Assert
        assertThrows( IllegalArgumentException.class,  () -> { simulateur.calculImpotSurRevenuNet();} );


    }

    // AVEC D'AUTRES IDEES DE TESTS
    // AVEC @ParameterizedTest et @CsvFileSource
    @DisplayName("Tests supplémentaires de cas variés de foyers fiscaux - ")
    @ParameterizedTest( name = " avec revenuNetDeclarant1={0}, revenuNetDeclarant2={1}, situationFamiliale={2}, nbEnfantsACharge={3}, nbEnfantsSituationHandicap={4}, parentIsole={5} - IMPOT NET ATTENDU = {6}")
    @CsvFileSource( resources={"/datasImposition.csv"} , numLinesToSkip = 1 )
    public void testCasImposition( int revenuNetDeclarant1, int revenuNetDeclarant2,  String situationFamiliale, int nbEnfantsACharge,
                                   int nbEnfantsSituationHandicap, boolean parentIsole, int impotAttendu) {

        // Arrange
        simulateur.setRevenusNetDeclarant1( revenuNetDeclarant1 );
        simulateur.setRevenusNetDeclarant2( revenuNetDeclarant2 );
        simulateur.setSituationFamiliale( SituationFamiliale.valueOf( situationFamiliale) );
        simulateur.setNbEnfantsACharge( nbEnfantsACharge );
        simulateur.setNbEnfantsSituationHandicap( nbEnfantsSituationHandicap );
        simulateur.setParentIsole( parentIsole );

        // Act
        simulateur.calculImpotSurRevenuNet();

        // Assert
        assertEquals(   Integer.valueOf(impotAttendu), simulateur.getImpotSurRevenuNet());
    }

}