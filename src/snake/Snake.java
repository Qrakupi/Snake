package snake;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener,KeyListener{
	public JFrame Jframe;
	
	public static Snake snake;
	
	public RenderPanel renderPanel;
	
	public Timer timer=new Timer(20,this);
	
	Dimension dim;
	
	public ArrayList<Point> SnakeParts=new ArrayList<Point>();
	
	public Point head,cherry;
		
	public final int UP=0,DOWN=1,LEFT=2,RIGHT=3,SCALE=10;
	
	public int ticks=0,direction=DOWN,score,tailLength=10;
	
	public boolean over=false,paused;

	public Random random;
	
	public Snake(){
		dim=Toolkit.getDefaultToolkit().getScreenSize();
		Jframe=new JFrame("Snake");
		Jframe.setVisible(true);
		Jframe.setSize(805,700);
		Jframe.setLocation(dim.width/2-Jframe.getWidth()/2, dim.height/2-Jframe.getHeight()/2);
		Jframe.add(renderPanel=new RenderPanel());
		Jframe.setResizable(false);
		Jframe.addKeyListener(this);
		Jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startGame();
	}
	public void startGame(){
		over=false;
		paused=false;
		score=0;
		tailLength=0;
		direction=DOWN;
		SnakeParts.clear();
		head=new Point(0,0);
		random=new Random();
		cherry=new Point(random.nextInt(79)
				,random.nextInt(66));
		System.out.println(cherry.x + " ," + cherry.y);
		for(int i=0;i<tailLength;i++){
			SnakeParts.add(new Point(head.x,head.y));
		}
		timer.start();
	}
	public void stopGame(){
		timer.stop();
	}
	public static void main(String[] args){
		snake=new Snake();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		renderPanel.repaint();
		ticks++;
		System.out.println(head.x + " ," + head.y);
		if(ticks%2==0 && head!=null && !over && !paused){
			SnakeParts.add(new Point(head.x,head.y));
			if(direction==UP){
				if(head.y - 1>=0 && noTailAt(head.x,head.y-1)){
					head=new Point(head.x,head.y-1);
				}
				else
					over=true;
			}
			if(direction==DOWN && noTailAt(head.x,head.y+1)){
				if(head.y + 1<67){
					head=new Point(head.x,head.y+1);
				}
				else
					over=true;
			}
			if(direction==LEFT && noTailAt(head.x-1,head.y)){
				if(head.x-1>=0){
					head=new Point(head.x-1,head.y);
				}
				else
					over=true;
			}
			if(direction==RIGHT && noTailAt(head.x+1,head.y)){
				if(head.x+1<80){
					head=new Point(head.x+1,head.y);
				}
				else
					over=true;
			}
			if(SnakeParts.size()>tailLength){
				SnakeParts.remove(0);
			}
			if(cherry!=null){
				if(head.equals(cherry)){
					score+=10;
					tailLength++;
					cherry.setLocation(random.nextInt(79)
							,random.nextInt(66));
					System.out.println(cherry.x + " ," + cherry.y);
				}
			}
		}
	}
	public boolean noTailAt(int x,int y){
		Point comparingPoint=new Point(x,y);
		for(Point part:SnakeParts){
			if(part.equals(comparingPoint)){
				return false;
			}
		}
		return true;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int i=e.getKeyCode();
		if(i==KeyEvent.VK_A && direction!=RIGHT)
			direction=LEFT;
		if(i==KeyEvent.VK_D && direction!=LEFT)
			direction=RIGHT;
		if(i==KeyEvent.VK_W && direction!=DOWN)
			direction=UP;
		if(i==KeyEvent.VK_S && direction!=UP)
			direction=DOWN;
		if(i==KeyEvent.VK_SPACE)
			if(over)
			startGame();
			else{
				if(paused==true){
					paused=false;
					timer.start();
				}
				else{
					paused=true;
					timer.stop();
				}
			}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {		
	}
}
