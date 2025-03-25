import java.util.Random;
import java.util.Scanner;



public class Board {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    private class Word{
        public int posX = 0;
        public int posY = 0;
        public String word = "";

        public enum TYPEOF {
            HorRight,
            VerDown,
            DigDown
        };

        public TYPEOF TYPE = TYPEOF.DigDown;

        Word(int x, int y, TYPEOF t, String w){
            this.posX = x;
            this.posY = y;
            this.word = w;

            this.TYPE = t;
        }

        public boolean VerifyGuess(int x, int y, TYPEOF t){
            
            if (this.posX == x && this.posY == y && this.TYPE == t){
                return true;
            }

            return false;
        }
    }


    private char[][] matrix = new char[16][16];
    public Word[] wordList = new Word[8];
    public int wordsFound = 0;
    private Random r = new Random();
    private boolean[][] occupiedCells = new boolean[16][16];

    Board(){
        System.out.println("A new game is about to start...\n\n");
        this.fillWordList();
        this.putWordsInMatrix();
    }


    private void markCellsOccupied(Word word) {
        int dirX = (word.TYPE == Word.TYPEOF.VerDown) ? 0 : 1;
        int dirY = (word.TYPE == Word.TYPEOF.HorRight) ? 0 : 1;

        for (int i = 0; i < 5; i++) {
            int x = word.posX + i * dirX;
            int y = word.posY + i * dirY;
            
            //marca la celda como ocupada para prevenir que otra palabra se ponga encima
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int newX = x + dx;
                    int newY = y + dy;
                    
                    if (newX >= 0 && newX < 16 && newY >= 0 && newY < 16) {
                        occupiedCells[newY][newX] = true;
                    }
                }
            }
        }
    }


    private boolean canPlaceWord(Word newWord) {
        int dirX = (newWord.TYPE == Word.TYPEOF.VerDown) ? 0 : 1;
        int dirY = (newWord.TYPE == Word.TYPEOF.HorRight) ? 0 : 1;

        //checaar si la palabra se sale del tablero
        if (newWord.posX + 4 * dirX >= 16 || newWord.posY + 4 * dirY >= 16) {
            return false;
        }

        //checar si la palabra se sobrepone con otra
        for (int i = 0; i < 5; i++) {
            int x = newWord.posX + i * dirX;
            int y = newWord.posY + i * dirY;
            
            //checar si la celda esta ocupada
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int newX = x + dx;
                    int newY = y + dy;
                    
                    if (newX >= 0 && newX < 16 && newY >= 0 && newY < 16) {
                        if (occupiedCells[newY][newX]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void fillWordList() {
        //resetear las celdas ocupadas
        occupiedCells = new boolean[16][16];
        Scanner sc = new Scanner(System.in);
        System.out.println("The capture of the word is about to start.");
        
        for (int index = 0; index < 8; index++) {
            while (true) {
                System.out.println("Introduce the word that is going to be added to the board:\t");
                String w = sc.nextLine().toUpperCase();

                if (w.length() == 5) {
                    Word newWord = null;
                    boolean wordPlaced = false;

                    // Try 1000 attempts to place the word
                    for (int attempt = 0; attempt < 1000; attempt++) {
                        // Randomly choose word type and position
                        int t = r.nextInt(3);
                        switch (t) {
                            case 0: 
                                newWord = new Word(r.nextInt(12), r.nextInt(16), Word.TYPEOF.HorRight, w);
                                break;
                            case 1: 
                                newWord = new Word(r.nextInt(16), r.nextInt(12), Word.TYPEOF.VerDown, w);
                                break;
                            case 2: 
                                newWord = new Word(r.nextInt(12), r.nextInt(12), Word.TYPEOF.DigDown, w);
                                break;
                        }


                        if (canPlaceWord(newWord)) {
                            this.wordList[index] = newWord;
                            markCellsOccupied(newWord);
                            wordPlaced = true;
                            break;
                        }
                    }

                    
                    if (!wordPlaced) {
                        System.out.println("Could not place the word after many attempts. Please try a different word.");
                        index--; 
                        continue;
                    }
                    break;
                }

                System.out.println("The word must be of 5 characters length");
            }
        }
    }
    public void fillMatrixWithRandom() {
        int[] unicodeLetras = {
            65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77,
            78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
            97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
            110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122
        };

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                this.matrix[i][j] = (char) unicodeLetras[r.nextInt(52)];
            }
        }
    }

    public void putWordsInMatrix() {
        fillMatrixWithRandom();

        for (Word w : this.wordList) {
            int dirX = (w.TYPE == Word.TYPEOF.VerDown) ? 0 : 1;
            int dirY = (w.TYPE == Word.TYPEOF.HorRight) ? 0 : 1;
    
            for (int i = 0; i < 5; i++) {
                this.matrix[w.posY + i * dirY][w.posX + i * dirX] = w.word.charAt(i);
            }
        }
    }

    public void printMatrix(){
        for (int i = 0; i < 16; i++){
            for (int subI = 0; subI < 16; subI++){
                if (this.matrix[i][subI] == '■'){
                    System.out.print(ANSI_RED);
                }else{
                    System.out.print(ANSI_RESET);
                }
                System.out.print(this.matrix[i][subI] + " ");
            }
            System.out.println("");
        }
    }
    int[] unicodeLetras = {
        65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77,
        78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
        97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
        110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122
    };
    
    public boolean VerifyGuessInWords(int x, int y, String t){
        Word.TYPEOF T;
        
        switch (t) {
            case "Diagonal down":
                T = Word.TYPEOF.DigDown;
                break;

            case "Vertical down":
                T = Word.TYPEOF.VerDown;
                break;

            case "Horizontal right":
                T = Word.TYPEOF.HorRight;
                break;
            default:
                T = Word.TYPEOF.HorRight;
                break;
        }
        
        for (Word word : this.wordList){
            if(word.VerifyGuess(x-1, y-1, T)){
                this.quitWordInMatrix(word);
                this.wordsFound++;
                return true;
            }
        }
        
        return false;
    }

    public void quitWordInMatrix(Word w){
        int dirX = (w.TYPE == Word.TYPEOF.VerDown)?0:1;
        int dirY = (w.TYPE == Word.TYPEOF.HorRight)?0:1;

        for (int i = 0; i < 5; i++){
            this.matrix[w.posY + i * dirY][w.posX + i*dirX] = '■';
        }
    }
}