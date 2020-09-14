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
public class SimpleKeyHandler {
	/**
	 * Control Keys are the keys that appear inside of key/keyTyped and can messwith
	 * you when you're just wanting to output the key that is getting pressed.To
	 * easily identify and deal with these I have made them into enums that youcan
	 * use. Inside the code Variable is the're keyCode so you can compare them
	 * yourself inside keyTyped.<br>
	 * <br>
	 * 
	 * <pre>
	The Control Keys are:
	&#183; Delete		: 127
	&#183; Backspace	: 8
	&#183; Enter		: 10
	&#183; Return		: 13
	&#183; Tab		: 9
	&#183; Escape		: 27
	 * </pre>
	 * 
	 * <br>
	 * More may be added as they are found.
	 * 
	 * @author Unlishema
	 *
	 */
	public static enum ControlKey {
		DELETE(127), BACKSPACE(8), ENTER(10), RETURN(13), TAB(9), ESCAPE(27);

		public final int code;

		private ControlKey(int code) {
			this.code = code;
		}
	};

	/**
	 * Function Keys are the keys at the top of your keyboard.To easily identify and
	 * deal with these I have made them into enums that youcan use. Inside the code
	 * Variable is the're keyCode so you can compare themyourself inside
	 * keyTyped.<br>
	 * <br>
	 * 
	 * <pre>
	The Function Keys are:
	&#183; F1	: 112
	&#183; F2	: 113
	&#183; F3	: 114
	&#183; F4	: 115
	&#183; F5	: 116
	&#183; F6	: 117
	&#183; F7	: 118
	&#183; F8	: 119
	&#183; F9	: 120
	&#183; F10	: 121
	&#183; F11	: 122
	&#183; F12	: 123
	 * </pre>
	 * 
	 * <br>
	 * More may be added if needed. ATM I have more pressing matters.
	 * 
	 * @author Unlishema
	 *
	 */
	public static enum FunctionKey {
		F1(112), F2(113), F3(114), F4(115), F5(116), F6(117), F7(118), F8(119), F9(120), F10(121), F11(122), F12(123);

		public final int code;

		private FunctionKey(int code) {
			this.code = code;
		}
	};

	/**
	 * Modifer Keys are the different keys on the keyboard that can Modify the key
	 * that is being pressed. Simply put these keys are usually held down before a
	 * key is pressed to Modify them so there can be more and simple controls. A
	 * good example of this is "Ctrl+C" which is commonly used to copy to the
	 * clipboard and "Ctrl+V" which is used to paste whatever is in your
	 * clipboard.<br>
	 * <br>
	 * 
	 * <pre>
	Modifier Keys are:
	&#183; Alternate Only	: ALT
	&#183; Control Only	: CTRL
	&#183; Shift Only	: SHIFT
	&#183; Windows Key Only	: WIN
	As well as ALL possible combinations of the above
	 * </pre>
	 * 
	 * <br>
	 * NOTICE: The Windows Key can make the program lose focus and cause a glitch.
	 * 
	 * @author Unlishema
	 *
	 */
	public static enum ModifierKey {
		NONE(0), ALT(18), CTRL(17), SHIFT(16), WIN(524), ALT_CTRL(0), ALT_SHIFT(0), ALT_WIN(0), CTRL_SHIFT(0),
		CTRL_WIN(0), SHIFT_WIN(0), ALT_CTRL_SHIFT(0), ALT_CTRL_WIN(0), ALT_SHIFT_WIN(0), CTRL_SHIFT_WIN(0),
		ALT_CTRL_SHIFT_WIN(0);

		public final int code;

		private ModifierKey(int code) {
			this.code = code;
		}
	};

	private final PApplet parent;

	private final List<SimpleKeyListener> listeners = Collections.synchronizedList(new ArrayList<SimpleKeyListener>());
	private final List<Integer> keysPressed = Collections.synchronizedList(new ArrayList<Integer>());
	private boolean overrideEscape = false;
	private char lastKeyTyped = Character.MIN_VALUE;

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
	public void focusGained() {
		this.parent.focusGained();
		System.err.println("NOTICE: Let Unlishema know the focusGained() method is fixed!");
		System.out.println("Focus Gained! Resetting keys being Pressed as a precaution.");
		if (isAnyKeyPressed())
			this.keysPressed.clear();
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
		if (isAnyKeyPressed())
			this.keysPressed.clear();
	}

