import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game implements Statistics{
    Jugador localJugador1 = null;
    Jugador localJugador2 = null;
    Jugador Jugador1 = null;
    Jugador Jugador2 = null;
    Arbitro arbitro = null;
    JFrame JF = null;
    JDialog gameDialog = null;
    JComboBox<String> typeGuesComBox = null;
    JComboBox<Integer> xGuesComBox = null;
    JComboBox<Integer> yGuesComBox = null;
    JPanel inputsJPanel = null;
    JLabel turnLabel = null;

    public int puntuacionTotal = 0;
    public int turnosJugados = 0;
    public int juegosJugados = 0;
    public int juegosGanados = 0;
    private Board board = null;
    public boolean gameFinish = false;

    private int actualPlayer = 1;

    public Game(Jugador j1, Jugador j2, Arbitro ar , JFrame Jf){
        this.localJugador1 = new Jugador(j1);
        this.localJugador2 = new Jugador(j2);
        this.Jugador1 = j1;
        this.Jugador2 = j2;
        this.arbitro = ar;
        this.JF = Jf;

        this.localJugador1.increaseJuegosJugados();
        this.localJugador2.increaseJuegosJugados();
        this.Jugador1.increaseJuegosJugados();
        this.Jugador2.increaseJuegosJugados();

        this.board = new Board();
        constructGameDialog();
    }

    public void startGame(){
        while(!this.board.filled){
            this.board.fillWordList();
            if(this.board.filled) this.board.putWordsInMatrix();
        }
       
        if (this.board.wordsFound < 8) gameFinish=false;

            while(!gameFinish){
                this.showGame();
            }
    }

    public void verifyGameEnd(){
        if(this.board.wordsFound == 8){
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
            this.gameDialog.setVisible(false);
        } 

    }

    public void showGame(){
        this.updatePanel();
        gameDialog.setVisible(true);
    }

    private void turnAction(){
        this.updateAllStats(this.board.VerifyGuessInWords(Integer.parseInt(xGuesComBox.getSelectedItem().toString()),Integer.parseInt( yGuesComBox.getSelectedItem().toString()), typeGuesComBox.getSelectedItem().toString()));
        updatePanel();
    }

    private void showStats(){
        JDialog Jd = new JDialog();
        Jd.setTitle("Stats");
        Jd.setSize(300, 300);
        Jd.setModal(true);
        Jd.setLayout(new GridLayout(7, 1));

        String[] personOptions =  {"Game", Jugador1.getNombre(), Jugador2.getNombre()};
        String[] typeOptions = {"Current", "Global"};

        JLabel titleLabel = new JLabel("Select what kind of stat are you looking at:");
        JComboBox<String>  personComBox = new JComboBox<>(personOptions);
        JComboBox<String>  typeComBox = new JComboBox<>(typeOptions);
        JButton confirmButton = new JButton("Confirm");
        JButton exitButton = new JButton("Exit");
        
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        Jd.add(titleLabel);
        Jd.add(new JLabel("Person:"));
        Jd.add(personComBox);
        Jd.add(new JLabel("Type:"));
        Jd.add(typeComBox);
        Jd.add(confirmButton);
        Jd.add(exitButton);
        
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Message;

                if (personComBox.getSelectedItem().toString()=="Game"){ 
                    
                    Message =  "Current game stats:\nTotal number of turns played: " + turnosJugados + "\nTotal number of correct answers:" + puntuacionTotal + "\n" + localJugador1.getDatos() + "\n" + localJugador2.getDatos();

                } else if(personComBox.getSelectedItem().toString()==Jugador1.getNombre()){

                    Message = "Stats of player: " + Jugador1.getNombre() + "\n" + ((typeComBox.getSelectedItem().toString()=="Current")?localJugador1.getDatos():Jugador1.getDatos());

                } else{
                    
                    Message = "Stats of player: " + Jugador2.getNombre() + "\n" + ((typeComBox.getSelectedItem().toString()=="Current")?localJugador2.getDatos():Jugador2.getDatos());

                }

                JOptionPane.showMessageDialog(Jd, Message, "Info", JOptionPane.INFORMATION_MESSAGE);
                Jd.setVisible(false);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Jd.setVisible(false);
            }
        });

        Jd.setVisible(true);
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

    private void constructGameDialog(){  
        gameDialog = new JDialog();
        gameDialog.setTitle("Game");
        gameDialog.setSize(1100, 800);
        gameDialog.setModal(true);
        gameDialog.setLayout(new GridLayout(1, 2));
        gameDialog.add(this.board.returnMatrixComponent());

        this.inputsJPanel = new JPanel();
        inputsJPanel.setLayout(new GridLayout(10, 1));
        inputsJPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        Integer[] coordlistOptions = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        String[] typelistOptions = {"Diagonal down","Vertical down", "Horizontal right"};
        this.turnLabel = new JLabel("The current turn is for the player " + ((actualPlayer == 1)?localJugador1.getNombre():localJugador2.getNombre()));
        JLabel xGuessLabel = new JLabel("Input the x:");
        this.xGuesComBox = new JComboBox<>(coordlistOptions);
        JLabel yGuessLabel = new JLabel("Input the y:");
        this.yGuesComBox = new JComboBox<>(coordlistOptions);
        JLabel typeGuessLabel = new JLabel("Input the type of the word:");
        this.typeGuesComBox = new JComboBox<>(typelistOptions);
        JButton guessButton = new JButton("Make a guess");
        JButton statsButton = new JButton("Stats");
        JButton exiButton = new JButton("Exit from game");
        
        
        inputsJPanel.add(turnLabel);
        inputsJPanel.add(xGuessLabel);
        inputsJPanel.add(xGuesComBox);
        inputsJPanel.add(yGuessLabel);
        inputsJPanel.add(yGuesComBox);
        inputsJPanel.add(typeGuessLabel);
        inputsJPanel.add(typeGuesComBox);
        inputsJPanel.add(guessButton);
        inputsJPanel.add(statsButton);
        inputsJPanel.add(exiButton);

        gameDialog.add(inputsJPanel);

        
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turnAction();
                verifyGameEnd();
            }
        });
        
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStats();
            }
        });

        exiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameFinish = true;
                gameDialog.setVisible(false);
            }
        });
    }

    private void updatePanel(){
        this.gameDialog.getContentPane().removeAll();
        this.gameDialog.add(this.board.returnMatrixComponent());
        this.gameDialog.add(this.inputsJPanel);
        this.gameDialog.revalidate();
        this.gameDialog.repaint();

        this.turnLabel.setText("The current turn is for the player " + ((actualPlayer == 1)?localJugador1.getNombre():localJugador2.getNombre()));
    }
}
