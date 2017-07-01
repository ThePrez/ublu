/*
 * Copyright (c) 2015, Absolute Performance, Inc. http://www.absolute-performance.com
 * Copyright (c) 2017, Jack J. Woehr jwoehr@softwoehr.com 
 * SoftWoehr LLC PO Box 51, Golden CO 80402-0051 http://www.softwoehr.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package ublu.win;

import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;
// import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ublu.util.Generics.StringArrayList;

/**
 *
 * @author jax
 */
public class UbluPanel extends javax.swing.JPanel {

    // private final EditorPaneOutputStream jEPOS;
    private final TextAreaOutputStream jTAOS;
    private UbluFrame ubluFrame;
    private final StringArrayList commands;
    private int pointer = 0;

    private int decPointer() {
        pointer = Math.max(0, --pointer);
        return pointer;
    }

    private int incPointer() {
        pointer = Math.min(commands.size() - 1, ++pointer);
        return pointer;
    }

    private String foreCommand() {
        String result = "";
        if (commands.size() > 0) {
            result = commands.get(pointer);
            incPointer();
        }
        return result;
    }

    private String backCommand() {
        String result = "";
        if (commands.size() > 0) {
            result = commands.get(pointer);
            decPointer();
        }
        return result;
    }

    /**
     *
     * @return
     */
    public TextAreaOutputStream getjTAOS() {
        return jTAOS;
    }

    /**
     *
     * @param ubluFrame
     */
    protected void setUbluFrame(UbluFrame ubluFrame) {
        this.ubluFrame = ubluFrame;
    }

    /**
     * Creates new form UbluPanel
     */
    public UbluPanel() {
        this.commands = new StringArrayList();
        initComponents();
        // jEPOS = new EditorPaneOutputStream(ubluEditorPane);
        // jTAOS = new TextAreaOutputStream(jTextArea1);
        jTAOS = new TextAreaOutputStream(this);
    }

    /**
     *
     */
    public void scrollToEnd() {
        JScrollBar jsb = jScrollPane1.getVerticalScrollBar();
        jsb.setValue(jsb.getMaximum());
    }

//    /**
//     *
//     * @return
//     */
//    protected JEditorPane getUbluTextArea() {
//        return ubluEditorPane;
//    }
    /**
     *
     * @return
     */
    protected JTextArea getUbluTextArea() {
        return jTextArea1;
    }

    /**
     *
     * @return
     */
    protected JTextField getUbluTextField() {
        return ubluTextField;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        ubluTextField = new javax.swing.JTextField();

        setDoubleBuffered(false);
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 13)); // NOI18N

        jTextArea1.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 13)); // NOI18N
        jTextArea1.setDoubleBuffered(true);
        jTextArea1.setName(""); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        ubluTextField.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 13)); // NOI18N
        ubluTextField.setDragEnabled(false);
        ubluTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ubluTextFieldKeyReleased(evt);
            }
        });
        add(ubluTextField, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void ubluTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ubluTextFieldKeyReleased
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                String ubluText = ubluTextField.getText();
                if (!ubluText.equals("")) {
                    commands.add(ubluText);
                    pointer = commands.size() - 1;
                }
                ubluText = ubluText + '\n';
                // jEPOS.write((ubluText).getBytes());
                jTAOS.write((ubluText).getBytes());
                ubluTextField.setText("");
                ubluFrame.interpretText(ubluText);
                scrollToEnd();
                break;
            case KeyEvent.VK_UP:
                ubluTextField.setText(backCommand());
                break;
            case KeyEvent.VK_DOWN:
                ubluTextField.setText(foreCommand());
        }
    }//GEN-LAST:event_ubluTextFieldKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField ubluTextField;
    // End of variables declaration//GEN-END:variables
}
