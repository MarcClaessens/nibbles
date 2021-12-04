package marcclaessens.nibbles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Food {
	private static final Color FOOD_COLOR = new Color(205, 0, 205);
	private Rectangle food;
	 
	public Food (Snake snake, Walls walls) {
		initFood(); 
		
		while (snake.touches(this) || Rectangle.touches(food, walls.getWalls())) {
			initFood();
		}
	}
	
	private void initFood() {
		Random r = new Random();
		int max = Defaults.SCREEN_SIZE_HEIGHT;
		int size = Defaults.RECT_SIZE;
		int x = r.nextInt(max/size);
		int y = r.nextInt(max/size);
		food = new Rectangle(x*size, y*size);		
	}
	
	public void draw(Graphics2D g) {
        g.setColor(FOOD_COLOR);
        g.fill(food);
	}
	
	public Rectangle getRectangle() {
		return food;
	}
}
