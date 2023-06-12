/*
 * Enumerácia turretiek.
 * Políčko si z tejto enumerácie preberá parametre.
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */

public enum DefenseType {
    CARROTMAN("CARROTMAN", 4, 15, "GROUND", 1, 20),
    BLUEJMAN("BLUEJMAN", 6, 10, "AIR", 1, 20)
;
    
    private final String typeBuildingType;
    private final int typeBuildingRange;
    private final int typeBuildingStrength;
    private final String typeBuildingTargetting;
    private final int typeBuildingAttackSpeed;
    private final int typeBuildingCost;
    
    

    /*
     * Konštruktor enumerácie assignujúci všetky parametre.
     */
    private DefenseType(String buildingType, int buildingRange, int buildingStrength, String buildingTargetting, int buildingAttackSpeed, int buildingCost) {
        this.typeBuildingType = buildingType;
        this.typeBuildingRange = buildingRange;
        this.typeBuildingStrength = buildingStrength;
        this.typeBuildingTargetting = buildingTargetting;
        this.typeBuildingAttackSpeed = buildingAttackSpeed;
        this.typeBuildingCost = buildingCost;
        
    }
    /*
     * Vracia typ enumerácie.
     */
    public DefenseType getDefenseType() {
        return this;
    }
    /*
     * Vracia Stringový typ enumerácue.
     */
    public String getTypeBuildingType() {
        return this.typeBuildingType;
    }
    /*
     * Vracia range turretky.
     */
    public int getTypeBuildingRange() {
        return this.typeBuildingRange;
    }
    /*
     * Vracia silu turretky.
     */
    public int getTypeBuildingStrength() {
        return this.typeBuildingStrength;
    }
    /*
     * Vracia targetting turretky.
     */
    public String getTypeBuildingTargetting() {
        return this.typeBuildingTargetting;
    }
    /*
     * Vracia rýchlosť útoku turretky.
     */
    public int getTypeBuildingAttackSpeed() {
        return this.typeBuildingAttackSpeed;
    }
    /*
     * Vracia cenu turretky.
     */
    public int getTypeBuildingCost() {
        return this.typeBuildingCost;
    }
    /*
     * Parsuje Stringový vstup na výstup enumerácie.
     */
    public static DefenseType getType(String type) {
        switch (type) {
            case "CARROTMAN":
                return DefenseType.CARROTMAN;
            case "BLUEJMAN":
                return DefenseType.BLUEJMAN;
            default :
                return null;
        }
    }
    
    

}