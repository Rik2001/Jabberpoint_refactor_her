package com.nhlstenden.Presentation;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/** <p>The abstract class for items on a slide.<p>
 * <p>All SlideItems have drawing capabilities.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public abstract class SlideItem {
	private int level; //The level of the SlideItem
	public SlideItem(int level) {
		this.level = level;
	}

//Returns the level
	public int getLevel() {
		return level;
	}

	/**
	 * returns the area of a SlideItem
	 * @param graphics graphics
	 * @param observer for working with images
	 * @param scale the scale of the jabberpoint slide
	 * @return Rectangle with area of the slide
	 */
	public abstract Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale);

//Draws the item
	public abstract void draw(int x, int y, float scale, Graphics graphics, ImageObserver observer);
}
