package marcclaessens.nibbles;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

public class Rectangle extends Rectangle2D.Double {
	private static final long serialVersionUID = 1L;

	public Rectangle(int x, int y) {
		this(x, y, 1, 1);
	}

	public Rectangle(int x, int y, int factorX, int factoryY) {
		super(x, y, factorX * Defaults.RECT_SIZE, factoryY * Defaults.RECT_SIZE);
	}

	
	public static boolean touches(Rectangle source, Collection<? extends Shape> collection) {
		for (Shape shape : collection) {
			if (shape.intersects(source)) {
				return true;
			}
		}
		return false;
	}
}
