

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;

public class GameLoop extends Applet implements Runnable, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y, z, c;
	
	public int k1 = -5398;// coordinates for road`s elements
	public int k2 = -5398 - 6000;// coordinates for road`s elements
	public Image offscreen;
	public Graphics d;
	public boolean left, right, down, up;//if any button is keyPressed
	public boolean canleft, canright, candown, canup;// if our car can move
														// there
	public Image bg, gameover;//image for background and GameOver image
	public Image userCar;//image for user`s car
	public Image fallcar;//image for all another car
	public boolean loaded;//if anything is loaded for set drawImage
	int roadbed;//it`s random variable with 1 or 2 for left or right roadbed
	boolean anotherCar = true;//if the fallcar is under the screen we need another car and then this will be true
	int countCar = 0;//How many cars have passed
	public boolean collision = false;//if your and on the PC`s cars are in the collision then this will be true 
	int score = -10;
	public Image moveRoad, moveRoad2; // for road`s elements
	public boolean inscriptionLevelTwo = false;//inscription for next level (level 2 )
	public boolean formulaMode = false;//if we are to level 2 (formulaMode) this variable will be true 

	Random randum = new Random();

	public void run() {
		/*
		 * coordinates for start point on your car
		 */
		x = 185;
		y = 450;

		/*
		 * we need always true while for listening key pressed and movement moving elements 
		 */
		while (true) {
			/*
			 * get element for road
			 */
			movedRoad(formulaMode);
			/*
			 * load your car
			 */
			loadcars(formulaMode);
			/*
			 * if any collision is true -> break
			 */
			collision(x, y, z, c, formulaMode);
			if (collision == true) {
				break;
			}
			
			/*
			 * load PC`s car if it`s don`t have (on random road)
			 */
			if (anotherCar == true) {
				fallcars(formulaMode);
				roadbed = randum.nextInt(2);
				if (roadbed == 1) {
					z = 115;
					c = -80;
				} else {
					z = 185;
					c = -80;
				}

			}

			/*
			 * up to second level when score == ...
			 */
			if (score == 100) {
				x = 185;
				y = 450;
				c = 800;
				countCar = 0;
				formulaMode = true;
				inscriptionLevelTwo = true;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				inscriptionLevelTwo = false;
			}
			/*
			 * moving PC`s cars and road`s element +countCar for acceleration 
			 */
			c += 19 + countCar / 4;
			k1 += 32 + countCar / 3;
			k2 += 32 + countCar / 3;

			/*
			 * if any road`s element is under the screen get new coordinates
			 */
			if (k1 >= 602) {
				k1 = -5398 - 6000;
			}
			if (k2 >= 602) {
				k2 = -5398 - 6000;
			}

			/*
			 * if PC`s car is under the screen we need another car
			 */
			if (c <= 600) {
				anotherCar = false;
			} else {
				anotherCar = true;
			}

			/*
			 * conditions where your car can move 
			 */
			if (x <= 110) {
				canleft = false;
			} else {
				canleft = true;
			}
			if (x >= 186) {
				canright = false;
			} else {
				canright = true;
			}
			if (y >= 480) {
				candown = false;
			} else {
				candown = true;
			}
			if (y <= 20) {
				canup = false;
			} else {
				canup = true;
			}

			/*
			 * if key is pressed and your car can moved there then your car moved
			 */
			if (left == true && canleft == true) {
				x -= 15;
			}
			if (right == true && canright == true) {
				x += 15;
			}
			
			if (up == true && canup == true) {
				y -= 5;
				c += 30;
				k1 += 30;
				k2 += 30;
			}
			if (down == true && candown == true) {
				y += 15;
			}

			try {
				Thread.sleep(105);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * method for collision with different values from different mode on game 
	 */
	public boolean collision(int x, int y, int z, int c, boolean mode) {
		int width;
		int height;
		if (mode) {
			width = 40;
			height = 120;
		} else {
			width = 40;
			height = 77;
		}

		if (x < z + width && x + width >= z && c + height > y && c < y + height) {
			gameover = new ImageIcon("images\\game-over.png")
					.getImage();
			collision = true;
			repaint();
		}

		return collision;
	}
	
	/*
	 * method for load your car
	 */
	private void loadcars(boolean mode) {

		if (mode) {
			userCar = new ImageIcon("images\\f1-ferrari2.png")
					.getImage();
			bg = new ImageIcon("images\\road.jpg").getImage();
		} else {
			userCar = new ImageIcon("images\\car-red.png")
					.getImage();
			bg = new ImageIcon("images\\road2.jpg").getImage();
		}

		loaded = true;
		repaint();
	}

	/*
	 * method for load image for road`s elements
	 */
	public void movedRoad(boolean mode) { 
		if (mode) {
			moveRoad = new ImageIcon("images\\road-moves.png")
					.getImage();
			moveRoad2 = new ImageIcon("images\\road-moves.png")
					.getImage();
		} else {
			moveRoad = new ImageIcon("images\\road-moves2.png")
					.getImage();
			moveRoad2 = new ImageIcon("images\\road-moves2.png")
					.getImage();
		}

		repaint();
	}

	/*
	 * here we load PC`s car on every new car you get 10 more score  
	 */
	public void fallcars(boolean mode) {
		countCar++;
		score += 10;
		if (mode) {
			int i = randum.nextInt(2);
			if (i == 1) {
				fallcar = new ImageIcon("images\\f1-lotus-yellow.png")
						.getImage();
			} else {
				fallcar = new ImageIcon("images\\f1-lotus-blue.png")
						.getImage();
			}
		} else {
			int i = randum.nextInt(2);
			if (i == 1) {
				fallcar = new ImageIcon("images\\car-blue.png")
						.getImage();
			} else {
				fallcar = new ImageIcon("images\\car-yellow.png")
						.getImage();
			}

		}

		repaint();
	}

	/*
	 * if any key is pressed  
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			left = true;
		}
		if (e.getKeyCode() == 39) {
			right = true;
		}
		if (e.getKeyCode() == 38) {
			up = true;
		}
		if (e.getKeyCode() == 40) {
			down = true;
		}

	}

	/*
	 * if any pressed key is released 
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 37) {

			left = false;
		}
		if (e.getKeyCode() == 39) {
			right = false;
		}
		if (e.getKeyCode() == 38) {
			c -= 18;
			k1 -= 25;
			k2 -= 25;
			up = false;
		}
		if (e.getKeyCode() == 40) {
			down = false;
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}
