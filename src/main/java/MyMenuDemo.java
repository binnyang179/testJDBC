import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class MyMenuDemo {
    private Frame frame;
    private MenuBar menuBar;
    private Menu menu, submenu;
    private MenuItem closeItem, openItem, saveItem, subItem, subItem1;
    private FileDialog openDialog,saveDialog;
    private TextArea textArea;
    private File file;

    public MyMenuDemo() {
        init();
    }

    private void init() {
        frame = new Frame("myWindown");
        frame.setBounds(300, 100, 600, 500);
//        frame.setLayout(new FlowLayout());

        menuBar = new MenuBar();
        menu = new Menu("File");

        closeItem = new MenuItem("exit");
        openItem = new MenuItem("open");
        saveItem = new MenuItem("save");

        submenu = new Menu("new establish:");
        subItem1 = new MenuItem("web Project ");
        subItem = new MenuItem("java project ");
        submenu.add(subItem);
        submenu.add(subItem1);

        menu.add(submenu);
        menu.add(openItem);
        menu.add(saveItem);
        menu.add(closeItem);
        menuBar.add(menu);

        openDialog = new FileDialog(frame, "open", FileDialog.LOAD);
        openDialog = new FileDialog(frame, "save", FileDialog.SAVE);

        textArea = new TextArea();

        frame.add(textArea);
        frame.setMenuBar(menuBar);
        myEvent();

        frame.setVisible(true);
    }

    private void myEvent() {
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    saveDialog.setVisible(true);
                    String dirPath = saveDialog.getDirectory();
                    String fileName = saveDialog.getFile();
                    if (dirPath == null || fileName == null)
                        return;
                    file = new File(dirPath, fileName);
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                        String text = textArea.getText();
                        bufferedWriter.write(text);
                        bufferedWriter.close();
                    } catch (IOException e1) {
                        throw new RuntimeException("save failed");
                    }
                }
            }
        });

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDialog.setVisible(true);
                String dirPath = openDialog.getDirectory();
                String fileName = openDialog.getFile();
                System.out.println(dirPath + "..." + fileName);
                if (dirPath == null || fileName == null) {
                    return;
                }
                textArea.setText("");
                file = new File(dirPath, fileName);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        textArea.append(line+"\r\n");
                    }
                    bufferedReader.close();
                } catch (FileNotFoundException e1) {
                    System.out.println("file not exist");
                    e1.printStackTrace();
                } catch (IOException e1) {
                    System.out.println("file open failed");
                    e1.printStackTrace();
                }
            }
        });

        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new MyMenuDemo();
    }
}
