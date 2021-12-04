package marcclaessens.nibbles;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	private static final Color HEAD_COLOR = new Color(0, 240, 0);
	private static final Color BODY_COLOR = new Color(0, 155, 0);
	
	private int grow = 0;
	private Direction direction;
	private Rectangle snakeHead = new Rectangle(2*Defaults.RECT_SIZE, 2*Defaults.RECT_SIZE);
	private final List<Rectangle> body = new ArrayList<>();
	private final List<Direction> directionQueue = new ArrayList<>();
	
	public Snake(Direction startDirection) {
		this.direction = startDirection;
		setDirection(startDirection);
	}
	
	public void setDirection(Direction direction) {
		if ((directionQueue.isEmpty()  && ! direction.isOppositeOrSame(this.direction) )
				|| 
			(! directionQueue.isEmpty() && ! direction.isOppositeOrSame(directionQueue.get(directionQueue.size()-1)))) {
			directionQueue.add(direction);
		}		
	}
	
	public void draw(Graphics2D g) {
        g.setColor(HEAD_COLOR);
        g.fill(snakeHead);
		for (Rectangle rect : body) {
	        g.setColor(BODY_COLOR);
	        g.fill(rect);
		}
	}
	
	public void move() {
		if (! directionQueue.isEmpty()) {
			direction = directionQueue.remove(0);
		}
		if (grow == 0) {
			if (! body.isEmpty()) {
				body.remove(body.size()-1);
			};
		} else {
			grow --;
		}
		body.add(0, snakeHead);
		int x = (int) snakeHead.getX();
		int y = (int) snakeHead.getY();
		switch(direction) {
			case LEFT:
				x = x - Defaults.RECT_SIZE;
				break;
			case RIGHT:
				x = x + Defaults.RECT_SIZE;
				break;
			case UP:
				y = y - Defaults.RECT_SIZE;
						break;
			case DOWN:
				y = y + Defaults.RECT_SIZE;
				break;
				
		}
		snakeHead = new Rectangle(x, y);
	}

	public void grow() {
		grow += Defaults.GROWTH_RATE; 
	}

	public boolean eats(Food food) {
		return snakeHead.intersects(food.getRectangle());
	}
	
	public boolean touches(Food food) {
		return eats(food) || Rectangle.touches(food.getRectangle(), body);
	}
	
	public boolean isEatingItself() {
		return Rectangle.touches(snakeHead, body);
	}
	
	public boolean isOutsideGame() {
		//System.out.println("current position " + snakeHead.getX() + "," + snakeHead.getY());
		
		boolean insideGame = snakeHead.getX() >= 0 &&
				snakeHead.getX() +  Defaults.RECT_SIZE <= Defaults.SCREEN_SIZE_WIDTH
				&& snakeHead.getY() >= 0 
				&& snakeHead.getY() +  Defaults.RECT_SIZE <= Defaults.SCREEN_SIZE_HEIGHT;
		if (! insideGame) {
			System.out.println("Outside of the game ...");
		}
		return ! insideGame;
	}
	
	public boolean hitWall(Walls walls) {
		return Rectangle.touches(snakeHead, walls.getWalls());
	}	
}
