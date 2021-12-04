package marcclaessens.nibbles;

public interface ScoreListener {
	void won();
	void lost();
	void restart();
	boolean isLevelUp();
	int getLevel();
}
