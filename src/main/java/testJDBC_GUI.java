import com.sun.media.jfxmedia.events.NewFrameEvent;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class testJDBC_GUI {
    private Frame frame;
    private TextField textField;
    private Button button;
    private TextArea textArea;
    private Dialog dialog;` `
    private Label label;
    private Button okbutton;

    public testJDBC_GUI() {
        init();
    }
    private void init() {
        frame = new Frame("my window");
        frame.setBounds(300, 100, 600, 500);
        frame.setLayout(new BorderLayout());

        textField = new TextField(60);

        button = new Button("execute");
        textArea = new TextArea(25, 70);

        frame.add(textField);
        frame.add(button);
        frame.add(textArea);

        myEvent();
        frame.setVisible(true);
    }

    private void myEvent() {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    showDir();
                }
            }
        });

        okbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });

        dialog.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDir();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void showDir() {
        String dirPath = textField.getText();

        File file = new File(dirPath);
        if (file.exists() && file.isDirectory()) {
            textArea.setText("");
            String[] names = file.list();
            for (String name : names) {
                textArea.append(name + "\r\n");
            }
        } else {
            String info = "ur information is" + dirPath + "wrong";
            label.setText(info);
            dialog.setVisible(true);
        }

        textField.setText("");
    }

    public static void main(String[] args) {
        new MyWinDemo();
    }
}
