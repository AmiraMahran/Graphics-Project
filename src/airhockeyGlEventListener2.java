
import Texture.TextureReader;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.BitSet;
import javax.media.opengl.glu.GLU;


public class airhockeyGlEventListener2 extends airhokeyListner{
int index=0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth / 2, y = maxHeight / 2;
    int x1= maxWidth / 2, y1= maxHeight / 2;

    float a=maxWidth/2;
    float b=maxHeight/2;
    float slope =7.0f/6.0f;
    float xDisk= a, yDisk= b;
    boolean movingRight = true;
    boolean movingUp = true;

    String textureNames[] = {"table.jpg","player1.png","player2.png","ball.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch( IOException e ) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
        handleKeyPress();
       handleKeyPress2();



        yDisk = (slope * (xDisk - a) + b);

        if (movingRight) {
            if (xDisk < 95) {
                xDisk+= 1;
            } else {
                movingRight = false;
                slope *= -1;
                a = xDisk;
                b = yDisk;
            }
        }
        if (! movingRight) {
            if (xDisk > 8) {
                xDisk -= 1;
            } else {
                movingRight = true;
                slope *= -1;
                a = xDisk;
                b = yDisk;
            }
        }
        if (movingUp) {
            if (! (yDisk < 95)) {
                slope *= -1;
                a = xDisk;
                b = yDisk;
                movingUp = false;
            }
        }
        if (! movingUp) {
            if (!(yDisk > 3)) {
                slope *= -1;
                a = xDisk;
                b = yDisk;
                movingUp = true;
            }
        }




//        DrawGraph(gl);
        DrawDisk(gl,xDisk,yDisk,3,1);
        DrawSprite1(gl, x, y, 2, 1);

        DrawSprite2(gl, x1, y1, 1, 1);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawSprite1(GL gl,int x, int y, int index, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 1, y / (maxHeight / 2.0) - 1.9, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        gl.glRotated(0,0,0,-90);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
        System.out.println(x +" "+y);

    }

    public void DrawSprite2(GL gl,int x1, int y1, int index, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x1 / (maxWidth / 2.0) -1, y1 / (maxHeight / 2.0) -0.09, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        gl.glRotated(0,0,0,-90);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
System.out.println(x1 +" "+y1);

    }

    public void DrawDisk(GL gl, float x2,  float y2, int index, int scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated( x2/(maxWidth/2.0)-1, y2/(maxHeight/2.0)-1.0, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);





    }

    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    /*
     * KeyListener
     */

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x> 8) {
                x--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < 93) {
                x++;
           }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 52) {
                y--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < 90) {
                y++;
            }
        }
    }


    public void handleKeyPress2() {

        if (isKeyPressed(KeyEvent.VK_A)) {
            if (x1> 6) {
                x1--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            if (x1 < 92) {
                x1++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            if (y1 > 10) {
                y1--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (y1 < 50) {
                y1++;
            }
        }
    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
}


