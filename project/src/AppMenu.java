

import java.util.HashMap;
import java.util.Scanner;

public class AppMenu {
    protected HashMap<String, Jugador> PlayersRegister = new HashMap<>(); 
    protected HashMap<String, Arbitro> RefereeRegister = new HashMap<>(); 
    protected Game[] gamesArr = new Game[30];
    protected int actualGame = 0;
    protected int totalGames = 0;
    protected Scanner sc =  new Scanner(System.in);

    public void StartApp(){

        while (PlayersRegister.size() < 2  || RefereeRegister.size() < 1){
            System.out.println("Before we start the app we need at least two players registered and one referee.");
            this.addPerson();
        }  

        int selection = 0;
        
        while (true) {
            System.out.println("Please enter what you want to do:\n\t1.Create a new game\n\t2.Continue a past game\n\t3.Continue the last game\n\t4.Add people to the game.\n\t5.Show the stats of a person\n\t6.Show the stats of a game.");

            selection = this.sc.nextInt();

            switch (selection) {
                case 1:
                    this.newGame();
                    break;
                
                case 2:
                    this.continueGame();
                    break;
                case 3: 
                    this.continueLastGame();
                    break;
                case 4:
                    this.addPerson();
                    break;
                case 5:
                    this.showPersonStats();
                    break;

                case 6:
                    this.showGameStats();
                    break;

                default:
                    System.out.println("Enter a valid answer.");
                    break;
            }
        }

    }

    public void newGame(){
        String j1 = this.verifyIfPersonExists("Player");
        String j2 = this.verifyIfPersonExists("Player");
        String ar = this.verifyIfPersonExists("Referee");

        gamesArr[totalGames] = new Game(PlayersRegister.get(j1), PlayersRegister.get(j2), RefereeRegister.get(ar));
        actualGame = totalGames;
        totalGames++;

        gamesArr[actualGame].startGame();
    }

    public String verifyIfPersonExists(String type){
        String name = "";
        
        while (true) {
            System.out.println("Introduce the name of " + type);
            name = this.sc.nextLine();

            if(type == "Player"){
                if(this.PlayersRegister.containsKey(name)){
                    return name;
                }
            }else{
                if(this.RefereeRegister.containsKey(name)){
                    return name;
                }
            }  

            System.out.println("That name not exist in the registers.");
        }
    }
    
    public void addPerson(){
        int totalRegistered  = PlayersRegister.size()+RefereeRegister.size();
        int selection = 0;

        if ( totalRegistered == 20){
            System.out.println("Sorry but we reach the limit to the persons can be registered by the system.");
            return;
        }

        while (true) {
            while (selection!=1 && selection !=2) {

                System.out.println("what kind of person you want to register:\n\t1.Refereen\n\t2.Player");
                selection = this.sc.nextInt();

                
                if (selection!=1 && selection !=2) System.out.println("Enter a valid answer.");
            }

            String name = "";
            int edad  = 0;
    
            while (true) {
                System.out.print("Please enter your name:\t");
                name = this.sc.nextLine();
    
                if(this.PlayersRegister.containsKey(name)) System.out.println("This user name already exists, try with another one.");
                else break;
            }
    
            System.out.print("\nPlease enter your age:\t");
            edad = this.sc.nextInt();


            if (selection == 1){
                this.RefereeRegister.put(name, new Arbitro(name, edad, totalRegistered+1, 0));
                break;
            }
            else{
                this.PlayersRegister.put(name, new Jugador(name, edad, totalRegistered+1));
                break;
            }
        }
    }

    public void showPersonStats(){
        String name = "";

        System.out.println("This is the list of persons that are already registered:");
        
        for (Jugador j : this.PlayersRegister.values()){
            System.out.println(  "\t" + j.getId() + "-" + j.getNombre());
        }

        for (Arbitro j : this.RefereeRegister.values()){
            System.out.println(  "\t" + j.getId() + "-" + j.getNombre());
        }

        while (true) {
            System.out.print("\nEnter the name of the person you want to know its stats: ");    
            name = this.sc.nextLine();

            if (!(this.RefereeRegister.containsKey(name)) && !(this.PlayersRegister.containsKey(name))){
                System.out.println("That person doesn't exists.");

            } else if (this.PlayersRegister.containsKey(name)){
                this.PlayersRegister.get(name).getDatos();
                break;

            }else{
                this.RefereeRegister.get(name).getDatos();
                break;
            }
        }
    }

    public void showGameStats(){
        if(actualGame==0){
            System.out.println("There are no games to show.");
            return;
        }

        System.out.println("This is the list of games that have been played:");

        int index = 1;
        for (Game g : gamesArr){
            if (g == null){
                break;
            }

            System.out.println("-Game number " + index);
            index++;
        }

        while (true) {
            System.out.print("Input the number of the game you want to know about:\t");
            index = this.sc.nextInt();

            if (index > totalGames || index < 1){
                System.out.println("Enter a valid number.");
            } else{
                this.gamesArr[index-1].showGameStats();
                break;
            }
        }
    }

    public void continueGame(){
        if (totalGames == 0){
            System.out.println("There are no games to continue.");
            return;
        }

        int index = 1;
        for (Game g : gamesArr){
            if (g == null){
                break;
            }

            System.out.println("-Game number " + index);
            index++;
        }
        
        while (true) {
            System.out.print("Input the number of the game you want to continue:\t");
            index = this.sc.nextInt();

            if (index > totalGames || index < 1){
                System.out.println("Enter a valid number.");
            } else{
                this.actualGame = index-1;
                this.gamesArr[this.actualGame].startGame();
                break;
            }
        }
    }

    public void continueLastGame(){
        if (totalGames == 0){
            System.out.println("There are no games to continue.");
            return;
        }
        
        this.gamesArr[this.actualGame].startGame();
    }
}
