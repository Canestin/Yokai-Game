import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Yokai {

    private Players[] players;
    ArrayList<Clues> clues;
    Grid[][] board;
    ArrayList<Card> cards = new ArrayList();
    ArrayList<Clues> standBy = new ArrayList();
    Clues clueChosen;
    private int tour = 1;
    private int rowBeforeMove;
    private int columnBeforeMove;
    private int rowAfterMove;
    private int columnAfterMove;
    private int chooseStandby;
    private int endAnswer;

    public void play() {

        System.out.println("\n\n                                               ***************************************\n");
        System.out.println("                                               *      Bienvenue dans la partie       *");
        System.out.println("\n                                               ***************************************\n\n");
        createPlayer();
        createBoard();
        createCards();
        initialiseDeck();
        while (!end()) {
            switchPlayer();
            viewCard();
            askMove();
            updateBoard();
            chooseClueDeck();
            updateDeck();
            printBoard();
            tour++;
        }
    }

    public void createPlayer() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Joueur 1 veuillez saisir votre pseudo ");
        String joueur1 = scanner.nextLine();
        System.out.println("Joueur 2 veuillez saisir votre pseudo ");
        String joueur2 = scanner.nextLine();
        System.out.println("\n" + "LET'S GO !  " + "\n ");

        Players player1 = new Players(joueur1);
        Players player2 = new Players(joueur2);
        players = new Players[]{player1, player2};

    }

    public void createBoard() {

        board = new Grid[12][12];

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                board[i][j] = new Grid(new Position(i, j));
            }
        }
        System.out.println("Le plateau vient d'etre crée !");
    }

    public void createCards() {

        try{

            ArrayList<Color> colors = new ArrayList<>(
                    Arrays.asList(Color.bleu, Color.bleu, Color.bleu, Color.bleu,
                            Color.rouge, Color.rouge, Color.rouge, Color.rouge,
                            Color.violet, Color.violet, Color.violet, Color.violet,
                            Color.vert, Color.vert, Color.vert, Color.vert));
            Collections.shuffle(colors);


            System.out.println("\nVoulez-vous une partie aléatoire ou une partie démo facile ?  0 pour aléatoire, 1 pour démo ");
            Scanner scanner = new Scanner(System.in);
            int rep = scanner.nextInt();

            if(rep==0) {

                int k = 0;
                for (int i = 4; i < 8; i++) {
                    for (int j = 4; j < 8; j++) {
                        Card card = new Card(new Position(i, j), colors.get(k));
                        cards.add(card);
                        board[i][j].setCard(card);
                        k++;
                    }
                }

                System.out.println("Les cartes ont été placées sur le plateau !");

            } else if(rep==1) {

                // Pour générer une partie facile !  en 4 coups  (4;4 -> 6;8) + (7;4->4;4) + (7;7->7;4) + (4;7->7;7)

                Card card1 = new Card(new Position(4, 4), Color.violet);
                cards.add(card1);
                board[4][4].setCard(card1);

                Card card3 = new Card(new Position(4, 5), Color.bleu);
                cards.add(card3);
                board[4][5].setCard(card3);

                Card card4 = new Card(new Position(4, 6), Color.bleu);
                cards.add(card4);
                board[4][6].setCard(card4);

                Card card5 = new Card(new Position(4, 7), Color.violet);
                cards.add(card5);
                board[4][7].setCard(card5);

                Card card6 = new Card(new Position(5, 4), Color.bleu);
                cards.add(card6);
                board[5][4].setCard(card6);

                Card card7 = new Card(new Position(5, 5), Color.rouge);
                cards.add(card7);
                board[5][5].setCard(card7);

                Card card8 = new Card(new Position(5, 6), Color.rouge);
                cards.add(card8);
                board[5][6].setCard(card8);

                Card card9 = new Card(new Position(5, 7), Color.rouge);
                cards.add(card9);
                board[5][7].setCard(card9);

                Card card10 = new Card(new Position(6, 4), Color.vert);
                cards.add(card10);
                board[6][4].setCard(card10);

                Card card11 = new Card(new Position(6, 5), Color.vert);
                cards.add(card11);
                board[6][5].setCard(card11);

                Card card12 = new Card(new Position(6, 6), Color.rouge);
                cards.add(card12);
                board[6][6].setCard(card12);

                Card card13 = new Card(new Position(6, 7), Color.violet);
                cards.add(card13);
                board[6][7].setCard(card13);

                Card card14 = new Card(new Position(7, 4), Color.bleu);
                cards.add(card14);
                board[7][4].setCard(card14);

                Card card15 = new Card(new Position(7, 5), Color.vert);
                cards.add(card15);
                board[7][5].setCard(card15);

                Card card16 = new Card(new Position(7, 6), Color.violet);
                cards.add(card16);
                board[7][6].setCard(card16);

                Card card = new Card(new Position(7, 7), Color.vert);
                cards.add(card);
                board[7][7].setCard(card);


                System.out.println("Les cartes ont été placées sur le plateau !");

            } else createCards();

        } catch (Exception e){
            createCards();
        }

    }

    public void initialiseDeck() {

        ArrayList<Clues> clues1 = new ArrayList<>(
                Arrays.asList(Clues.cRouge, Clues.cBleu, Clues.cVert, Clues.cViolet));
        // Pour choisir deux indices d'une couleur au hasard !
        Collections.shuffle(clues1);   // On mélange d'abord la liste !
        clues1.remove(3);        // Puis on supprime deux éléments
        clues1.remove(2);

        ArrayList<Clues> clues2 = new ArrayList<>(
                Arrays.asList(Clues.cBleuRouge, Clues.cBleuVert, Clues.cBleuViolet, Clues.cRougeViolet,
                        Clues.cRougeVert, Clues.cVioletVert));
        // Pour choisir trois indices de deux couleurs au hasard !
        Collections.shuffle(clues2);
        clues2.remove(5);
        clues2.remove(4);
        clues2.remove(3);

        ArrayList<Clues> clues3 = new ArrayList<>(
                Arrays.asList(Clues.cVertVioletRouge, Clues.cVertVioletBleu,
                        Clues.cRougeBleuVert, Clues.cRougeBleuViolet));
        // Pour choisir deux indices de trois couleurs au hasard !
        Collections.shuffle(clues3);
        clues3.remove(3);
        clues3.remove(2);

        // La liste d'indices définitive
        clues = new ArrayList<>(
                Arrays.asList(clues1.get(0), clues1.get(1), clues2.get(0), clues2.get(1),
                        clues2.get(2), clues3.get(0), clues3.get(1)));

        System.out.println("Les indices ont été choisis aléatoirement : \n");
        for (Clues cls : clues) {
            System.out.println(cls);
        }

        Collections.shuffle(clues);   // Après avoir affiché les indices, on les mélange dans la liste
    }

    public void switchPlayer() {
        if (tour % 2 == 0) {
            System.out.println("\n\nTour de " + players[1]);
        } else System.out.println("\n\nTour de " + players[0]);
    }

    public void viewCard1(){

        try {

            System.out.println("Veuillez regarder la premiere carte ( format 0;11 )  (ligne;colonne)");
            Scanner scanner = new Scanner(System.in);
            String choice1 = scanner.nextLine();

            String[] pos01 = choice1.split(";");

            int rowChoice1 = Integer.parseInt(pos01[0]);
            int columnChoice1 = Integer.parseInt(pos01[1]);
            if (board[rowChoice1][columnChoice1].isEmpty()) {

                System.out.println("Il n'y a aucune carte sur cette case !");
                viewCard1();
            } else {
                if (board[rowChoice1][columnChoice1].thereIsClue()) {
                    System.out.println("Y a un indice sur cette carte, vous ne pouvez pas la voir ! ");
                    viewCard1();
                } else System.out.println("Vous avez regardé la carte " +
                        board[rowChoice1][columnChoice1].getCard().getColor() + " : " +
                        "["+rowChoice1+";"+columnChoice1+"]");
            }

        } catch (Exception NumberFormatException) {
            System.out.println("Oups ");
            viewCard1();
        }
    }

    public void viewCard2() {

        try {

            System.out.println("Veuillez regarder la deuxième carte (format 0;11)");
            Scanner scanner = new Scanner(System.in);
            String choice2 = scanner.nextLine();

            String[] pos02 = choice2.split(";");

            int rowChoice2 = Integer.parseInt(pos02[0]);
            int columnChoice2 = Integer.parseInt(pos02[1]);
            if (board[rowChoice2][columnChoice2].isEmpty()) {
                System.out.println("Il n'y a aucune carte sur cette case !");
                viewCard2();
            } else {
                if (board[rowChoice2][columnChoice2].thereIsClue()) {
                    System.out.println("Y a un indice sur cette carte, vous ne pouvez pas la voir ! ");
                    viewCard1();
                } else System.out.println("Vous avez regargé la carte " +
                        board[rowChoice2][columnChoice2].getCard().getColor() + " : " +
                        "["+rowChoice2+";"+columnChoice2+"]");
            }

        } catch (Exception NumberFormatException) {
            System.out.println("Oups ! ");
            viewCard2();
        }
    }

    public void viewCard() {
        System.out.println("--------------------------------------------------------" +
                "\nVous devez observer deux cartes ");

        viewCard1();
        viewCard2();

    }


    public String askMoveAvant() {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Quelle carte voulez-vous déplacer ?  (format 0;11) ");
            String coup = scanner.nextLine();

            String[] position = coup.split(";");

            rowBeforeMove = Integer.parseInt(position[0]);
            columnBeforeMove = Integer.parseInt(position[1]);
            if (board[rowBeforeMove][columnBeforeMove].isEmpty()) {
                System.out.println("Il n y aucune carte sur cette case !");
                askMoveAvant();
            } else {
                if (board[rowBeforeMove][columnBeforeMove].thereIsClue()) {
                    System.out.println("Y a un indice sur cette carte, vous ne pouvez pas la déplacer ");
                    askMoveAvant();
                } else System.out.println("");
            }
            return coup;
        } catch (Exception NumberFormatException) {
            return askMoveAvant();
        }

    }

    public String askMoveApres() {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Veuillez saisir votre prochain coup  : ");
            String prochaincoup = scanner.nextLine();

            String[] newposition = prochaincoup.split(";");
            rowAfterMove = Integer.parseInt(newposition[0]);
            columnAfterMove = Integer.parseInt(newposition[1]);

            if (board[rowAfterMove][columnAfterMove].isEmpty()) {

                if (board[rowAfterMove][columnAfterMove - 1].isEmpty() &&
                        board[rowAfterMove][columnAfterMove + 1].isEmpty() &&
                        board[rowAfterMove + 1][columnAfterMove].isEmpty() &&
                        board[rowAfterMove - 1][columnAfterMove].isEmpty()) {
                    System.out.println("Ce déplacement n'est passoible ! Les cartes doivent etre cote à cote ");
                    askMoveApres();

                } else System.out.println("Vous avez déplacé la carte en " + "[" + rowAfterMove + ";" + columnAfterMove + "] ");

            } else {
                System.out.println("Cette case est occupée");
                askMoveApres();
            }

            return prochaincoup;
        } catch (Exception NumberFormatException) {
            return askMoveApres();
        }
    }

    public String askMove() {

        return askMoveAvant() + askMoveApres();

    }

    public void updateBoard() {

        Color cc = board[rowBeforeMove][columnBeforeMove].getCard().getColor();
        Card card = new Card(new Position(rowAfterMove, columnAfterMove), cc);
        board[rowAfterMove][columnAfterMove].setCard(card);
        board[rowBeforeMove][columnBeforeMove].setCard(null);
        cards.clear();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {

                if (!isEmpty(i, j)) {
                    Color c = board[i][j].getCard().getColor();
                    Card cardd = new Card(new Position(i, j), c);
                    board[i][j].setCard(cardd);
                    board[i][j].setColor(c);
                    cards.add(cardd);
                }
                else {
                    board[i][j].setColor(Color.empty);
                }
            }
        }

    //    System.out.println("\nVerifions si la case avant le mouvement est vide : " + isEmpty(rowBeforeMove, columnBeforeMove));
    //    System.out.println("Verifions si la case après le mouvement est vide : " + isEmpty(rowAfterMove, columnAfterMove));

    /*    System.out.println("Affichons les cartes :  (Normalement elles seront masquées)");

        for (Card ca : cards) {
            System.out.println(ca.getColor() + " : [" + ca.getPos().getRow() + ";" + ca.getPos().getColumn() + "]");
        }

        System.out.println("\nFin de updateBoard\n");         */

    }


    public void chooseClueDeck() {

        clueChosen = clues.get(0);
        System.out.println("\nL'indice est : " + clueChosen);
        System.out.println("\nVoulez-vous utiliser cet indice ?   0 pour NON, 1 pour OUI !");

        try {

            Scanner scanner = new Scanner(System.in);
            int reponse = scanner.nextInt();
            if (reponse == 0) {
                if(standBy.size()>0){
                    System.out.println("Les indices en standBy sont : \n");
                    for (Clues st : standBy) {
                        System.out.println(st);
                    }
                    System.out.println("\n");

                    System.out.println("\nVoulez-vous utiliser un des indices en standBy ?   0 pour NON, 1 pour OUI !");
                    int rep = scanner.nextInt();
                    standBy.add(clueChosen);
                    if (rep == 0) {
                        return;
                    } else {
                        chooseClueStandBy();
                    }
                } else {
                    standBy.add(clueChosen);
                    return;
                }


            } else if(reponse==1) askMoveClue();

            else chooseClueDeck();

        } catch (Exception e) {
            System.out.println("Mauvaise saisie, veuillez recommencer !\n");
            chooseClueDeck();
        }
    }

    public void chooseClueStandBy(){

        try{
            System.out.println("Veuillez choisir un des indices en standBy (Dans l'ordre affiché !)");
            do {
                Scanner scanner = new Scanner(System.in);
                chooseStandby = scanner.nextInt();
                System.out.println("Déposez le " + standBy.get(chooseStandby - 1) + " sur une carte !");
                askMoveClue();
            } while (chooseStandby < 1 || chooseStandby > standBy.size());

        } catch (Exception e){
            chooseClueStandBy();
        }

    }

    public void updateDeck() {

        clues.remove(0);      // A la fin du tour !
        System.out.println("\n");
    }


    public void askMoveClue() {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Veuillez placer votre indice  ( format 0;11 )");
            String placeClue = scanner.nextLine();

            String[] newposition = placeClue.split(";");
            int newligne = Integer.parseInt(newposition[0]);
            int newcolonne = Integer.parseInt(newposition[1]);

            if (!board[newligne][newcolonne].isEmpty()) {

                if (board[newligne][newcolonne].thereIsClue()) {
                    System.out.print("Y a deja un indice sur cette case \n ");
                    askMoveClue();
                } else System.out.println("L'indice  a été placé avec succès ");

                Deck deck = new Deck(new Position(newligne, newcolonne), clueChosen);
                board[newligne][newcolonne].setDeck(deck);

            } else {
                System.out.println("Cette case est vide");
                askMoveClue();
            }
        } catch (Exception NumberFormatException) {
            askMoveClue();
        }
    }

    public void printBoard(){

        for(int i=0; i<11; i++){
            if(board[11][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[11][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[10][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[10][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[9][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[9][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[8][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[8][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[7][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[7][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");
        for(int i=0; i<11; i++){
            if(board[6][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[6][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[5][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[5][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");
        for(int i=0; i<11; i++){
            if(board[4][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[4][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");
        for(int i=0; i<11; i++){
            if(board[3][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[3][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");
        for(int i=0; i<11; i++){
            if(board[2][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[2][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");
        for(int i=0; i<11; i++){
            if(board[1][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[1][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }
        System.out.println("\n");
        for(int i=0; i<11; i++){
            if(board[0][i].getColor()==Color.empty){
                System.out.print("         ");
            }
            else {
                if(board[0][i].thereIsClue()){
                    System.out.print("clue     ");
                } else  System.out.print("card     ");
            }
        }

        System.out.println("");

                 // FIN DE UPDATBOARD
    }

    public boolean isEmpty(int i, int j) {
        if (board[i][j].isEmpty()) {
            return true;
        } else
            return false;
    }

    public void endQuestion(){
        try{
            System.out.println("Les Yokais sont-ils apaisés ?  0 pour NON,  1 pour OUI !");
            Scanner sc = new Scanner(System.in);
            endAnswer = sc.nextInt();
        } catch (Exception e){
            endQuestion();
        }
    }
    private boolean end() {

        if(tour>1){

            endQuestion();
            if (endAnswer == 0)
                return false;
            else   {
                youWon();
                return true;
            }
        } else return false;

    }


    public void youWon() {


        Card c1 = cards.get(0);
        Card c2 = cards.get(1);
        Card c3 = cards.get(2);
        Card c4 = cards.get(3);
        Card c5 = cards.get(4);
        Card c6 = cards.get(5);
        Card c7 = cards.get(6);
        Card c8 = cards.get(7);
        Card c9 = cards.get(8);
        Card c10 = cards.get(9);
        Card c11 = cards.get(10);
        Card c12 = cards.get(11);
        Card c13 = cards.get(12);
        Card c14 = cards.get(13);
        Card c15 = cards.get(14);
        Card c16 = cards.get(15);


        for(int i=0; i<11; i++){
            if(board[11][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[11][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[11][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[11][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[11][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[10][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[10][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[10][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[10][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[10][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[9][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[9][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[9][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[9][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[9][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[8][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[8][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[8][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[8][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[8][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[7][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[7][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[7][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[7][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[7][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[6][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[6][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[6][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[6][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[6][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[5][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[5][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[5][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[5][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[5][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[4][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[4][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[4][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[4][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[4][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[3][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[3][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[3][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[3][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[3][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[2][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[2][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[2][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[2][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[2][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }

        }

        System.out.println("\n");

        for(int i=0; i<11; i++){
            if(board[1][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[1][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[1][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[1][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[1][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }
        }

        System.out.println("\n");
        for(int i=0; i<11; i++){
            if(board[0][i].getColor()==Color.empty){
                System.out.print("          ");
            }
            else if(board[0][i].getColor()==Color.bleu){
                System.out.print("\u001B[34m" + "color     ");
            }
            else if(board[0][i].getColor()==Color.rouge){
                System.out.print("\u001B[31m" + "color     ");
            }
            else if(board[0][i].getColor()==Color.vert){
                System.out.print("\u001B[32m" + "color     ");
            }
            else if(board[0][i].getColor()==Color.violet){
                System.out.print("\u001B[35m" + "color     ");
            }
        }

        System.out.println("\n");



        if ((c1.getColor() == board[c1.getPos().getRow()][c1.getPos().getColumn() - 1].getColor() ||
                (c1.getColor() == board[c1.getPos().getRow() + 1][c1.getPos().getColumn()].getColor()) ||
                (c1.getColor() == board[c1.getPos().getRow() - 1][c1.getPos().getColumn()].getColor()) ||
                (c1.getColor() == board[c1.getPos().getRow()][c1.getPos().getColumn() + 1].getColor()))
                &&
                (c2.getColor() == board[c2.getPos().getRow()][c2.getPos().getColumn() - 1].getColor() ||
                        (c2.getColor() == board[c2.getPos().getRow() + 1][c2.getPos().getColumn()].getColor()) ||
                        (c2.getColor() == board[c2.getPos().getRow() - 1][c2.getPos().getColumn()].getColor()) ||
                        (c2.getColor() == board[c2.getPos().getRow()][c2.getPos().getColumn() + 1].getColor()))
                &&
                (c3.getColor() == board[c3.getPos().getRow()][c3.getPos().getColumn() - 1].getColor() ||
                        (c3.getColor() == board[c3.getPos().getRow() + 1][c3.getPos().getColumn()].getColor()) ||
                        (c3.getColor() == board[c3.getPos().getRow() - 1][c3.getPos().getColumn()].getColor()) ||
                        (c3.getColor() == board[c3.getPos().getRow()][c3.getPos().getColumn() + 1].getColor()))
                &&
                (c4.getColor() == board[c4.getPos().getRow()][c4.getPos().getColumn() - 1].getColor() ||
                        (c4.getColor() == board[c4.getPos().getRow() + 1][c4.getPos().getColumn()].getColor()) ||
                        (c4.getColor() == board[c4.getPos().getRow() - 1][c4.getPos().getColumn()].getColor()) ||
                        (c4.getColor() == board[c4.getPos().getRow()][c4.getPos().getColumn() + 1].getColor()))
                &&
                (c5.getColor() == board[c5.getPos().getRow()][c5.getPos().getColumn() - 1].getColor() ||
                        (c5.getColor() == board[c5.getPos().getRow() + 1][c5.getPos().getColumn()].getColor()) ||
                        (c5.getColor() == board[c5.getPos().getRow() - 1][c5.getPos().getColumn()].getColor()) ||
                        (c5.getColor() == board[c5.getPos().getRow()][c5.getPos().getColumn() + 1].getColor()))
                &&
                (c6.getColor() == board[c6.getPos().getRow()][c6.getPos().getColumn() - 1].getColor() ||
                        (c6.getColor() == board[c6.getPos().getRow() + 1][c6.getPos().getColumn()].getColor()) ||
                        (c6.getColor() == board[c6.getPos().getRow() - 1][c6.getPos().getColumn()].getColor()) ||
                        (c6.getColor() == board[c6.getPos().getRow()][c6.getPos().getColumn() + 1].getColor()))
                &&
                (c7.getColor() == board[c7.getPos().getRow()][c7.getPos().getColumn() - 1].getColor() ||
                        (c7.getColor() == board[c7.getPos().getRow() + 1][c7.getPos().getColumn()].getColor()) ||
                        (c7.getColor() == board[c7.getPos().getRow() - 1][c7.getPos().getColumn()].getColor()) ||
                        (c7.getColor() == board[c7.getPos().getRow()][c7.getPos().getColumn() + 1].getColor()))
                &&
                (c8.getColor() == board[c8.getPos().getRow()][c8.getPos().getColumn() - 1].getColor() ||
                        (c8.getColor() == board[c8.getPos().getRow() + 1][c8.getPos().getColumn()].getColor()) ||
                        (c8.getColor() == board[c8.getPos().getRow() - 1][c8.getPos().getColumn()].getColor()) ||
                        (c8.getColor() == board[c8.getPos().getRow()][c8.getPos().getColumn() + 1].getColor()))
                &&
                (c9.getColor() == board[c9.getPos().getRow()][c9.getPos().getColumn() - 1].getColor() ||
                        (c9.getColor() == board[c9.getPos().getRow() + 1][c9.getPos().getColumn()].getColor()) ||
                        (c9.getColor() == board[c9.getPos().getRow() - 1][c9.getPos().getColumn()].getColor()) ||
                        (c9.getColor() == board[c9.getPos().getRow()][c9.getPos().getColumn() + 1].getColor()))
                &&
                (c10.getColor() == board[c10.getPos().getRow()][c10.getPos().getColumn() - 1].getColor() ||
                        (c10.getColor() == board[c10.getPos().getRow() + 1][c10.getPos().getColumn()].getColor()) ||
                        (c10.getColor() == board[c10.getPos().getRow() - 1][c10.getPos().getColumn()].getColor()) ||
                        (c10.getColor() == board[c10.getPos().getRow()][c10.getPos().getColumn() + 1].getColor()))
                &&
                (c11.getColor() == board[c11.getPos().getRow()][c11.getPos().getColumn() - 1].getColor() ||
                        (c11.getColor() == board[c11.getPos().getRow() + 1][c11.getPos().getColumn()].getColor()) ||
                        (c11.getColor() == board[c11.getPos().getRow() - 1][c11.getPos().getColumn()].getColor()) ||
                        (c11.getColor() == board[c11.getPos().getRow()][c11.getPos().getColumn() + 1].getColor()))
                &&
                (c12.getColor() == board[c12.getPos().getRow()][c12.getPos().getColumn() - 1].getColor() ||
                        (c12.getColor() == board[c12.getPos().getRow() + 1][c12.getPos().getColumn()].getColor()) ||
                        (c12.getColor() == board[c12.getPos().getRow() - 1][c12.getPos().getColumn()].getColor()) ||
                        (c12.getColor() == board[c12.getPos().getRow()][c12.getPos().getColumn() + 1].getColor()))
                &&
                (c13.getColor() == board[c13.getPos().getRow()][c13.getPos().getColumn() - 1].getColor() ||
                        (c13.getColor() == board[c13.getPos().getRow() + 1][c13.getPos().getColumn()].getColor()) ||
                        (c13.getColor() == board[c13.getPos().getRow() - 1][c13.getPos().getColumn()].getColor()) ||
                        (c13.getColor() == board[c13.getPos().getRow()][c13.getPos().getColumn() + 1].getColor()))
                &&
                (c14.getColor() == board[c14.getPos().getRow()][cards.get(0).getPos().getColumn() - 1].getColor() ||
                        (c14.getColor() == board[c14.getPos().getRow() + 1][c14.getPos().getColumn()].getColor()) ||
                        (c14.getColor() == board[c14.getPos().getRow() - 1][c14.getPos().getColumn()].getColor()) ||
                        (c14.getColor() == board[c14.getPos().getRow()][c14.getPos().getColumn() + 1].getColor()))
                &&
                (c15.getColor() == board[c15.getPos().getRow()][c15.getPos().getColumn() - 1].getColor() ||
                        (c15.getColor() == board[c15.getPos().getRow() + 1][c15.getPos().getColumn()].getColor()) ||
                        (c15.getColor() == board[c15.getPos().getRow() - 1][c15.getPos().getColumn()].getColor()) ||
                        (c15.getColor() == board[c15.getPos().getRow()][c15.getPos().getColumn() + 1].getColor()))) {

            System.out.println("\u001B[29m");
            System.out.println("                                         BRAVO ! Vous avez réuni toutes les familles ");
        }
            else {
                System.out.println("\u001B[29m");
                System.out.println("                                                     OUPS ! Vous avez perdu"); }

        System.out.println("\n                        ------------------------------- Merci d'avoir joué ! --------------------------------\n\n");

        System.out.println("Voulez-vous rejouer ?  0 pour NON, 1 pour OUI !");
        Scanner sc = new Scanner(System.in);
        int rep = sc.nextInt();
        if(rep==0){
            System.out.println("À bientôt !");
        }
        else {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("\n                          ------------------------------- NOUVELLE PARTIE ! --------------------------------\n\n\n\n\n\n\n\n\n\n\n");
            play();
        }

    }

}
