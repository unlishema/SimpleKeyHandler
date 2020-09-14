import org.unlishema.simpleKeyHandler.*;

SimpleKeyHandler skh = new SimpleKeyHandler(this);

char keyToTest = 'A';
boolean aAnyPressed = false, aNoModifierPressed = false, stillWorks = false;

/**
 * NOTICE: Until I find a way to determine when window loses and gains focus, there is a glitch where a key can be held if pressed before switching to another window
 *     and then releasing the key won't remove it from list. Due to this you could technically make it so a character could walk without holding the key down.
 */

void setup() {
  size(500, 500);
  surface.setResizable(true);

  skh.addListener(new SimpleKeyListener() {
    public void onKeyPressed() {
      aAnyPressed = skh.isKeyPressed(keyToTest);
      aNoModifierPressed = skh.isModifiedKeyPressed(SimpleKeyHandler.ModifierKey.NONE, keyToTest);

      // NOTICE: If you press a modifier key(s) with these 2 keys it will NOT work btw
      if (skh.isModifiedKeysPressed(SimpleKeyHandler.ModifierKey.NONE, SimpleKeyHandler.FunctionKey.F3.code, 'Q')) {
        skh.overrideEscape(!skh.isOverridingEscape());
      }

      // NOTICE: If you press any other key(s) other than just this key it will still work btw
      if (skh.isOverridingEscape() && skh.isKeyPressed(SimpleKeyHandler.ControlKey.ESCAPE)) { // Override Escape
        println("Exit Pressed... Open Menu Instead?");
      }
    }

    public void onKeyReleased() {
      aAnyPressed = skh.isKeyPressed(keyToTest);
      aNoModifierPressed = skh.isModifiedKeyPressed(SimpleKeyHandler.ModifierKey.NONE, keyToTest);
    }

    public void onKeyTyped() {
    }
  }
  );
}

public void keyPressed() {
  stillWorks = keyCode == keyToTest;
}

public void keyReleased() {
  stillWorks = !(keyCode == keyToTest);
}

void draw() {
  background(0);
  textAlign(LEFT, TOP);
  textSize(21);
  text("Overriding Esc (F3+Q): " + skh.isOverridingEscape(), 10, 10);

  int w = this.width * 1/2, h = this.height * 2/3, align = -4, textSpacing = 30;

  textAlign(CENTER, CENTER);
  text("Simple key tester for \"" + this.keyToTest + "\"", w, h + (align++ * textSpacing));
  text("ANY Modifider Pressed: " + aAnyPressed, w, h + (align++ * textSpacing));
  text("NO Modifider Pressed: " + aNoModifierPressed, w, h + (align++ * textSpacing));
  text("Key Pressed Still Works: " + stillWorks, w, h + (align++ * textSpacing));
}
