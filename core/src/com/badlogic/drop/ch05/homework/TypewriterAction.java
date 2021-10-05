package com.badlogic.drop.ch05.homework;

public class TypewriterAction extends SetTextAction{
    private float elapsedTime;
    private float charactersPerSecond;

    public TypewriterAction(String textToDisplay) {
        super(textToDisplay);
        elapsedTime = 0;
        charactersPerSecond = 30;
    }

    @Override
    public boolean act(float delta) {
        elapsedTime +=delta;
        int numberofCharacters = (int) (elapsedTime * charactersPerSecond);
        if (numberofCharacters > textToDisplay.length())
            numberofCharacters = textToDisplay.length();

        String partialText = textToDisplay.substring(0, numberofCharacters);
        DialogBox db = (DialogBox) target;
        db.setText(partialText);

        return (numberofCharacters >= textToDisplay.length());
    }
}
