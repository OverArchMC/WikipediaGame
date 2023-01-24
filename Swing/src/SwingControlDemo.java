import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class SwingControlDemo  implements ActionListener, AbTest {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JMenuItem save, newMessage, deleteMessage;
    private JTextArea ta;
    private int WIDTH=800;
    private int HEIGHT=700;


    public SwingControlDemo() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(3, 1));




        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        save = new JMenuItem("save");
        newMessage = new JMenuItem("newFile");
        deleteMessage = new JMenuItem("deleteFile");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        save.addActionListener(this);
        newMessage.addActionListener(this);
        deleteMessage.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        file.add(save);
        file.add(newMessage);
        file.add(deleteMessage);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);

        ta = new JTextArea();
        ta.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        mainFrame.add(mb);
        mainFrame.add(ta);
        mainFrame.setJMenuBar(mb);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

       // mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");

        JButton okButton = new JButton("Send");
        JButton submitButton = new JButton("Greet");
        JButton cancelButton = new JButton("Delete message");
        JButton openSavedButton = new JButton("Open Saved File");

        okButton.setActionCommand("Send");
        submitButton.setActionCommand("Greet");
        cancelButton.setActionCommand("Delete message");
        openSavedButton.setActionCommand("Open Saved File");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());
        openSavedButton.addActionListener((new ButtonClickListener()));

        controlPanel.add(okButton);
        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);
        controlPanel.add(openSavedButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();

        if(e.getSource() == save) // to do
            ;
        if(e.getSource() == newMessage){
            ta.selectAll();
            ta.cut();
        }
        if(e.getSource() == deleteMessage) {
            ta.selectAll();
            ta.cut();
        }
    }

    @Override
    public void test() {
        
    }

    @Override
    public int test2(int x) {
        return 0;
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Send")) {
                statusLabel.setText("Message sent: " + ta.getText());
            } else if (command.equals("Greet")) {
                statusLabel.setText("Welcome");
            } else if(command.equals("Open Saved File")){
                ta.selectAll();
                ta.cut();

                File file = new File("Saved");
                /*FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();*/
                Scanner scanner = new Scanner(System.in);

                String str = "";
                while(scanner.hasNext()){
                    str += scanner.next();
                }

                ta.insert(str, 0);
            }
            else {
                //ta.removeAll();
                ta.selectAll();
                ta.cut();
                statusLabel.setText("Message deleted.");
            }
        }
    }

    /*private String convertFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }*/
}