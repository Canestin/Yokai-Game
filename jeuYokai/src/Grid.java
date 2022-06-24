public class Grid {

    private Card card;
    private Position pos;
    private Deck deck;
    private Color color;

    public Grid(Position p) {
        this.pos = p;
    }
 
    public boolean isEmpty(){
        if (card == null) return true;
        else return false;
    }                                                                       // si la case est vide !

    public Card getCard(){
        return card;          // Donne la carte actuelle dans la grille
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean thereIsClue(){
        if (deck == null) return false;
        else return true;
    }    // S'il n'y a aucun indice sur la case !


    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;          // Donne la carte actuelle dans la grille
    }
}    