package com.nhlstenden.Viewer;

import com.nhlstenden.Controller.KeyController;
import com.nhlstenden.Controller.MenuController;
import com.nhlstenden.Presentation.Presentation;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * <p>The applicatiewindow for a slideviewcomponent</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;
	private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	public final static int WIDTH = 1200;
	private final static int HEIGHT = 800;
	
	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		setupWindow(slideViewerComponent);

		slideViewerComponent.update(Events.CREATE, 0);
	}

	/**
	 * set up the GUI
	 * @param slideViewerComponent component which has the presentation instance
	 */
	public void setupWindow(SlideViewerComponent slideViewerComponent) {
		setTitle(JABTITLE);
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		getContentPane().add(slideViewerComponent);
		addKeyListener(new KeyController(slideViewerComponent)); //Add a controller
		setMenuBar(new MenuController(this, slideViewerComponent));	//Add another controller
		setSize(new Dimension(WIDTH, HEIGHT)); //Same sizes a slide has
		setVisible(true);
	}
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	public static int getPreferredWidth()
	{
		return WIDTH;
	}
	public static int getPreferredHeight()
	{
		return HEIGHT;
	}
}
