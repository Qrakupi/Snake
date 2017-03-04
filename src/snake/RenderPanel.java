package snake;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
	public static int bgColor=420;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(bgColor));
		g.fillRect(0, 0, 800, 700);
		Snake snake=Snake.snake;
		g.setColor(Color.RED);
		for(Point point:snake.SnakeParts){
			g.fillRect(point.x*snake.SCALE, point.y*snake.SCALE,
					snake.SCALE,snake.SCALE);
		}
		g.fillRect(snake.head.x*snake.SCALE, snake.head.y*snake.SCALE,
				snake.SCALE,snake.SCALE);
		g.setColor(Color.BLACK);
		g.fillRect(snake.cherry.x*snake.SCALE, snake.cherry.y*snake.SCALE
				, snake.SCALE, snake.SCALE);
			String score=String.format("Score:%d Snake Length:%d Time:%d"
					,snake.score,snake.tailLength,snake.ticks/20);
			g.setColor(Color.WHITE);
			g.drawString(score, (int)snake.dim.getWidth()/2 - score.length()*3, 20);
			if(snake.over){
				snake.stopGame();
			}
	}
}
