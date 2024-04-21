package com.nhlstenden.Controller;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import javax.swing.JOptionPane;
import com.nhlstenden.Viewer.Events;
import com.nhlstenden.Viewer.Mediator;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	private final Frame parent;
	private static final long serialVersionUID = 227L;
	private final Mediator mediator;
	private static final String ABOUT = "About";
	private static final String FILE = "File";
	private static final String EXIT = "Exit";
	private static final String GOTO = "Go to";
	private static final String HELP = "Help";
	private static final String NEW = "New";
	private static final String NEXT = "Next";
	private static final String OPEN = "Open";
	private static final String PAGENR = "Page number?";
	private static final String PREV = "Prev";
	private static final String SAVE = "Save";
	private static final String VIEW = "View";

	public MenuController(Frame frame,Mediator mediator) {
		parent = frame;
		this.mediator = mediator;

		//Add all the different menu's to the menu-bar
		add(createFileMenu());
		add(createViewMenu());
		setHelpMenu(createHelpMenu());		//Needed for portability (Motif, etc.).
	}

	/**
	 * create a menu Item
	 * @param name	name of the menu item
	 * @return		an empty menu-item
	 */
	private MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}

	/**
	 * Create menu for file manipulation
	 * @return 	File-menu
	 */
	private Menu createFileMenu()
	{
		MenuItem menuItem;
		Menu fileMenu = new Menu(FILE);

		fileMenu.add(menuItem = mkMenuItem(OPEN));
		menuItem.addActionListener(actionEvent -> mediator.update(Events.OPEN, 0));

		fileMenu.add(menuItem = mkMenuItem(NEW));
		menuItem.addActionListener(actionEvent -> mediator.update(Events.NEW, 0));

		fileMenu.add(menuItem = mkMenuItem(SAVE));
		menuItem.addActionListener(e -> mediator.update(Events.SAVE, 0));

		fileMenu.addSeparator();

		fileMenu.add(menuItem = mkMenuItem(EXIT));
		menuItem.addActionListener(actionEvent -> mediator.update(Events.EXIT, 0));
		return fileMenu;
	}

	/**
	 * create menu for Presentation manipulation
	 * @return	Presentation-menu
	 */
	private Menu createViewMenu()
	{
		MenuItem menuItem;
		Menu viewMenu = new Menu(VIEW);

		viewMenu.add(menuItem = mkMenuItem(NEXT));
		menuItem.addActionListener(actionEvent -> mediator.update(Events.NEXT_SLIDE, 0));

		viewMenu.add(menuItem = mkMenuItem(PREV));
		menuItem.addActionListener(actionEvent -> mediator.update(Events.PREV_SLIDE, 0));

		viewMenu.add(menuItem = mkMenuItem(GOTO));
		menuItem.addActionListener(actionEvent -> {
			String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
			int pageNumber = Integer.parseInt(pageNumberStr);
			mediator.update(Events.GOTO, pageNumber);
		});

		return viewMenu;
	}

	/**
	 * create menu for Jabberpoint information
	 * @return	help-menu
	 */
	private Menu createHelpMenu()
	{
		MenuItem menuItem;
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(menuItem = mkMenuItem(ABOUT));
		menuItem.addActionListener(actionEvent -> AboutBox.show(parent));

		return helpMenu;
	}
}
