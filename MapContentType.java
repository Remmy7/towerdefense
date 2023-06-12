/*
 * Enumerácia contentu políčka.
 * Drží si cestu k obrázku, char pre parsovanie z textového súboru, a 3 booleany pre setnutie cesty, rozbiteľnosti a možnosti stavania.
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public enum MapContentType {
    PATH('p', "pictures/EMPTY.png", false, true, false),    
    BLUEJMAN(' ', "xdg.png", false, false, true),
    CARROTMAN(' ', "pictures/carrotman.png", false, false, true),
    EMPTY_BUILDING_SPACE('e', "pictures/emptybuildingspace.png", true, false, false),
    PLACEHOLDER(' ', "pictures/emptybuildingspace.png", false, false, false),
    MOB_SPAWN_POINT('S', "pictures/spawnPoint.png", false, true, false),
    MOB_FINISH_POINT('F', "pictures/finishPoint.png", false, true, false),
    ROCK('R', "pictures/rock.png", false, false, true),
    WATER('w', "pictures/water.png", false, false, false)
    ;

    private final char charMapContent;
    private String typeMapContent;
    private boolean isBuildable;
    private boolean isAPath;
    private boolean isBreakable;
    
   
    /*
     * Konštruktor assignujúci premenné.
     */
    private MapContentType(char charMapContent, String typeMapContent, boolean isBuildable, boolean isAPath, boolean isBreakable) {    
        this.typeMapContent = typeMapContent;
        this.charMapContent = charMapContent;
        this.isBuildable = isBuildable;
        this.isAPath = isAPath;
        this.isBreakable = isBreakable;
    }
    /*
     * Vracia enumeráciu.
     */
    public String getContent() {
        return this.typeMapContent;
    }
    /*
     * Vracia char value enumerácie.
     */
    public char getCharContent() {
        return this.charMapContent;
    }
    /*
     * Vracia či je to cesta.
     */
    public boolean getIsAPath() {
        return this.isAPath;
    }
    /*
     * Vracia či sa na tom dá stavať.
     */
    public boolean getIsBuildable() {
        return this.isBuildable;        
    }
    /*
     * Vracia či sa to dá rozbiť.
     */
    public boolean getIsBreakable() {
        return this.isBreakable;
    }
    /*
     * Switch na chary pre vytváranie mapy z textového súboru.
     */
    public static MapContentType changeMapContent(char charMapContent) {
        switch (charMapContent) {
            case 'p':
                return MapContentType.PATH;
            case 'e':
                return MapContentType.EMPTY_BUILDING_SPACE;
            case 'S':  
                return MapContentType.MOB_SPAWN_POINT;
            case 'F':   
                return MapContentType.MOB_FINISH_POINT;
            case 'w':
                return MapContentType.WATER;
            case 'R':   
                return MapContentType.ROCK;
            default:
                return MapContentType.PATH;
        }
    }
    /*
     * Switch vracajúci enumeráciu po sparsovaní Stringu na vstupe.
     */
    public static MapContentType changeMapContent(String stringMapContent) {
        switch (stringMapContent) {
            case "CARROTMAN":
                return MapContentType.CARROTMAN;
            case "BLUEJMAN":
                return MapContentType.BLUEJMAN;
            case "EMPTY":   
                return MapContentType.EMPTY_BUILDING_SPACE;
            
            
            default:
                return null;
                
        }
    }

}