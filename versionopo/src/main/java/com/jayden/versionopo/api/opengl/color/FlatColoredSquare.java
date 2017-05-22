package com.jayden.versionopo.api.opengl.color;

import com.jayden.versionopo.api.opengl.animation.Square;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Jayden on 2017/5/17.
 */

public class FlatColoredSquare extends Square {
    public FlatColoredSquare(){
        super();
    }
    /**
     * This function draws our square on screen.
     * @param gl
     */
    public void draw(GL10 gl) {
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
        super.draw(gl);
    }
}
