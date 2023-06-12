import java.io.FileNotFoundException;
import java.util.Scanner;
/*
 * 
 * Aplikácia hry.
 * Slúži ako main tejto towerdefense hry.
 * 
 * 
 * @autor Tibor Michalov
 * @version 0.1
 */
public class Application {
    
    private static GameManager game;
    /*
     * Vytvára main.
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner vstup = new Scanner(System.in);
        boolean continuePlaying = false; // ak bude chcieť hráč hrať ďalej
        
        
        do {
            Application.game = new GameManager(); // vytvorí hru
            while(game.getIsPlaying()){
                
            }
            
            Platno.odstranPlatno();
            
            Application.game = null; // vypne hru
            
            System.out.println("Chcete hrat znovu? Y/N");
            
            String reset;
        
            do {
                reset = vstup.nextLine(); // vstup užívateľa
                if (reset.equals("n") || reset.equals("N")) {
                    continuePlaying = false;
                } else if (reset.equals("y") || reset.equals("Y")) {
                    continuePlaying = true;
                }           
            } while (!(reset.equals("n") || reset.equals("N") || reset.equals("y") || reset.equals("Y")));
        } while (continuePlaying);
        vstup.close(); // koniec programu
    }
}
