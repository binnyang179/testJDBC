import java.awt.*;
import java.awt.event.*;

public class MouseAndKeyEvent {
    private Frame frame;
    private Button button;
    private TextField textField;

    public MouseAndKeyEvent() {
        init();
    }

    private void init() {
        frame = new Frame("me frame");
        frame.setBounds(300, 200, 600, 500);
        frame.setLayout(new FlowLayout());

        textField = new TextField(20);
        button = new Button("my button");

        frame.add(textField);
        frame.add(button);

        event();

        frame.setVisible(true);

    }

    private void event() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (!(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9)) {
                    System.out.println(code + "illegal input");
                    e.consume();
                }
            }
        });


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("actionPerformed once");
            }
        });

        button.addMouseListener(new MouseAdapter() {
            private int count = 0;
            private int clickCount=1;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("double action");
                } else
                    System.out.println("click action"+clickCount++);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouse into editing components"+count++);
            }
        });

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar() + "..." + e.getKeyCode());
                System.out.println(KeyEvent.getKeyText(e.getKeyCode()) + "...." + e.getKeyCode());
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.out.println("i am sending message");
                }
            }
        });
    }

    public static void main(String[] args) {
        new MouseAndKeyEvent();
    }
}
