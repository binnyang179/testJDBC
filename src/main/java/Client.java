import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client extends JFrame {

    private JTextArea sqlJTextArea, outputJTextArea;
    private JTable resultJTable;
    private JScrollPane sqlJScrollPane, outputJScrollPane, resultJScrollPane;
    private JPanel southJPanel, main;
    static DefaultTableModel defaultTableModel;
    private Server server;

    private Client() {
        initComponent();
        initListener();
        server = new Server();
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
//        1. 全局设置
        setTitle("MySQL Client");
        setExtendedState(MAXIMIZED_BOTH);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = dimension.width;
        int height = dimension.height;
        Font font = new Font("consolas", Font.PLAIN, 24);

//        2. SQL
        sqlJTextArea = new JTextArea();
        sqlJTextArea.setBackground(Color.darkGray);
        sqlJTextArea.setForeground(Color.WHITE);
        sqlJTextArea.setFont(font);
        sqlJTextArea.setCaretColor(Color.WHITE);
        sqlJTextArea.setCaretPosition(sqlJTextArea.getDocument().getLength());

        sqlJScrollPane = new JScrollPane(sqlJTextArea);
        sqlJScrollPane.setPreferredSize(new Dimension(width, height / 2));

//        3. output
        outputJTextArea = new JTextArea();
        outputJTextArea.setBackground(Color.WHITE);
        outputJTextArea.setFont(font);
        outputJTextArea.setEditable(false);

        outputJScrollPane = new JScrollPane(outputJTextArea);
        outputJScrollPane.setPreferredSize(new Dimension(width / 2, (int) (height / 2.5)));

//        4. result
        resultJTable = new JTable();
        resultJTable.setBackground(Color.WHITE);
        resultJTable.setFont(font);
        resultJTable.getTableHeader().setFont(font);
        resultJTable.setRowHeight(30);

        resultJScrollPane = new JScrollPane(resultJTable);
        resultJScrollPane.setPreferredSize(new Dimension(width / 2, (int) (height / 2.5)));

        southJPanel = new JPanel();
        southJPanel.setLayout(new BorderLayout());
        southJPanel.setPreferredSize(new Dimension(width, (int) (height / 2.5)));

        southJPanel.add(outputJScrollPane, BorderLayout.WEST);
        southJPanel.add(resultJScrollPane, BorderLayout.EAST);

//        5. main
        main = new JPanel();
        main.setLayout(new BorderLayout());

        main.add(sqlJScrollPane, BorderLayout.NORTH);
        main.add(southJPanel, BorderLayout.SOUTH);

//        6. show
        add(main);

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                sqlJTextArea.requestFocus();
            }
        });

        setVisible(true);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        // shortcut
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK);
        sqlJTextArea.getInputMap().put(keyStroke, "Execute");
        sqlJTextArea.getActionMap().put("Execute", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = sqlJTextArea.getSelectedText();
                String output = server.dispatch(sql);
                if (output != null) {
                    outputJTextArea.append(output + "\n");
                }
                if (defaultTableModel != null) {
                    resultJTable.setModel(defaultTableModel);
                }
            }
        });
    }

    public static void main(String[] args) {
        new Client();
    }
}