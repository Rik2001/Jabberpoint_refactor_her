package com.nhlstenden.Presentation;

import javax.swing.*;
import java.util.ArrayList;

/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String showTitle; //The title of the presentation
	private final ArrayList<Slide> slides; //An ArrayList with slides
	private int currentSlideNumber; //The number of the current slide

	public Presentation() {
		this.slides = new ArrayList<>();
		this.currentSlideNumber = 0;
	}

	//Navigate to the previous slide unless we are at the first slide
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
		}
	}

	//Navigate to the next slide unless we are at the last slide
	public void nextSlide() {
		if (currentSlideNumber < (slides.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	public int getSize() {
		return slides.size();
	}

	//Return the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	//Returns the number of the current slide
	public int getSlideNumber() {
		return currentSlideNumber;
	}

	//Change the current slide number and report it the window
	public void setSlideNumber(int number) {
		if(number >= 0 && number < slides.size()) {
			currentSlideNumber = number;
		}else{
			JOptionPane.showMessageDialog(new JFrame(),"Slide " + (number + 1) + " is out of range: 1 - "
					+ slides.size() + " and doesn't exist!");
		}
	}

	//Add a slide to the presentation
	public void appendSlide(Slide slide) {
		slides.add(slide);
	}

	//Return a slide with a specific number
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
			return slides.get(number);
	}
}
