import java.util.Scanner;

public class Game implements Statistics{
    Jugador localJugador1 = null;
    Jugador localJugador2 = null;
    Jugador Jugador1 = null;
    Jugador Jugador2 = null;
    Arbitro arbitro = null;

    public int puntuacionTotal = 0;
    public int turnosJugados = 0;
    public int juegosJugados = 0;
    public int juegosGanados = 0;

    private Scanner sc = new Scanner(System.in);

    private Board board = null;
    public boolean gameFinish = false;

    private int actualPlayer = 1;

    public Game(Jugador j1, Jugador j2, Arbitro ar){
        this.localJugador1 = new Jugador(j1);
        this.localJugador2 = new Jugador(j2);
        this.Jugador1 = j1;
        this.Jugador2 = j2;
        this.arbitro = ar;

        this.localJugador1.increaseJuegosJugados();
        this.localJugador2.increaseJuegosJugados();
        this.Jugador1.increaseJuegosJugados();
        this.Jugador2.increaseJuegosJugados();

        this.board = new Board();
    }

    public void startGame(){
        System.out.println(((gameFinish)?"The game already end.":"The game is about to start."));
        int selection = 5;

        while (!gameFinish) {
            
            if(this.board.wordsFound == 8){
                System.out.println("The game just ended. The winner is " + ((this.localJugador1.getPuntuacionTotal()>this.localJugador2.getPuntuacionTotal())?this.localJugador1.getNombre():this.localJugador2.getNombre()));
                this.increaseJuegosGanados();

                if(this.localJugador1.getPuntuacionTotal()>this.localJugador2.getPuntuacionTotal()){
                    
                    this.localJugador1.increaseJuegosGanados();
                    this.Jugador1.increaseJuegosGanados();

                }
                else{
                    
                    this.localJugador2.increaseJuegosGanados();
                    this.Jugador2.increaseJuegosGanados();

                }
                
                this.gameFinish = true;
                break;
            } 

            while((selection > 4 || selection < 0) || !gameFinish){
                this.showGame();
                selection = this.sc.nextInt();
    
                switch (selection) {
                    case 1:
                        this.selectStats();
                        break;
                
                    case 2:
                        this.turnAction(); 
                        break;
                
                    case 3:
                        System.out.println("Quiting the game...");
                        gameFinish = true;
                        break;
                
                    default:
                        System.out.println("Enter a valid input.");
                        break;
                }
            }
        }

        if (selection == 3) gameFinish = false;
    }

    public void showGame(){
        this.board.printMatrix();

        System.out.println("\nThe current turn is for the player " + ((actualPlayer == 1)?localJugador1.getNombre():localJugador2.getNombre()));
        System.out.println("\nChoose the action you want to do:\n\t1.Show stats\n\t2.Make a guess\n\t3.Quit the game.");
    }

    private void turnAction(){
        int[] coords = {0,0}; //index  0 = x and 1 = y
        String type = ""; 

        System.out.println("\n\n\n\n");
        this.board.printMatrix();
        System.out.println("Enter please your guess:");
        
        this.validateCoordinates(coords, 0);
        this.validateCoordinates(coords, 1);

        System.out.println("Introduce the type of the word you found: (Introduce the exact name without spaces after the type or capital letters that it doesnt have.) ");
        System.out.println("-Diagonal down");
        System.out.println("-Vertical down");
        System.out.println("-Horizontal right");
        type = this.sc.nextLine();
        type = this.sc.nextLine();
        
        this.updateAllStats(this.board.VerifyGuessInWords(coords[0], coords[1], type));
    }

    private void validateCoordinates(int[] coord, int index){
        int selection = 18;
        
        System.out.println("Coordinates starts in the top right coorner in (1,1).");
        
        while(selection > 16 || selection < 1){
            System.out.println("Enter a number for the " + ((index == 0)?"x ":"y ") + "coordinate:");
            selection = this.sc.nextInt();

            
            if (selection > 16 || selection < 1) {
                System.out.println("Enter a valid input.");
            }
            else{
                coord[index]= selection;
                break;
            }
            
        }
    }

    private void selectStats(){
        int selection = 123;
        while(selection > 4 || selection < 0){
            System.out.println("Select for what type of stats you want to look at:\n\t1.Current\n\t2.Global stats of the players\n\t3.Game stats");
            selection = this.sc.nextInt();

            switch (selection) {
                case 1:
                    this.showCurrentStats(); 
                    break;
            
                case 2:
                    this.showGlobalStats();
                    break;
            
                case 3:
                    this.showGameStats();
                    break;
            
                default:
                    System.out.println("Enter a valid input.");
                    break;
            }
        }

        System.out.println("\n\n");
    }

    private void showCurrentStats(){
        int selection = 0;

        while (selection != 1 && selection != 2) {
            System.out.println("\n\nSelect for which player you want to look at:\n\t1."+ this.Jugador1.getNombre() + "\n\t2." + this.Jugador2.getNombre() );
            selection = this.sc.nextInt();
            
            switch (selection) {
                case 1:
                    localJugador1.getDatos();
                    break;
                case 2:
                    localJugador2.getDatos();
                    break;
            
                default:
                    System.out.println("Enter a valid input.");
                    break;
            }
        }
    }

    private void showGlobalStats(){
        int selection = 0;

        while (selection != 1 && selection != 2) {
            System.out.println("\n\nSelect for which player you want to look at:\n\t1."+ this.Jugador1.getNombre() + "\n\t2." + this.Jugador2.getNombre() );
            selection = this.sc.nextInt();

            switch (selection) {
                case 1:
                    Jugador1.getDatos();
                    break;
                case 2:
                    Jugador2.getDatos();
                    break;
                    
                default:
                System.out.println("Enter a valid input.");
                break;
            }
        }
    }

    public void showGameStats(){
        System.out.println("\n\nCurrent game stats:");
        System.out.println("Total number of turns played: " + this.turnosJugados);
        System.out.println("Total number of correct answers: " + this.puntuacionTotal);

        System.out.println("Players stats during the game:");
        this.localJugador1.getDatos();
        System.out.println();
        this.localJugador2.getDatos();
    }

    public void updateAllStats(boolean guess){
        this.increaseAllTurnosJugados();

        if(guess){
            this.increasePuntuacionTotal();

            if(this.actualPlayer == 1){
                this.localJugador1.increasePuntuacionTotal();
                this.Jugador1.increasePuntuacionTotal();
            }else{
                this.localJugador2.increasePuntuacionTotal();
                this.Jugador2.increasePuntuacionTotal();
            }

        }else{

            this.actualPlayer = (this.actualPlayer==1)?2:1;
        }
    }

    public void increaseAllTurnosJugados(){
        if (this.actualPlayer == 1){
            this.localJugador1.increaseTurnosJugados();
            this.Jugador1.increaseTurnosJugados();
        } else{
            this.localJugador2.increaseTurnosJugados();
            this.Jugador2.increaseTurnosJugados();
        }

        this.increaseTurnosJugados();
    }

    @Override
    public void increasePuntuacionTotal() {
        this.puntuacionTotal++;
    }

    @Override
    public void increaseTurnosJugados() {
        this.turnosJugados++;
    }

    @Override
    public void increaseJuegosGanados() {
        this.juegosGanados++;
    }

    @Override
    public void increaseJuegosJugados() {
        this.juegosGanados++;
    }
}
