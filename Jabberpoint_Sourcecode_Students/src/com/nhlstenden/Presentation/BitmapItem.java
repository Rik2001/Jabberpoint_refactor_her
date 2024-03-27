package com.nhlstenden.Presentation;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;


/** <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem {
  	private BufferedImage bufferedImage;
  	private String imageName;
  	private static final String FILE = "File ";
  	private static final String NOTFOUND = " not found";


  	//level indicates the item-level; name indicates the name of the file with the image
	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;
		try {
			bufferedImage = ImageIO.read(new File(imageName));
		}
		catch (IOException e) {
			System.err.println(FILE + imageName + NOTFOUND) ;
		}
	}

	//Returns the bounding box of the image
	public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale) {
		Style style = Style.getInstance(this.getLevel());
		return new Rectangle((int) (style.getIndent() * scale), 0,
				(int) (bufferedImage.getWidth(observer) * scale),
				((int) (style.getLeading() * scale)) +
				(int) (bufferedImage.getHeight(observer) * scale));
	}

	//Draws the image
	public void draw(int x, int y, float scale, Graphics graphics, ImageObserver observer) {
		Style style = Style.getInstance(this.getLevel());
		int width = x + (int) (style.getIndent() * scale);
		int height = y + (int) (style.getLeading() * scale);

		graphics.drawImage(bufferedImage, width, height,(int) (bufferedImage.getWidth(observer)*scale),
                (int) (bufferedImage.getHeight(observer)*scale), observer);
	}

	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}

	//Returns the filename of the image
	public String getName() {
		return imageName;
	}
}