	/**
	 * The keyEvent that the sketch uses to execute the events.<br>
	 * <br>
	 * We are just extending its normal functionality.
	 * 
	 * @param e The Event for the keyEvent
	 */
	// TODO Make sure we remove all keys being pressed if window loses focus
	public void keyEvent(final KeyEvent e) {
		switch (e.getAction()) {
			// When Key is Pressed register key and execute all Listeners onKeyPressed
			case KeyEvent.PRESS:
				int keyCode = e.getKeyCode();
				this.registerKey(keyCode);
				// Override the Escape Key if the user wants it overridden
				if (this.overrideEscape && keyCode == ControlKey.ESCAPE.code)
					this.parent.key = 0;
				if (this.listeners.size() > 0)
					for (SimpleKeyListener skl : this.listeners)
						skl.onKeyPressed();
				break;
			// When Key is Released deregister key and execute all Listeners onKeyReleased
			case KeyEvent.RELEASE:
				this.deregisterKey(e.getKeyCode());
				if (this.listeners.size() > 0)
					for (SimpleKeyListener skl : this.listeners)
						skl.onKeyReleased();
				break;
			// When Key is Typed update last key typed and execute all Listeners onKeyTyped
			case KeyEvent.TYPE:
				this.updateTypedKey(e.getKey());
				if (this.listeners.size() > 0)
					for (SimpleKeyListener skl : this.listeners)
						skl.onKeyTyped();
				break;
			default:
				break;
		}
	}

	/**
	 * Deregister the Key with the Handler so it knows the key is being
	 * Released
	 * 
	 * @param index keyCode of the key being released
	 */
	private void deregisterKey(int index) {
		if (this.isKeyPressed(index))
			this.keysPressed.remove((Object) index);
	}

	/**
	 * Register the Key with the Handler so it knows the key is Pressed Down
	 * 
	 * @param index keyCode of the key being pressed
	 */
	private void registerKey(int index) {
		if (!this.isKeyPressed(index))
			this.keysPressed.add(index);
	}

