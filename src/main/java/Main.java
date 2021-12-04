import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import marcclaessens.nibbles.Defaults;
import marcclaessens.nibbles.Nibbles;
import marcclaessens.nibbles.ScorePanel;
import marcclaessens.nibbles.SettingsPanel;

public class Main {

	private final Font awesome = new Font("FontAwesome", Font.PLAIN, 20);
	private JFrame frame;
	private JTabbedPane menuPanel;
	private ScorePanel scorePanel; 
	private SettingsPanel settingsPanel;
	
	public void init() {
		Nibbles game = new Nibbles();
		createMenuPanel(game);

		game.setScoreListener(scorePanel);
		
		createMainFrame(game);

	
		frame.addKeyListener(game);
		menuPanel.addKeyListener(game);
		game.addKeyListener(game);
		frame.addKeyListener(game);
		menuPanel.addKeyListener(game);
		
		game.init();
	}
	
	private void createMainFrame(Nibbles game) {
		frame = new JFrame("Nibbles by Marc");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("Nibbles.ico")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel root = new JPanel();
		root.setLayout(new BorderLayout());
		root.add(menuPanel, BorderLayout.NORTH);
		root.add(game, BorderLayout.CENTER);
		
		frame.getContentPane().add(root);
		frame.pack();
		// center screen
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
	
	private void createMenuPanel(Nibbles game) {
        menuPanel = new JTabbedPane();
        menuPanel.setTabPlacement(JTabbedPane.LEFT);
        menuPanel.setFont(awesome);
        menuPanel.setPreferredSize(new Dimension(Defaults.SCREEN_SIZE_WIDTH, (int) (0.10d * Defaults.SCREEN_SIZE_HEIGHT)));
        
		scorePanel = new ScorePanel();
		menuPanel.addTab("\uf015", scorePanel);
		
		if (Defaults.SHOWSETTINGS) {
			settingsPanel = new SettingsPanel(game);
			menuPanel.addTab("\uf013", settingsPanel);
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			setDefaults(args);
		}
		loadFonts("/fonts/fontawesome-webfont.ttf");
		new Main().init();
	}

	private static void setDefaults(String[] args) {
		try {
			for (int i=0; i<args.length; i++) {
				if (args[i].equals("-screensize")) {
					i++;
					MessageFormat format = new MessageFormat("{0}x{1}");
					Object[] o;
					try {
						o = format.parse(args[i]);
					} catch (ParseException e) {
						throw new IllegalArgumentException("Invalid screensize " + args[i], e);
					}
					Defaults.SCREEN_SIZE_WIDTH = Integer.parseInt((String) o[0]);
					Defaults.SCREEN_SIZE_HEIGHT = Integer.parseInt((String) o[1]);
				} else if (args[i].equals("-upgrade")) {
					i++;
					Defaults.LEVELUP = Integer.parseInt(args[i]);
				} else if (args[i].equals("-speed")) {
					i++;
					Defaults.SPEED = Integer.parseInt(args[i]);
				} else if (args[i].equals("-growthrate")) {
					i++;
					Defaults.GROWTH_RATE = Integer.parseInt(args[i]);
				} else if (args[i].equals("-squaresize")) {
					i++;
					Defaults.RECT_SIZE = Integer.parseInt(args[i]);
				} else if (args[i].equals("-showsettings")) {
					Defaults.SHOWSETTINGS = true;
				} else {
					throw new IllegalArgumentException("Invalid argument " + args[i]);
				}
			}
		} catch(RuntimeException re) {
			dumpUsage();
			throw re;
		}
	}
	
	private static void dumpUsage() {
		System.out.println("Usage :" );
		System.out.println("java Main [-screensize AxB] [-upgrade B] [-speed C]  [-growthrate D] [-squaresize E] [-showsettings]");
		System.out.println("where A = screen size in pixels separated by small 'x' (default = 1200x800)");
		System.out.println("where B = number of wins needed before upgrading to next level (default = 8)");
		System.out.println("where C = Timer speed in millis (lower is faster) (default = 200)");
		System.out.println("where D = number of squares added to the snake body everytime it eats (default = 4)");
		System.out.println("where E = number of pixels that make up 1 square for nibble, snake and wall (default = 20)");
		System.out.println("where showsettings (without other arguments) enables the settings menu (for future/experimental featuress)");
	}
	
    private static void loadFonts(String... resources) throws Exception {
        for (String resource : resources) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResource(resource).openStream());
            font.deriveFont(Font.PLAIN, 18);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        }
    }
}
