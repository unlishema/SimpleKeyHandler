package org.unlishema.simpleKeyHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 * <h2>Simple Key Handler</h2>
 * 
 * This is a Library created to make key presses a little more advanced but
 * still simple to use.<br>
 * <br>
 * I have extended the functionality of the KeyEvents to allow you to add
 * Listeners into your sketch, this way you are not limited to a single
 * keyPressed, keyReleased, and keyTyped method. You also don't even have to use
 * the Listeners, they are just there incase you want more functionality.<br>
 * <br>
 * There is a few different functions added to make determining which keys are
 * pressed simple and neat. You can check to see if a single key is being
 * pressed, you can check for multiple different keys being pressed at the same
 * time, and you can check for modifiers being used on these keys like Shift,
 * Alt, Ctrl, and Win.<br>
 * <br>
 * A couple notes to keep in mind is keyPressed and keyReleased are more for
 * controls for the sketch, while keyTyped is more for input of keys into a
 * String, TextField, or similar. You don't have to follow this. The keyPressed
 * method is called on all keys being pressed; however, the keyTyped is only
 * called on specific keys being pressed.<br>
 * <br>
 * This Library does not Override original functionality; however, there may be
 * some "Bugs" that is not yet known. If you happen to find any please report
 * the bug in the issues section on the github.<br>
 * 
 * <h3>Examples:</h3>
 * &#183; Click File-&gt;Examples... in PDE, then Contributed
 * Libraries-&gt;SimpleKeyHandler on Pop-up to see all Examples.<br>
 * &#183; You can also view or download the FullLibraryTest.pde <a href=
 * "https://github.com/unlishema/SimpleKeyHandler/tree/master/examples/FullLibraryTest/FullLibraryTest.pde">here</a>.<br>
 * 
 * <h3>Known Bugs:</h3>
 * &#183; If you hold a key and change windows the key will stay held down until
 * you change back and press it again. (This is being worked on right now)<br>
 * &#183; If you use the default keyPressed, keyReleased, and keyTyped methods
 * there is an issue. It executes them before keyEvent is executed causing an
 * issue of not registering and deregistering keys before method is
 * executed.<br>
 * 
 * <h2>More Information</h2>
 * If you are wanting to extend this Library for your own Library please take a
 * look at <a href=
 * "https://github.com/unlishema/SimpleKeyHandler/blob/master/README_Library.md">README_Library.md</a>.<br>
 * <br>
 * Also a great resources for starting a Library is <a href=
 * "https://github.com/processing/processing/wiki/Library-Basics">here</a>.<br>
 * 
 * @author Unlishema
 *
 */
public class SimpleKeyHandler extends SimpleKeyEvent {

	private final PApplet parent;

	private final List<SimpleKeyListener> listeners = Collections.synchronizedList(new ArrayList<SimpleKeyListener>());

	/**
	 * Default and ONLY Constructor of the Simple Key Handler
	 * 
	 * @param parent The PApplet that this Library is being used on (Usually is
	 *               "this")
	 */
	public SimpleKeyHandler(final PApplet parent) {
		this.parent = parent;
		this.parent.registerMethod("keyEvent", this);
		this.parent.registerMethod("focusGained", this);
		this.parent.registerMethod("focusLost", this);
	}

	/**
	 * Added this in so if the function is ever fixed it will fix the focus
	 * issue.<br>
	 * <br>
	 * When we gain focus of the window we want to make sure the keys that are
	 * pressed is empty and ready to be used.
	 */
	// FIXME Make sure we remove all keys being pressed if window loses focus
	// for some reason it don't trigger these event on minimize or anything
	public void focusGained() {
		this.parent.focusGained();
		System.err.println("NOTICE: Let Unlishema know the focusGained() method is fixed!");
		System.out.println("Focus Gained! Resetting keys being Pressed as a precaution.");
		this.clear();
	}

	/**
	 * Added this in so if the function is ever fixed it will fix the focus
	 * issue.<br>
	 * <br>
	 * When we lose focus of the window we want to make sure no keys are being
	 * pressed down.
	 */
	public void focusLost() {
		this.parent.focusLost();
		System.err.println("NOTICE: Let Unlishema know the focusLost() method is fixed!");
		System.out.println("Focus Lost! Resetting keys being Pressed to prevent ghost held keys.");
		this.clear();
	}

	/**
	 * The keyEvent that the sketch uses to execute the events.<br>
	 * <br>
	 * We are just extending its normal functionality.
	 * 
	 * @param e The Event for the keyEvent
	 */
	public void keyEvent(final KeyEvent e) {
		switch (e.getAction()) {
			// When Key is Pressed register key and execute all Listeners onKeyPressed
			case KeyEvent.PRESS:
				int keyCode = e.getKeyCode();
				this.registerKey(keyCode);
				// Override the Escape Key if the user wants it overridden
				if (this.isOverridingEscape() && keyCode == ControlKey.ESCAPE.code)
					this.parent.key = 0;
				if (this.listeners.size() > 0)
					for (SimpleKeyListener skl : this.listeners)
						skl.onKeyPressed(this);
				break;
			// When Key is Released deregister key and execute all Listeners onKeyReleased
			case KeyEvent.RELEASE:
				this.deregisterKey(e.getKeyCode());
				if (this.listeners.size() > 0)
					for (SimpleKeyListener skl : this.listeners)
						skl.onKeyReleased(this);
				break;
			// When Key is Typed update last key typed and execute all Listeners onKeyTyped
			case KeyEvent.TYPE:
				this.updateTypedKey(e.getKey());
				if (this.listeners.size() > 0)
					for (SimpleKeyListener skl : this.listeners)
						skl.onKeyTyped(this);
				break;
			default:
				break;
		}
	}

	/**
	 * Add a new listener into the Handler.<br>
	 * <br>
	 * Here is where you add the listeners that you create into the Handler so the
	 * Handler can execute your code.
	 * 
	 * @param kel new SimpleKeyListener() {}
	 */
	public void addListener(SimpleKeyListener kel) {
		if (!this.listeners.contains(kel))
			this.listeners.add(kel);
	}

	/**
	 * Override the Escape Key functionality that closes the Sketch when you press
	 * Escape<br>
	 * <br>
	 * Default is false
	 * 
	 * @param overrideEscape true to override it, otherwise false
	 */
	public void overrideEscape(boolean overrideEscape) {
		this.overrideEscape = overrideEscape;
	}

	/**
	 * Remove All the Listeners that are in the Handler's List
	 */
	public void removeAllListeners() {
		this.listeners.clear();
	}

	/**
	 * Remove a listener from the List so it won't be executed
	 * 
	 * @param kel The SimpleKeyListener that you want to remove from the Handler
	 */
	public void removeListener(final SimpleKeyListener kel) {
		if (this.listeners.contains(kel))
			this.listeners.remove(kel);
	}

	/**
	 * Prints the Library Information to the Console for Easy Info Checking
	 */
	public void printLibraryInfo() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
	}

	/**
	 * Get the Current Version of the Library in its "Fancy" form
	 * 
	 * @return String
	 */
	public static String version() {
		return "##library.prettyVersion##";
	}

	/**
	 * Get the Current Version of the Library in its "Raw" form
	 * 
	 * @return int
	 */
	public static int versionRaw() {
		return Integer.parseInt("##library.version##");
	}
}
