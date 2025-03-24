import java.util.Random;
import java.util.Scanner;

public class Board {
  
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
    public Word[]  wordList = new Word[8];
    public Random r = new Random();

    int[] unicodeLetras = {
        65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77,
        78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
        97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
        110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122
    };

    public void fillMatrixWithRandom(){
        for (int i = 0; i < 16; i++){
            for (int subI = 0; subI < 16; subI++){
                this.matrix[i][subI] = (char)unicodeLetras[r.nextInt(52)];
            }
        }
    }

    public void fillWordList(){
        Scanner sc  = new Scanner(System.in);
        int t = 0;
        String w = "";

        System.out.println("The capture of the word is about to start.");
        
        for (int index = 0; index < 8; index++){

            while (true) {
                System.out.println("Introduce the word that is going to be added to the board:\t");
                w = sc.nextLine();
                int diagCount = 0;

                if ( w.length() == 5 ){
                    t = r.nextInt((diagCount<=1)?3:2);
                    // t = r.nextInt(2);

                    switch (t) {
                        case 2:
                            this.wordList[index] = new Word(r.nextInt(12), r.nextInt(12), Word.TYPEOF.DigDown, w);
                            diagCount++;
                            break;

                        case 1:
                            this.wordList[index] = new Word(r.nextInt(16), r.nextInt(12), Word.TYPEOF.VerDown, w);
                            break;

                        case 0:
                            this.wordList[index] = new Word(r.nextInt(12), r.nextInt(16), Word.TYPEOF.HorRight, w);
                            break;
                    }
                    
                    break;
                }

                System.out.println("The word must be of 5 characters length");
            }
        }

    }
    
    public boolean VerifyGuessInWords(int x, int y, String t){
        Word.TYPEOF T;
        
        switch (t) {
            case "Diagonal down":
                T = Word.TYPEOF.DigDown;
                break;

            case "Vertical down":
                T = Word.TYPEOF.VerDown;
                break;

            case "Horizontal Right":
                T = Word.TYPEOF.HorRight;
                break;
            default:
                T = Word.TYPEOF.HorRight;
                break;
        }
        
        for (Word word : this.wordList){
            if(word.VerifyGuess(x-1, y-1, T)){
                this.quitWordInMatrix(word);
                return true;
            }
        }
        
        return false;
    }

    private boolean verifyWordCross(Word w, int stop){

        int dirX1 = (w.TYPE == Word.TYPEOF.VerDown)?0:1;
        int dirY1 = (w.TYPE == Word.TYPEOF.HorRight)?0:1;
        
        int count = 0;
        for (Word word : this.wordList){
            if (count == stop){
                System.out.println("Comprobation has endended.");
                return false;
            }

            int dirX2 = (word.TYPE == Word.TYPEOF.VerDown)?0:1;
            int dirY2 = (word.TYPE == Word.TYPEOF.HorRight)?0:1;

            if (word.TYPE == Word.TYPEOF.DigDown){
                System.out.println(word.word + " " + word.TYPE);
                for (int i= 0 ; i < 5 ; i++){
                    if( ((word.posX + i * dirX2) >= w.posX && (word.posX + i * dirX2)<=(w.posX + 4 * dirX1))
                    &&  ((word.posY + i * dirY2) >= w.posY && (word.posY + i * dirY2)<=(w.posY + 4 * dirY1))){
                        
                        System.out.println("Word char pos " + (word.posX + i * dirX2) + " " + (word.posY + i * dirY2));
                        System.out.println("W char pos " + (w.posX + i * dirX1) + " " + (w.posY + i * dirY1));

                        if (w.posX == 11 && w.posY == 11){
                            w.posX = 0;
                            w.posY = 0;
                        }
                        else if (w.posX == 11){
                            w.posX = 0;
                            w.posY = w.posY + 1;
                        }else if(w.posY == 11){
                            w.posX = w.posX + 1;
                            w.posY = 0;
                        } else{
                            w.posX = w.posX + 1;
                        }

                        return false;
                    }

                    count++;
                }
            }else{
                    if( (((w.posX >= word.posX) && (w.posX <= (word.posX + 4 * dirX2))) || (((w.posX + 4* dirX1) >=word.posX) && ((w.posX + 4* dirX1) <= (word.posX + 4 * dirX2))) )
                    &&  (((w.posY >= word.posY) && (w.posY <= (word.posY + 4 * dirY2))) || (((w.posY + 4* dirY1) >=word.posY) && ((w.posY + 4* dirY1) <= (word.posY + 4 * dirY2))) )){
                        
                        int t = r.nextInt(2);

                        switch (t) {
                            case 0:
                                w.TYPE = Word.TYPEOF.HorRight;
                                w.posX = r.nextInt(12);
                                w.posY = r.nextInt(16);
                                break;

                            case 1:
                                w.TYPE = Word.TYPEOF.VerDown;
                                w.posX = r.nextInt(16);
                                w.posY = r.nextInt(12);
                                break;
                        }
                        System.out.println(w.word + " is cross with another word\n");
                        System.out.println(word.posX + " actual position " + word.posY);
                        return true;
                    } else{
                        count++;
                    }
            }
        }

        return false;
    }

    public void putWordsInMatrix(){
        //Coordinates starts at the top left coorner as 0,0.
        int count = 0;
        for (Word w : this.wordList){
            while (this.verifyWordCross(w, count)) {
                
            }
            
            int dirX = (w.TYPE == Word.TYPEOF.VerDown)?0:1;
            int dirY = (w.TYPE == Word.TYPEOF.HorRight)?0:1;
    
            for (int i = 0; i < 5; i++){
                this.matrix[w.posY + i * dirY][w.posX + i*dirX] = w.word.charAt(i);
            }

            count++;
        }
    }

    public void quitWordInMatrix(Word w){
        int dirX = (w.TYPE == Word.TYPEOF.VerDown)?0:1;
        int dirY = (w.TYPE == Word.TYPEOF.HorRight)?0:1;

        for (int i = 0; i < 5; i++){
            this.matrix[w.posY + i * dirY][w.posX + i*dirX] = '-';
        }
    }

    public void printMatrix(){
        for (int i = 0; i < 16; i++){
            for (int subI = 0; subI < 16; subI++){
                System.out.print(this.matrix[i][subI] + "\t");
            }
            System.out.println("");
        }
    }
}