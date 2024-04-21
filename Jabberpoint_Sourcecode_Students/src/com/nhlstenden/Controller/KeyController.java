package com.nhlstenden.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import com.nhlstenden.Viewer.Events;
import com.nhlstenden.Viewer.Mediator;

/** <p>This is the KeyController (KeyListener)</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class KeyController extends KeyAdapter {
	private final Mediator mediator;
	public KeyController(Mediator mediator) {
		this.mediator = mediator;
	}

	/**
	 * Executes a function when a certain key is pressed.
	 * @param keyEvent the event to be processed
	 */
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()) {
			case KeyEvent.VK_PAGE_DOWN:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_ENTER:
			case '+':
				mediator.update(Events.NEXT_SLIDE, 0);
				break;
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_UP:
			case '-':
				mediator.update(Events.PREV_SLIDE, 0);
				break;
			case 'q':
			case 'Q':
				mediator.update(Events.EXIT, 0);
				break; //Should not be reached
			default:
				break;
		}
	}
}
