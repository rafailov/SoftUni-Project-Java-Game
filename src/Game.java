

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Game extends GameLoop {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static void main(String[] args) {
	
}
	public void init() {
		setSize(350, 600);
		Thread th = new Thread(this);
		th.start();

		offscreen = createImage(350, 600);
		d = offscreen.getGraphics();
		addKeyListener(this);

	}

	public void paint(Graphics g) {
		g.drawImage(offscreen, 0, 0, this);
		/*
		 * if our Image is loaded we can start it`s visualisation
		 */
		if (loaded) {
			g.drawImage(bg, 0, 0, null);// our background
			g.drawImage(moveRoad2, 0, k1, null);//element for road
			g.drawImage(moveRoad, 0, k2, null);//element for road
			g.drawImage(fallcar, z, c, null);//another cars
			g.drawImage(userCar, x, y, null);//our car
			Font font = new Font("Serif", Font.BOLD, 27);//set font-family and font-size for our text
			String s = Integer.toString(score);
			
			/*
			 * this if is true only when you moved on next level
			 * After you are in next level this variable is false
			 * here is text for next level
			 * example: GOD.. Level 2
			 */
			if (inscriptionLevelTwo == true) {

				font = new Font("Serif", Font.BOLD, 25);
				g.setFont(font);
				g.setColor(Color.BLACK);
				g.drawString("GOD..", 131, 340);
				g.drawString("Level 2", 131, 370);
				g.setColor(Color.GREEN);
				g.drawString("GOD..", 130, 339);
				g.drawString("Level 2", 130, 369);
			}
			
			/*
			 * here we visualize the image GameOver and text with your score
			 * it is true if it have a crash
			 */
			if (collision == true) {
				g.drawImage(gameover, 91, 200, null);
				font = new Font("Serif", Font.BOLD, 25);
				g.setFont(font);
				g.setColor(Color.BLACK);
				g.drawString("Your Score", 111, 340);
				g.drawString("is : " + s, 131, 370);
				g.setColor(Color.GREEN);
				g.drawString("Your Score", 110, 339);
				g.drawString("is : " + s, 130, 369);
			}

			/*
			 * this is your score
			 * it is always visualize
			 */
			g.setFont(font);

			g.setColor(Color.BLACK);
			g.drawString("Score:", 11, 21);
			g.setColor(Color.RED);
			g.drawString("Score:", 10, 20);
			g.setColor(Color.BLACK);
			g.drawString(s, 11, 51);
			g.setColor(Color.RED);
			g.drawString(s, 10, 50);
		}
	}

	public void update(Graphics g) {
		paint(g);
	}

}
