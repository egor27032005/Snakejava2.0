package Game;

public interface GUI {

    int getWidth();

    int getHeight();

    int getY();

    int getX();

    Sprite Sprite();
    int receiveClick(int x, int y, int button);
    default boolean isHit(int xclick, int yclick){
        return ( (xclick > getX()) && (xclick < getX()+this.getWidth()) )
                &&( (yclick > getY()) && (yclick < getY()+this.getHeight()) );
    }
}