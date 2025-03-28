import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AppMenuGUI {
    protected HashMap<String, Jugador> PlayersRegister = new HashMap<>(); 
    protected HashMap<String, Arbitro> RefereeRegister = new HashMap<>(); 
    protected Game[] gamesArr = new Game[30];
    protected int actualGame = 0;
    protected int totalGames = 0;
    
    private JFrame frame;
    private JTextArea textArea;
    private JButton newGameButton, continueGameButton, continueLastGameButton, addPersonButton, showPersonStatsButton, showGameStatsButton;
    
    public AppMenuGUI() {
        frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea));
        
        newGameButton = new JButton("Create New Game");
        continueGameButton = new JButton("Continue a Past Game");
        continueLastGameButton = new JButton("Continue Last Game");
        addPersonButton = new JButton("Add Person");
        showPersonStatsButton = new JButton("Show Person Stats");
        showGameStatsButton = new JButton("Show Game Stats");
        
        panel.add(newGameButton);
        panel.add(continueGameButton);
        panel.add(continueLastGameButton);
        panel.add(addPersonButton);
        panel.add(showPersonStatsButton);
        panel.add(showGameStatsButton);
        
        frame.add(panel);
        
        addActionListeners();
        
        frame.setVisible(true);
    }
    
    private void addActionListeners() {
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        
        continueGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                continueGame();
            }
        });
        
        continueLastGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                continueLastGame();
            }
        });
        
        addPersonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPerson();
            }
        });
        
        showPersonStatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPersonStats();
            }
        });
        
        showGameStatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameStats();
            }
        });
    }
    
    public void newGame() {
        textArea.append("New game created!\n");
    }
    
    public void addPerson() {
        int totalRegistered = PlayersRegister.size() + RefereeRegister.size();
        if (totalRegistered == 20) {
            JOptionPane.showMessageDialog(frame, "Sorry, the system has reached the registration limit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] options = {"Referee", "Player"};
        int selection = JOptionPane.showOptionDialog(frame, "What kind of person do you want to register?", "Add Person", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (selection == -1) return;
        
        String name;
        while (true) {
            name = JOptionPane.showInputDialog(frame, "Please enter your name:");
            if (name == null || name.trim().isEmpty()) return;
            if (PlayersRegister.containsKey(name) || RefereeRegister.containsKey(name)) {
                JOptionPane.showMessageDialog(frame, "This username already exists, try another one.", "Error", JOptionPane.ERROR_MESSAGE);
            } else break;
        }
        
        int age;
        try {
            age = Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter your age:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid age input.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selection == 0) {
            RefereeRegister.put(name, new Arbitro(name, age, totalRegistered + 1, 0));
        } else {
            PlayersRegister.put(name, new Jugador(name, age, totalRegistered + 1));
        }
        textArea.append("Person added: " + name + "\n");
    }
    
    public void showPersonStats() {
        StringBuilder personsList = new StringBuilder("Registered persons:\n");
        PlayersRegister.values().forEach(j -> personsList.append(j.getId()).append(" - ").append(j.getNombre()).append("\n"));
        RefereeRegister.values().forEach(r -> personsList.append(r.getId()).append(" - ").append(r.getNombre()).append("\n"));
        
        String name = JOptionPane.showInputDialog(frame, personsList.append("\nEnter the name of the person to view stats:").toString());
        if (name == null || name.trim().isEmpty()) return;
        
        if (PlayersRegister.containsKey(name)) {
            textArea.append(PlayersRegister.get(name).getDatos() + "\n");
        } else if (RefereeRegister.containsKey(name)) {
            textArea.append(RefereeRegister.get(name).getDatos() + "\n");
        } else {
            JOptionPane.showMessageDialog(frame, "That person doesn't exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void showGameStats() {
        if (totalGames == 0) {
            JOptionPane.showMessageDialog(frame, "There are no games to show.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder gamesList = new StringBuilder("Games played:\n");
        for (int i = 0; i < totalGames; i++) {
            gamesList.append("Game number ").append(i + 1).append("\n");
        }
        
        int gameNumber = Integer.parseInt(JOptionPane.showInputDialog(frame, gamesList.append("\nEnter the game number to view stats:").toString()));
        if (gameNumber < 1 || gameNumber > totalGames) {
            JOptionPane.showMessageDialog(frame, "Invalid game number.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //textArea.append(gamesArr[gameNumber - 1].showGameStats() + "\n");
        }
    }
    
    public void continueGame() {
        if (totalGames == 0) {
            JOptionPane.showMessageDialog(frame, "There are no games to continue.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int gameNumber = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the game number to continue:"));
        if (gameNumber < 1 || gameNumber > totalGames) {
            JOptionPane.showMessageDialog(frame, "Invalid game number.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            actualGame = gameNumber - 1;
            gamesArr[actualGame].startGame();
            textArea.append("Continuing game " + gameNumber + "\n");
        }
    }
    
    public void continueLastGame() {
        if (totalGames == 0) {
            JOptionPane.showMessageDialog(frame, "There are no games to continue.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        gamesArr[actualGame].startGame();
        textArea.append("Continuing last game\n");
    }


}

