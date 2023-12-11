
import Texture.TextureReader;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.BitSet;
import javax.media.opengl.glu.GLU;

import javax.swing.*;




public class airhockeyGlEventListener2 extends airhokeyListner {
    int index = 0;
    int maxWidth = 600;
    int maxHeight = 900;
    int x = 275, y = 35;

    int x1 = 275, y1 = 810;

    float a = 300;
    float b = 450;
    float slope = 7.0f / 6.0f;
    float xDisk = a, yDisk = b;
    boolean movingRight = true;
    boolean movingUp = true;
    String only1playername , player1name, player2name,playername;

    int speedofDesk;
int i;
int count;
 int animationindex = 4;
 int soundindex = 7;
 boolean d =false;
 boolean u = false;


    float speedX, speedY, speed = 15, min_speed = 3;
    int direction;

    Rectangle ball;
    Rectangle player1;


    String textureNames[] = {"table.jpg", "player1.png", "player2.png", "ball.png","startmenu.jpg","pict2menu.jpg"};
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

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
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
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }

        }
       // player1 = new Rectangle(x, y, 40, 40);
        ball = new Rectangle((int) xDisk - 8, (int) yDisk + 25, 30, 30);
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        //DrawBackground(gl);
        handleKeyPress();
        handleKeyPress2();

            if (movingRight) {
                xDisk += speedX;
                yDisk += speedY;
            }


        DrawStartMenu(gl, animationindex);


        mins_speed();

        BouncingAirHockey();


        //youWin();


//
//        if (movingRight) {
//            if (xDisk < 550) {
//                //speedX = xDisk += 15;
//            } else {
//                movingRight = false;
//                slope *= -1;
//                a = xDisk;
//                b = yDisk;
//            }
//        }
//        if (!movingRight) {
//            if (xDisk > 20) {
//               // speedX = xDisk -= 15;
//            } else {
//                movingRight = true;
//                slope *= -1;
//                a = xDisk;
//                b = yDisk;
//            }
//        }
//        if (movingUp) {
//            if (!(yDisk < 850)) {
//                slope *= -1;
//                a = xDisk;
//                b = yDisk;
//                movingUp = false;
//            }
//        }
//        if (!movingUp) {
//            if (!(yDisk > 20)) {
//                slope *= -1;
//                a = xDisk;
//                b = yDisk;
//                movingUp = true;
//            }
//        }


