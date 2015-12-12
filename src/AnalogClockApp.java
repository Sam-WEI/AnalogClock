import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class AnalogClockApp{
	
	private JFrame mainFrame;
	private ClockPanal clockPanel;
	private JPanel controlPanel;
	private Timer timer;
	private JLabel timeLabel;
	private JLabel dateLabel;
	
	private Calendar calendar;
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat();
	
	private void initUI(){
		calendar = Calendar.getInstance();
		dateFormatter.applyPattern("MMM/dd/yyyy  EEE");
		
		JPanel panalAll = new JPanel();
		panalAll.setLayout(new BorderLayout());
		
		clockPanel = new ClockPanal();
		panalAll.add(clockPanel, BorderLayout.CENTER);
		
		controlPanel = new JPanel();
		controlPanel.setBackground(Color.WHITE);
		controlPanel.setLayout(new GridLayout(7, 1));
		controlPanel.setPreferredSize(new Dimension(150, 50));
		timeLabel = new JLabel();
		controlPanel.add(timeLabel);
		
		dateLabel = new JLabel();
		controlPanel.add(dateLabel);
		
		panalAll.add(controlPanel, BorderLayout.EAST);
		
		
		
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle("Analog Clock");
		mainFrame.getContentPane().add(panalAll);
		mainFrame.setSize(500, 300);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				stopClock();
			}

		});
		mainFrame.setVisible(true);
		
		clockPanel.init();
		startClock();
		
	}
	
	
	public void startClock(){
		timer = new Timer();
		timer.schedule(new ClockRun(), 0, 1000);
	}
	
	public void stopClock(){
		if(timer != null){
			timer.cancel();
		}
	}
	
	private class ClockRun extends TimerTask{
		@Override
		public void run() {
			clockPanel.setTime(System.currentTimeMillis());
			
			calendar.setTimeInMillis(System.currentTimeMillis());
			
			int hour = calendar.get(Calendar.HOUR);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);
			int AM_PM = calendar.get(Calendar.AM_PM);
			String strAM_PM = (AM_PM == Calendar.AM ? "AM" : "PM");
			timeLabel.setText(String.format("%02d:%02d:%02d %s", hour, minute, second, strAM_PM));
			
			dateLabel.setText(dateFormatter.format(calendar.getTime()));
			
			
		}
	}
	
	public static void main(String[] args) {
		AnalogClockApp clockApplet = new AnalogClockApp();
		
		clockApplet.initUI();
	}
	
	
	
	
/*	public void cuckoo(){
		AudioClip ac = getAudioClip(getCodeBase(), "..\\sounds\\Cuckoo.wav");
		ac.play();
	}
	
	@Override
	public void init() {
		initUI();
	}	*/
}
