import java.util.ArrayList;
/*
 * Content políčka alebo mobky na mape.
 * Používa classu Obrázok, aby zobrazil na plátne obrázky.
 * Drží si okrem všetkého v políčku ArrayList obrázkov mobov, aby sa ľahšie ukazovali a mazali pri práci s nimi.
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class MapContent {
    
    private Obrazok vizualization;
    private MapContentType mapContentType;
    private ArrayList <Obrazok> mobs;
    
    /*
     * Skonštruuje arraylist, a pridá políčku content podľa enumerácie.
     */
    public MapContent(MapContentType mapContentType, int x, int y) {
        this.mapContentType = mapContentType;
        this.vizualization = new Obrazok(mapContentType.getContent());
        this.vizualization.zmenPolohu(x, y);
        this.mobs = new ArrayList();
        
        
    }
    /*
     * Skryje všetky mobky v ArrayListe.
     */
    public void hideMobs() {
        for (int x = this.mobs.size() - 1; x >= 0; x--) {
            this.mobs.get(x).skry();
            this.mobs.remove(x);
        }            
    }
    /*
     * Skryje mobku na indexe 0 v ArrayListe.
     */
    public void hideMob() {
        this.mobs.get(0).skry();
        this.mobs.remove(0);
    }
    /*
     * Zmení content políčka podľa enumerácie.
     */
    public void changeMapContent(MapContentType mapContentType) {
        this.mapContentType = mapContentType;
        this.vizualization.zmenObrazok(this.mapContentType.getContent());
        this.vizualization.zobraz();
        
    }
    /*
     * Zmení obrázok mobky // zobrazí ju.
     */
    public void setMapContent(String mapContent, int x, int y) {        
        this.vizualization = new Obrazok(mapContent);
        this.mobs.add(this.vizualization);
        this.vizualization.zmenPolohu(x, y);
        this.vizualization.zobraz();
    }
    /*
     * Vráti, či je content mapky cesta.
     */
    public boolean getIsAPath() {
        return this.mapContentType.getIsAPath();
    }
     /*
     * Vráti, či sa dá na políčku stavať..
     */
    public boolean getIsBuildable() {
        return this.mapContentType.getIsBuildable();
    }
     /*
     * Vráti, či je content mapky rozbiteľný..
     */
    public boolean getIsBreakable() {
        return this.mapContentType.getIsBreakable();
    }
    /*
     * Enumerácia sparsuje String, vráti enumeráciu mapContentType.
     */
    public MapContentType getMapContentType(String mapContentType) {
        return MapContentType.changeMapContent(mapContentType);
    }
    /*
     * Enumerácia sparsuje char.
     */
    public MapContentType getMapContentTypeChar(char mapContentType) {
        return MapContentType.changeMapContent(mapContentType);
    }
}