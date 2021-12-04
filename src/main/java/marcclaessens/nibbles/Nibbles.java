package marcclaessens.nibbles;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import marcclaessens.soundutil.SoundPlayer;

public class Nibbles extends JPanel implements KeyListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private Walls walls;
	private Snake snake;
	private Food food;

	private Timer timer;
	private Image offScreenBuffer;
	private Graphics offScreenGraphics;
	private SoundPlayer player = new SoundPlayer();

	private ScoreListener scoreListener;

	private boolean initialized;

	public Nibbles() {
		setBackground(Color.WHITE);
		this.setFocusable(true);
		this.setRequestFocusEnabled(true);
	}

	public void init() {
		offScreenBuffer = createImage(getWidth(), getHeight());
		offScreenGraphics = offScreenBuffer.getGraphics();
		this.requestFocus();
		timer = new Timer(Defaults.SPEED, this);
		initialized = true;
	}

	public void setScoreListener(ScoreListener scoreListener) {
		this.scoreListener = scoreListener;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Defaults.SCREEN_SIZE_WIDTH, Defaults.SCREEN_SIZE_HEIGHT);
	}

	/**
	 * re-initializes all field elements
	 */
	private void initRound(int level) {
		walls = new Walls(level);
		snake = new Snake(walls.getStartDirection());
		food = new Food(snake, walls);
	}

	/**
	 * Called automatically after a repaint request
	 */
	public void paint(Graphics g) {
		if (initialized) {
			draw((Graphics2D) offScreenGraphics);
			g.drawImage(offScreenBuffer, 0, 0, this);
		} else {
			super.paint(g);
		}
	}

	public void draw(Graphics2D g) {
		super.paint(g);
		if (walls != null) {
			g.setColor(Color.BLACK);

			walls.draw(g);
			snake.draw(g);
			food.draw(g);
		}
	}

	public void actionPerformed(ActionEvent e) {
		snake.move();

		if (snake.eats(food)) {
			win();
		}
		if (snake.isEatingItself() || snake.isOutsideGame() || snake.hitWall(walls)) {
			lose();
		}

		repaint();
	}

	private void win() {
		if (scoreListener != null) {
			scoreListener.won();
			if (scoreListener.isLevelUp()) {
				int newlevel = scoreListener.getLevel();
				if (newlevel % 2 == 0) {
					player.play(GameSounds.LEVEL_UP0.getSound());
				} else {
					player.play(GameSounds.LEVEL_UP1.getSound());
				}
				timer.stop();
				initRound(newlevel);
				try {
					Thread.sleep(3 * Defaults.SPEED);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				timer.start();
			} else {
				player.play(GameSounds.WIN.getSound());
				food = new Food(snake, walls);
				snake.grow();
			}
		}
	}

	private void lose() {
		player.play(GameSounds.LOSE.getSound());
		timer.stop(); // keep after WAV
		if (scoreListener != null) {
			scoreListener.lost();
		}
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println("pressed"  + e.getKeyCode());
		if (snake == null) {
			return;
		}
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
				snake.setDirection(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
				snake.setDirection(Direction.RIGHT);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				snake.setDirection(Direction.DOWN);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				snake.setDirection(Direction.UP);
				break;
		}
	}

	/**
	 * handles any key released events and restarts the game if the Timer is not
	 * running and user types 'r'
	 */
	public void keyReleased(KeyEvent e) {
		//System.out.println("released "  + e.getKeyCode());
		switch (e.getKeyCode()) {
		case KeyEvent.VK_P:
			if (timer.isRunning()) {
				timer.stop();
			} else {
				timer.start();
			}
			break;
		case KeyEvent.VK_ENTER:
			if (!timer.isRunning()) {
				restart(0);
			}
			break;
		case KeyEvent.VK_NUMPAD1:
			restart(1);
			break;
		case KeyEvent.VK_NUMPAD2:
			restart(2);
			break;
		case KeyEvent.VK_NUMPAD3:
			restart(3);
			break;
		case KeyEvent.VK_NUMPAD4:
			restart(4);
			break;
		case KeyEvent.VK_NUMPAD5:
			restart(5);
			break;
		}
	}

	private void restart(int level) {
		if (scoreListener != null) {
			scoreListener.restart();
		}
		timer.start();
		initRound(level);
	}

	public void keyTyped(KeyEvent e) {
	}

}
