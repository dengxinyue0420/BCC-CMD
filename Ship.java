

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship {
	public double x;
	public double y;
	public double dx;
	public double dy;
	public double direction;
	public long time;
	public double size;
	
	public String id;

	boolean self;
	
	private static int IMAGE_OFFSET1[]  = new int[] {1, 37, 74, 113, 151, 185, 225, 266, 304};
	private static int IMAGE_OFFSET2[]  = new int[] {1, 38, 77, 118, 158, 192, 230, 269, 306};
	private static double INTERVAL = 22.5;
	private static double HALF_INTERVAL = INTERVAL / 2;

	public Ship(double x, double y, double dx, double dy, double size) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.size = size;
		this.time = System.currentTimeMillis();
		// TODO Radian and direction
	}
	
	public void draw(Graphics g) {
		int index = getDirectionImageIndex();
		int width;
		int height = 44;
		if (index < 8) {
			width = IMAGE_OFFSET1[index+1] - IMAGE_OFFSET1[index];
			g.drawImage(BattleField.scv_blue1, (int)(x-width/2), (int)(y-height/2), (int)(x+width/2), (int)(y+height/2), IMAGE_OFFSET1[index],1,(int) IMAGE_OFFSET1[index+1], (int) height, null);
		} else {
			index -= 8;
			width = IMAGE_OFFSET2[index+1] - IMAGE_OFFSET2[index];
			g.drawImage(BattleField.scv_blue2, (int)(x-width/2), (int)(y-height/2), (int)(x+width/2), (int)(y+height/2), IMAGE_OFFSET2[index],1,(int) IMAGE_OFFSET2[index+1], (int) height, null);
		}
	}
	
	public int getDirectionImageIndex() {
		double angle = getDirection();
		angle -= (90 - HALF_INTERVAL);
		if (angle < 0) {
			angle += 360;
		}
		return (int)(angle / INTERVAL);
	}
	
	public double getDirection() {
		return 180.0 * getRadians(dx, dy) / Math.PI;
	}
	
	public static double getRadians(double x, double y) {
		double ret = Math.atan2(y, x);
		if (ret < 0) ret += Math.PI * 2;
		return ret;
	}
}
