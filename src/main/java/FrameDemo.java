import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameDemo {
    private Frame frame;
    private Button button;

    FrameDemo() {
        init();
    }

    public void init() {
        frame = new Frame("my freame");
        frame.setBounds(300, 100, 600, 500);
        frame.setLayout(new FlowLayout());

        button = new Button("my Button");
        frame.add(button);

        myEvent();

        frame.setVisible(true);
    }

    private void myEvent() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        FrameDemo frameDemo = new FrameDemo();
    }

}