	/**
	 * Update the Last Key Typed in the Handler.
	 * 
	 * @param c The Key to change the Last Key Typed to
	 */
	private void updateTypedKey(char c) {
		this.lastKeyTyped = c;
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
	 * Get the Last Key that was Typed.<br>
	 * <br>
	 * This function is only useful for the keyTyped method. It will return the last
	 * key that was typed so you can use it as you please.
	 * 
	 * @return Last key that was typed or Character.MIN_VALUE if nothing has been
	 *         typed yet
	 */
	public char getLastKeyTyped() {
		return this.lastKeyTyped;
	}

	/**
	 * Check and see if ANY key is pressed down at this current time
	 * 
	 * @return true if ANY key is press, otherwise false
	 */
	public boolean isAnyKeyPressed() {
		return this.keysPressed.size() > 0;
	}

	/**
	 * Check and see if a specific key is pressed.<br>
	 * <br>
	 * This will make sure that the key you specify is pressed.
	 * 
	 * @param index The key you want to check and see if they are pressed
	 * @return true if the key you declared is pressed, otherwise false
	 */
	public boolean isKeyPressed(int index) {
		if (this.isAnyKeyPressed())
			return this.keysPressed.contains((Object) index);
		return false;
	}

	/**
	 * Check and see if a specific Control Key is pressed.<br>
	 * <br>
	 * This will make sure that the Control Key you specify is pressed.
	 * 
	 * @param controlKey The Control Key you want to check and see if they are
	 *                   pressed
	 * @return true if the Control Key you declared is pressed, otherwise false
	 */
	public boolean isKeyPressed(ControlKey controlKey) {
		return this.isKeyPressed(controlKey.code);
	}

	/**
	 * Check and see if a specific Function Key is pressed.<br>
	 * <br>
	 * This will make sure that the Function Key you specify is pressed.
	 * 
	 * @param functionKey The Function Key you want to check and see if they are
	 *                    pressed
	 * @return true if the Function Key you declared is pressed, otherwise false
	 */
	public boolean isKeyPressed(FunctionKey functionKey) {
		return this.isKeyPressed(functionKey.code);
	}

	/**
	 * <pre>
	 * Check and see if a specific Modifier Key is pressed.
	 * </pre>
	 * 
	 * <pre>
	 * This will make sure that the Modifier Key you specify is pressed.
	 * </pre>
	 * 
	 * <pre>
	 * Note: This will ONLY work for SHIFT, ALT, and CTRL. The Combination versions
	 * have undefined behavior here.
	 * </pre>
	 * 
	 * @param modifierKey The Modifier Key you want to check and see if they are
	 *                    pressed
	 * @return true if the Modifier Key you declared is pressed, otherwise false
	 */
	public boolean isKeyPressed(ModifierKey modifierKey) {
		return this.isKeyPressed(modifierKey.code);
	}

	/**
	 * Check and see if a group of specific keys are pressed, ANY keys.<br>
	 * <br>
	 * This will make sure that all the keys you specify are pressed.
	 * 
	 * @param indexies The keys you want to check and see if they are pressed
	 * @return true ONLY if ALL the keys you declared are pressed, otherwise false
	 */
	public boolean isKeysPressed(int... indexies) {
		if (this.isAnyKeyPressed()) {
			for (int index : indexies)
				if (!this.isKeyPressed(index))
					return false;
			return true;
		}
		return false;
	}

	/**
	 * Check and see if a specific key is pressed, with a modifier.<br>
	 * <br>
	 * This will make sure that not only the key you specify is pressed, but
	 * it will also check to make sure the modifiers you want are pressed.
	 * 
	 * @param modifier The Modifier you want to check if it is pressed
	 * @param index    The key you want to check and see if they are pressed
	 * @return true if ONLY the modifier you specify is pressed and the key you
	 *         declared is pressed, otherwise false
	 */
	public boolean isModifiedKeyPressed(ModifierKey modifier, int index) {
		return this.isModifierPressed(modifier) && this.isKeyPressed(index);
	}

	/**
	 * Check and see if a specific Control key is pressed, with a modifier.<br>
	 * <br>
	 * This will make sure that not only the key you specify is pressed, but
	 * it will also check to make sure the modifiers you want are pressed.
	 * 
	 * @param modifier   The Modifier you want to check if it is pressed
	 * @param controlKey The Control Key you want to check and see if they are
	 *                   pressed
	 * @return true if ONLY the modifier you specify is pressed and the key you
	 *         declared is pressed, otherwise false
	 */
	public boolean isModifiedKeyPressed(ModifierKey modifier, ControlKey controlKey) {
		return this.isModifierPressed(modifier) && this.isKeyPressed(controlKey);
	}

	/**
	 * Check and see if a specific Function key is pressed, with a modifier.<br>
	 * <br>
	 * This will make sure that not only the key you specify is pressed, but
	 * it will also check to make sure the modifiers you want are pressed.
	 * 
	 * @param modifier    The Modifier you want to check if it is pressed
	 * @param functionKey The Function Key you want to check and see if they are
	 *                    pressed
	 * @return true if ONLY the modifier you specify is pressed and the key you
	 *         declared is pressed, otherwise false
	 */
	public boolean isModifiedKeyPressed(ModifierKey modifier, FunctionKey functionKey) {
		return this.isModifierPressed(modifier) && this.isKeyPressed(functionKey);
	}

	/**
	 * Check and see if a group of specific keys are pressed, with a modifier.<br>
	 * <br>
	 * This will make sure that not only all the keys you specify are pressed, but
	 * it will also check to make sure the modifiers you want are pressed.
	 * 
	 * @param modifier The Modifier you want to check if it is pressed
	 * @param indexies The keys you want to check and see if they are pressed
	 * @return true if ONLY the modifier you specify is pressed and ALL the keys you
	 *         declared are pressed, otherwise false
	 */
	public boolean isModifiedKeysPressed(ModifierKey modifier, int... indexies) {
		if (this.isAnyKeyPressed() && this.isModifierPressed(modifier))
			return this.isKeysPressed(indexies);
		return false;
	}

	/**
	 * Check and see if the Modifier is Pressed.<br>
	 * <br>
	 * When using this function, if you declare ALT as the modifier being checked,
	 * it will only return true if ONLY ALT is pressed and no other modifiers are
	 * pressed.
	 * 
	 * @param modifier The Modifier you want to check if it is pressed
	 * @return true if ONLY the modifier you specify is pressed, otherwise false
	 */
	public boolean isModifierPressed(ModifierKey modifier) {
		switch (modifier) {
			case NONE:
				return !this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case ALT:
				return this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case CTRL:
				return !this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case SHIFT:
				return !this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case WIN:
				return !this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			case ALT_CTRL:
				return this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case ALT_SHIFT:
				return this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case ALT_WIN:
				return this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			case CTRL_SHIFT:
				return !this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case CTRL_WIN:
				return !this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			case SHIFT_WIN:
				return !this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			case ALT_CTRL_SHIFT:
				return this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && !this.isKeyPressed(ModifierKey.WIN);
			case ALT_CTRL_WIN:
				return this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& !this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			case ALT_SHIFT_WIN:
				return this.isKeyPressed(ModifierKey.ALT) && !this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			case CTRL_SHIFT_WIN:
				return !this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			case ALT_CTRL_SHIFT_WIN:
				return this.isKeyPressed(ModifierKey.ALT) && this.isKeyPressed(ModifierKey.CTRL)
						&& this.isKeyPressed(ModifierKey.SHIFT) && this.isKeyPressed(ModifierKey.WIN);
			default:
				break;
		}
		return false;
	}

	/**
	 * Check if we are Overriding the Escape Key
	 * 
	 * @return true if we are overriding, otherwise false
	 */
	public boolean isOverridingEscape() {
		return this.overrideEscape;
	}

	/**
	 * Prints the Library Information to the Console for Easy Info Checking
	 */
	public void printLibraryInfo() {
		System.out.println("##library.name## ##library.prettyVersion## by ##author##");
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
