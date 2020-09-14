package org.unlishema.simpleKeyHandler;

/**
 * <pre>
 * This is a Simple Key Listener to be used with SimpleKeyHandler.
 * </pre>
 * 
 * <pre>
 * This Listener is based off of the sketches default methods as well as java's AWT Events. Once a Listener is registered with the Handler, when a key is typed all Listeners onKeyTyped method is executed, so make sure you take this into account when creating your Listeners.
 * </pre>
 * 
 * <pre>
 * You can either create the Listeners as needed since sketches doesn't have to have Objects to run, you can use Java's default feature of implementation, or you can get keys on the Fly.
 * </pre>
 * 
 * <pre>
 * Simply put you can do any of the following to make a Listener or get keys.
 * </pre>
 * 
 * <h3>
 * As Needed:
 * </h3>
 * 
 * <pre style="color: #000;background-color: #D6D6D6;padding: 2px;">
 * SimpleKeyHandler skh = new SimpleKeyHandler();
 * boolean aPressed = false;
 * 
 * void setup() {
 * 	SimpleKeyListener skl = new SimpleKeyListener() {
 * 		void onKeyPressed() {
 * 			aPressed = skh.isModifiedKeyPressed(SimpleKeyHandler.ModifierKey.NONE, 'A');
 * 		}
 * 		
 * 		void onKeyReleased() {}
 * 		
 * 		void onKeyTyped() {}
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
 * SimpleKeyHandler skh = new SimpleKeyHandler();
 * ObjectInGame objectInGame = new ObjectInGame();
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
 * 	void onKeyPressed() {
 * 		aPressed = skh.isModifiedKeyPressed(SimpleKeyHandler.ModifierKey.NONE, 'A');
 * 	}
 * 
 * 	void onKeyReleased() {
 * 	}
 * 
 * 	void onKeyTyped() {
 * 	}
 * }
 * </pre>
 * 
 * <h3>
 * On the Fly:
 * </h3>
 * 
 * <pre style="color: #000;background-color: #D6D6D6;padding: 2px;">
 * SimpleKeyHandler skh = new SimpleKeyHandler();
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
 * @author Unlishema
 */

public interface SimpleKeyListener {
	public void onKeyPressed();

	public void onKeyReleased();

	public void onKeyTyped();
}
