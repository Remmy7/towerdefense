/*
 * Classa držiaca Xový a Yový index v plátne.
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class TypeSquareDimension {
    private int x;
    private int y;
    /*
     * Konštruktor pre vytvorenie Xovej a Yovej súradnice pre plátno.
     */
    public TypeSquareDimension(int x, int y) {
        this.x = x;
        this.y = y;
        
    }
    /*
     * Vracia variable x.
     */
    public int getX() {
        return this.x;
    }
    /*
     * Vracia variable y.
     */
    public int getY() {
        return this.y;
    }
    
    
}
