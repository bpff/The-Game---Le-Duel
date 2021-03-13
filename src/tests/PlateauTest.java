package tests;

import static org.junit.Assert.*;
import jeu.Joueur;
import jeu.Plateau;
import org.junit.Test;

public class PlateauTest {

    /**
     * On teste ici si le joueur 1 peut jouer avec 3 cartes qu'il possède en main, c'est un coup classique
     */
    @Test
    public void methodePeutJouerCondition1() {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");
        new Plateau();

        j1.getMainDuJoueur().getListeCarte().clear();

        j1.getPileAsc().setSommetDeLaPile(56);
        j1.getPileDesc().setSommetDeLaPile(30);
        j2.getPileAsc().setSommetDeLaPile(56);
        j2.getPileDesc().setSommetDeLaPile(6);

        j1.getMainDuJoueur().recupCarte(18);
        j1.getMainDuJoueur().recupCarte(25);
        j1.getMainDuJoueur().recupCarte(28);

        j1.getMainDuJoueur().trierMain();

        assertTrue(j1.peutJouer(j2));
    }


    /**
     * On teste ici si le joueur 1 peut jouer avec 5 cartes dans sa main, mais aucune d'elle ne peut être posée sur ses piles, il ne peut
     * poser qu'une carte chez l'adversaire donc il ne peut pas jouer.
     */
    @Test
    public void methodePeutJouerCondition2() {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");
        Plateau plateau = new Plateau();

        j1.getMainDuJoueur().getListeCarte().clear();
        j2.getMainDuJoueur().getListeCarte().clear();

        j1.getPileAsc().setSommetDeLaPile(56);
        j1.getPileDesc().setSommetDeLaPile(7);
        j2.getPileAsc().setSommetDeLaPile(56);
        j2.getPileDesc().setSommetDeLaPile(6);

        j2.getMainDuJoueur().recupCarte(15);
        j1.getMainDuJoueur().recupCarte(18);
        j1.getMainDuJoueur().recupCarte(25);
        j1.getMainDuJoueur().recupCarte(28);
        j1.getMainDuJoueur().recupCarte(37);
        j1.getMainDuJoueur().recupCarte(38);
        j1.getMainDuJoueur().recupCarte(52);

        j1.getMainDuJoueur().trierMain();

        assertFalse(j1.peutJouer(j2));
//        assertTrue(plateau.gagnerPartie(j2,j1));
    }

    /**
     * On vérifie qu'il ne peut pas jouer avec 2 cartes dans sa main mais qu'une seule qu'il peut poser.
     */
    @Test
    public void methodePeutJouerCondition3() {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");
        new Plateau();

        j1.getMainDuJoueur().getListeCarte().clear();

        j1.getPileAsc().setSommetDeLaPile(57);
        j1.getPileDesc().setSommetDeLaPile(1);
        j2.getPileAsc().setSommetDeLaPile(2);
        j2.getPileDesc().setSommetDeLaPile(57);

        j1.getMainDuJoueur().recupCarte(58);


        j1.getMainDuJoueur().recupCarte(34);


        j1.getMainDuJoueur().trierMain();

        assertFalse(j1.peutJouer(j2));

    }

    /**
     * On vérifie qu'il ne peut pas jouer avec seulement une carte dans sa main.
     */
    @Test
    public void peutJouerSi1SeuleCarte() {
        Joueur j1 = new Joueur("NORD"), j2 = new Joueur("SUD");
        new Plateau();

        j1.getMainDuJoueur().getListeCarte().clear();

        j1.getPileAsc().setSommetDeLaPile(30);
        j1.getPileDesc().setSommetDeLaPile(1);
        j2.getPileAsc().setSommetDeLaPile(59);
        j2.getPileDesc().setSommetDeLaPile(57);

        j1.getMainDuJoueur().recupCarte(58);

        assertFalse(j1.peutJouer(j2));

    }

    /**
     * On vérifie qu'il ne peut pas jouer avec une main vide, qui ne contient aucune carte.
     */
    @Test
    public void peutJouerSiMainVide() {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");
        new Plateau();

        j1.getMainDuJoueur().getListeCarte().clear();

        j1.getPileAsc().setSommetDeLaPile(30);
        j1.getPileDesc().setSommetDeLaPile(1);
        j2.getPileAsc().setSommetDeLaPile(59);
        j2.getPileDesc().setSommetDeLaPile(57);

        assertFalse(j1.peutJouer(j2));
    }

    /**
     * On vérifie que le joueur ne peut pas piocher car la pioche est vide, donc il ne peut pas reprendre de cartes
     */
    @Test
    public void peutJouerSiPiocheVide() {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");
        new Plateau();

        j1.getMainDuJoueur().getListeCarte().clear();
        j1.getPioche().getListeCarteDansPioche().clear();


        j1.getMainDuJoueur().recupCarte(58);

        j1.getPileAsc().setSommetDeLaPile(30);
        j1.getPileDesc().setSommetDeLaPile(1);
        j2.getPileAsc().setSommetDeLaPile(59);
        j2.getPileDesc().setSommetDeLaPile(57);

        assertFalse(j1.peutPiocher());
        assertFalse(j1.peutJouer(j2));
    }

    /**
     * On vérifie que le joueur ne peut pas jouer avec une carte seulement dans la pioche.
     */
    @Test
    public void peutJouerSiPiocheEgaleA1() {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        j1.getMainDuJoueur().getListeCarte().clear();
        j1.getPioche().getListeCarteDansPioche().clear();

        j1.getPioche().getListeCarteDansPioche().add(45);

        j1.getPileAsc().setSommetDeLaPile(1);
        j1.getPileDesc().setSommetDeLaPile(60);
        j2.getPileAsc().setSommetDeLaPile(1);
        j2.getPileDesc().setSommetDeLaPile(60);

        j1.piocheCartes(1);

        assertEquals(j1.getPioche().getListeCarteDansPioche().size(),0);

        //on decide de jouer toutes les cartes, donc le joueur est censé avoir 1 carte dans sa main

        assertEquals(j1.getMainDuJoueur().nbrCartes(),1);

        assertFalse(j1.peutJouer(j2));
    }

    /**
     * Permet de vérifier si on peut jouer 2 fois de suite la règle des 10
     */
    @Test
    public void peutJouerConditionDes10() {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        j1.getMainDuJoueur().getListeCarte().clear();
        j1.getPioche().getListeCarteDansPioche().clear();

        j1.getMainDuJoueur().getListeCarte().add(30);
        j1.getMainDuJoueur().getListeCarte().add(20);
        j1.getMainDuJoueur().getListeCarte().add(10);

        j1.getPileAsc().setSommetDeLaPile(40);
        j1.getPileDesc().setSommetDeLaPile(5);
        j2.getPileAsc().setSommetDeLaPile(1);
        j2.getPileDesc().setSommetDeLaPile(60);

        assertTrue(j1.peutJouer(j2));
    }

}
