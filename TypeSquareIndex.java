/*
 * Classa držiaca Xový a Yový index v matici poľa.
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class TypeSquareIndex {
   
    private int indexX;
    private int indexY;
    /*
     * Konštruktor s 2 parametrami pre indexy pola.
     */
    public TypeSquareIndex(int x, int y) {
        this.indexX = x;
        this.indexY = y;
    }
    /*
     * Vracia Xový index.
     */
    public int returnIndexX() {
        return this.indexX;
    }
    /*
     * Vracia Yový index.
     */
    public int returnIndexY() {
        return this.indexY;
    }
}
