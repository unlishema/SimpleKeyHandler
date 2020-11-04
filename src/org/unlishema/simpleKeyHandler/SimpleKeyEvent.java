package org.unlishema.simpleKeyHandler;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A SimpleKeyEvent just holds all the data for the SimpleKeyHandler to process
 * and use. You can access the data through both the Handler and the Event.<br>
 * 
 * @author Unlishema
 *
 */
public class SimpleKeyEvent {
	/**
	 * Control Keys are the keys that appear inside of key/keyTyped and can mess
	 * with you when you're just wanting to output the key that is getting
	 * pressed.To easily identify and deal with these I have made them into enums
	 * that you can use. Inside the code Variable is the're keyCode so you can
	 * compare them yourself inside keyTyped.<br>
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
		DELETE(KeyEvent.VK_DELETE), BACKSPACE(KeyEvent.VK_BACK_SPACE), ENTER(10), RETURN(13), TAB(KeyEvent.VK_TAB),
		ESCAPE(KeyEvent.VK_ESCAPE);

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
		F1(KeyEvent.VK_F1), F2(KeyEvent.VK_F2), F3(KeyEvent.VK_F3), F4(KeyEvent.VK_F4), F5(KeyEvent.VK_F5),
		F6(KeyEvent.VK_F6), F7(KeyEvent.VK_F7), F8(KeyEvent.VK_F8), F9(KeyEvent.VK_F9), F10(KeyEvent.VK_F10),
		F11(KeyEvent.VK_F11), F12(KeyEvent.VK_F12);

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
	 * NOTICE: The Windows & Alt Keys can make the program lose focus and cause a glitch.
	 * 
	 * @author Unlishema
	 *
	 */
	public static enum ModifierKey {
		NONE(0), ALT(KeyEvent.VK_ALT), CTRL(KeyEvent.VK_CONTROL), SHIFT(KeyEvent.VK_SHIFT), WIN(KeyEvent.VK_WINDOWS),
		ALT_CTRL(0), ALT_SHIFT(0), ALT_WIN(0), CTRL_SHIFT(0), CTRL_WIN(0), SHIFT_WIN(0), ALT_CTRL_SHIFT(0),
		ALT_CTRL_WIN(0), ALT_SHIFT_WIN(0), CTRL_SHIFT_WIN(0), ALT_CTRL_SHIFT_WIN(0);

		public final int code;

		private ModifierKey(int code) {
			this.code = code;
		}
	};

	// Private Variables
	private final List<Integer> keysPressed = Collections.synchronizedList(new ArrayList<Integer>());
	private char lastKeyTyped = Character.MIN_VALUE;
	protected boolean overrideEscape = false;

	/**
	 * Clear the KeyEvent of all of the currently pressed keys
	 */
	public void clear() {
		if (this.isAnyKeyPressed()) this.keysPressed.clear();
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
		if (this.isAnyKeyPressed()) return this.keysPressed.contains((Object) index);
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
			for (int index : indexies) if (!this.isKeyPressed(index)) return false;
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
		if (this.isAnyKeyPressed() && this.isModifierPressed(modifier)) return this.isKeysPressed(indexies);
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
	 * Deregister the Key with the Handler so it knows the key is being
	 * Released
	 * 
	 * @param index keyCode of the key being released
	 */
	protected void deregisterKey(int index) {
		if (this.isKeyPressed(index)) this.keysPressed.remove((Object) index);
	}

	/**
	 * Update the Last Key Typed in the Handler.
	 * 
	 * @param c The Key to change the Last Key Typed to
	 */
	protected void updateTypedKey(char c) {
		this.lastKeyTyped = c;
	}

	/**
	 * Register the Key with the Handler so it knows the key is Pressed Down
	 * 
	 * @param index keyCode of the key being pressed
	 */
	protected void registerKey(int index) {
		if (!this.isKeyPressed(index)) this.keysPressed.add(index);
	}
}
