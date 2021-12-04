package marcclaessens.nibbles;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel implements ScoreListener{
	private static final long serialVersionUID = 1L;

	private JLabel scorelabel = new JLabel();
	
	private int score;
	private int won;
	private int lost;
	
	public ScorePanel() {
		scorelabel.setDoubleBuffered(true);
		this.add(scorelabel);
		updateLabel();
	}
	
	@Override
	public void won() {
		won ++;
		score ++;
		updateLabel();
	}
	
	@Override
	public void restart() {
		score = 0;		
	}
	
	@Override
	public boolean isLevelUp() {
		return score % Defaults.LEVELUP == 0;
	}

	@Override
	public int getLevel() {
		return score / Defaults.LEVELUP ;
	}

	@Override
	public void lost() {
		lost ++;
		updateLabel();
	}
	
	private void updateLabel() {
		String [] lines = 
				{ 		String.format("%d won and %d lost games", won, lost),
						String.format("current level: %d", getLevel())
				};
		
		String br = "<br/>";
		StringBuilder sb = new StringBuilder().append("<html>");
		for (int i=0; i<lines.length; i++) {
			sb.append(lines[i]).append(br);
		}
		sb.append("</html>");
		scorelabel.setText(sb.toString());
		scorelabel.repaint();
	}
}
