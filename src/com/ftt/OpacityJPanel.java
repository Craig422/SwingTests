package com.ftt;



import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by craighowarth on 02/02/15.
 */

public class OpacityJPanel extends JPanel {
    boolean hidden = false;
    int opacity = 0xFF;

    public OpacityJPanel() {
        super();
        setOpaque(false);
    }

    /**
     *
     * @return boolean true if the bg pane is hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     *
     * @param hidden boolean - If true the bg pane will not be drawn
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     *
     * @param opacity a floating point value between 0 and 1.0 to set the pane bg opacity.
     */
    public void setOpacity(float opacity) {
        this.opacity = (0xFF & (int)(opacity * 0xFF));
    }

    /**
     *
     * @return a float value of the pane opacity 0 to 1.0f
     */
    public float getOpacity(){
        return opacity/(float)0xFF;
    }

//        @Override
//        protected void paintChildren(Graphics g) {
//            Rectangle clipBounds = g.getClipBounds();
//            BufferedImage image = new BufferedImage(clipBounds.width, clipBounds.height, BufferedImage.TYPE_INT_ARGB);
//            Graphics ig = image.getGraphics();
//            ig.translate(-clipBounds.width, -clipBounds.height);
//            //System.out.println("paintChildren(g) ****");
//            //System.out.println(clipBounds);
//
////            AlphaComposite ac = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F);
//            Graphics2D g2d = image.createGraphics();
////            g2d.setComposite(ac);
//            super.paintChildren(g2d);
//
//            ig.dispose();
//            g2d.dispose();
//
//            g.drawImage(image, clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height, null);
//
//
//        }

    /**
     *
     * @param g is the current graphics context
     * Provides transparent background draw functionality
     */
        @Override
        protected void paintComponent(Graphics g) {

            Rectangle clipBounds = g.getClipBounds();

            Color bg = new Color(getBackground().getRed(),getBackground().getGreen(),getBackground().getBlue(),opacity);


            g.setColor(bg);
            g.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
           if(!hidden) {
               super.paintComponent(g);

           }
        }


}