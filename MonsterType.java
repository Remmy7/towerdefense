/*
 * Enumerácia mobiek.
 * Konštruktor mobiek si pomocou getterov preberá staty mobky.
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public enum MonsterType { 
    MOB("MOB", 30, 1, 1, 3, false, "pictures/mob1.png")
    
;
    private String typeMonsterType;
    private int typeMonsterHealth;
    private int typeMonsterDefense;
    private int typeMonsterSpeed;
    private int typeMonsterOnKillMoney;
    private String monsterPicture;
    private boolean typeMonsterFlying;
    /*
     * Vytvára kostru mobky.
     */
    private MonsterType(String monsterType, int monsterHealth, int monsterDefense, int monsterSpeed, int monsterOnKillMoney, boolean monsterFlying, String monsterPicture) {
        this.typeMonsterType = monsterType;
        this.typeMonsterHealth = monsterHealth;
        this.typeMonsterDefense = monsterDefense;
        this.typeMonsterSpeed = monsterSpeed;
        this.typeMonsterOnKillMoney = monsterOnKillMoney;
        this.typeMonsterFlying = monsterFlying;
        this.monsterPicture = monsterPicture;
        
    }
    /*
     * Vracia život mobky.
     */
    public int getMonsterHealth() {
        return this.typeMonsterHealth;
    } 
    /*
     * Vracia defense mobky.
     */
    public int getMonsterDefense() {
        return this.typeMonsterDefense;
    } 
    /*
     * Vracia rýchlosť mobky.
     */
    public int getMonsterSpeed() {
        return this.typeMonsterSpeed;
    } 
    /*
     * Vracia množstvo peňazí za zabitie.
     */
    public int getMonsterOnKillMoney() {        
        return this.typeMonsterOnKillMoney;
    } 
    /*
     * Vracia boolean, či dokáže mobka lietať.
     */
    public boolean getMonsterFlying() {
        return this.typeMonsterFlying;
    }
    /*
     * Vráti Stringovú hodnotu, ktorá je cestou ku zložke s obrázkom.
     */
    public String getMonsterPicture() {
        return this.monsterPicture;
    }
    
    
    
    /*
     * Vráti enumový typ monštra.
     */
    public MonsterType getMonsterType() {
        return this;
    }
    
    
    /*
     * Podľa Stringového vstupu vracia enumový typ mobky.
     */
    public static MonsterType returnMonsterType(String monsterType) {
        switch (monsterType) {
            case "MOB":
                return MonsterType.MOB;
            
            
            
            
            default:
                return null;
        }
    }
    

}