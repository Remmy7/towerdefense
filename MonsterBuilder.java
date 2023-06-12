/*
 * Konštruktor pre mobky.
 * Drží v sebe všetky potrebné premenné na prácu s budúcimi metódami.
 * Vždy vytvára novú inštanciu.
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */

public class MonsterBuilder {
    
    private MonsterType monsterType;
    private int monsterDifficulty;
    private int monsterHealth;
    private int monsterDefense;
    private int monsterSpeed;
    private boolean monsterFlyingAttribute;
    private int monsterKillMoney;
    private String monsterPicture;
    
    /*
     * Konštruktor pre mobky. 
     * Drží si všetky hodnoty z enumu, ktoré si prebral getterami.
     */
    public MonsterBuilder(String monsterType, int monsterDifficulty) {
        this.monsterType = this.monsterType.returnMonsterType(monsterType);
        this.monsterDifficulty = monsterDifficulty;
        this.monsterHealth = this.monsterType.getMonsterHealth();
        this.monsterDefense = this.monsterType.getMonsterDefense();
        this.monsterKillMoney = this.monsterType.getMonsterOnKillMoney();
        this.monsterFlyingAttribute = this.monsterType.getMonsterFlying();
        this.monsterSpeed = this.monsterType.getMonsterSpeed();
        this.monsterPicture = this.monsterType.getMonsterPicture();       
        this.multiplyStats();
    }
    /*
     * Vracia Stringovú hodnotu pre nájdenie obrázku mobky v priečinkoch.
     */
    public String getMobPicture() {
        return this.monsterPicture;
    }
    /*
     * Násobí staty podľa obtiažnosti mobky.
     */
    public void multiplyStats() {
        if (monsterDifficulty > 0) {            
            this.monsterHealth *= this.monsterDifficulty;
            this.monsterDefense *= this.monsterDifficulty;
            this.monsterKillMoney *= this.monsterDifficulty * 2;
        } else {
            this.monsterDifficulty = 1;
        }
    }
    
    /*
     * Vracia enumový typ mobky zo stringu.
     */
    public MonsterType returnMonsterType(String monsterType) {
        return MonsterType.returnMonsterType(monsterType);
    }

    /*
     * Vracia život mobky.
     */
    public int getMonsterHealth() {
        return this.monsterHealth;
    } 
    /*
     * Damaguje mobku, ak má mobka viac defense ako by bol damage, nič sa nedeje.
     */
    public void damageMonster(int damage) {
        if (damage > this.monsterDefense) {
            this.monsterHealth -= (damage - this.monsterDefense);
        } 
    }
    /*
     * Vracia defense mobky.
     */
    public int getMonsterDefense() {
        return this.monsterDefense;
    }
    /*
     * Vracia rýchlosť mobky.
     */
    public int getMonsterSpeed() {
        return this.monsterSpeed;
    }
    /*
     * Vracia boolean, či dokáže mobka lietať alebo nie.
     */
    public boolean getMonsterFlying() {
        return this.monsterFlyingAttribute;
    }
    /*
     * Vracia množstvo peňazí za kill.
     */
    public int getMonsterOnKillMoney() {
        return this.monsterKillMoney;
    }
}
