package com.nhlstenden.Presentation;

import java.awt.Color;
import java.awt.Font;

/** <p>Style stands for Indent, Color, Font and Leading.</p>
 * <p>The link between a style number and a item level is hard-linked:
 * in Slide the style is grabbed for an item
 * with a style number the same as the item level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style {
	private static Style[] styles; //All the styles used in this program
	private static final String FONTNAME = "Helvetica";
	private final int indent;
	private final Color color;
	private final Font font;
	private final int fontSize;
	private final int leading;

	/**
	 * constructor is private as to make Class singleton
	 * @param indent	the indent in the styling
	 * @param color		color of the styling
	 * @param points	size of the font
	 * @param leading	scaling of the items
	 */
	private Style(int indent, Color color, int points, int leading) {
		this.indent = indent;
		this.color = color;
		this.font = new Font(FONTNAME, Font.BOLD, fontSize=points);
		this.leading = leading;
	}

	/**
	 * function to get instance of Styles. Function is created in this way as to make sure everyone accesses the same Styles
	 * @param level	Style level which is requested
	 * @return		Style of a certain level
	 */
	public static Style getInstance(int level){
		//Check if styles have been created. If not start creation of styles
		if(styles == null){
			//create styles array
			styles = new Style[5];
			//The styles are always the same
			styles[0] = new Style(0, Color.red,   48, 20);	// style voor item-level 0
			styles[1] = new Style(20, Color.blue,  40, 10);	// style voor item-level 1
			styles[2] = new Style(50, Color.black, 36, 10);	// style voor item-level 2
			styles[3] = new Style(70, Color.black, 30, 10);	// style voor item-level 3
			styles[4] = new Style(90, Color.black, 24, 10);	// style voor item-level 4
		}
		//return requested level
		return styles[level];
	}
	public int getIndent() {
		return indent;
	}

	public Color getColor() {
		return color;
	}

	public Font getFont(float scale) {
		return font.deriveFont(fontSize * scale);
	}

	public int getLeading() {
		return leading;
	}
	public String toString() {
		return "["+ indent + "," + color + "; " + fontSize + " on " + leading +"]";
	}
}
