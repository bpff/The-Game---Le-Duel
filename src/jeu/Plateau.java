package jeu;

import java.util.ArrayList;


public class Plateau {
    private int compteurDeCartesPosees;//on initialise un compteur de cartes pour savoir combien de cartes onta été posées
    private int compteurDeCartesPiochees;//on initialise un compteur de carte(s) pour connaitre le nombre de carte(s) à piochée(s)
    private boolean joueChezLadverse;// on initialise un compteur pour savoir si un joueur joue chez l'adversaire

    /*----------------------AFFICHAGE--------------------------*/

    /**
     * Permet l'affichage d'un tour de jeu
     * @param joueur le joueur courant
     * @param autreJoueur le joueur adverse
     * @return l'affichage complet d'un tour avec les pseudos des joueurs, leurs piles et la main du joueur courant
     */
    public String afficherTour(Joueur joueur, Joueur autreJoueur){
        Joueur j1;
        Joueur j2;

        if(joueur.getPseudo().equals("NORD")){
            j1 = joueur;
            j2 = autreJoueur;
        } else{
            j1=autreJoueur;
            j2=joueur;
        }
        String s = "";
        s+= j1.toString();
        s+= j2.toString();
        s+=joueur.toStringMainCourante();
        s+= "\n> ";
        return s;
    }

    /**
     * Permet d'afficher le dernier tour de la partie
     * @param gagnant le joueur gagant
     * @param perdant le joueur perdant
     * @return l'affichage du dernier tour
     */
    public String dernierTour(Joueur gagnant, Joueur perdant){
        Joueur j1;
        Joueur j2;

        if(gagnant.getPseudo().equals("NORD")){
            j1 = gagnant;
            j2 = perdant;
        } else{
            j1=perdant;
            j2=gagnant;
        }
        String s = "";
        s+= j1.toString();
        s+= j2.toString();
        s+=perdant.toStringMainCourante();
        s+= "\npartie finie, " + gagnant.getPseudo() + " a gagné";
        return s;
    }

    /**
     * Permet de convertir 2 caractères en entier
     * @param c1 le 1er caractere
     * @param c2 le 2eme caractere
     * @return l'entier converti
     */
    private static int convertirEnEntier(char c1, char c2) {
        String s = "";
        s += c1;
        s+= c2;
        return Integer.parseInt(s);
    }

    /**
     * Permet à un joueur de jouer ses cartes sur les différentes piles (les siennes, ou celles de l'adversaires)
     * @param courant le joueur courant (joueur qui joue)
     * @param autreJoueur l'autre joueur
     * @param coup la chaine de caractère contenant le coup joué par le joueur
     */
    public void joueurJoueDesCartes(Joueur courant, Joueur autreJoueur, String coup) /*throws RuntimeException*/ {
        //-------------------
        int tmpAsc = courant.getPileAsc().getSommetDeLaPile(),
                tmpDesc = courant.getPileDesc().getSommetDeLaPile(),
                tmpAscEnnemi = autreJoueur.getPileAsc().getSommetDeLaPile(),
                tmpDescEnnemi = autreJoueur.getPileDesc().getSommetDeLaPile();
        ArrayList<Integer> mainTMP = courant.getMainDuJoueur().creationClone();

        this.compteurDeCartesPosees = 0;
        this.joueChezLadverse = false;
        courant.setFauteJoueur(false);
        //-------------------
        String[] mots = coup.split("\\s+");
        int i = 0;
        for (; i < mots.length; i++) {
            if (courant.getFauteJoueur()) break;
            if (mots[i].isEmpty() || mots.length == 1||mots[i].length()<3) {

                courant.setFauteJoueur(true);
                break;
            }
            switch (mots[i].substring(2)) {
                case "^":
                    jouerPileAscendante(courant,Plateau.convertirEnEntier(mots[i].charAt(0), mots[i].charAt(1)));
                    if (!courant.getFauteJoueur()) {

                        this.compteurDeCartesPosees++;
                    } break;
                case "^'":
                    jouerPileAscendanteEnnemi(courant ,autreJoueur, Plateau.convertirEnEntier(mots[i].charAt(0), mots[i].charAt(1)));
                    if (!courant.getFauteJoueur()) {

                        this.compteurDeCartesPosees++;
                        this.joueChezLadverse = true;
                    } break;
                case "v":
                    jouerPileDescendante(courant,Plateau.convertirEnEntier(mots[i].charAt(0), mots[i].charAt(1)));
                    if (!courant.getFauteJoueur()) {

                        this.compteurDeCartesPosees++;
                    } break;
                case "v'":
                    jouerPileDescendanteEnnemi(courant ,autreJoueur, Plateau.convertirEnEntier(mots[i].charAt(0), mots[i].charAt(1)));
                    if (!courant.getFauteJoueur()) {

                        this.compteurDeCartesPosees++;
                        this.joueChezLadverse=true;
                    } break;
                default:
                    courant.setFauteJoueur(true);
            }
        }
        if (courant.getFauteJoueur()) {
            courant.getPileAsc().setSommetDeLaPile(tmpAsc);
            courant.getPileDesc().setSommetDeLaPile(tmpDesc);
            autreJoueur.getPileAsc().setSommetDeLaPile(tmpAscEnnemi);
            autreJoueur.getPileDesc().setSommetDeLaPile(tmpDescEnnemi);
            courant.getMainDuJoueur().setListeCarte(mainTMP);
        }
        courant.getMainDuJoueur().trierMain();
    }

