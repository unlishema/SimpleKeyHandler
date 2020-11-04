import org.unlishema.simpleKeyHandler.*;

char keyToTest = 'A';
String typed = "Press Enter to Type here!";
boolean typing = false, printKeys = false;
final SimpleKeyHandler skh = new SimpleKeyHandler(this);

/**
 * NOTICE: Until I find a way to determine when window loses and gains focus, there is a glitch where a key can be held if pressed before switching to another window
 *     and then releasing the key won't remove it from list. Due to this you could technically make it so a character could walk without holding the key down.
 */

void setup() {
  size(500, 500);
  surface.setResizable(true);

  skh.overrideEscape(false);
  skh.addListener(new SimpleKeyListener() {
    public void onKeyPressed(final SimpleKeyEvent event) {
      if (printKeys) println("Key Pressed: " + keyCode + "[" + key + "]");

      // NOTICE: If you press any other key(s) other than just these 2 keys it will still work btw
      if (event.isKeysPressed(SimpleKeyEvent.FunctionKey.F3.code, 'Q')) {
        skh.overrideEscape(!skh.isOverridingEscape());
      }

      // NOTICE: If you press a modifier key(s) with these 2 keys it will NOT work btw
      if (event.isModifiedKeysPressed(SimpleKeyEvent.ModifierKey.NONE, SimpleKeyEvent.FunctionKey.F3.code, 'P')) {
        printKeys = !printKeys;
      }
    }

    public void onKeyReleased(final SimpleKeyEvent event) {
      if (printKeys) println("Key Released: " + keyCode + "[" + key + "]");
    }

    public void onKeyTyped(final SimpleKeyEvent event) {
      if (printKeys) println("Key Typed: " + keyCode + "[" + key + "]");
      char lastTyped = event.getLastKeyTyped();
      if (skh.isOverridingEscape() && lastTyped == SimpleKeyEvent.ControlKey.ESCAPE.code) { // Override Escape
        if (typing) {
          // Reset for not typing
          typing = false;
          skh.overrideEscape(typing);
          typed = "Press Enter to Type here!";
        } else {
          println("Exit Pressed... Open Menu Instead?");
        }
      } else if (!typing && (lastTyped == SimpleKeyEvent.ControlKey.ENTER.code || lastTyped == SimpleKeyHandler.ControlKey.RETURN.code)) { // Enable Typing
        typing = true;
        skh.overrideEscape(typing);
        typed = "|";
      } else if (typing) { // Control Typing into the string
        if (lastTyped == SimpleKeyEvent.ControlKey.BACKSPACE.code) { // Backspace
          // Remove Caret
          typed = typed.substring(0, typed.length() - 1);

          // Remove Last Character if the length is big enough
          typed = typed.length() > 1 ? typed.substring(0, typed.length() - 1) : "";

          // Add Caret
          typed += "|";
        } else if (lastTyped == SimpleKeyEvent.ControlKey.DELETE.code) { // Delete
          // TODO need to add in a proper caret for this to work
        } else if (lastTyped == SimpleKeyEvent.ControlKey.ENTER.code || lastTyped == SimpleKeyHandler.ControlKey.RETURN.code) { // Enter or Return
          if (!typed.equals("|")) {
            // Remove Caret
            typed = typed.substring(0, typed.length() - 1);

            // Print out what we typed in
            println("You Typed in: \"" + typed + "\"");

            // Reset for not typing
            typing = false;
            skh.overrideEscape(typing);
            typed = "Press Enter to Type here!";
          }
        } else {
          // Remove Caret
          typed = typed.substring(0, typed.length() - 1);

          // Add New Character
          typed += lastTyped;

          // Add Caret
          typed += "|";
        }
      }
    }
  }
  );
}

void draw() {
  background(0);
  textAlign(LEFT, TOP);
  textSize(21);
  text("Typed: " + ((!typing || (this.frameCount % 30 > 0 && this.frameCount % 30 < 15)) ? typed : typed.substring(0, typed.length() - 1)), 10, 10);
  text("Overriding Esc (F3+Q): " + skh.isOverridingEscape(), 10, 50);
  text("Printing Keys (F3+P): " + this.printKeys, 10, 75);

  int w = this.width * 1/2, h = this.height * 2/3, alignX = -1, alignY = -4, textSpacingX = this.width * 1/2, textSpacingY = 30;

  textAlign(CENTER, CENTER);
  text("Check Modifiers for Key \"" + this.keyToTest + "\"", w, h + (-5 * textSpacingY));

  textAlign(LEFT, CENTER);
  text("Alt + Ctrl + Shift: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT_CTRL_SHIFT, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Alt + Ctrl + Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT_CTRL_WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Alt + Shift + Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT_SHIFT_WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Ctrl + Shift + Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.CTRL_SHIFT_WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Atl: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Ctrl: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.CTRL, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Shift: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.SHIFT, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));

  alignX += 2;
  alignY = -4;
  textAlign(RIGHT, CENTER);
  text("None: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.NONE, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Alt + Ctrl: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT_CTRL, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Alt + Shift: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT_SHIFT, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Alt + Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT_WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Ctrl + Shift: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.CTRL_SHIFT, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Ctrl + Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.CTRL_WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
  text("Shift + Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.SHIFT_WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));  
  text("Alt + Ctrl + Shift + Win: " + (!typing && skh.isModifiedKeyPressed(SimpleKeyEvent.ModifierKey.ALT_CTRL_SHIFT_WIN, keyToTest)), w + (alignX * textSpacingX), h + (alignY++ * textSpacingY));
}
