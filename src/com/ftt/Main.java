package com.ftt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static class SettingsPane extends JLayeredPane {

        public SettingsPane() {

            setBorder(new EmptyBorder(10, 10, 10, 10));
            add(new JLabel("Settings"));

        }
    }

    class MyGlassPane extends JComponent
            implements ItemListener {
        Point point;

        //React to change button clicks.
        public void itemStateChanged(ItemEvent e) {
            setVisible(e.getStateChange() == ItemEvent.SELECTED);
        }

        protected void paintComponent(Graphics g) {

            if (point != null) {
                g.setColor(Color.red);
                g.fillOval(point.x - 10, point.y - 10, 20, 20);
            }
        }

        public void setPoint(Point p) {
            point = p;
        }

        public MyGlassPane(AbstractButton aButton,
                           JMenuBar menuBar,
                           Container contentPane) {
            CBListener listener = new CBListener(aButton, menuBar,
                    this, contentPane);
            addMouseListener(listener);
            addMouseMotionListener(listener);
        }
    }

    /**
     * Listen for all events that our check box is likely to be
     * interested in.  Redispatch them to the check box.
     */
    class CBListener extends MouseInputAdapter {
        Toolkit toolkit;
        Component liveButton;
        JMenuBar menuBar;
        MyGlassPane glassPane;
        Container contentPane;

        public CBListener(Component liveButton, JMenuBar menuBar,
                          MyGlassPane glassPane, Container contentPane) {
            toolkit = Toolkit.getDefaultToolkit();
            this.liveButton = liveButton;
            this.menuBar = menuBar;
            this.glassPane = glassPane;
            this.contentPane = contentPane;
        }

        public void mouseMoved(MouseEvent e) {
            redispatchMouseEvent(e, false);
        }

        public void mouseDragged(MouseEvent e) {
            redispatchMouseEvent(e, false);
        }

        public void mouseClicked(MouseEvent e) {
            redispatchMouseEvent(e, false);
        }

        public void mouseEntered(MouseEvent e) {
            redispatchMouseEvent(e, false);
        }

        public void mouseExited(MouseEvent e) {
            redispatchMouseEvent(e, false);
        }

        public void mousePressed(MouseEvent e) {
            redispatchMouseEvent(e, false);
        }

        public void mouseReleased(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        //A basic implementation of redispatching events.
        private void redispatchMouseEvent(MouseEvent e,
                                          boolean repaint) {
            Point glassPanePoint = e.getPoint();
            Container container = contentPane;
            Point containerPoint = SwingUtilities.convertPoint(
                    glassPane,
                    glassPanePoint,
                    contentPane);
            if (containerPoint.y < 0) { //we're not in the content pane
                if (containerPoint.y + menuBar.getHeight() >= 0) {
                    //The mouse event is over the menu bar.
                    //Could handle specially.
                } else {
                    //The mouse event is over non-system window
                    //decorations, such as the ones provided by
                    //the Java look and feel.
                    //Could handle specially.
                }
            } else {
                //The mouse event is probably over the content pane.
                //Find out exactly which component it's over.
                Component component =
                        SwingUtilities.getDeepestComponentAt(
                                container,
                                containerPoint.x,
                                containerPoint.y);

                if ((component != null)
                        && (component.equals(liveButton))) {
                    //Forward events over the check box.
                    Point componentPoint = SwingUtilities.convertPoint(
                            glassPane,
                            glassPanePoint,
                            component);
                    component.dispatchEvent(new MouseEvent(component,
                            e.getID(),
                            e.getWhen(),
                            e.getModifiers(),
                            componentPoint.x,
                            componentPoint.y,
                            e.getClickCount(),
                            e.isPopupTrigger()));
                }
            }

            //Update the glass pane if requested.
            if (repaint) {
                glassPane.setPoint(glassPanePoint);
                glassPane.repaint();
            }
        }
    }

    public void doGUI() {

        final JFrame testWindow = new JFrame("Test");
        testWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        testWindow.setSize(500, 500);

        final JPanel contentPane = (JPanel) testWindow.getContentPane();
        final JMenuBar menuBar = new JMenuBar();
        final PlaybackPanel playbackPanel = new PlaybackPanel(testWindow);
        boolean osWindows = System.getProperty("os.name").contains("Windows");
        int offset = osWindows ? 6 : 6;
        Point rPoint = testWindow.getLocation();

            playbackPanel.setLocation(rPoint.x, rPoint.y + testWindow.getHeight() + offset);
        playbackPanel.setVisible(true);
        ActionListener menuItemListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand() == "Test") {

                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    GraphicsDevice vc = ge.getDefaultScreenDevice();
                    if (!(vc.getFullScreenWindow() == testWindow)) {
                        if(!playbackPanel.isEmbedded())
                        playbackPanel.setEmbeddedMode(true);
                        vc.setFullScreenWindow(testWindow);
                    }
                    else {

                        vc.setFullScreenWindow(null);
                        playbackPanel.setEmbeddedMode(false);
                    }
                }else if(e.getActionCommand()=="Embed"){
                    playbackPanel.setEmbeddedMode(!playbackPanel.isEmbedded());
                }
            }
        };
        JMenu doNothingMenu = new JMenu("Menu");
        JMenuItem mb1_1 = new JMenuItem("Test");
        mb1_1.addActionListener(menuItemListener);
        mb1_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.META_MASK));
        JMenuItem mb1_2 = new JMenuItem("Embed");
        mb1_2.addActionListener(menuItemListener);
        mb1_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.META_MASK));
        menuBar.add(doNothingMenu);
        doNothingMenu.add(mb1_1);
        doNothingMenu.add(mb1_2);
        testWindow.setJMenuBar(menuBar);
        menuBar.setVisible(true);


        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.blue);

        testWindow.setVisible(true);

        testWindow.requestFocus();

//        testWindow.invalidate();

    }

    public static void main(String[] args) {


        new Main().doGUI();


        // write your code here
    }
}
