package com.editor.visualcomponent.toolbarbutton;

import com.editor.visualcomponent.JGridPanel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Date: 04.05.12
 * Time: 9:43
 *
 * @author: Alexey
 */
public abstract class JToolBarButton extends JButton {
    private final static Logger LOGGER = Logger.getLogger(JToolBarButton.class);
    private JGridPanel workAria;
    private Point point = null;
    private Cursor cursor = null;
    private boolean inAria;

    public JToolBarButton(JGridPanel workAria) {
        this.workAria = workAria;
        init();
    }

    public JToolBarButton(Icon icon, JGridPanel workAria) {
        super(icon);
        this.workAria = workAria;
        init();

    }

    public JToolBarButton(String text, JGridPanel workAria) {
        super(text);
        this.workAria = workAria;
        init();

    }

    public JToolBarButton(Action a, JGridPanel workAria) {
        super(a);
        this.workAria = workAria;
        init();

    }

    public JToolBarButton(String text, Icon icon, JGridPanel workAria) {
        super(text, icon);
        this.workAria = workAria;
        init();

    }

    private void init() {
        if (workAria == null) {
            throw new IllegalArgumentException(new NullPointerException("'work' aria can't be null."));
        }
        addActionListener(actionListener);
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
    }

    //TODO create own cursors
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            point = SwingUtilities.convertPoint(JToolBarButton.this, e.getPoint(), workAria);
            if ((point != null) && (point.x >= 0) && (point.y >= 0) && (workAria.getWidth() >= point.x) && (workAria.getHeight() >= point.y)) {
                inAria = true;
            } else {
                inAria = false;
            }
            if(inAria){
                getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }else{
                getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }


        }

        @Override
        public void mousePressed(MouseEvent e) {
            point = null;
            cursor = getRootPane().getCursor();
            getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            inAria = false;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            getRootPane().setCursor(cursor);
            if (inAria) {
                LOGGER.trace("OKK");
                actionPerformed(point);
            }
        }
    };

    public JGridPanel getWorkAria() {
        return workAria;
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JToolBarButton.this.actionPerformed();
        }
    };

    public abstract void actionPerformed();

    public abstract void actionPerformed(Point point);


}
