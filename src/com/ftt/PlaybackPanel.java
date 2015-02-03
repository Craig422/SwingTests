package com.ftt;

//import ftt3d.core.AircraftLoaders;
//import ftt3d.core.DavaViewerCore;
//import ftt3d.core.SceneLoaders;
//import ftt3d.utils.AnimatedCamera;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is a project control panel floating dialog box and contains some
 * frequently used application controls.
 *
 * @author Craig
 */

public class PlaybackPanel extends JFrame {


    private int embeddedHorizontalPadding = 30;
    private int embeddedBottomPadding = 30;
    private Box.Filler fillerPadding;
    private boolean embedded = false;
    Component oldGlassPane = null;
    JFrame owner = null;
    JPanel fillerRow = null;

    private ContentPanel contentPanel = null;
    private ButtonScrollPanel buttonPanel=null;

    public boolean isEmbedded() {
        return embedded;
    }


    /**
     * ButtonScrollPanel is the primary control embedded into the main frame or a floating JFrame
     */
    private class ButtonScrollPanel extends JPanel implements ActionListener  {
        //        private Scene scene = null;
//        private SpeedMenu speedMenu;
        boolean hidden = false;
        private boolean scrollUpdated = false;
        protected JButton leftButton = new JButton("<-");
        protected JButton rightButton = new JButton("->");
        protected JButton rewind = new JButton("Rew");
        protected JButton togglePause = new JButton("Pause");
        protected JButton Speed = new JButton("Speed");

        private JSlider scroll;

        @Override
        public void actionPerformed(ActionEvent e) {

            //  Actions for buttons


//            if (e.getSource()
//                    == rewind) {
//                if (scene != null) {
//                    scene.getTimeline().reset();
//                }
//
//            } else if (e.getSource()
//                    == togglePause) {
//                if (scene != null) {
//                    scene.getTimeline().togglePause();
//
//                    togglePause.setLabel(scene.getTimeline().getPaused() ? "Play" : "Pause");
//                }
//
//            } else if (e.getSource()
//                    == Speed) {
//                if (scene != null) {
////                scene.getTimeline().toggleSlomo();
////
////                toggleSlomo.setLabel(scene.getTimeline().getSlomo() ? "1X" : "Slow");
//
//                    speedMenu.ShowMenu(Speed,0,0);
//
//                }
//
//            } else if (e.getSource()
//                    == leftButton) {
//                if (scene != null) {
//                    scene.getTimeline().backStep();
//                }
//
//            } else if (e.getSource()
//                    == rightButton) {
//
//                if (scene != null) {
//                    scene.getTimeline().forwardStep();
//                }
//
//            }

        }



        public void setHidden(boolean hidden) {
            this.hidden = hidden;

            setVisible(!hidden);
        }

        void updateScroll(int time, int start, int end) {

            scroll.setMinimum(start);
            scroll.setMaximum(end);
            scroll.setValue(time);
            // set flag to disable stateChanged event propogation
            scrollUpdated = true;
        }

