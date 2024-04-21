package com.nhlstenden.Viewer;

import com.nhlstenden.Parser.XMLParser;
import com.nhlstenden.Presentation.Presentation;
import com.nhlstenden.Presentation.Slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;


/** <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent implements Mediator{
		
	private Slide slide; //The current slide
	private Font labelFont; //The font for labels
	private Presentation presentation; //The one and only presentation
	private final JFrame frame; //parent of this slideViewerComponent
	private static final long serialVersionUID = 227L;
	private static final Color BGCOLOR = Color.white;
	private static final Color COLOR = Color.black;
	private static final String FONTNAME = "Dialog";
	private static final int FONTSTYLE = Font.BOLD;
	private static final int FONTHEIGHT = 10;
	private static final int XPOS = 1100;
	private static final int YPOS = 20;
	private static final String TESTFILE = "testPresentation.xml";
	private static final String SAVEFILE = "savedPresentation.xml";

	public SlideViewerComponent(Presentation presentation, JFrame frame) {
		setBackground(BGCOLOR); 
		this.presentation = presentation;
		labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
		this.frame = frame;
	}

	/**
	 * events which can be called to manipulate the Pressentation instance
	 * @param event event type which has been called
	 * @param value a value which belongs to the event and is necessary to execute the event
	 */
	@Override
	public void update(Events event, int value) {
		//TODO: Think of way to change int value to something more generally usable
		XMLParser xmlParser = new XMLParser();
		switch (event)
		{
			case GOTO:
				this.presentation.setSlideNumber(value - 1);
				updateSlideViewerComponent();
				break;
			case OPEN:
				this.presentation = xmlParser.loadPresentation(TESTFILE);
				updateSlideViewerComponent();
				break;
			case SAVE:
				xmlParser.savePresentation(this.presentation, SAVEFILE);
				break;
			case NEXT_SLIDE:
				this.presentation.nextSlide();
				updateSlideViewerComponent();
				break;
			case PREV_SLIDE:
				this.presentation.prevSlide();
				updateSlideViewerComponent();
				break;
			case NEW:
				this.presentation = new Presentation();
				updateSlideViewerComponent();
				break;
			case EXIT:
				System.exit(0);
				break;
			case CREATE:
				updateSlideViewerComponent();
				break;
		}
	}

	/**
	 * function to draw the current Slide again
	 */
	private void updateSlideViewerComponent()
	{
		this.slide = presentation.getCurrentSlide();
		repaint();
		frame.setTitle(presentation.getTitle());
	}

	/**
	 * draw the current slide
	 * @param graphics the <code>Graphics</code> object to protect
	 */
	public void paintComponent(Graphics graphics) {
		graphics.setColor(BGCOLOR);
		graphics.fillRect(0, 0, getSize().width, getSize().height);
		if (presentation.getSlideNumber() < 0 || slide == null) {
			return;
		}
		graphics.setFont(labelFont);
		graphics.setColor(COLOR);
		graphics.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
                 presentation.getSize(), XPOS, YPOS);
		Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
		slide.draw(graphics, area, this);
	}
}
