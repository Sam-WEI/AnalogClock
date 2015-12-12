import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;

import javax.swing.JPanel;


public class ClockPanal extends JPanel {
	
	private int CENTER_DOT_RADIUS = 10;
	
	private int centerX;
	private int centerY;
	private int radius;
	private int width;
	private int height;
	
	
	private Calendar calender;
	
	public ClockPanal() {
		calender = Calendar.getInstance();
	}
	
	public void init(){
		System.out.println("init");
		setBackground(Color.WHITE);
		width = getWidth();
		height = getHeight();
		centerX = width / 2;
		centerY = height / 2;
		radius = centerY;
		
	}
	
	public void setTime(long timeInMillis){
		calender.setTimeInMillis(timeInMillis);
		repaint();
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		width = getWidth();
		height = getHeight();
		centerX = width / 2;
		centerY = height / 2;
		radius = Math.min(width, height) / 2;
		
//		g.fillOval(0, 0, getWidth(), getHeight());
		
		drawScales(g);
		
	}
	
	private void drawScales(Graphics g){
		g.setColor(Color.BLACK);
		final int innerCircleR1 = radius - 10;
		g.drawOval(centerX - innerCircleR1, centerY - innerCircleR1, innerCircleR1 * 2,  innerCircleR1 * 2);
		

		for(int i = -89; i <= 270; i++){
			if(i % 6 == 0){
				int innerRadius = (i % 30  == 0 ? radius - 10 : radius - 5);
				
				int x1 = (int) Math.round((Math.cos(Math.toRadians(i)) * innerRadius + centerX));
				int y1 = (int) Math.round((Math.sin(Math.toRadians(i)) * innerRadius + centerY));
				
				int x2 = (int) Math.round((Math.cos(Math.toRadians(i)) * radius + centerX));
				int y2 = (int) Math.round((Math.sin(Math.toRadians(i)) * radius + centerY));
				
				g.drawLine(x1, y1, x2, y2);
				
				
				if(i % 30 == 0) {
					String num = (i + 90) / 30 + "";
					
					int radiusNum = radius - 30;
					int x3 = (int) (Math.cos(Math.toRadians(i)) * radiusNum + centerX);
					int y3 = (int) (Math.sin(Math.toRadians(i)) * radiusNum + centerY);
					
					g.setFont(new Font("Harrington", Font.BOLD, 40));
					
					FontMetrics fm = g.getFontMetrics();  
		            Rectangle2D bound = fm.getStringBounds(num, g); 
					g.drawString(num, (int)(x3 - bound.getWidth() / 2), (int)(y3 + bound.getHeight() / 3));
				}
				
				
			}
			
		}
		
		drawHands(g);
		
		g.setColor(new Color(0xff0000));
		g.fillOval(centerX - CENTER_DOT_RADIUS / 2, centerY - CENTER_DOT_RADIUS / 2, CENTER_DOT_RADIUS, CENTER_DOT_RADIUS);
		
	}
	
	private void drawHands(Graphics g) {
		int hour = calender.get(Calendar.HOUR);
		int min = calender.get(Calendar.MINUTE);
		int sec = calender.get(Calendar.SECOND);
		
		g.setColor(new Color(0x000000));
		drawHand(g, hour * 30 + (int)(min / 60f * 30), 80, 20, 6, 1);//hour hand
		
		drawHand(g, min * 6 + (int)Math.round(sec / 60f * 6), 120, 20, 5, 1);//minute hand
		
		g.setColor(new Color(0xff0000));
		drawHand(g, sec * 6, 120, 35, 2, 1);//second hand
	}
	
	private void drawHand(Graphics g, int degree, int length, int tailLength, int tailWidth, int headWidth){
		int xCenter = centerX;
		int yCenter = centerY;
		
		double xMidTail = xCenter - tailLength * Math.sin(Math.toRadians(degree));
		double yMidTail = yCenter + tailLength * Math.cos(Math.toRadians(degree));
		
		double xMidHead = xCenter + length * Math.sin(Math.toRadians(degree));
		double yMidHead = yCenter - length * Math.cos(Math.toRadians(degree));
		
		double xTail1 = xMidTail - tailWidth/2f * Math.cos(Math.toRadians(degree));
		double yTail1 = yMidTail - tailWidth/2f * Math.sin(Math.toRadians(degree));
		
		double xTail2 = xMidTail + tailWidth/2f * Math.cos(Math.toRadians(degree));
		double yTail2 = yMidTail + tailWidth/2f * Math.sin(Math.toRadians(degree));
		
		double xHead1 = xMidHead - headWidth/2f * Math.cos(Math.toRadians(degree));
		double yHead1 = yMidHead - headWidth/2f * Math.sin(Math.toRadians(degree));
		
		double xHead2 = xMidHead + headWidth/2f * Math.cos(Math.toRadians(degree));
		double yHead2 = yMidHead + headWidth/2f * Math.sin(Math.toRadians(degree));
		
//		g.drawLine(xCenter, yCenter, (int)xTail1, (int)yTail1);
//		g.drawLine(xCenter, yCenter, (int)xTail2, (int)yTail2);
//		
//		g.drawLine(xCenter, yCenter, (int)xHead1, (int)yHead1);
//		g.drawLine(xCenter, yCenter, (int)xHead2, (int)yHead2);
		
		int[] xArr = new int[]{(int) xTail1, (int) xTail2, (int) xHead2, (int) xHead1};
		int[] yArr = new int[]{(int) yTail1, (int) yTail2, (int) yHead2, (int) yHead1};
		
		g.fillPolygon(xArr, yArr, xArr.length);
		
	}
	
	public void playSound(){
		
	}
	
	

}
