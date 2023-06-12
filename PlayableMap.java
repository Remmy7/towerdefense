import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/*
 * Dvojrozmerné pole políčok.
 * Drží si ArrayListi cesty, ktorú si vypočíta algoritmom.
 * Taktiež si drží ArrayList indexov turriet, ktoré sú momentálne položené.
 * 
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class PlayableMap {

    private MapSquare[][] mapGrid;
    private ArrayList <Integer> pathX;
    private ArrayList <Integer> pathY;
    private ArrayList <Integer> turretIndexX;
    private ArrayList <Integer> turretIndexY;
    
    private int lastIndexX;
    private int lastIndexY;
    private int firstIndexX;
    private int firstIndexY;

    private boolean isPlayed = false;

    /*
     * Konštruktor mapy.
     * Vytvára základ ArrayListov pre dané indexy.
     * Vytvára políčka na plátne 70pixelov od seba
     */
    public PlayableMap(int length, int height) throws FileNotFoundException {
        this.pathX = new ArrayList();
        this.pathY = new ArrayList();
        this.turretIndexX = new ArrayList();
        this.turretIndexY = new ArrayList();
        this.lastIndexX = 0;
        this.lastIndexY = 0;
        
        if (isPlayed) {
            System.out.println("already playing");
            return;
        } else {
            this.mapGrid = new MapSquare[length][height];
            for (int i = 0; i < mapGrid.length; i++) {
                for (int j = 0; j < mapGrid[i].length; j++) {
                    mapGrid[i][j] = new MapSquare(i * 70 + 70, j * 70 + 70, false, true, i, j);                                        
                }
            }
            this.isPlayed = true;
        }
    }
    
    /*
     * Najdôležitejšia časť tejto classy.
     * Pomocou scannera číta textový dokument, ktorý potom sparsuje enumeráciami a vytvorí mapku.
     */
    public static PlayableMap createMapFromTextFile(String textFile) throws FileNotFoundException {
        File map = new File(textFile);
        Scanner scanner = new Scanner(map);
        
        int length = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine();
        
        PlayableMap mapGridFromFile = new PlayableMap(length, height);
        
        for (int lengthIndex = 0; lengthIndex < height; lengthIndex++) {
            String line = scanner.nextLine();
            for (int heightIndex = 0; heightIndex < length; heightIndex++) {
                char letter = line.charAt(heightIndex);                
                mapGridFromFile.mapGrid[heightIndex][lengthIndex].setMapContentFromChar(letter);                
            }
        }
        
        
        scanner.close();
        return mapGridFromFile;
    }
    /*
     * Algoritmus na vypočítanie cesty.
     */
    public void calculatePath() {
        int previousIndexX = 0;
        int previousIndexY = 0;
        for (int x = 0; x < this.mapGrid.length; x++) {
            for (int y = 0; y < this.mapGrid[x].length; y++) {
                if (mapGrid[x][y].getIsSpawnpoint()) { // ak je cesta spawnpoint, nastaví si začiatočný index mapy na index spawnpointu.
                    this.firstIndexX = x;
                    this.firstIndexY = y;
                    previousIndexX = x;
                    previousIndexY = y;
                    this.pathX.add(x);
                    this.pathY.add(y);
                } else if (mapGrid[x][y].getFinishPoint()) { // ak je cesta finishpoint, ...
                    this.lastIndexX = x;
                    this.lastIndexY = y;
                }
            }
        }
        
        int indexX = previousIndexX; // predošlé indexy X a Y v poli
        int indexY = previousIndexY;
        
        
        
        
        while (indexX < this.lastIndexX + 1 && indexY < this.lastIndexY + 1) { // pokiaľ sa momentálny index nerovná poslednému indexu
            if (indexX == this.lastIndexX && indexY == this.lastIndexY) {
                return;
            }
            if (indexX - 1 >= 0 && indexX - 1 != previousIndexX && this.mapGrid[indexX - 1][indexY].getIsAPath()) { // posunie sa o 1 vľavo
                this.pathX.add(indexX - 1);
                this.pathY.add(indexY);
                previousIndexY = indexY;
                previousIndexX = indexX;                
                indexX -= 1;
                
            } else if (indexY - 1 >= 0 && indexY - 1 != previousIndexY && this.mapGrid[indexX][indexY - 1].getIsAPath()) { // posunie sa o 1 hore
                this.pathX.add(indexX);
                this.pathY.add(indexY - 1);
                previousIndexY = indexY;
                previousIndexX = indexX;
                indexY -= 1;
            } else if (indexX + 1 <= this.mapGrid.length - 1 && indexX + 1 != previousIndexX && this.mapGrid[indexX + 1][indexY].getIsAPath()) { // posunie sa o 1 vpravo                this.pathX.add(indexX + 1);
                this.pathX.add(indexX + 1);
                this.pathY.add(indexY);
                previousIndexY = indexY;
                previousIndexX = indexX;
                
                indexX += 1;
            } else if (indexY + 1 <= this.mapGrid[indexX].length - 1 && indexY + 1 != previousIndexY && this.mapGrid[indexX][indexY + 1].getIsAPath()) { // posunie sa o 1 dole
                this.pathX.add(indexX);
                this.pathY.add(indexY + 1);
                previousIndexY = indexY;
                previousIndexX = indexX;                
                indexY += 1;
            }           
        }        
    }
    /*
     * Vracia začiatočný index X.
     */
    public int getFirstIndexX() {
        return this.firstIndexX;
    }
    /*
     * Vracia začiatočný index Y.
     */
    public int getFirstIndexY() {
        return this.firstIndexY;
    }
    /*
     * Vracia posledný index X.
     */
    public int getLastIndexX() {
        return this.lastIndexX;
    }
    /*
     * Vracia posledný index Y.
     */
    public int getLastIndexY() {
        return this.lastIndexY;
    }
    /*
     * Zobrazuje mobky na danom políčku.
     */
    public void showMobs(int x, int y) {
        this.mapGrid[x][y].showMobs();
    }
    /*
     * Skrýva mobky na danom políčku.
     */
    public void hideMobs(int x, int y) {
        this.mapGrid[x][y].hideMobs();
    }
    /*
     * Vracia index X na ceste.
     */
    public int getPathX(int index) {
        return this.pathX.get(index);
    }
    /*
     * Vracia index Y na ceste.
     */
    public int getPathY(int index) {
        return this.pathY.get(index);
    }
    /*
     * Vracia dĺžku ArrayListu trasy X.
     */
    public int getSizePathX() {
        return this.pathX.size();
    }
    /*
     * Vracia dĺžku ArrayListu trasy Y.
     */
    public int getSizePathY() {
        return this.pathY.size();
    }
    /*
     * Vracia veľkosť ArrayListu mobiek v danom políčku.
     */
    public int getSizeOfLastArrayList() {
        return this.mapGrid[this.lastIndexX][this.lastIndexY].getSizeOfMobArrayList();
    }
    /*
     * Maže všetky mobky z posledného ArrayListu.
     */
    public void removeLastArrayList() {
        this.mapGrid[this.lastIndexX][this.lastIndexY].removeAllMobsFromArrayList();
    }
    /*
     * Vráti mapku.
     */
    public PlayableMap returnMapGrid() {
        return this;
    }
    /*
     * Vypočíta pomocou algoritmu indexy ciest, na ktoré dovidí turretka, a uloží ich do ArrayListu.
     * Slúži pre optimalizovanie behu hry, aby sa neloopovala turretka cez miesta, ktoré ani nie sú cesta.
     */
    public void calculateSquaresInRange(int indexX, int indexY) { 
       
        int attackRangeRight = this.mapGrid[indexX][indexY].getRange();
        int attackRangeDown = this.mapGrid[indexX][indexY].getRange();
        int indexUpperLeftCornerX = (indexX - (attackRangeRight / 2));
        int indexUpperLeftCornerY = (indexY - (attackRangeRight / 2));
        
        this.mapGrid[indexX][indexY].nullSquaresInRange();
      
        if (indexUpperLeftCornerX < 0) { // ak je out of bounds, posunie sa na 0 a pripočíta attackRange na pravú stranu.
            attackRangeRight += indexUpperLeftCornerX;
            indexUpperLeftCornerX = 0;
        }
        
        if (indexUpperLeftCornerY < 0) { // ak je out of bounds, posunie sa na 0 a pripočíta attackRange na spodnú stranu.
            attackRangeDown += indexUpperLeftCornerY;
            indexUpperLeftCornerY = 0;
        }
        for (int x = attackRangeRight; x >= 0; x--) {
            if (x + indexUpperLeftCornerX > this.mapGrid.length || x + indexUpperLeftCornerX < 0) {
                this.doNothing(); //ak je ajtak nejakým zázrakom out of bounds
            } else {
                for (int y = attackRangeDown; y >= 0; y--) {
                    if (y + indexUpperLeftCornerY > this.mapGrid[x].length || y + indexUpperLeftCornerY < 0) {
                        this.doNothing();  // ak je znova out of bounds, ale na Y osi              
                    } else if (this.mapGrid[indexUpperLeftCornerX + x][indexUpperLeftCornerY + y].getIsAPath()) {
                        this.mapGrid[indexX][indexY].addSquareToSquaresInRange(this.mapGrid[indexUpperLeftCornerX + x][indexUpperLeftCornerY + y].getSquareIndex());
                    } // pridá index políška do ArrayListu.
                }   
            }
        }
    }
    /*
     * Pomocná metóda ktorá neurobí nič.
     */
    private void doNothing() {
        
    }
    /*
     * Spawne turretku s daným menom na danom políčku.
     * Pridá jej index do indexu turriet, a vypočíta mu políčka, na ktoré dovidí
     */
    public void spawnTurret(String turretName, int indexX, int indexY) {        
        this.mapGrid[indexX][indexY].placeTurret(turretName);
        this.calculateSquaresInRange(indexX, indexY);
        this.turretIndexX.add(indexX);
        this.turretIndexY.add(indexY);                 
    }
    /*
     * Vráti boolean či sa dá stavať na danom políčku.
     */
    public boolean getIsBuildable(int x, int y) {
        return this.mapGrid[x][y].getIsBuildable();
    }
    /*
     * Vráti boolean či sa dá rozbiť obsah daného políčka.
     */
    public boolean getIsBreakable(int x, int y) {
        return this.mapGrid[x][y].getIsBreakable();
    }
    /*
     * Vráti sa damage turretky daného políčka.
     */
    public int getDamage(int x, int y) {
        return this.mapGrid[x][y].getDamage();
    }
    /*
     * Vráti true, ak sa turretka nachádza na políčku
     */
    public boolean isDefenseTypeNull(int x, int y) {
        return this.mapGrid[x][y].getDefenseType();
    }
    /*
     * Rozbije turretku/kameň, nastaví ho na building space
     */
    public void breakTurret(int indexX, int indexY) {
        this.mapGrid[indexX][indexY].setMapContent("EMPTY");                    
    }
    /*
     * Vyhodí index turretky z ArrayListov pre indexy
     */
    public void removeIndexOfTurret(int x, int y) {
        for (int indexX = this.turretIndexX.size() - 1; indexX >= 0; indexX--) {
            if (this.turretIndexX.get(indexX) == x && this.turretIndexY.get(indexX) == y) {
                this.turretIndexX.remove(indexX);                                
                this.turretIndexY.remove(indexX);
                return;
            }           
        }
    }    
    /*
     * Vráti index turretky X
     */
    public int getTurretIndexX(int index) {
        return this.turretIndexX.get(index);
    }
    /*
     * Vráti index turretky Y
     */
    public int getTurretIndexY(int index) {
        return this.turretIndexY.get(index);
    }
    /*
     * Zavolá si každu jednu turretku, a každej dá príkaz nech dá damage všetkým svojim mobkám v dosahu
     */
    public int damageAllSquares() {
        int moneyGained = 0;
        for (int indexX = this.turretIndexX.size() - 1; indexX >= 0; indexX--) {
            for (int indexY = this.turretIndexY.size() - 1; indexY >= 0; indexY--) {   
                int int1 = this.turretIndexX.get(indexX);
                int int2 = this.turretIndexY.get(indexY);
                int damage = this.mapGrid[int1][int2].getDamage();
                for (int x = this.mapGrid[int1][int2].getSizeOfSquaresInRange() - 1; x >= 0; x--) {
                    int dmgIndex1 = this.mapGrid[int1][int2].getSquareX(x);
                    int dmgIndex2 = this.mapGrid[int1][int2].getSquareY(x);
                    moneyGained += this.mapGrid[dmgIndex1][dmgIndex2].attackMobs(damage);
                }
            }
        }
        return moneyGained;
    }
    /*
     * Vráti mapku.
     */
    public static String chooseMapType(int mapID) {
        switch (mapID) {
            case 1:
                return "maps/map1.txt";
            case 2:
                return "maps/map2.txt";
            default:
                return "maps/map2.txt";
        }
        
    }
    /*
     * Spawne mobku na začiatočnom indexe.
     */    
    public void spawnMob(String monsterType, int difficulty) {
        this.mapGrid[this.firstIndexX][this.firstIndexY].spawnMob(monsterType, difficulty);
        this.mapGrid[0][0].showMob();
    }
    /*
     * Posunie všetky mobky o 1 políčko ďalej, počíta sa od posledného políčka cesty.
     * Využíva ArrayListy pathX a pathY na vybratie indexov danej cesty.
     */
    public void moveMobs(int indexX1, int indexY1, int indexX2, int indexY2) {
        for (int index = this.mapGrid[indexX1][indexY1].getSizeOfMobArrayList() - 1; index >= 0 ; index--) {            
            this.mapGrid[indexX2][indexY2].addMobToMonsterArrayList(this.mapGrid[indexX1][indexY1].getMobFromArrayList(index));            
        }
        for (int index2 = this.mapGrid[indexX1][indexY1].getSizeOfMobArrayList() - 1; index2 >= 0; index2--) {
            this.mapGrid[indexX1][indexY1].removeMobFromArrayList(index2);            
        }
        this.hideMobs(indexX1, indexY1);
        this.showMobs(indexX2, indexY2);
    }       
}