import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



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
    public boolean filled = false;

    Board(){
        this.fillWordList();
        if(this.filled) this.putWordsInMatrix();
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
        String[] wordListStr = new String[8];

        JLabel formTitle = new JLabel("Game constructor: Words\n\n");
        formTitle.setFont(new Font("Arial", Font.BOLD, 24));
        
        JTextField JtWord1 = new JTextField();
        JTextField JtWord2 = new JTextField();
        JTextField JtWord3 = new JTextField();
        JTextField JtWord4 = new JTextField();
        JTextField JtWord5 = new JTextField();
        JTextField JtWord6 = new JTextField();
        JTextField JtWord7 = new JTextField();
        JTextField JtWord8 = new JTextField();
        JButton confirmButton = new JButton("Continue");

        JtWord1.setBorder(new EmptyBorder(2,2,2,2));
        JtWord2.setBorder(new EmptyBorder(2,2,2,2));
        JtWord3.setBorder(new EmptyBorder(2,2,2,2));
        JtWord4.setBorder(new EmptyBorder(2,2,2,2));
        JtWord5.setBorder(new EmptyBorder(2,2,2,2));
        JtWord6.setBorder(new EmptyBorder(2,2,2,2));
        JtWord7.setBorder(new EmptyBorder(2,2,2,2));
        JtWord8.setBorder(new EmptyBorder(2,2,2,2));
        confirmButton.setBorder(new EmptyBorder(2,2,2,2));

        JDialog boardPanel = new JDialog();
        boardPanel.setTitle("Stats");
        boardPanel.setSize(400, 800);
        boardPanel.setModal(true);
        boardPanel.setLayout(new GridLayout(20, 1));

        boardPanel.setPreferredSize(new Dimension(400,400));
        boardPanel.add(formTitle);
        boardPanel.add(new JLabel("Word 1:\t"));
        boardPanel.add(JtWord1);
        boardPanel.add(new JLabel("Word 2:\t"));
        boardPanel.add(JtWord2);
        boardPanel.add(new JLabel("Word 3:\t"));
        boardPanel.add(JtWord3);
        boardPanel.add(new JLabel("Word 4:\t"));
        boardPanel.add(JtWord4);
        boardPanel.add(new JLabel("Word 5:\t"));
        boardPanel.add(JtWord5);
        boardPanel.add(new JLabel("Word 6:\t"));
        boardPanel.add(JtWord6);
        boardPanel.add(new JLabel("Word 7:\t"));
        boardPanel.add(JtWord7);
        boardPanel.add(new JLabel("Word 8:\t"));
        boardPanel.add(JtWord8);
        boardPanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean blankWords = false;
                boolean biggerWords = false;

                wordListStr[0] = JtWord1.getText();
                wordListStr[1] = JtWord2.getText();
                wordListStr[2] = JtWord3.getText();
                wordListStr[3] = JtWord4.getText();
                wordListStr[4] = JtWord5.getText();
                wordListStr[5] = JtWord6.getText();
                wordListStr[6] = JtWord7.getText();
                wordListStr[7] = JtWord8.getText();
        
                for (String s : wordListStr) {
                    if (s.isEmpty()) {
                        blankWords = true;
                    } else if (s.length() != 5) {
                        biggerWords = true;
                    }
                }
                
                // Si no hay palabras vacías ni palabras más grandes que 5 caracteres, salimos del ciclo
                if (!blankWords && !biggerWords) {
                    
                    for (int index = 0; index < 8; index++) {
                        while (true) {
                                Word newWord = null;
                                boolean wordPlaced = false;

                                // Try 1000 attempts to place the word
                                for (int attempt = 0; attempt < 1000; attempt++) {
                                    // Randomly choose word type and position
                                    int t = r.nextInt(3);
                                    switch (t) {
                                        case 0: 
                                            newWord = new Word(r.nextInt(12), r.nextInt(16), Word.TYPEOF.HorRight, wordListStr[index]);
                                            break;
                                        case 1: 
                                            newWord = new Word(r.nextInt(16), r.nextInt(12), Word.TYPEOF.VerDown, wordListStr[index]);
                                            break;
                                        case 2: 
                                            newWord = new Word(r.nextInt(12), r.nextInt(12), Word.TYPEOF.DigDown, wordListStr[index]);
                                            break;
                                    }

                                    if (canPlaceWord(newWord)) {
                                        wordList[index] = newWord;
                                        markCellsOccupied(newWord);
                                        wordPlaced = true;
                                        break;
                                    }
                                }

                                if (!wordPlaced) {
                                    index--; 
                                    continue;
                                }

                                break;
                        }
                    }
                    filled = true;
                    boardPanel.setVisible(false);

                } else {
                    // Mostrar un mensaje de error para que el usuario corrija su entrada
                    if (blankWords) {
                        JOptionPane.showMessageDialog(boardPanel, "No blank spaces are accepted.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if (biggerWords) {
                        JOptionPane.showMessageDialog(boardPanel, "The words must be of 5 characters length.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });  
        
        boardPanel.setVisible(true);
        
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

    public JPanel returnMatrixComponent(){
        JPanel Jp = new JPanel(new GridLayout(17,17));
        Jp.setPreferredSize(new Dimension(400,400));

        for (int i = 0 ; i < 17 ; i++){
            if(i == 0){
                Jp.add(new JLabel("  "));
            } else{
                Jp.add(new JLabel(String.valueOf(i) + " "));
            }
        }

        for (int i = 0; i < 16; i++){
            Jp.add(new JLabel(String.valueOf(i+1)));

            for (int subI = 0; subI < 16; subI++){
                JLabel temp;
                
                if (this.matrix[i][subI] == '■'){
                    temp = new JLabel("<html><span style='color:red'>" + this.matrix[i][subI] +  "  </span></html>");
                } else{
                    temp = new JLabel(this.matrix[i][subI] +  "  ");
                }
                
                temp.setBorder(new EmptyBorder(5,5,5,5));
                Jp.add(temp);
            }
        }

        Jp.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        return Jp;
    }
}