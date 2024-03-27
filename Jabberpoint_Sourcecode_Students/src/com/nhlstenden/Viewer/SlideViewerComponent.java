package com.nhlstenden.Viewer;

import com.nhlstenden.Parser.XMLParser;
import com.nhlstenden.Presentation.Presentation;
import com.nhlstenden.Presentation.Slide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
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
	private Font labelFont = null; //The font for labels
	private Presentation presentation = null; //The presentation
	private JFrame frame = null;
	private static final long serialVersionUID = 227L;
	private static final Color BGCOLOR = Color.white;
	private static final Color COLOR = Color.black;
	private static final String FONTNAME = "Dialog";
	private static final int FONTSTYLE = Font.BOLD;
	private static final int FONTHEIGHT = 10;
	private static final int XPOS = 1100;
	private static final int YPOS = 20;
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 800;
	private static final String TESTFILE = "testPresentation.xml";
	private static final String SAVEFILE = "savedPresentation.xml";

	public SlideViewerComponent(Presentation presentation, JFrame frame) {
		setBackground(BGCOLOR); 
		this.presentation = presentation;
		labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
		this.frame = frame;
	}

	@Override
	public void update(Events event, int value) {
		XMLParser xmlParser = new XMLParser();
		switch (event)
		{
			case GOTO:
				this.presentation.setSlideNumber(value - 1);
				break;
			case OPEN:
				this.presentation = xmlParser.loadPresentation(TESTFILE);
				break;
			case SAVE:
				xmlParser.savePresentation(this.presentation, SAVEFILE);
				break;
			case NEXT_SLIDE:
				this.presentation.nextSlide();
				break;
			case PREV_SLIDE:
				this.presentation.prevSlide();
				break;
			case NEW:
				this.presentation.clear();
				break;
			case EXIT:
				System.exit(0);
				break;
			case CREATE:
				break;
			default:

				break;
		}
		this.slide = presentation.getCurrentSlide();
		repaint();
		frame.setTitle(presentation.getTitle());
	}

//Draw the slide
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

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
