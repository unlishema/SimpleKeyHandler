package org.unlishema.simpleKeyHandler;

/**
 * This is a SimpleKeyListener to be used with SimpleKeyHandler.<br>
 * <br>
 * This Listener is based off of the sketches default methods as well as java's
 * AWT Events. Once a Listener is registered with the Handler, when a key is
 * typed all Listeners onKeyTyped method is executed, so make sure you take this
 * into account when creating your Listeners.<br>
 * <br>
 * You can either create the Listeners as needed since sketches doesn't have to
 * have Objects to run, you can use Java's default feature of implementation, or
 * you can get keys on the Fly.<br>
 * <br>
 * Simply put you can do any of the following to make a Listener or get
 * keys.<br>
 * <br>
 * 
 * <h3>
 * As Needed:
 * </h3>
 * 
 * <pre style="color: #000;background-color: #D6D6D6;padding: 2px;">
 * final SimpleKeyHandler skh = new SimpleKeyHandler(this);
 * boolean aPressed = false;
 * 
 * void setup() {
 * 	SimpleKeyListener skl = new SimpleKeyListener() {
 * 		void onKeyPressed(final SimpleKeyEvent event) {
 * 			aPressed = event.isModifiedKeyPressed(SimpleKeyHandler.ModifierKey.NONE, 'A');
 * 		}
 * 		
 * 		void onKeyReleased(final SimpleKeyEvent event) {}
 * 		
 * 		void onKeyTyped(final SimpleKeyEvent event) {}
 * 	}
 * 	skh.addListener(skl);
 * }
 * 
 * void draw() {
 * 	background(0);
 * 	textAlign(CENTER, CENTER);
 * 	text("Is A Pressed Down: " + aPressed, this.width / 2, this.height / 2);
 * }
 * </pre>
 * 
 * <h3>
 * Java's default feature of implementation:
 * </h3>
 * 
 * <pre style="color: #000;background-color: #D6D6D6;padding: 2px;">
 * final SimpleKeyHandler skh = new SimpleKeyHandler(this);
 * final ObjectInGame objectInGame = new ObjectInGame();
 * boolean aPressed = false;
 * 
 * void setup() {
 * 	skh.addListener(objectInGame);
 * }
 * 
 * void draw() {
 * 	background(0);
 * 	textAlign(CENTER, CENTER);
 * 	text("Is A Pressed Down: " + aPressed, this.width / 2, this.height / 2);
 * }
 * 
 * class ObjectInGame implements SimpleKeyListener {
 * 	void onKeyPressed(final SimpleKeyEvent event) {
 * 		aPressed = skh.isModifiedKeyPressed(SimpleKeyHandler.ModifierKey.NONE, 'A');
 * 	}
 * 
 * 	void onKeyReleased(final SimpleKeyEvent event) {
 * 	}
 * 
 * 	void onKeyTyped(final SimpleKeyEvent event) {
 * 	}
 * }
 * </pre>
 * 
 * <h3>
 * On the Fly:
 * </h3>
 * 
 * <pre style="color: #000;background-color: #D6D6D6;padding: 2px;">
 * final SimpleKeyHandler skh = new SimpleKeyHandler(this);
 * 
 * void setup() {
 * }
 * 
 * void draw() {
 * 	background(0);
 * 	textAlign(CENTER, CENTER);
 * 	text("Is A Pressed Down: " + skh.isKeyPressed('A'), this.width / 2, this.height / 2);
 * }
 * </pre>
 * 
 * <h3>
 * 
 * <pre>
 * NOTICE: This will not work until processing adds a keyEventPre() method so that I can register keys before processing calls the keyPressed(), keyReleased(), and keyTyped() methods.
 * </pre>
 * 
 * </h3>
 * 
 * <pre style="color: #000;background-color: #D6D6D6;padding: 2px;">
 * final SimpleKeyHandler skh = new SimpleKeyHandler(this);
 * boolean isAKeyPressed = false;
 * 
 * void setup() {
 * }
 * 
 * public void keyPressed() {
 * 	isAKeyPressed = skh.isKeyPressed('A');
 * }
 * 
 * void draw() {
 * 	background(0);
 * 	textAlign(CENTER, CENTER);
 * 	text("Is A Pressed Down: " + isAKeyPressed, this.width / 2, this.height / 2);
 * }
 * </pre>
 * 
 * @author Unlishema
 */

public interface SimpleKeyListener {
	/**
	 * A function for you to do when a key is pressed. A copy of SimpleKeyEvent is
	 * sent so you can access the system.
	 * 
	 * @param event An Event to access and determine what keys are pressed.
	 */
	public void onKeyPressed(final SimpleKeyEvent event);

	/**
	 * A function for you to do when a key is released. A copy of SimpleKeyEvent is
	 * sent so you can access the system.
	 * 
	 * @param event An Event to access and determine what keys are released.
	 */
	public void onKeyReleased(final SimpleKeyEvent event);

	/**
	 * A function for you to do when a key is typed. A copy of SimpleKeyEvent is
	 * sent so you can access the system.
	 * 
	 * @param event An Event to access and determine what keys are typed.
	 */
	public void onKeyTyped(final SimpleKeyEvent event);
}
