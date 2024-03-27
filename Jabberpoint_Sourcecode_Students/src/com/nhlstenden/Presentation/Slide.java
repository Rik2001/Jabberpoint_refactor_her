package com.nhlstenden.Presentation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

/** <p>A slide. This class has drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 800;
	private String title; //The title is kept separately
	private Vector<SlideItem> slideItems; //The SlideItems are kept in a vector

	public Slide() {
		slideItems = new Vector<SlideItem>();
	}

	//Draws the slide on a Jcomponent
	public void draw(Graphics graphics, Rectangle area, ImageObserver view) {
		float scale = getScale(area);
		int y = area.y;

		//Draw the Slide Title
		SlideItem slideItem = new TextItem(0, getTitle());							//Create new SlideItem for slide title
		slideItem.draw(area.x, y, scale, graphics, view);								//Draw Title

		for (int slideItemIndex = 0; slideItemIndex < getSize(); slideItemIndex++) {
			y = y + slideItem.getBoundingBox(graphics, view, scale).height;				//Calculate new Y-axis height
			slideItem = getSlideItem(slideItemIndex);									//Get new SlideItem
			slideItem.draw(area.x, y, scale, graphics, view);							//draw the SlideItem
		}
	}

	//Returns the scale to draw a slide
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
	}

	//Add a SlideItem
	public void appendSlideItem(SlideItem slideItem) {
		slideItems.addElement(slideItem);
	}

	public void removeSlidItem(SlideItem slideItem)
	{
		slideItems.remove(slideItem);
	}

	//Return the title of a slide
	public String getTitle() {
		return title;
	}

	//Change the title of a slide
	public void setTitle(String newTitle) {
		title = newTitle;
	}

	//Returns the SlideItem
	public SlideItem getSlideItem(int number) {
		if (number < 0 || number >= getSize()){
			return null;
		}
		return slideItems.elementAt(number);
	}

	//Return all the SlideItems in a vector
	public Vector<SlideItem> getSlideItems() {
		return slideItems;
	}

	//Returns the size of a slide
	public int getSize() {
		return slideItems.size();
	}
	public int getWidth(){
		return WIDTH;
	}
	public int getHeight(){
		return HEIGHT;
	}
}
