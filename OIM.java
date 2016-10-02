package oim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class OIM extends JFrame
{

    private Container contents;
    private JLabel channelLabel, channelListLabel, friendLabel;
    private JList channelList, onLine;
    private JTextArea chatArea;
    private JScrollPane scrollPane;
    private JTextField chatInput;
    private JButton logoutButton, sendButton;
    private GridBagConstraints GBC = new GridBagConstraints();
    private DefaultListModel channelListModel, onLineModel;
    
    BufferedReader in;
    PrintWriter out;
    Socket socket;
    
    
    public OIM()
    {
        super("Oakland Instant Messager");
        contents = getContentPane();
        contents.setLayout(new GridBagLayout());
        
        channelLabel = new JLabel("Channel Name: ");
        channelLabel.setForeground(Color.yellow);
        channelLabel.setBackground(Color.BLACK);
        channelLabel.setOpaque(true);
        GBC.fill = GridBagConstraints.BOTH;
        //GBC.weighty = .5;
        GBC.weightx = .5;
        GBC.gridx = 1;
        GBC.gridy = 0;
        GBC.gridwidth = 2;
        contents.add(channelLabel, GBC);
        
        channelListLabel = new JLabel("Channels");
        channelListLabel.setForeground(Color.yellow);
        channelListLabel.setBackground(Color.BLACK);
        channelListLabel.setOpaque(true);
        GBC.fill = GridBagConstraints.BOTH;
        //GBC.weighty = .5;
        GBC.weightx = .05;
        GBC.gridx = 0;
        GBC.gridy = 0;
        contents.add(channelListLabel, GBC);
        
        friendLabel = new JLabel("Online");
        friendLabel.setForeground(Color.yellow);
        friendLabel.setBackground(Color.BLACK);
        friendLabel.setOpaque(true);
        GBC.fill = GridBagConstraints.BOTH;
        //GBC.weighty = .5;
        GBC.weightx = .05;
        GBC.gridx = 3;
        GBC.gridy = 0;
        contents.add(friendLabel, GBC);
        
        chatArea = new JTextArea();
        scrollPane = new JScrollPane(chatArea);
        chatArea.setBackground(Color.WHITE);
        GBC.fill = GridBagConstraints.BOTH;
        GBC.weighty = 1;
        GBC.gridx = 1;
        GBC.gridy = 1;
        GBC.gridheight = 2;
        GBC.gridwidth = 2;
        contents.add(scrollPane, GBC);
        
        chatInput = new JTextField();
        chatInput.setBackground(Color.WHITE);
        GBC.weighty = .05;
        GBC.gridx = 1;
        GBC.gridy = 3;
        contents.add(chatInput, GBC);
        
        chatInput.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent enter){
                out.println(chatInput.getText());
                chatInput.setText("");
            }
        });
               
        channelListModel = new DefaultListModel();
        channelListModel.addElement("Channel 1");
        channelListModel.addElement("Channel 2");
        channelListModel.addElement("Channel 3");
        
        channelList = new JList(channelListModel);
        channelList.setBackground(Color.LIGHT_GRAY);
        channelList.setLayoutOrientation(JList.VERTICAL);
        GBC.weighty = .5;
        GBC.weightx = .05;
        GBC.gridx = 0;
        GBC.gridy = 1;
        GBC.gridheight = 1;
        GBC.gridwidth = 1;
        contents.add(channelList, GBC);
        
        logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.BLACK);
        logoutButton.setForeground(Color.yellow);
        GBC.weighty = .05;
        GBC.weightx = .05;
        GBC.gridx = 0;
        GBC.gridy = 3;
        contents.add(logoutButton, GBC);
        
        sendButton = new JButton("Send");
        sendButton.setBackground(Color.BLACK);
        sendButton.setForeground(Color.yellow);
        GBC.weighty = .05;
        GBC.weightx = .05;
        GBC.gridx = 3;
        GBC.gridy = 3;
        contents.add(sendButton, GBC);
        
        sendButton.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent send){
                out.println(chatInput.getText());
                chatInput.setText("");
            }
        });
        
        onLineModel = new DefaultListModel();
        onLine = new JList(onLineModel);
        onLine.setBackground(Color.LIGHT_GRAY);
        onLine.setLayoutOrientation(JList.VERTICAL);
        GBC.weighty = .5;
        GBC.weightx = .05;
        GBC.gridx = 3;
        GBC.gridy = 1;
        GBC.gridheight = 1;
        GBC.gridwidth = 1;
        contents.add(onLine, GBC);
        
        
        setSize(1000,700);
        
        setVisible(true);
    }
    
        
    private boolean run() {
        
        try {
            Socket socket = new Socket("host", 9090);
        } 
        catch ( Exception exc){
            chatArea.append("Error connecting to the server." + exc);
        }
        
        chatArea.append("Connected to " + socket.getInetAddress() + ":" + socket.getPort());
        
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        }
        
        catch(IOException excIO){
            chatArea.append("Error creating new I/O Streams: " + excIO);
            return false;
        }
        
        while(true) {
            try {
                String line = in.readLine();
                chatArea.append(line.substring(WIDTH)+ "\n");
            }
            catch(IOException exIO){
                chatArea.append("Error reading from the server: " + exIO);
            }
        }
    }
    
    
    public static void main(String[] args) 
    {
        OIM Gui = new OIM();
        Gui.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //Gui.run();
    }
    
}
