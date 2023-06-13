import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import util.MySocket;

public class Window extends JFrame {
    private JPanel panel;
    private CardLayout cardLayout;
    private MySocket sc;
    private DefaultListModel<String> listModel;
    private JList<String> jlist;
    private CountDownLatch latch;

    public Window(String name) {
        super("Window");
        this.latch = new CountDownLatch(1);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        cardLayout = new CardLayout();
        panel.setLayout(cardLayout);

    }

    private JPanel createLoginPanel() {
        JPanel helloPanel = new JPanel();
        helloPanel.setLayout(new FlowLayout());

        JTextField name = new JTextField(20);
        JButton button = new JButton("Log in");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nickname = name.getText();
                sc = new MySocket(nickname);
                setTitle(nickname);
                latch.countDown();
                cardLayout.show(panel, "loggedIn");
            }
        });

        // Add the label and button to the helloPanel
        helloPanel.add(name);
        helloPanel.add(button);

        return helloPanel;
    }

    private JPanel createLoggedInPanel() {

        JPanel loggedInPanel = new JPanel();
        loggedInPanel.setLayout(new FlowLayout());

        JTextField message = new JTextField(20);
        loggedInPanel.add(message);

        listModel = new DefaultListModel<>();
        jlist = new JList<>(listModel);
        loggedInPanel.add(new JScrollPane(jlist));

        message.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String text = message.getText();
                    System.out.println(text);
                    sc.write(text);
                    message.setText("");
                    listModel.addElement("You: " + text);
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
            }
        });

        return loggedInPanel;
    }

    public MySocket connect() {
        JPanel helloPanel = createLoginPanel();
        panel.add(helloPanel, "hello");

        JPanel loggedInPanel = createLoggedInPanel();
        panel.add(loggedInPanel, "loggedIn");

        cardLayout.show(panel, "hello");

        setContentPane(panel);

        setVisible(true);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return sc;
    }

    public void write(String line) {
        listModel.addElement(line);
    }
}

