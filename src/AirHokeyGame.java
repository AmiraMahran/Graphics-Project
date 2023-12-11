


import com.sun.opengl.util.*;

import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;;


public class AirHokeyGame extends JFrame {



        public static void main(String[] args) {
            new AirHokeyGame();

        }

        public  AirHokeyGame() {
            GLCanvas glcanvas;
            Animator animator;
            airhockeyGlEventListener2   listener = new airhockeyGlEventListener2 ();
            glcanvas = new GLCanvas();
            glcanvas.addGLEventListener(listener);
            glcanvas.addKeyListener(listener);
            getContentPane().add(glcanvas, BorderLayout.CENTER);
            animator = new FPSAnimator(30);
            animator.add(glcanvas);
            animator.start();

            setTitle("fishGame Test");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 900);
            setLocationRelativeTo(null);
            setVisible(true);
            setFocusable(true);
            glcanvas.requestFocus();
        }


    }





