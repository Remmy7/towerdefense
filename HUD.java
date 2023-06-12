/*
 * Vytvára plátno pre GameManazera a spracuváva ho
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class HUD {

    private Obrazok vizualization;
    private Manazer manager;
    private Platno canvas;

    /*
     * Vytvára plátno pre GameManazera a spracuváva ho
     */
    public HUD() {
        this.canvas = Platno.dajPlatno();
        
    }
    /*
     * Switch, čo vracia font
     */
    public void setFont(int font) {
        switch(font) {
            case 1:
                this.canvas.setFont();
                break;
            case 2:
                this.canvas.setFont2(); 
                break;
            case 3:
                this.canvas.setFont3();
                break;
        }
    }
    /*
     * Napíše String na plátno.
     */
    public void drawString(String text, int x, int y) {
        this.canvas.drawString(text, x, y); 
    }

}