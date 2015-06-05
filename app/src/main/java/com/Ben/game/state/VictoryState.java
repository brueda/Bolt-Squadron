package com.Ben.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.Ben.framework.util.Painter;
import com.Ben.framework.util.Renderer;
import com.Ben.simpleandroidgdf.Assets;

/**
 * Created by Benjamin on 6/5/2015.
 */
public class VictoryState extends State {
    @Override
    public void init(){}

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        Renderer.renderBackground(g);
        g.setFont(Typeface.SANS_SERIF, 50);
        g.setColor(Color.YELLOW);
        g.drawString("Victory",100,200);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        setCurrentState(new MenuState());
        return true;
    }
}
