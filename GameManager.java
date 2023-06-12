import java.io.FileNotFoundException;


/*
 * 
 * Manažér hry.
 * Zabezpečuje plynulý chod hry, klikanie na mapu, ukazuje hráčovi život, kolo a počet peňazí.
 * Je spravovaný classou Manažér, využíva jeho tik, klávesnicu a myš.
 * 
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class GameManager {

    private int roundCounter;
    private int moneyCounter;
    private int healthCounter;
    private PlayableMap gameMap;    
    private int gameSpeedMultiplier;
    private HUD hud;
    private boolean playing;
    private Manazer gameManager; 
    private int tickTimer;
    private boolean endless;
     
    
    public static final int PRICE_TURRET = 30;
    public static final int PRICE_TURRET_2 = 40;
   
    
    
    /*
     * Konštruktor, vytvára mapku, pridáva manažéra, štartuje hru.
     */
    public GameManager() throws FileNotFoundException  {
        this.roundCounter = 1;
        this.moneyCounter = 100;
        this.healthCounter = 20;
        this.gameSpeedMultiplier = 1;
        this.tickTimer = 0;
        this.gameMap = PlayableMap.createMapFromTextFile(PlayableMap.chooseMapType(2));
        this.gameManager = new Manazer();
        this.hud = new HUD();
        this.gameMap.calculatePath();
        
        this.hud.setFont(1);
        this.start();
    }
    /*
     * Updatuje staty hráča vo formáte String na plátne.
     */
    private void drawCounterChanges() {
        this.hud.setFont(1);
        this.hud.drawString("Health:", 950, 150);
        this.hud.drawString("Money:", 950, 220);
        this.hud.drawString("Round:", 950, 290);
        this.hud.drawString("SKIP ROUND?", 950, 400);
        
        this.hud.setFont(2);
        this.hud.drawString(String.valueOf(this.healthCounter), 1120, 150);
        this.hud.drawString(String.valueOf(this.moneyCounter), 1120, 220);
        this.hud.drawString(String.valueOf(this.roundCounter), 1120, 290);
    }
    
    
    /*
     * Položí turretku.
     */
    public void placeTurret(int x, int y) {
        this.tickTimer = 0;
        if (this.moneyCounter > this.PRICE_TURRET) {
            if (this.gameMap.getIsBuildable(x, y)) {
                this.moneyCounter -= this.PRICE_TURRET;
                this.gameMap.spawnTurret("CARROTMAN", x, y);
                
            }
        }            
    }
    /*
     * Spawne mobku.
     */
    public void spawnMob(String mobType, int difficulty) {
        this.gameMap.spawnMob(mobType, difficulty);
    }
    /*
     * Vráti switch, ktorý spawne mobov podľa momentálneho kola.
     */
    public void roundSpawner() {
        this.roundMobSpawn();
    }
    /*
     * Vráti counter na peniaze.
     */
    public int getMoney() {
        return this.moneyCounter;
    }

    /*
     * Odštartuje hru, pridá túto inštanciu do spravovaných objektov manažéra.
     * 
     */
    public void start() {
        if (playing) {
            System.out.println("already playing");
            return;
        } else {
            this.playing = true;
            this.gameSpeedMultiplier = gameSpeedMultiplier;
            this.gameManager.spravujObjekt(this);
            this.roundMobSpawn();
            
            
        }
    }
    /*
     * Skipne kolo na ďalšie, pridá hráčovi peniaze podľa množstva skipnutých sekúnd.
     */
    public void skipTurn() {
        this.moneyCounter += Math.abs((this.tickTimer - 600 / 10));
        this.nextRound();
    }
    /*
     * Ak hráč klikne na "skip turn?" na mape, spustí sa táto metóda, ktorá skipne kolo.
     */
    public void mapClick(int indexX, int indexY) {
        if (indexX >= 954 && indexX <= 1200 && indexY >= 349 && indexY <= 401) {
            this.skipTurn();
        }
    }
    /*
     * Každých 0.1 sekundy pridá +1 na ticktimer.
     * Ticktimer podľa modula spúšťa rôzne metódy, ako posun mobov, updatnutie stringov ukazujúcich staty,
     * spawnutie mobov, ďalšie kolo atď.
     * Taktiež má za úlohu kontrolovať, či hráčov život nepadol na 0, v tom prípade volá koniec hry
     */
    public void tick() {
        this.tickTimer += 1;   
        if (endless) {
            this.spawnMob("MOB", 150);
        }
        this.drawCounterChanges();
        if (this.tickTimer % 15 == 0) {
            this.moneyCounter += 1;
            this.moneyCounter += this.gameMap.damageAllSquares();
            this.moveMobs();              
            this.drawCounterChanges();
            if (this.gameMap.getSizeOfLastArrayList() != 0) {
                this.healthCounter -= this.gameMap.getSizeOfLastArrayList();
                this.gameMap.removeLastArrayList();
                //this.drawCounterChanges();
                if (this.healthCounter <= 0) {
                    this.end();
                }
            }
            
        }        
        if (this.tickTimer % 30 == 0) {
            this.spawnMob("MOB", 2); 
            this.drawCounterChanges();            
        }
        
        if (this.tickTimer == 600) {
            this.tickTimer = 0;
            this.nextRound();
        }
    }
    /*
     * Hýbe mobkami z predošlého políčka na ďalšie.
     */
    public void moveMobs() {
        for (int indexX = this.gameMap.getSizePathX() - 1; indexX >= 1; indexX--) {            
            this.gameMap.moveMobs(this.gameMap.getPathX(indexX - 1), this.gameMap.getPathY(indexX - 1),
                this.gameMap.getPathX(indexX), this.gameMap.getPathY(indexX));            
        }
        this.drawCounterChanges();
    }
    public boolean getIsPlaying() {
        return this.playing;
    }
    
    /*
     * Checkne či je na square turreta, kameň alebo nič, ničí turretu a vymaže jej index, inak zničí kameň a redukuje peniaze 
     */
    
    public void breakTurret(int x, int y) {
        int squareX = (x - 35) / 70;
        int squareY = (y - 35) / 70;
        if (this.gameMap.getIsBreakable(squareX, squareY) && this.moneyCounter >= 10 && !this.gameMap.isDefenseTypeNull(squareX, squareY)) {
            this.gameMap.breakTurret(squareX, squareY);
            this.moneyCounter -= 10;
            this.gameMap.removeIndexOfTurret(squareX, squareY);
        } else if (this.gameMap.getIsBreakable(squareX, squareY) && this.moneyCounter >= 10) {
            this.gameMap.breakTurret(squareX, squareY);
            this.moneyCounter -= 10;
        }
    }
    /*
     * Ukončuje hru,
     */
    public void end() {
        if (!playing) {
            System.out.println("not yet playing");
            return;
        } else {
            this.playing = false;
            this.gameManager.prestanSpravovatObjekt(this);
            this.hud.setFont(3);
            this.hud.drawString("You lost!", 0, 500);
        }
    }
    /*
     * Skočí na ďalšie kolo, spawne mobov.
     */
    private void nextRound() {
        this.roundCounter += 1;
        this.roundMobSpawn();
    }
    /*
     * Vráti multiplier na rýchlost toku hry.
     */
    public int getGameSpeedMultiplier() {
        return this.gameSpeedMultiplier;
    }
    /*
     * Pridá peniaze hráčovi.
     */
    private void addMoney(int money) {
        this.moneyCounter += money;
    }

    /*
     * Setne rýchlost toku hry.
     * 
     */
    public void setGameSpeedMultiplier(int gameSpeedMultiplier) {
        this.gameSpeedMultiplier = gameSpeedMultiplier;
    }
    /*
     * Vráti momentálne kolo. 
     */
    public int getRoundCounter() {
        return this.roundCounter;
    }

    /* 
     * Setne momentálne kolo.
     */
    private void setRoundCounter(int roundCounter) {
        this.roundCounter = roundCounter;
    }
    /*
     * Skryje mobky na indexe 0, 0.
     */
    public void hideMobs() {
        this.gameMap.hideMobs(0, 0);
    }
    /*
     * Spawne mobky podľa kola.
     */
    public void roundMobSpawn() {
        switch (this.roundCounter) {
            case 1:
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 5);
                this.gameMap.showMobs(0, 0);
                break;
            case 2: 
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 5);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.gameMap.showMobs(0, 0);
                break;
            case 3: 
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.gameMap.showMobs(0, 0);
                break;
            case 4:
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.gameMap.showMobs(0, 0);
                break;
            case 5:
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 10);
                this.spawnMob("MOB", 15);
                this.spawnMob("MOB", 15);
                this.spawnMob("MOB", 15);
                this.gameMap.showMobs(0, 0);
                break;
            case 6:
                this.spawnMob("MOB", 15);
                this.spawnMob("MOB", 15);
                this.spawnMob("MOB", 15);
                this.spawnMob("MOB", 20);
                this.spawnMob("MOB", 20);
                this.spawnMob("MOB", 20);
                this.gameMap.showMobs(0, 0);
                break;
            case 7:
                this.spawnMob("MOB", 20);
                this.spawnMob("MOB", 20);
                this.spawnMob("MOB", 20);
                this.spawnMob("MOB", 25);
                this.spawnMob("MOB", 25);
                this.spawnMob("MOB", 25);
                this.gameMap.showMobs(0, 0);
                break;
            case 8:
                this.spawnMob("MOB", 25);
                this.spawnMob("MOB", 25);
                this.spawnMob("MOB", 25);
                this.spawnMob("MOB", 30);
                this.spawnMob("MOB", 30);
                this.spawnMob("MOB", 30);
                this.gameMap.showMobs(0, 0);
                break;
            case 9:
                this.spawnMob("MOB", 30);
                this.spawnMob("MOB", 30);
                this.spawnMob("MOB", 30);
                this.spawnMob("MOB", 35);
                this.spawnMob("MOB", 35);
                this.spawnMob("MOB", 35);
                this.gameMap.showMobs(0, 0);
                break;
            case 10:
                this.spawnMob("MOB", 35);
                this.spawnMob("MOB", 35);
                this.spawnMob("MOB", 35);
                this.spawnMob("MOB", 40);
                this.spawnMob("MOB", 40);
                this.spawnMob("MOB", 40);
                this.gameMap.showMobs(0, 0);
                break; 
            default:
                this.endless = true;
        }
    }

}