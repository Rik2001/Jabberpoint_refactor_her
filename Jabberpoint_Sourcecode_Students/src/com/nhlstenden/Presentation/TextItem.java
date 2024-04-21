package com.nhlstenden.Presentation;

import com.nhlstenden.Viewer.SlideViewerFrame;

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
	private final String text;

	/**
	 * Constructor of a TextItem
	 * @param level level of the style
	 * @param string string of text for the slideItem
	 */
	public TextItem(int level, String string) {
		super(level);
		this.text = string;
	}

	/**
	 * Returns an AttributedString for the textItem
	 * @param scale the scale of the slide
	 * @return String of the textItem with attribute of Font the length of the string
	 */
	public AttributedString getAttributedString(float scale) {
		AttributedString attributedString = new AttributedString(getText());

		Style style = Style.getInstance(this.getLevel());
		attributedString.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
		return attributedString;
	}

	//Returns the bounding box of an Item
	public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer,
									float scale) {
		Style style = Style.getInstance(this.getLevel());
		List<TextLayout> layouts = getLayouts(graphics, scale);
		int xsize = 0, ysize = (int) (style.getLeading() * scale);
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
		return new Rectangle((int) (style.getIndent()*scale), 0, xsize, ysize );
	}

	//Draws the textItem
	public void draw(int x, int y, float scale, Graphics graphics, ImageObserver o) {
		//check if TextItem has any text
		if (text == null || text.isEmpty()) {
			return;
		}

		Style style = Style.getInstance(this.getLevel());
		List<TextLayout> layouts = getLayouts(graphics, scale);
		Point pen = new Point(x + (int)(style.getIndent() * scale), y + (int) (style.getLeading() * scale));
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setColor(style.getColor());

        for (TextLayout layout : layouts) {
            pen.y += (int) layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += (int) layout.getDescent();
        }
	}

	private List<TextLayout> getLayouts(Graphics graphics, float scale) {
		Style style = Style.getInstance(this.getLevel());
		List<TextLayout> layouts = new ArrayList<>();
		AttributedString attrStr = getAttributedString(scale);
		Graphics2D graphics2D = (Graphics2D) graphics;
		FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), fontRenderContext);

		float wrappingWidth = (SlideViewerFrame.getPreferredWidth() - style.getIndent()) * scale;
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
