## Simple Key Handler

This is a Library created to make key presses a little more advanced but still simple to use.
 
I have extended the functionality of the KeyEvents to allow you to add Listeners into your sketch, this way you are not limited to a single keyPressed, keyReleased, and keyTyped method. You also don't even have to use the Listeners, they are just there incase you want more functionality.
 
There is a few different functions added to make determining which keys are pressed simple and neat. You can check to see if a single key is being pressed, you can check for multiple different keys being pressed at the same time, and you can check for modifiers being used on these keys like Shift, Alt, Ctrl, and Win.
 
A couple notes to keep in mind is keyPressed and keyReleased are more for controls for the sketch, while keyTyped is more for input of keys into a String, TextField, or similar. You don't have to follow this. The keyPressed method is called on all keys being pressed; however, the keyTyped is only called on specific keys being pressed.
 
This Library does not Override original functionality; however, there may be some "Bugs" that is not yet known. If you happen to find any please report the bug in the issues section on the github.
 
### Examples:
* Click File->Examples... in PDE, then Contributed Libraries->SimpleKeyHandler on Pop-up to see all Examples.
* You can also view or download the FullLibraryTest.pde [here](https://github.com/unlishema/SimpleKeyHandler/tree/master/examples/FullLibraryTest/FullLibraryTest.pde).

### Known Bugs:
* If you hold a key and change windows the key will stay held down until you change back and press it again. (This is being worked on right now)
* If you use the default keyPressed, keyReleased, and keyTyped methods there is an issue. It executes them before keyEvent is executed causing an issue of not registering and deregistering keys before method is executed.

## More Information
If you are wanting to extend this Library for your own Library please take a look at [README_Library.md](https://github.com/unlishema/SimpleKeyHandler/blob/master/README_Library.md).

Also a great resources for starting a Library is [here](https://github.com/processing/processing/wiki/Library-Basics).