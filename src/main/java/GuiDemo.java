import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiDemo {
    public static void main(String[] args) {
        Frame my_awt = new Frame("my awt");
        my_awt.setSize(500, 400);
        my_awt.setLocation(300, 200);
        my_awt.setLayout(new FlowLayout());

        Button i_am_a_new_button = new Button("i am a new button");

        my_awt.add(i_am_a_new_button);

        my_awt.addWindowListener(new MyWin());

        my_awt.setVisible(true);

        System.out.println("Hello " +
                "world");
    }
}


class MyWin extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent event) {
        System.out.println("i am closing");
        System.exit(0);
    }

    @Override
    public void windowActivated(WindowEvent event) {
        System.out.println("i am backing");
    }

    @Override
    public void windowOpened(WindowEvent event) {
        System.out.println("i am opening");
    }
}
