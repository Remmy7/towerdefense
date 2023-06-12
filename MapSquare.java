import java.util.ArrayList;
import java.util.Random;
/*
 * Políčka v poli.
 * Sú základom kompletne celej hry, držia si všetky potrebné arraylisti a parametre s ktorými neskôr pracuje.
 * Drží si arraylisty mobiek v sebe, alebo indexy druhých políčok na ktoré dovidí turretka.
 * Taktiež si drží parametre, či sa dá rozbiť, stavať na nej, či je to cesta, spawning point pre mobky alebo finish point pre mobky.
 * Zabezpečuje zobrazenie hry na plátne cez classu MapContent
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class MapSquare {
    
    private MapContent content;
    
    private TypeSquareDimension squareDimension;
    private TypeSquareIndex squareIndex;
    
    private MonsterBuilder monsterBuilder;
    private DefenseType defenseType;
    
    private boolean isBuildable;
    private boolean isAPath;
    private boolean isBreakable;
    private boolean isSpawningPoint;
    private boolean isFinishPoint;
    
    private ArrayList <MonsterBuilder> monsterArrayList;
    private ArrayList <TypeSquareIndex> indexOfSquaresInRange;

    /*
     * Konštruktor pre jedno políčko.
     * Drží si hodnotu, kde sa práve nachádza na poli, svôj index v dvojrozmernom poli, či sa dá na ňom stavať a či je to cesta.
     * Defaultne si obrázok nastavuje na placeholder, ktorý sa neskôr mení na potrebný typ.
     * 
     */
    public MapSquare(int x, int y, boolean isBuildable, boolean isAPath, int indexX, int indexY) {
        this.squareDimension = new TypeSquareDimension(x, y);
        this.squareIndex = new TypeSquareIndex(indexX, indexY);
        this.isBuildable = isBuildable;
        this.isAPath = isAPath;
        this.isBreakable = false;
        this.content = new MapContent(MapContentType.PLACEHOLDER, x, y);
        this.isSpawningPoint = false;
        this.isFinishPoint = false;
       
        this.defenseType = null;
        
        this.indexOfSquaresInRange = null;
        this.monsterArrayList = null;
    }   
    /*
     * Vracia polohu X na plátne.
     */
    public int getX() {
        return this.squareDimension.getX();
    }
    /*
     * Vracia polohu Y na plátne.
     */
    public int getY() {
        return this.squareDimension.getY();
    }
    /*
     * Vracia svôj index v hracom poli.
     */
    public TypeSquareIndex getSquareIndex() {
        return this.squareIndex;
    }
    /*
     * Vracia index X v hracom poli.
     */
    public int getTypeSquareIndexX() {
        return this.squareIndex.returnIndexX();
    }
    /*
     * Vracia index Y v hracom poli.
     */
    public int getTypeSquareIndexY() {
        return this.squareIndex.returnIndexY();
    }
    /*
     * Vracia dĺžku ArrayListu indexov políčok, na ktoré dovidí turretka.
     */
    public int getSizeOfSquaresInRange() {
        return this.indexOfSquaresInRange.size();
    }
    /*
     * Vracia Xový index políčka z ArrayListu.
     */
    public int getSquareX(int x) {
        return this.indexOfSquaresInRange.get(x).returnIndexX();
    }
    /*
     * Vracia Yový index polížka z ArrayListu.
     */
    public int getSquareY(int x) {
        return this.indexOfSquaresInRange.get(x).returnIndexY();
    }
    /*
     * Checkuje, či je spawnpoint.
     */
    public boolean getIsSpawnpoint() {
        return this.isSpawningPoint;
    }
    /*
     * Setuje spawnpoint.
     */
    public void setSpawningPoint (boolean spawnPoint) {
        this.isSpawningPoint = spawnPoint;
    }
    /*
     * Setuje finishpoint.
     */
    public void setFinishPoint (boolean finishPoint) {
        this.isFinishPoint = finishPoint;
    }
    /*
     * Checkuje finishpoint.
     */
    public boolean getFinishPoint () {
        return this.isFinishPoint;
    }
    /*
     * Zobrazuje všetky mobky z ArrayListu na danom políčku v plátne.
     * Používa Random().nextInt pre náhodné rozloženie vo vnútri políčka.
     */
    public void showMobs() {
        if (this.monsterArrayList == null) {
            return;
        }
        for (int i = 0; i <= this.monsterArrayList.size() - 1; i++) { 
            int xRand = new Random().nextInt(30);
            int yRand = new Random().nextInt(30);
            this.content.setMapContent(this.monsterArrayList.get(i).getMobPicture(), this.getX() - 20 + xRand, this.getY() - 20 + yRand);
        }
    }
    /*
     * Zobrazí moba na poslednom indexe ArrayListu.
     */
    public void showMob() {
        int xRand = new Random().nextInt(30);
        int yRand = new Random().nextInt(30);
        this.content.setMapContent(this.monsterArrayList.get(this.getSizeOfMobArrayList() - 1).getMobPicture(), this.getX() - 20 + xRand, this.getY() - 20 + yRand);
    }
    /*
     * Skryje mobky.
     */
    public void hideMobs() {
        this.content.hideMobs();        
    }
    /*
     * Skryje mobku.
     */
    public void hideMob() {
        this.content.hideMob();
    }
    /*
     * Checkuje, či je políčko cesta.
     */
    public boolean getIsAPath() {
        return this.isAPath;
    }
    /*
     * Settuje, že je políčko cesta.
     */
    
    public void setIsAPath(boolean isAPath) {
        this.isAPath = isAPath;
    }
    /*
     * Checkuje, či sa dá stavať na políčku.
     */
    
    public boolean getIsBuildable() {
        return this.isBuildable;
    }
    /*
     * Settuje povolenie stavania na políčku.
     */
    
    public void setIsBuildable(boolean isBuildable) {
        this.isBuildable = isBuildable;
    }
    /*
     * Checkuje, či sa dá content políčka rozbiť.
     */
    
    public boolean getIsBreakable() {
        return this.isBreakable;
    }
    /*
     * Settuje povolenie na rozbitie políčka.
     */
    public void setIsBreakable(boolean isBreakable) {
        this.isBreakable = isBreakable;
    }
    /*
     * Stringový vstup sa sparsuje v enumerácií mapContentu, potom sa nájde presný file, ktorý sa zobrazí na plátne ako obrázok.
     * Zároveň sa menia povolenia na danom políčku podľa enumerácie.
     */
    public void setMapContent(String mapContentType) {
        this.content.changeMapContent(this.content.getMapContentType(mapContentType));
        this.setIsAPath(this.content.getIsAPath());
        this.setIsBuildable(this.content.getIsBuildable());
        this.setIsBreakable(this.content.getIsBreakable());        
    }
    /*
     * To isté, len teraz z charu, aby sa dokázala mapka načítať z textového súboru pomocou scannera.
     */
    public void setMapContentFromChar(char mapContentType) {
        this.content.changeMapContent(this.content.getMapContentTypeChar(mapContentType));
        this.setIsAPath(this.content.getIsAPath());
        this.setIsBuildable(this.content.getIsBuildable());
        this.setIsBreakable(this.content.getIsBreakable());
        if (!isAPath && mapContentType != 'w') {
            this.indexOfSquaresInRange = new ArrayList();
        }
        if (isAPath) {
            this.monsterArrayList = new ArrayList();
        }
        if (mapContentType == 'F') {
            this.isFinishPoint = true;
        }
        if (mapContentType == 'S') {
            this.isSpawningPoint = true;
        }
        
    }
    /*
     * Pridáva mobku do ArrayListu mobiek.
     */
    public void addMobToMonsterArrayList(MonsterBuilder monsterBuilderer) {
        this.monsterArrayList.add(monsterBuilderer);
    }
    /*
     * Vracia mobku z ArrayListu.
     */    
    public MonsterBuilder getMobFromArrayList(int index) {        
        return this.monsterArrayList.get(index);       
    }
    /*
     * Vracia veľkosť ArrayListu mobiek.
     */
    public int getSizeOfMobArrayList() {
        if (this.monsterArrayList != null) {
            return this.monsterArrayList.size();
        } else {
            return 0;
        }
    }
    /*
     * Vymaže na danom indexe mobku z ArrayListu.
     */
    public void removeMobFromArrayList(int index) {
        this.monsterArrayList.remove(index);
    }
    /*
     * Vyčistí kompletne celý ArrayList mobiek.
     */
    public void removeAllMobsFromArrayList() {
        this.monsterArrayList.clear();
        this.hideMobs();
    }    
    /*
     * Vracia true, ak nie je vygenerovaný ArrayList mobiek.
     * Inak povedané, nie je cesta.
     */
    public boolean returnIfArrayNull() {
        return this.monsterArrayList == null;
    }
    /*
     * Pridáva index iného políčka do ArrayListu políčok v dohľade turretky.
     */
    public void addSquareToSquaresInRange(TypeSquareIndex squareIndex) {
        indexOfSquaresInRange.add(squareIndex);
    }
    /*
     * Vyčistí ArrayList políčok v dohľade turretky.
     */
    
    public void nullSquaresInRange() {
        this.indexOfSquaresInRange.clear();
    }
    /*
     * Ak sa dá stavať, položí turretku.
     */
    public void placeTurret(String turretType) {
        if (isBuildable) {
            this.defenseType = DefenseType.getType(turretType);
            this.setMapContent(turretType);            
        }
    }
    /*
     * Vracia range turretky.
     */
    public int getRange() {
        return this.defenseType.getTypeBuildingRange();
    }
    /*
     * Rozbíja turretku, alebo kameň.
     */
    
    public void breakTurret() {
        if (isBreakable) {
            this.defenseType = null;
            this.setMapContent("EMPTY");
        }
    }
    /*
     * Spawne daný typ mobky, taktiež mení jej obtiažnosť.
     */
    
    public void spawnMob(String monsterType, int difficulty) {
        MonsterBuilder mob = new MonsterBuilder(monsterType, difficulty);
        this.addMobToMonsterArrayList(mob);
        
    }
    
    /*
     * Prejde celým ArrayListom mobiek a dá im damage.
     * Ak mobka umrie, vymaže sa z ArrayListu a hráčovi sa pripočítajú peniaze.
     */
    public int attackMobs(int damage) {
        int moneyGained = 0;              
        for (int x = this.monsterArrayList.size() - 1; x >= 0; x--) {
            this.monsterArrayList.get(x).damageMonster(damage);
            if (this.monsterArrayList.get(x).getMonsterHealth() <= 0) {
                moneyGained += this.addMoney(this.monsterArrayList.get(x));
                this.monsterArrayList.remove(x);                               
            }     
        }
        return moneyGained;
        
    }
    /*
     * Vracia damage turretky.
     */
    public int getDamage() {
        return this.defenseType.getTypeBuildingStrength();
    }
    /*
     * Vracia enumeračný typ turretky.
     */
    public DefenseType getType() {
        return this.defenseType;
    }
    /*
     * Checkuje, či je enumeračný typ turretky nastavený na null.
     */
    public boolean getDefenseType() {
        return this.defenseType == null;
    }
    /*
     * Vracia peniaze z enumerácie mobky.
     */
    private int addMoney(MonsterBuilder mob) {
        return mob.getMonsterOnKillMoney();        
    }

}