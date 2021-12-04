package marcclaessens.nibbles;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.text.NumberFormatter;

public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private String initMem = getMemory();
	
	
	public SettingsPanel(Nibbles game) {
		this.setLayout(new GridLayout(3,1));
		
		this.add(initSpeedPanel(game));
		this.add(initMemory());
		this.add(focusChanger(game));
		
	}
	
	private JComponent focusChanger(Nibbles game) {
		JPanel focusChanger = new JPanel();
		JButton changeFocus = new JButton("set focus to game");
		focusChanger.addMouseListener(new MouseAdapter() {
			 @Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				game.requestFocus();
			}
		});
		changeFocus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.requestFocus();
			}
		});
		
		focusChanger.add(changeFocus);
		return focusChanger;
	}
	
	
	private JComponent initSpeedPanel(Nibbles game) {
		JPanel speedPanel = new JPanel(new FlowLayout());

		JSpinner speedSpinner =  new JSpinner(new SpinnerNumberModel(150, 60, 1000, 10));
		JFormattedTextField txt = ((JSpinner.NumberEditor) speedSpinner.getEditor()).getTextField();
		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

		JLabel speedLabel = new JLabel("speed : ");
		speedPanel.add(speedLabel);
		speedPanel.add(speedSpinner);
		
		return speedPanel;
	}
	
	private JComponent initMemory() {
		JLabel mem = new JLabel();
		mem.setDoubleBuffered(true);
		mem.setAlignmentX(CENTER_ALIGNMENT);

		updateLabel(mem);
		
		new Timer(2000, e -> updateLabel(mem)).start();
		return mem;

	}

		
	private void updateLabel(JLabel mem) {
		String line = String.format("start memory : %s - current memory : %s ", initMem, getMemory());
		mem.setText(line);
		mem.repaint();
	}

	
	public String getMemory() {
		NumberFormat format = NumberFormat.getInstance();
		long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return format.format(mem / 1024 / 1024) + "MB";
	}
}
