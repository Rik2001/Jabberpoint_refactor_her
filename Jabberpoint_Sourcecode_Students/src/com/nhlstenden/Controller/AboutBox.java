package com.nhlstenden.Controller;

import java.awt.Frame;
import javax.swing.JOptionPane;

/**The About-box for JabberPoint.
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class AboutBox {
	private static final String ABOUTBOXTEXT = """
			JabberPoint is a primitive slide-show program in Java(tm). It
			is freely copyable as long as you keep this notice and
			the splash screen intact.
			Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.
			Adapted by Gert Florijn (version 1.1) and Sylvia Stuurman (version 1.2 and higher) for the OpenUniversity of the Netherlands, 2002 -- now.
			Author's version available from https://www.darwinsys.com/""";

	private static final String TITLE = "About JabberPoint";

	/**
	 * show the aboutBOX
	 * @param parent	the current frame from which this function is called
	 */
	public static void show(Frame parent) {
		JOptionPane.showMessageDialog(parent, ABOUTBOXTEXT, TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
}