        public ButtonScrollPanel() {
            JPanel scrollPanel = new JPanel(new BorderLayout());
//        scrollPanel.setOpaque(false);
            //scrollPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

            scroll = new JSlider(0, 100, 50);

            scroll.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (!scrollUpdated) {
                        // this checks to see if this event was created by the updateScroll method or user mouse drag
                        //thus stopping an update event generating a timeline set event loop
//                        scene.getTimeline().setTime(scroll.getValue());
                        // System.out.println((String.valueOf(scroll.getValue())));
                    } else {
                        scrollUpdated = false;
                    }
                }
            });


            /**
             *  CP_UpdateTimer is a class that updates the scrollbar every 100mS
             */
            class CP_UpdateTimer implements Runnable {

                @Override
                public void run() {

                    //update CP every 500ms
                    Timer updateTimer = new Timer(100, new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

//                            updateScroll((int) scene.getTimeline().time(), scene.getTimeline().getStartFrame(), scene.getTimeline().getEndFrame());
                        }
                    });
                    updateTimer.setRepeats(true);
                    updateTimer.start();

                }

            }



            leftButton.addActionListener(this);
            rightButton.addActionListener(this);
            rewind.addActionListener(this);
            togglePause.addActionListener(this);
            Speed.addActionListener(this);
            Dimension minSize = new Dimension(12, 1);
            Dimension prefSize = new Dimension(12, 1);
            Dimension maxSize = new Dimension(Short.MAX_VALUE, 1);
            //scroll.setSize(mainFrame.getWidth()-50,25);
            scrollPanel.add(new Box.Filler(minSize, prefSize, maxSize), BorderLayout.WEST);
            scrollPanel.add(scroll);
            scrollPanel.add(new Box.Filler(minSize, prefSize, maxSize), BorderLayout.EAST);

            JPanel buttonsPanel = new JPanel();
            buttonsPanel.add(rewind);
            buttonsPanel.add(leftButton);
            buttonsPanel.add(togglePause);
            buttonsPanel.add(Speed);
            buttonsPanel.add(rightButton);
            //  addKeyListener();

            setLayout(new BorderLayout());
            add(scrollPanel, BorderLayout.NORTH);
            add(buttonsPanel, BorderLayout.SOUTH);

            //Update timer thread
            Thread uThread = new Thread(new CP_UpdateTimer());
            uThread.start();


        }



    }


    public JPanel getContentPanel() {
        return contentPanel;
    }


    /**
     * This method embeds the control into the main mainFrame JFrame and positions it at the bottom by adding a filler panel to the glass pane panel.
     *
     * @param embed if true embed the control or if false removes it back to a floating JFrame
     */
    void setEmbeddedMode(boolean embed) {
        if (embed) {
            //Make Filler panel to position the controls when embedded into mainFrame window
//            buttonPanel.setHidden(true);
            int contentPaneHeight = owner.getContentPane().getHeight();
            int buttonPanelHeight = buttonPanel.getHeight();
            buttonPanel.repaint();
            contentPanel.attachListener();

            Dimension paneFillerMinSize = new Dimension(owner.getWidth()-embeddedHorizontalPadding*2,
                    contentPaneHeight - embeddedBottomPadding - buttonPanelHeight);
            Dimension paneFillerPrefSize = new Dimension(owner.getWidth()-embeddedHorizontalPadding*2,
                    contentPaneHeight - embeddedBottomPadding - buttonPanelHeight);
            Dimension paneFillerMaxSize = new Dimension(Short.MAX_VALUE,
                    contentPaneHeight - embeddedBottomPadding - buttonPanelHeight);
            fillerRow = new JPanel();
            fillerRow.setOpaque(false);
            fillerPadding = new Box.Filler(paneFillerMinSize, paneFillerPrefSize, paneFillerMaxSize);
            fillerRow.add(fillerPadding);

            oldGlassPane = owner.getGlassPane();
            this.remove(contentPanel);
            GridBagConstraints gbConstraints = new GridBagConstraints();
            gbConstraints.fill = GridBagConstraints.HORIZONTAL;
            gbConstraints.anchor = GridBagConstraints.NORTH;
            gbConstraints.gridx = 0;
            gbConstraints.gridy = 0;
            contentPanel.add(fillerRow, gbConstraints);
            owner.setGlassPane(contentPanel);

            contentPanel.setVisible(true);
            //Hide the JFrame floating control parent
            this.setVisible(false);
//
        } else {
            owner.setGlassPane(oldGlassPane);
            contentPanel.remove(fillerRow);
            contentPanel.invalidate();
            this.add(contentPanel);
            this.setVisible(true);
            buttonPanel.setHidden(false);
            contentPanel.detachListener();


        }
        embedded = embed;

    }


    class ContentPanel extends JPanel implements MouseMotionListener,ComponentListener{
        private JFrame floatingPanel=null;
        JFrame mainFrame = null;
        Timer updateTimer=null;
        Thread uThread = new Thread(new CP_VisbilityTimer());
        private boolean attached=false;


        /**
         *  CP_VisibiltyTimer is a class that hides an embedded the buttonPanel after 1 Second
         */
        class CP_VisbilityTimer implements Runnable {

            @Override
            public void run() {


                updateTimer = new Timer(1000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(attached) {
                            buttonPanel.setHidden(true);
                            contentPanel.repaint();
                        }
//
                    }
                });
                updateTimer.setRepeats(false);


            }

        }



        public ContentPanel(JFrame ownerJFrame,JFrame floatingPanel) {
            super();
            mainFrame = ownerJFrame;
            this.floatingPanel = floatingPanel;
            uThread.start();
        }

        void attachListener(){
            attached = true;
            mainFrame.addMouseMotionListener(this);

            if(updateTimer!=null)
                updateTimer.start();
        }

        void detachListener(){
            attached=false;
            mainFrame.removeMouseMotionListener(this);

        }

        @Override
        public void mouseDragged(MouseEvent e) {
//            System.out.println(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point containerPoint = e.getPoint();
            int menuHeight = mainFrame.getJMenuBar().getHeight();
            if((containerPoint.y > (fillerPadding.getHeight())+menuHeight)
                    && (containerPoint.y < (fillerPadding.getHeight())+menuHeight+buttonPanel.getHeight())
                    )
            {
                buttonPanel.setHidden(false);

                buttonPanel.repaint();
                if(updateTimer!=null)
                    updateTimer.stop();

            } else {
                    if(updateTimer!=null)
                        updateTimer.restart();

            }
        }

        @Override
        public void componentResized(ComponentEvent e) {
//            System.out.println("Resized");
            if(attached) {
                int contentPaneHeight = mainFrame.getContentPane().getHeight();
                int buttonPanelHeight = buttonPanel.getHeight();
                Dimension paneFillerMinSize = new Dimension(mainFrame.getWidth() - embeddedHorizontalPadding * 2,
                        contentPaneHeight - embeddedBottomPadding - buttonPanelHeight);
                Dimension paneFillerPrefSize = new Dimension(mainFrame.getWidth() - embeddedHorizontalPadding * 2,
                        contentPaneHeight - embeddedBottomPadding - buttonPanelHeight);
                Dimension paneFillerMaxSize = new Dimension(Short.MAX_VALUE,
                        contentPaneHeight - embeddedBottomPadding - buttonPanelHeight);
                fillerPadding.setPreferredSize(paneFillerPrefSize);
                fillerPadding.setMinimumSize(paneFillerMinSize);
                fillerPadding.setMaximumSize(paneFillerMaxSize);
                fillerRow.invalidate();
            }else
            {

                if(mainFrame.getWidth()>450) {
                    componentMoved(e);
                    floatingPanel.setSize(mainFrame.getWidth(), floatingPanel.getHeight());
                }
            }
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            System.out.println("Moved");
            if(!attached) {
                boolean osWindows = System.getProperty("os.name").contains("Windows");
                int offset = osWindows ? 6 : 6;
                Point rPoint = mainFrame.getLocation();
                if(floatingPanel!=null)
                floatingPanel.setLocation(rPoint.x, rPoint.y + mainFrame.getHeight() + offset);
            }
        }

        @Override
        public void componentShown(ComponentEvent e) {
//            System.out.println("Shown");
        }

        @Override
        public void componentHidden(ComponentEvent e) {
//            System.out.println("Hidden");
        }
    }

    /**
     * Playback controls panel constructor object. It must be supplied with a current scene and Frame
     *
     * @param owner The JFrame mainFrame of this control
     */
    public PlaybackPanel(JFrame owner) {
        super();
        // super(mainFrame, "Control Panel", false);
        //setUndecorated(true);
        this.owner = owner;


        //setDefaultLookAndFeelDecorated(true);
        contentPanel = new ContentPanel(owner,this);
        owner.addComponentListener(contentPanel);
        add(contentPanel);
        this.setResizable(false);
        //panel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        boolean osWindows = System.getProperty("os.name").contains("Windows");
        int height = (osWindows ? 120 : 90);
        setSize(owner.getWidth(), height);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbConstraints.anchor = GridBagConstraints.NORTH;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 1;
        buttonPanel = new ButtonScrollPanel();
        contentPanel.add(buttonPanel, gbConstraints);


    }


}
