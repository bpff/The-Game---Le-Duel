package jeu;

public class Pile {
    private final char signePile; //on définit la pile ou nous allons jouer grâce au signe de la pile (^,v)
    private int sommetDeLaPile; // le sommet de pile

    /**
     * Constructeur d'une pile
     * @param signe le signe de la pile
     * @param sommet le sommet de la pile
     */
    public Pile(char signe,int sommet){
        this.signePile=signe;
        this.sommetDeLaPile=sommet;
    }

    /*------------------------AFFICHAGE--------------------------*/

    /**
     * Permet de concaténer le signe de la pile et son sommet associé
     * @return le signe de la pile et le sommet de la pile
     */
    public String toString() {
        return this.signePile + "[" + String.format("%02d",this.sommetDeLaPile) +"]";
    }

    /*--------------------GETTERS/SETTERS-----------------------*/

    public int getSommetDeLaPile() {
        return sommetDeLaPile;
    }

    public void setSommetDeLaPile(int sommetDeLaPile) {
        this.sommetDeLaPile = sommetDeLaPile;
    }
}