    /**
     * Permet à un joueur de verifier s'il peut jouer sa carte sur la pile ascendante et ainsi de la jouer ou non
     * @param courant le joueur courant (le joueur qui joue)
     * @param carte la carte jouer par le joueur
     */
    private void jouerPileAscendante(Joueur courant, int carte) {
        if (carte > courant.getPileAsc().getSommetDeLaPile() || carte == courant.getPileAsc().getSommetDeLaPile() - 10) {
            courant.poseUneCarte(true, carte);
        } else courant.setFauteJoueur(true);
    }

    /**
     * Permet à un joueur de verifier s'il peut jouer sa carte sur la pile descendante et ainsi de la jouer ou non
     * @param courant le joueur courant (le joueur qui joue)
     * @param carte la carte jouer par le joueur
     */
    private void jouerPileDescendante(Joueur courant, int carte) {
        if (carte < courant.getPileDesc().getSommetDeLaPile() || carte == courant.getPileDesc().getSommetDeLaPile() + 10) {
            courant.poseUneCarte(false,carte );
        } else courant.setFauteJoueur(true);
    }

    /**
     * Permet à un joueur de verifier s'il peut jouer sa carte sur la pile ascendante de l'adversaire et ainsi de la jouer ou non
     * @param courant le joueur courant (le joueur qui joue)
     * @param adverse le joueur adverse
     * @param carte la carte jouer par le joueur
     */
    private void jouerPileAscendanteEnnemi(Joueur courant, Joueur adverse, int carte ) {
        if (carte < adverse.getPileAsc().getSommetDeLaPile() && !joueChezLadverse) {
            courant.poseUneCarte(true, carte,adverse );
        } else courant.setFauteJoueur(true);
    }

    /**
     * Permet à un joueur de verifier s'il peut jouer sa carte sur la pile descendante de l'adversaire et ainsi de la jouer ou non
     * @param courant le joueur courant (le joueur qui joue)
     * @param adverse le joueur adverse
     * @param carte la carte jouer par le joueur
     */
    private void jouerPileDescendanteEnnemi(Joueur courant, Joueur adverse, int carte) {
        if (carte > adverse.getPileDesc().getSommetDeLaPile() && !joueChezLadverse) {
            courant.poseUneCarte(false, carte,adverse );
        } else courant.setFauteJoueur(true);
    }

    /**
     * Permet à un joueur de piocher le nombre de cartes qu'il dois piocher
     * @param courant le joueur qui pioche
     */
    public void piocheSesCartes(Joueur courant){
        compteurDeCartesPiochees =0;
        if (!courant.peutPiocher())
            return;
        int nombreCarteARecup = Joueur.NBR_DE_CARTES_MAX_MAIN - courant.getMainDuJoueur().nbrCartes();

        if (joueChezLadverse) {
            if (courant.getPioche().getListeCarteDansPioche().size()<nombreCarteARecup)
                for (int i=0 ; i < courant.getPioche().getListeCarteDansPioche().size(); i++)  {
                    compteurDeCartesPiochees++;
                }
            else
                for (int i=0 ; i < nombreCarteARecup; i++) {
                    compteurDeCartesPiochees++;
                }
        }
        else {
            if (courant.getPioche().getListeCarteDansPioche().size()==1)
                compteurDeCartesPiochees=1;
            else
                compteurDeCartesPiochees=2;
        }
        courant.piocheCartes(compteurDeCartesPiochees);
        courant.getMainDuJoueur().trierMain();
    }

    /**
     * Vérifie si le joueur a perdu ou non
     * @param pasCourant
     * @param courant
     * @return false si le joueur perdant ne peut plus jouer
     */
    private boolean perdrePartie(Joueur pasCourant,Joueur courant) {
        return !pasCourant.peutJouer(courant);
    }

    /**
     * Vérifier si un joueur a gagné ou non
     * @param courant le joueur potentiellement gagnant
     * @param pasCourant le joueur potentiellement perdant
     * @return vrai si le Joueur courant a gagné la partie
     */
    public boolean gagnerPartie(Joueur courant, Joueur pasCourant){
        return perdrePartie(pasCourant,courant) || courant.cartesRestantes() ==0;
    }
    /*--------------------------------------------------GETTERS-SETTERS------------------------------------------------------*/
    public int getCompteurDeCartesPiochees() {
        return compteurDeCartesPiochees;
    }

    public int getCompteurDeCartesPosees() {
        return compteurDeCartesPosees;
    }
}

