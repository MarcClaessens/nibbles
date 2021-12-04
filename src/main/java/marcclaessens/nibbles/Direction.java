package marcclaessens.nibbles;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	// note that argument can be null
	public boolean isOppositeOrSame(Direction direction) {
		switch(this) {
			case UP:
			case DOWN:
				return direction == UP ||direction == DOWN;
			case LEFT:
			case RIGHT:
				return direction == LEFT ||direction == RIGHT;
		}
		return false;
	}
}
