
package jeu;

import java.util.ArrayList;
import java.util.Collections;

public class MainJ {

    private ArrayList<Integer> listeCarte;// on choisit de stocker sa main sous forme d'entiers dans une liste

    /**
     * Constructeur de la main du joueur
     * @param liste la liste de cartes du joueur
     */
    public MainJ(ArrayList<Integer> liste) {
        this.listeCarte = liste;
    }

    /**
     * Permet de trier la main
     */
    public void trierMain() {
        Collections.sort(this.listeCarte);
    }

    /**
     * Permet de recuperer une carte pour la mettre dans la main
     * @param carteRecup la carte qu'on insere
     */
    public void recupCarte(int carteRecup) {
        this.listeCarte.add(carteRecup);
    }

    /**
     * Permet de créer un clone de la main actuelle
     * @return une clone de la main actuelle
     */
    public ArrayList<Integer> creationClone() {
        return new ArrayList<>(this.listeCarte);
    }

    /**
     * Permet de poser une carte
     * @param carte la carte que l'on va poser
     * @return la carte que l'on pose
     */
    public int poseCarte(int carte) {
        this.listeCarte.remove(listeCarte.indexOf(carte));
        return carte;
    }

    /**
     * Permet de verifier si une carte est présente dans la main
     * @param carte la carte que l'on vérifie
     * @return vrai si la carte est présente dans la main
     */
    public boolean verifSiCartePresente(int carte) {
        return this.listeCarte.contains(carte);
    }

    /**
     * permet de renvoyer la taille de la main
     * @return la taille
     */
    public int nbrCartes() {
        return this.listeCarte.size();
    }

    /**
     * permet d'afficher la main sous une forme précise
     * @return la chaine de caractere representant la main
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(" { ");
        for (int i : this.listeCarte)
            sb.append(String.format("%02d", i)).append(" "); /* permet d'ajouter un 0 aux chiffres inférieurs à 10*/
        return sb.toString() + "}";
    }
    /*--------------------------------------------------GETTERS-SETTERS------------------------------------------------------*/

    public ArrayList<Integer> getListeCarte() {
        return listeCarte;
    }

    public void setListeCarte(ArrayList<Integer> liste) {
        this.listeCarte = liste;
    }
}
