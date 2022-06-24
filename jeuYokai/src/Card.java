public class Card {

   private Position pos;
   private Color color;

   //  Archivages : 952781

    public Card(Position position, Color color) {
        this.pos = position;
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public Position getPos() {
        return pos;
    }


}