package com.nhlstenden.Presentation;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/** <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private String text;
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 800;

//A textitem of int level with text string
	public TextItem(int level, String string) {
		super(level);
		text = string;
	}

	//Returns the AttributedString for the Item
	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());
		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
		return attrStr;
	}

	//Returns the bounding box of an Item
	public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer,
									float scale, Style myStyle) {
		List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
		int xsize = 0, ysize = (int) (myStyle.getLeading() * scale);
        for (TextLayout layout : layouts) {
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize) {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                ysize += (int) bounds.getHeight();
            }
            ysize += (int) (layout.getLeading() + layout.getDescent());
        }
		return new Rectangle((int) (myStyle.getIndent()*scale), 0, xsize, ysize );
	}

	//Draws the item
	public void draw(int x, int y, float scale, Graphics graphics,
					 Style myStyle, ImageObserver o) {
		if (text == null || text.length() == 0) {
			return;
		}
		List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
		Point pen = new Point(x + (int)(myStyle.getIndent() * scale),
				y + (int) (myStyle.getLeading() * scale));
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setColor(myStyle.getColor());
        for (TextLayout layout : layouts) {
            pen.y += (int) layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += (int) layout.getDescent();
        }
	}

	private List<TextLayout> getLayouts(Graphics graphics, Style style, float scale) {
		List<TextLayout> layouts = new ArrayList<TextLayout>();
		AttributedString attrStr = getAttributedString(style, scale);
		Graphics2D g2d = (Graphics2D) graphics;
		FontRenderContext frc = g2d.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
		float wrappingWidth = (WIDTH - style.getIndent()) * scale;
		while (measurer.getPosition() < getText().length()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}
		return layouts;
	}

//Returns the text
	public String getText() {
		return text == null ? "" : text;
	}

	public String toString() {
		return "TextItem[" + getLevel()+","+getText()+"]";
	}
}
