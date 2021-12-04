package marcclaessens.nibbles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Walls {
	private static Color WALL_COLOR = new Color(160, 40, 40);
	// in theory we can add Arc2D and other shapes (although visually that makes the
	// game too challenging)
	private final List<Shape> walls = new ArrayList<>();
	private final Direction startDirection;

	public Walls(int level) {

		if (level == 0) {
			defaultBorderWalls();
			startDirection = Direction.RIGHT;
		} else if (level == 1) {
			defaultBorderWalls();
			verticalWallsCenter();
			startDirection = Direction.RIGHT;
		} else if (level == 2) {
			defaultBorderWalls();
			verticalWallsOneAndTwoThirds();
			startDirection = Direction.RIGHT;
		} else if (level == 3) {
			defaultBorderWalls();
			smileyWalls();
			startDirection = Direction.RIGHT;
		} else if (level == 4) {
			defaultBorderWalls();
			openSquareWalls();
			startDirection = Direction.RIGHT;
		} else {
			defaultBorderWalls();
			for (int i = 0; i < Defaults.SCREEN_SIZE_HEIGHT / 40; i++) {
				randomWalls();
			}
			startDirection = Direction.RIGHT;
		}
	}

	private void defaultBorderWalls() {

		int diff = Defaults.RECT_SIZE;
		for (int i = 0; i < Defaults.SCREEN_SIZE_WIDTH / diff; i++) {
			// top
			addWall(i * diff, 0);
			// bottom
			addWall(i * diff, Defaults.SCREEN_SIZE_HEIGHT - diff);
		}
		for (int i = 0; i < Defaults.SCREEN_SIZE_HEIGHT / diff; i++) {
			// left
			addWall(0, i * diff);
			// right
			addWall(Defaults.SCREEN_SIZE_WIDTH - diff, i * diff);
		}

	}

	private int relativePos(int base, double factor) {
		return (int) (base * factor / Defaults.RECT_SIZE) * Defaults.RECT_SIZE;
	}

	private int relativeSize(int base, double factor) {
		return (int) (base * factor / Defaults.RECT_SIZE);
	}

	private void verticalWallsCenter() {
		int size = relativeSize(Defaults.SCREEN_SIZE_HEIGHT, 3.0 / 5);
		int startY = relativePos(Defaults.SCREEN_SIZE_HEIGHT, 1.0 / 5);
		int middleX = relativePos(Defaults.SCREEN_SIZE_WIDTH, 1.0 / 2);

		for (int i = 0; i < size; i++) {
			addWall(middleX, startY + i * Defaults.RECT_SIZE);
		}
	}

	private void verticalWallsOneAndTwoThirds() {
		int size = relativeSize(Defaults.SCREEN_SIZE_HEIGHT, 3.0 / 5);
		int startY = relativePos(Defaults.SCREEN_SIZE_HEIGHT, 1.0 / 5);
		int onethirdX = relativePos(Defaults.SCREEN_SIZE_WIDTH, 1.0 / 3);
		int twothirdX = relativePos(Defaults.SCREEN_SIZE_WIDTH, 2.0 / 3);

		for (int i = 0; i < size; i++) {
			addWall(onethirdX, startY + i * Defaults.RECT_SIZE);
			addWall(twothirdX, startY + i * Defaults.RECT_SIZE);
		}
	}

	private void smileyWalls() {
		int diff = Defaults.RECT_SIZE;
		int eyesX1 = relativePos(Defaults.SCREEN_SIZE_WIDTH, 1.0 / 3);
		int eyesX2 = relativePos(Defaults.SCREEN_SIZE_WIDTH, 2.0 / 3);
		int eyesY = relativePos(Defaults.SCREEN_SIZE_HEIGHT, 1.0 / 5) - 1 * diff;

		// eyes
		/*
		 * for (int x=0; x<4; x++) { for (int y=0; y<4; y++) { addWall(eyesX1 -
		 * (x+1)*diff, eyesY + y*diff); addWall(eyesX2 + x*diff, eyesY + y*diff); } }
		 */

		walls.add(new Rectangle(eyesX1 - 4 * diff, eyesY, 4, 4));
		walls.add(new Rectangle(eyesX2, eyesY, 4, 4));

		// mouth - horizontal line
		int mouthX = eyesX1 - 4 * diff;
		int mouthY = relativePos(Defaults.SCREEN_SIZE_HEIGHT, 2.0 / 3);
		int size = 7 + (eyesX1 / diff);

		for (int i = 0; i < size; i++) {
			addWall(mouthX + i * diff, mouthY);
		}
		for (int i = 1; i < size; i++) {
			addWall(mouthX + i * diff, mouthY + diff);
		}
		for (int i = 2; i < size - 1; i++) {
			addWall(mouthX + i * diff, mouthY + diff * 2);
		}

		// mouth - 2 vertical lines
		for (int i = 0; i < 2; i++) {
			addWall(mouthX, mouthY - i * diff);
			addWall(mouthX + size * diff, mouthY - i * diff);
		}
	}

	private void openSquareWalls() {
		int diff = Defaults.RECT_SIZE;
		int sizeX = relativeSize(Defaults.SCREEN_SIZE_WIDTH, 3.0 / 5);
		int sizeY = relativeSize(Defaults.SCREEN_SIZE_HEIGHT, 3.0 / 5);
		int startX1 = relativePos(Defaults.SCREEN_SIZE_WIDTH, 1.0 / 5);
		int startY1 = relativePos(Defaults.SCREEN_SIZE_HEIGHT, 1.0 / 5);
		int startX2 = relativePos(Defaults.SCREEN_SIZE_WIDTH, 4.0 / 5);
		int startY2 = relativePos(Defaults.SCREEN_SIZE_HEIGHT, 4.0 / 5);

		walls.add(new Rectangle(startX1 + 2 * diff, startY1 - 1 * diff, sizeX - 4, 1));
		walls.add(new Rectangle(startX1 + 2 * diff, startY2, sizeX - 4, 1));
		walls.add(new Rectangle(startX1 - 1 * diff, startY1 + 2 * diff, 1, sizeY - 4));
		walls.add(new Rectangle(startX2, startY1 + 2 * diff, 1, sizeY - 4));
	}

	private void randomWalls() {
		Random r = new Random();
		int max = Defaults.SCREEN_SIZE_HEIGHT - (Defaults.RECT_SIZE * 16);

		int x = r.nextInt(max / Defaults.RECT_SIZE) + 2;
		int y = r.nextInt(max / Defaults.RECT_SIZE) + 3;
		int size = r.nextInt(max / 3 / Defaults.RECT_SIZE) + 6;
		// System.out.format("size %d x %d y %d %n", size, x, y);

		for (int i = 0; i < size; i++) {
			addWall((x + i) * Defaults.RECT_SIZE, y * Defaults.RECT_SIZE);
		}

	}

	private void addWall(int x, int y) {
		// System.out.println("Wall @ " + x + "," + y);
		walls.add(new Rectangle(x, y));
	}

	public Direction getStartDirection() {
		return startDirection;
	}

	public void draw(Graphics2D g) {
		for (Shape shape : walls) {
			g.setColor(WALL_COLOR);
			g.fill(shape);
		}
	}

	public List<Shape> getWalls() {
		return walls;
	}
}
