package com.ftt;

import javax.swing.*;

/**
 * Created by craighowarth on 02/02/15.
 */

public class FadingPanel extends JPanel {
    boolean hidden = false;

    public FadingPanel() {
        super();
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;

        setVisible(!hidden);
    }

//        @Override
//        public void paint(Graphics g) {
//          if(!hidden) super.paint(g);
//        }

//        @Override
//        protected void paintChildren(Graphics g) {
////            Rectangle clipBounds = g.getClipBounds();
////            BufferedImage image = new BufferedImage(clipBounds.width, clipBounds.height, BufferedImage.TYPE_INT_ARGB);
////            Graphics ig = image.getGraphics();
////            ig.translate(-clipBounds.width, -clipBounds.height);
////            System.out.println(clipBounds);
////            super.paintChildren(ig);
////
////            ig.dispose();
////            g.drawImage(image, clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height, null);
//            if(!hidden)
//            super.paintChildren(g);
//        }

//        @Override
//        protected void paintComponent(Graphics g) {
//            if(!hidden)
//            super.paintComponent(g);
//        }
//
//        @Override
//        public void repaint(long tm, int x, int y, int width, int height) {
//            if(!hidden)
//                super.repaint(tm, x, y, width, height);
//        }
//
//        @Override
//        public void repaint() {
//            if(!hidden)
//                super.repaint();
//        }
//
//        @Override
//        public void paintAll(Graphics g) {
//            if(!hidden)
//                super.paintAll(g);
//        }
}