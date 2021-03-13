package appli;

import jeu.Joueur;
import jeu.Plateau;
import java.util.Scanner;

public class Application {

    /**
     * Permet a un joueur de jouer en prenant le coup qu'il va jouer, et de joueur sa carte;
     * @param plateau le plateau de jeu
     * @param joueurCourant le joueur qui va jouer
     * @param autreJoueur le joueur ennemi
     */
    public static void jouer(Plateau plateau, Joueur joueurCourant, Joueur autreJoueur){

        if (plateau.gagnerPartie(autreJoueur,joueurCourant)) {
            System.out.println(plateau.dernierTour(autreJoueur, joueurCourant));
            return;
        }

        System.out.print(plateau.afficherTour(joueurCourant,autreJoueur));

        Scanner scanner = new Scanner(System.in);
        String coup = scanner.nextLine().trim();
        plateau.joueurJoueDesCartes(joueurCourant, autreJoueur,coup);

        while (joueurCourant.getFauteJoueur()) {
            System.out.print("#> ");
            coup = scanner.nextLine().trim();
            plateau.joueurJoueDesCartes(joueurCourant, autreJoueur,coup);
        }

        plateau.piocheSesCartes(joueurCourant);
        System.out.println(plateau.getCompteurDeCartesPosees() + " cartes posées, " + plateau.getCompteurDeCartesPiochees() + " cartes piochées");

        if (plateau.gagnerPartie(joueurCourant,autreJoueur)) {
            System.out.println(plateau.dernierTour(joueurCourant, autreJoueur));
        }
    }

    public static void main(String[] args) {
        Joueur j1 = new Joueur("NORD");
        Joueur j2 = new Joueur("SUD");

        Plateau plateauDeJeu = new Plateau();
        while (j1.peutJouer((j2)) || j2.peutJouer(j1)){

            jouer(plateauDeJeu, j1,j2);

            if ((plateauDeJeu.gagnerPartie(j1, j2)))
                break;

            jouer(plateauDeJeu,j2,j1);

            if ((plateauDeJeu.gagnerPartie(j2, j1)))
                break;
        }
    }
}
