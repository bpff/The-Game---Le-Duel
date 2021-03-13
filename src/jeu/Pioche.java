package jeu;


import java.util.Collections;
import java.util.LinkedList;

public class Pioche {
    public static final int NBR_DE_CARTES_DEPART_PIOCHE = 60; // on initialise le nombre de cartes d'un joueur au départ dans son jeu
    private final LinkedList<Integer> listeCarteDansPioche;// on choisit de stocker ses cartes sous formes d'entiers dans une liste,
    // plus facile à manipuler
    /**
     * Constructeur d'une pioche
     */
    public Pioche(){
        listeCarteDansPioche=creationPioche();
    }

    /**
     * Retirer une carte dans la pioche
     * @return la carte piochée
     */
    public int prendUneCarte(){
        return this.listeCarteDansPioche.pop();
    }

    /**
     * Initialise la pioche du joueur sans les cartes 1 et 60 (qui correspondent aux premières cartes de ses piles
     * Trie aléatoirement sa pioche de 58 cartes
     * @return la liste de la pioche du joueur
     */
    private LinkedList<Integer> creationPioche() {
        LinkedList<Integer> liste = new LinkedList<>();
        for (int compt = 2; compt < NBR_DE_CARTES_DEPART_PIOCHE; compt++)
            liste.push(compt);
        Collections.shuffle(liste);/*permet de trier aléatoirement la liste*/
        return liste;
    }

    /*--------------------GETTERS/SETTERS-----------------------*/

    public LinkedList<Integer> getListeCarteDansPioche() {
        return listeCarteDansPioche;
    }
}