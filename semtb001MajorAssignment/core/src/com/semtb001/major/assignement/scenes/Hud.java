package com.semtb001.major.assignement.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.semtb001.major.assignement.Semtb001MajorAssignment;
import com.semtb001.major.assignement.screens.PlayScreen;

/* Class to present a overlay on the game screen showing the number of wheat harvested and the
pause functionality */
public class Hud implements Disposable {

    // HUD stage and viewport objects
    public Stage stage;
    private Viewport viewport;
    private PlayScreen screen;

    // Objects that will be displayed in the HUD
    private Label pause;
    private Label harvested;
    private Label harvestedCountLabel;
    private Label time;
    private Label timeCountLabel;

    // Boolean to track if the paused button has been pressed
    public boolean pausedPressed;

    // Objects for keeping track of time
    public Integer worldTimer;
    private float timeCount;

    // Boolean to track if the time is up
    private boolean timeUp;

    public Hud(SpriteBatch spriteBatch, final PlayScreen playScreen) {

        // Instantiate the viewport and stage objects
        viewport = Semtb001MajorAssignment.viewport;
        screen = playScreen;
        stage = new Stage(viewport, spriteBatch);

        // Set the timeCount to 0 and the worldTimer to 90 (90 seconds for each game)
        timeCount = 0;
        worldTimer = 90;

        // Setup the table that is displayed in the HUD
        Table hudTable = new Table();
        hudTable.top().padTop(30);
        hudTable.setFillParent(true);

        // Setup the labels that will go inside of the table
        pause = new Label("II", Semtb001MajorAssignment.tinyFontFontWhite);
        harvested = new Label("HARVESTED: ", Semtb001MajorAssignment.tinyFontFontWhite);
        harvestedCountLabel = new Label(Integer.toString(screen.getWheatHarvested()), Semtb001MajorAssignment.tinyFontFontWhite);
        time = new Label("TIME: ", Semtb001MajorAssignment.tinyFontFontWhite);
        timeCountLabel = new Label(String.format("%02d", worldTimer), Semtb001MajorAssignment.tinyFontFontWhite);

        // Set the HUD labels to have an opacity of 75% so that the game view isn't as obstructed
        float hudTextAlpha = 0.75f;
        pause.setColor(1, 1, 1, hudTextAlpha);
        harvested.setColor(1, 1, 1, hudTextAlpha);
        harvestedCountLabel.setColor(1, 1, 1, hudTextAlpha);
        time.setColor(1, 1, 1, hudTextAlpha);
        timeCountLabel.setColor(1, 1, 1, hudTextAlpha);

        // Add the labels to the table
        hudTable.add(harvested).padLeft(Semtb001MajorAssignment.PPM * 2);
        hudTable.add(harvestedCountLabel);
        hudTable.add(time).right().expandX();
        hudTable.add(timeCountLabel).padRight(Semtb001MajorAssignment.PPM * 2);
        hudTable.add(pause).right().padRight(Semtb001MajorAssignment.PPM * 2);

        // Add the table to the stage
        stage.addActor(hudTable);

        // Pause label input listener
        pause.addListener(new InputListener() {

            // If the paused label is 'touched down' change the font colour to grey
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pause.setStyle(Semtb001MajorAssignment.tinyFontFontGrey);
                pausedPressed = true;
                return true;
            }

            //If the user touches down on the pause label and drags
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                /* If the user touch is dragged and still on the pause label
                (change to grey font colour and active pausedPressed) */
                if (x > 0 && x < pause.getWidth() && y > 0 && y < pause.getHeight()) {
                    pause.setStyle(Semtb001MajorAssignment.tinyFontFontGrey);
                    pausedPressed = true;
                } else {

                    /* If the user touch is dragged and not over the pause label
                    (de-activate pausedPressed and set the font colour to white) */
                    pause.setStyle(Semtb001MajorAssignment.tinyFontFontWhite);
                    pausedPressed = false;
                }
            }

            /* If the user 'touches up' (lets go of the touch) and pausedPressed is active: pause
            the game and set the font colour back to white */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (pausedPressed) {
                    Semtb001MajorAssignment.playMenuClick();
                    playScreen.setPaused(true);
                }
                pausedPressed = false;
                pause.setStyle(Semtb001MajorAssignment.tinyFontFontWhite);
            }
        });

    }

    // Method to update the HUD
    public void update(float dt){

        // If the world timer is greather than 0, decrease it by 1 every second
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;

                // If the worldTimer reaches 0, set the timeUp to true
            } else {
                timeUp = true;
            }

            // Update the timeCount label to the worldTimer value
            timeCountLabel.setText(String.format("%02d", worldTimer));
            timeCount = 0;
        }

        // Update the harvestedCountLabel to the value of wheat harvested
        harvestedCountLabel.setText(Integer.toString(screen.getWheatHarvested()));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // Getters
    public boolean getTimeUp(){
        return timeUp;
    }

    public float getWorldTimer(){
        return worldTimer;
    }

}
