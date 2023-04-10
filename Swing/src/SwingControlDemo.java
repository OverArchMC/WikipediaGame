import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    public static ArrayList<ArrayList<String>> possiblePaths;


    public SwingControlDemo() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showEventDemo();



        /*testing HTMLread

        ArrayList<String> test = HtmlRead("https://en.wikipedia.org/wiki/Jennifer_Aniston");

        for(int i = 0; i < test.size(); i++){
            System.out.println(test.get(i));
        }*/

        //String targetURL = "https://en.wikipedia.org/wiki/Mulholland_Drive_(film)";
        //String startingURL = "https://en.wikipedia.org/wiki/Jennifer_Aniston";


        String targetURL = "https://en.wikipedia.org/wiki/Brad_Pitt";
        String startingURL = "https://en.wikipedia.org/wiki/Jennifer_Aniston";

        //String startingURL = "https://en.wikipedia.org/wiki/Brad_Pitt_filmography";
        //String targetURL = "https://en.wikipedia.org/wiki/Friends_(TV_series)";
        possiblePaths = new ArrayList<>();


        /*ArrayList<String> result = wikiGame(new ArrayList<String>(), targetURL, startingURL, startingURL);

        if(result == null){
            System.out.println("Something went wrong");
        }else{
            System.out.println("Links away: " + (result.size()-5));
            for(int i = 4; i < result.size(); i++){
                System.out.println(result.get(i));
            }
        }


        ArrayList<ArrayList<String>> justForTesting = shortestWikiGame(new ArrayList<String>(), targetURL, startingURL, startingURL);
        ArrayList<String> bestPath = possiblePaths.get(0);
        int minLength = possiblePaths.get(0).size(); // create a max depth variable for searching for links
        for(int i = 1; i < possiblePaths.size(); i++){
            //System.out.println("Links in path: " + possiblePaths.get(0).size());
            if(possiblePaths.get(i).size() < minLength){
                minLength = possiblePaths.get(i).size();
                bestPath = possiblePaths.get(i);
            }
        }
        //System.out.println("Links away: " + (bestPath.size()));
        for(int i = 0; i < bestPath.size(); i++){
            //System.out.println(bestPath.get(i));
        }


        /*ArrayList<String> testingHtml = HtmlRead("https://en.wikipedia.org/wiki/Jennifer_Aniston");

        for(String s : testingHtml){
            System.out.println(s);
        }*/


        ArrayList<String> realtesting = new ArrayList<>();
        realtesting.add(startingURL);
        ArrayList<String> justforatest = bestWikiGame(realtesting, targetURL);
        System.out.println(justforatest.size());
        for(String s : justforatest){
            System.out.println(s);
        }
    }

    public static ArrayList<String> wikiGame(ArrayList<String> result, String targetURL, String startingURL, String currentURL){
        if(result.contains(targetURL)){
            return result;
        }if(result.size() > 5 || new HashSet<String>(result).size() < result.size()){
            return null;
        }
        ArrayList<String> currPageLinks = HtmlRead(currentURL);

        for(int i = 0; i < currPageLinks.size(); i++){
            ArrayList<String> temp = result;
            temp.add(currPageLinks.get(i));

            if(wikiGame(temp, targetURL, startingURL, currPageLinks.get(i)) != null){
                return temp;
            }
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> shortestWikiGame(ArrayList<String> result, String targetURL, String startingURL, String currentURL){

        if(result.contains(targetURL)){
            //return result;
            possiblePaths.add(result);
        }else if(result.size() > 5 || new HashSet<String>(result).size() < result.size()){
            return null;
        }else {
            ArrayList<ArrayList<String>> actualResult = new ArrayList<>();
            ArrayList<String> currPageLinks = HtmlRead(currentURL);

            for (int i = 0; i < currPageLinks.size(); i++) {
                if(!result.contains(currPageLinks.get(i))) {
                    ArrayList<String> temp = result;
                    temp.add(currPageLinks.get(i));

                    if (shortestWikiGame(temp, targetURL, startingURL, currPageLinks.get(i)) != null) {
                        actualResult.add(temp);
                    }
                }
            }
            return actualResult;
        }
        return null;
    }

    public static ArrayList<String> bestWikiGame(ArrayList<String> currPath, String targetUrl){
        if(currPath.size() > 5){
            return null;
        }else{
            ArrayList<String> currPageLinks = HtmlRead(currPath.get(currPath.size()-1));
            for(String s : currPageLinks){ // add code to prevent repeats (should be done)
                if(!currPath.contains(s)) {
                    ArrayList<String> temp = (ArrayList<String>) currPageLinks.clone();
                    temp.add(s);
                    if (s.equals(targetUrl)) {
                        return temp;
                    }
                }
            }
            for(String s : currPageLinks){
                if(!currPath.contains(s)) {
                    ArrayList<String> temp = (ArrayList<String>) currPageLinks.clone();
                    temp.add(s);
                    bestWikiGame(temp, targetUrl);
                }
            }
        }
        return null;
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


    public static ArrayList<String> HtmlRead(String givenURL) {



        ArrayList<String> result = new ArrayList<>();

        try {
            System.out.println();
            //System.out.print("hello \n");

            //String contains = "";

            // public Rocks[] rocks;

            //rocks = new Rocks[4]; 


            URL url = new URL(givenURL);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ( (line = reader.readLine()) != null ) {
                String[] temp = line.split("\'");
                for(int i = 0; i < temp.length; i++){
                    if((temp[i].indexOf("https://en.wikipedia") == 0 || temp[i].indexOf("/wiki/") == 0) && !temp[i].contains("title") && !temp[i].contains("Category")
                            && !temp[i].contains("Template") && !temp[i].contains("File") && !temp[i].contains("Portal")){

                        if (temp[i].indexOf("https://en.wikipedia") == 0) {
                            result.add(temp[i]);
                        }else{
                            result.add("https://en.wikipedia.org" + temp[i]);
                        }
                        //System.out.println(temp[i]);
                    }

                }
                String[] temp2 = line.split("\"");
                for(int i = 0; i < temp2.length; i++){
                    if((temp2[i].indexOf("https://en.wikipedia") == 0 || temp2[i].indexOf("/wiki/") == 0) && !temp2[i].contains("title") && !temp2[i].contains("Category")
                            && !temp2[i].contains("Template") && !temp2[i].contains("File") && !temp2[i].contains("Portal")
                            && !temp2[i].contains("Main_Page") && !temp2[i].contains("Special:") && !temp2[i].contains("Wikipedia:")
                            && !temp2[i].contains("Help:") && !temp2[i].contains("Talk:") && !temp2[i].contains("#")){

                        if (temp2[i].indexOf("https://en.wikipedia") == 0) {
                            result.add(temp2[i]);
                        }else{
                            result.add("https://en.wikipedia.org" + temp2[i]);
                        }


                        //System.out.println(temp2[i]);
                    }

                }
                //System.out.println(line);
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }

        return result;

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