package jeu;

import java.util.ArrayList;
import java.util.Collections;

public class Joueur {
    public static final int NBR_DE_CARTES_MAX_MAIN = 6;// on initialise le nombre de carte maximal dans la main
    private final String pseudo; // on a besoin de lui affecter un pseudo
    private final Pile pileAsc , pileDesc;// initialisation des 2 piles
    private final Pioche pioche; // la pioche du joueur
    private final MainJ mainJDuJoueur; // la main du joueur
    private boolean fauteJoueur; // va permettre de traiter toutes les erreurs potentielles faites par le joueur, l'utilisateur

    /**
     * Le constructeur d'un joueur
     * @param pseudo la chaine de caractere representant le pseudo
     */
    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.pioche = new Pioche();
        this.mainJDuJoueur = new MainJ(creationMain());
        this.pileAsc = new Pile('^', 1);
        this.pileDesc = new Pile('v', 60);
    }

    /**
     * Initialise la main du joueur avec 6 cartes piochées a partir de la pioche du joueur
     * @return la liste de la main du joueur
     */
    private ArrayList<Integer> creationMain() {
        ArrayList<Integer> liste = new ArrayList<>();
        for (int i = 0; i < NBR_DE_CARTES_MAX_MAIN; i++) {
            liste.add(this.pioche.prendUneCarte());
        }
        Collections.sort(liste);
        return liste;
    }

    /**
     * Permet de piocher des cartes dans la pioche
     * @param nbrCarteAPioche le nombre de carte(s) a piocher
     */
    public void piocheCartes(int nbrCarteAPioche) {
        for (int i = 0; i < nbrCarteAPioche; i++)
            this.mainJDuJoueur.recupCarte(this.pioche.prendUneCarte());
    }

    /**
     * indique le nombre de carte(s) restante(s) du joueur
     * @return le nombre de carte(s) restante(s) du joueur
     */
    public int cartesRestantes() {
        return this.pioche.getListeCarteDansPioche().size() + this.mainJDuJoueur.nbrCartes();
    }

    /*-------------------------------------------TOSTRING-----------------------------------------------*/

    /**
     * Permet de faire l'affichage du pseudo, des joueurs avec leurs piles ascendantes et descendantes
     * Permet de faire l'affichage du nombre de cartes dans la main et dans la pioche
     * @return le pseudo du joueur avec les sommets de ses piles et le nombre de sa main et de sa pioche
     */
    public String toString() {
        String s = "";
        if (this.pseudo.equals("SUD"))
            s += this.pseudo + "  ";
        else
            s += this.pseudo + " ";
        s += pileAsc + " " + pileDesc + " (m" + this.mainJDuJoueur.nbrCartes() + "p" +
                (cartesRestantes()-this.mainJDuJoueur.nbrCartes()) + ")\n";
        return s;
    }

    /**
     * Permet de faire l'affichage de la main du joueur courant
     * @return la main du joueur
     */
    public String toStringMainCourante() {
        return "cartes " + this.pseudo + this.mainJDuJoueur;
    }

    /*-----------------------------------------------PIOCHER---------------------------------------------------------*/

    /**
     * Permet de savoir si le joueur peut piocher
     * @return faux si la pioche du joueur est vide
     */
    public boolean peutPiocher() {
        return !(this.pioche.getListeCarteDansPioche().isEmpty());
    }

    /**
     * Permet au joueur de poser une carte sur une pile
     * @param surPileAsc vrai si le joueur va poser la carte sur la pile ascendante
     * @param carte jouée par le joueur
     */
    public void poseUneCarte(boolean surPileAsc, int carte){
        if (surPileAsc){
            if (this.mainJDuJoueur.verifSiCartePresente(carte)) {
                pileAsc.setSommetDeLaPile(this.mainJDuJoueur.poseCarte(carte));
            } else this.fauteJoueur=true;
        }else {
            if (this.mainJDuJoueur.verifSiCartePresente(carte)) {
                pileDesc.setSommetDeLaPile(this.mainJDuJoueur.poseCarte(carte));
            } else this.fauteJoueur = true;
        }
    }

    /**
     * Permet au joueur de poser une carte sur une pile adverse
     * @param surPileAsc vrai si le joueur va poser la carte sur la pile ascendante
     * @param carte jouée par le joueur
     * @param autreJoueur le joueur adverse
     */
    public void poseUneCarte(boolean surPileAsc, int carte, Joueur autreJoueur){
        if (surPileAsc){
            if (this.mainJDuJoueur.verifSiCartePresente(carte)) {
                autreJoueur.pileAsc.setSommetDeLaPile(this.mainJDuJoueur.poseCarte(carte));
            } else this.fauteJoueur=true;
        }else {
            if (this.mainJDuJoueur.verifSiCartePresente(carte)) {
                autreJoueur.pileDesc.setSommetDeLaPile(this.mainJDuJoueur.poseCarte(carte));
            } else this.fauteJoueur = true;
        }
    }

    /**
     * Permet de savoir si le joueur courant peut jouer
     * @param autreJoueur permet d'avoir les informations renseignant ses piles
     * @return true si le joueur peut jouer
     */
    public boolean peutJouer(Joueur autreJoueur) {
        if (this.mainJDuJoueur.nbrCartes()==1){
            return false;
        }
        ArrayList<Integer> cartesJouablesSurMaPile = new ArrayList<>();
        ArrayList<Integer> cartesJouablesSurPileAdverse = new ArrayList<>();

        for (int i : this.mainJDuJoueur.getListeCarte()) {
            if (i > this.pileAsc.getSommetDeLaPile() || i == this.pileAsc.getSommetDeLaPile() - 10
                    || i < this.pileDesc.getSommetDeLaPile() || i == this.pileDesc.getSommetDeLaPile() + 10) {
                cartesJouablesSurMaPile.add(i);
            } else if (i < autreJoueur.pileAsc.getSommetDeLaPile() || i > autreJoueur.pileDesc.getSommetDeLaPile()) {
                cartesJouablesSurPileAdverse.add(i);
            }
        }
        if (cartesJouablesSurMaPile.size() >= 2) {
            return true;

        } else if (cartesJouablesSurMaPile.isEmpty()) {
            return false;

        } else if (!cartesJouablesSurPileAdverse.isEmpty()) {

            if (cartesJouablesSurPileAdverse.size() == 1) {
                return !(cartesJouablesSurMaPile.get(0).equals(cartesJouablesSurPileAdverse.get(0)));
            } else return true;

        } else {
            if (cartesJouablesSurMaPile.get(0) == this.pileAsc.getSommetDeLaPile() - 10) {
                for (int carte : this.mainJDuJoueur.getListeCarte()) {
                    if (carte > cartesJouablesSurMaPile.get(0) || carte == cartesJouablesSurMaPile.get(0) - 10)
                        return true;
                }
            } else if (cartesJouablesSurMaPile.get(0) == this.pileDesc.getSommetDeLaPile() + 10) {
                for (int carte : this.mainJDuJoueur.getListeCarte()) {
                    if (carte < cartesJouablesSurMaPile.get(0) || carte == cartesJouablesSurMaPile.get(0) + 10)
                        return true;
                }
            }
        }
        return false;
    }

    /*--------------------------------------------------GETTERS-SETTERS------------------------------------------------------*/

    public boolean getFauteJoueur() {
        return fauteJoueur;
    }

    public void setFauteJoueur(boolean fauteJoueur) {
        this.fauteJoueur = fauteJoueur;
    }

    public String getPseudo() { return pseudo; }

    public Pile getPileAsc() {
        return pileAsc;
    }

    public Pile getPileDesc() {
        return pileDesc;
    }

    public Pioche getPioche() {
        return pioche;
    }

    public MainJ getMainDuJoueur() {
        return mainJDuJoueur;
    }

}