if(d) {
    DrawBackground(gl);
    DrawDisk(gl, xDisk, yDisk, 3, 1);

    DrawSprite1(gl, x, y, 2, 1);

    DrawSprite1(gl, x1, y1, 1, 1);
}

    }




    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawSprite1(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glRotated(0, 0, 0, -90);
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
        System.out.println(xDisk + " " + yDisk);
        System.out.println(x + " " + y);


    }

    public void DrawSprite2(GL gl, int x1, int y1, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x1 / (maxWidth / 2.0) - 1, y1 / (maxHeight / 2.0) - 0.09, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glRotated(0, 0, 0, -90);
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


    }

    public void DrawDisk(GL gl, float x2, float y2, int index, int scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x2 / (maxWidth / 2.0) - 1, y2 / (maxHeight / 2.0) - 1.0, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
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
    private void DrawStartMenu(GL gl , int index) {
 gl.glEnable(GL.GL_BLEND);
 gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);// Turn Blending On

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



    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);    // Turn Blending On

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


    public boolean collision() {
        return ball.intersects(player1);
    }

    /*
     * KeyListener
     */

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 40) {
                x -= 15;
            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < 550) {
                x += 15;
            }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 20) {
                y -= 15;
            }
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < 400) {
                y += 15;
            }
        }
    }


    public void handleKeyPress2() {

        if (isKeyPressed(KeyEvent.VK_A)) {
            if (x1 > 40) {
                x1-=15;
            }
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            if (x1 < 550) {
                x1+=15;
            }
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            if (y1 >450) {
                y1-=15;
            }
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (y1 < 830) {
                y1+=15;
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        System.out.println(e.getX() + " " + (550-e.getY()));

            if (e.getX() >= 30 && e.getX() <= 495) {
                x = e.getX();

            }
            if (e.getY() >= 420&& e.getY() <= 803) {
               y = 550 - e.getY();

            }




    }


    public void BouncingAirHockey() {

        switch (direction) {
            case 1: // left
                if (xDisk <= 30) {
                    speedX *= -1;
                    speedX -= min_speed;
                    direction = 5; // right
                    if (yDisk > 405) {
                        speedY = min_speed - speedY;
                        direction = 6;
                    }  //if the bot stuck with the ball
                }
                break;
            case 2:  // up-left
                if (xDisk <= 30) {
                    speedX *= -1;
                    speedX -= min_speed;

                    speedY -= min_speed;
                    direction = 4; // up-right
                }
                if (yDisk >= 803) {
                    speedY *= -1;
                    speedX += min_speed;
                    speedY += min_speed;
                    direction = 8; // down-left
                }
                break;
            case 3: //up
                if (yDisk >= 803) {
                    speedY *= -1;
                    speedY += min_speed;
                    direction = 7; // down
                    if ( yDisk > 405) {
                        speedY = min_speed - speedY;
                        direction = 6;
                    }
                }
                break;
            case 4: // up-right
                if (yDisk >= 803) {
                    speedY *= -1;
                    speedX -= min_speed;
                    speedY += min_speed;
                    direction = 6; // down-right
                }
                if (xDisk >= 525) {
                    speedX *= -1;
                    speedX += min_speed;
                    speedY -= min_speed;
                    direction = 2; // up-left
                }
                break;
            case 5: // right
                if (xDisk >= 525) {
                    speedX *= -1;
                    speedX += min_speed;
                    direction = 1; // left
                    if ( yDisk > 405) {
                        speedY = min_speed - speedY;
                        direction = 8;
                    }
                }
                break;
            case 6: // down-right
                if (xDisk >= 525) {
                    speedX *= -1;
                    speedX += min_speed;
                    speedY += min_speed;
                    direction = 8; // down-left
                }
                if (yDisk <= 23) {
                    speedY *= -1;
                    speedX -= min_speed;
                    speedY -= min_speed;
                    direction = 4; // up-right
                }
                break;
            case 7: // down
                if (yDisk <= 23) {
                    speedY *= -1;
                    speedY -= min_speed;
                    direction = 3; // up
                }
                break;
            case 8: // down-left
                if (yDisk <= 23) {
                    speedY *= -1;
                    speedX += min_speed;
                    speedY -= min_speed;
                    direction = 2; // up-left
                }
                if (xDisk <= 30) {
                    speedX *= -1;
                    speedX -= min_speed;
                    speedY += min_speed;
                    direction = 6; // down-right
                }
                break;

        }


    }


    public void mins_speed(){  //function to decrease the speed of the ball
        if (speedX == min_speed || speedY == min_speed
                || speedX == -min_speed || speedY == -min_speed) {
            i++;
            if (i > 30) {
                speedX = 0;
                speedY = 0;
                i = 0;
            }
        }
    }



    public boolean collision_left() {
        return (ball.intersects(new Rectangle(x - 30,  y+ 23, 23, 23)))// (shifting the rec then gives it the width and lenght it needs)
                || ball.intersects(new Rectangle(x1- 30, y1 + 23, 23, 23));
    }

    public boolean collision_up_left() {
        return ball.intersects(new Rectangle(x- 30, y + 60, 30, 30))
                || ball.intersects(new Rectangle(x1 - 20, y1 + 60, 30, 30));
    }

    public boolean collision_up() {
        return ball.intersects(new Rectangle(x+ 8 , y + 60, 23, 23))
                || ball.intersects(new Rectangle(x1 + 8 , y1 + 60, 23, 23));
    }

    public boolean collision_up_right() {
        return ball.intersects(new Rectangle(x+ 23, y + 60, 30, 30))
                || ball.intersects(new Rectangle(x1 + 23, y1 + 60, 30, 30));
    }

    public boolean collision_right() {
        return ball.intersects(new Rectangle(x +30, y + 23, 23, 23))
                || ball.intersects(new Rectangle(x1 + 30, y1 + 23, 23, 23));
    }

    public boolean collision_down_right() {
        return ball.intersects(new Rectangle(x +23, y, 30, 30))
                || ball.intersects(new Rectangle(x1 + 23, y1 , 30, 30));
    }

    public boolean collision_down() {
        return ball.intersects(new Rectangle(x +8,y , 23, 23))
                || ball.intersects(new Rectangle(x1 + 8 , y1 , 23, 23));
    }

    public boolean collision_down_left() {
        return ball.intersects(new Rectangle(x - 30, y , 30, 30))
                || ball.intersects(new Rectangle(x1 - 30, y1 , 30, 30));

    }


    public void hit() {
        if (collision_left()) {
            speedX = -speed;
            speedY = 0;
            direction = 1;
        }

        if (collision_up_left()) {
            speedX = -speed;
            speedY = speed;
            direction = 2;
        }
        if (collision_up()) {
            speedX = 0;
            speedY = speed;
            direction = 3;
        }
        if (collision_up_right()) {
            speedX = speed;
            speedY = speed;
            direction = 4;
        }

        if (collision_right()) {
            speedX = speed;
            speedY = 0;
            direction = 5;
        }
        if (collision_down_right()) {
            speedX = speed;
            speedY = -speed;
            direction = 6;
        }
        if (collision_down()) {
            speedX = 0;
            speedY = -speed;
            direction = 7;
        }
        if (collision_down_left()) {
            speedX = -speed;
            speedY = -speed;
            direction = 8;
        }


    }

    private void youWin() {
        if(xDisk>155&&xDisk<375&&yDisk>20&&yDisk<95){
            count++;
            System.out.println(" ball raa3a");

        }
        if(xDisk>155&&xDisk<375&&yDisk>735&&yDisk<830) {
            System.out.println(" ball raa3a");

        }



}


    @Override
    public void mouseClicked(MouseEvent e) {


            if(animationindex == 4) {
                if (e.getX() >= 100 && e.getX() <= 466 && e.getY() >= 376 && e.getY() <= 485) {
                    int g = -1;
                    while(g < 0){
                        only1playername = JOptionPane.showInputDialog("Enter your name: ");
                        System.out.println(only1playername);
                        if(only1playername == null|| only1playername.length() == 0)
                        continue;
                    else
                        g++;
                        //(playername.length() > 0)
                    }

                    animationindex = 5;
                }

                if (e.getX() >= 89 && e.getX() <= 480 && e.getY() >= 510 && e.getY() <= 621) {

                    playername = "";
                    for(int i = 0 ; i< 2; i++){
                        int g = -1;
                        while(g < 0){
                            playername = JOptionPane.showInputDialog("Enter player " + (i+1) + " name: ");
                            System.out.println(playername);
                            if(playername == null ||playername.length() == 0)
                            continue;
                        else
                            g++;
                        }
                        if (i == 0)
                            player1name = playername;
                        else player2name = playername;

                        playername="";
                    }

                    animationindex = 5;
                }

                if (e.getX() >= 195 && e.getX() <= 389 && e.getY() >= 641 && e.getY() <= 726)
                    animationindex = 6;
                if (e.getX() >= 219 && e.getX() <= 372 && e.getY() >= 745 && e.getY() <= 822)
                    System.exit(0);
            }
            else if(animationindex == 6) {
                if(e.getX() >= 41 && e.getX() <= 228 && e.getY() >= 20 && e.getY() <= 81)
                    animationindex = 4;
            }

            else {
                if (e.getX() >= 207 && e.getX() <= 381 && e.getY() >= 302 && e.getY() <= 397)
                    d = true;
                if (e.getX() >= 154 && e.getX() <= 427 && e.getY() >= 425 && e.getY() <= 518)
                    d = true;
                if (e.getX() >= 161 && e.getX() <= 458 && e.getY() >= 542 && e.getY() <= 640)
                    d = true;
                if (e.getX() >= 194 && e.getX() <= 397 && e.getY() >= 676 && e.getY() <= 767)
                    animationindex = 4;

            }

            System.out.println("x = " + e.getX() + " , y = " + e.getY());
        }






    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



    public void  handel_AI() {
        double c = 0;

        if (xDisk < 350 && xDisk > 50) {
            c = 1.5; //bot movement with the ball direction
        } else {
            if (x1 > 20 && x1 < 350) {
                c = -1.5;//condition if the bot reached the wall return in the opposite direction
            }
        }
        if (xDisk > x1) {
            x1 += c * speedofDesk; //if the ball direction was his right
        }
        if (xDisk < x1) {
            x1 -= c * speedofDesk;//if the ball direction was his left
        }

        if (yDisk > 270 && yDisk < y1) {
            y1 -= speedofDesk;//to play only in his side
        } else if (y1 < 490) {
            y1 += speedofDesk;//to return to his original position if the ball was not in his side
}

}
}