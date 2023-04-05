import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Creating the Menu Interface to run everything and create the GUI
public class MenuInterface extends JFrame {
    // Jpanels and JPanes
    private JPanel panelMain = new JPanel();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JSplitPane splitPaneAB = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JSplitPane splitPaneABC = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JSplitPane splitPaneCT = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JPanel panelAlice = new JPanel();
    private JPanel panelBob = new JPanel();
    private JPanel panelCharlie = new JPanel();
    private JPanel toolBar = new JPanel();

    // JLabels
    private JLabel labelAlice= new JLabel("Alice");
    private JLabel labelBob= new JLabel("Bob");
    private JLabel labelCharlie= new JLabel("Charlie");

    // JText Areas
    private JTextArea aliceText = new JTextArea();
    private JTextArea bobText = new JTextArea();
    private JTextArea charlieText = new JTextArea();
    private JTextArea console = new JTextArea("console");

    // JButtons
    private JButton generateKeys = new JButton("Generate keys");
    private JButton choose_p_and_q = new JButton("Choose P and Q");
    private JButton encrypt = new JButton("Encrypt");
    private JButton decrypt = new JButton("Decrypt");
    private JButton outputKeys = new JButton("Output Keys");

    // JScrollPanes
    private JScrollPane scrollPaneAlice = new JScrollPane(aliceText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JScrollPane scrollPaneBob = new JScrollPane(bobText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JScrollPane scrollPaneCharlie = new JScrollPane(charlieText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JScrollPane scrollPaneConsole = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Creating the classes of Bob, Alice and Charlie to be used in the program
    private Bob bob = new Bob();
    private Alice alice = new Alice();
    private Charlie charlie = new Charlie();

    public MenuInterface(){
        // Title and sizing data
        setTitle("RSA Simulator");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(50, 50, 50));

        panelMain.setBorder(new LineBorder(new Color(10,10,10)));
        panelMain.setLayout(new FlowLayout());

        //toolbar section
        splitPaneABC.setDividerLocation(550);

        toolBar.add(generateKeys);
        generateKeys.addActionListener(this::keyGeneration);
        toolBar.add(choose_p_and_q);
        choose_p_and_q.addActionListener(this::addPQ);
        toolBar.add(encrypt);
        encrypt.addActionListener(this::setEncrypt);
        toolBar.add(decrypt);
        decrypt.addActionListener(this::setDecrypt);
        toolBar.add(outputKeys);
        outputKeys.addActionListener(this::getOutputKeys);

        //alice
        aliceText.setLineWrap(true);
        aliceText.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        aliceText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelAlice.setBackground(new Color(150, 200, 150));
        panelAlice.setLayout(new BoxLayout(panelAlice,BoxLayout.PAGE_AXIS));

        panelAlice.add(labelAlice);
        panelAlice.add(scrollPaneAlice);

        //bob
        bobText.setBorder(BorderFactory.createRaisedBevelBorder());
        bobText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bobText.setLineWrap(true);
        bobText.setEditable(false);
        panelBob.setBackground(new Color(150, 150, 200));
        panelBob.setLayout(new BoxLayout(panelBob,BoxLayout.PAGE_AXIS));

        panelBob.add(labelBob);
        panelBob.add(scrollPaneBob);

        //charlie
        panelCharlie.add(labelCharlie);
        panelCharlie.add(scrollPaneCharlie);
        panelCharlie.setBackground(new Color(200,150,150));
        panelCharlie.setLayout(new BoxLayout(panelCharlie,BoxLayout.PAGE_AXIS));
        panelCharlie.setBorder(BorderFactory.createRaisedBevelBorder());
        panelCharlie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        charlieText.setEditable(false);
        charlieText.setLineWrap(true);

        //console
        console.setEditable(false);
        console.setLineWrap(true);

        //splitpanes management
        splitPaneCT.setTopComponent(scrollPaneConsole);
        splitPaneCT.setBottomComponent(toolBar);
        splitPaneCT.setDividerLocation(150);
        splitPaneCT.setDividerSize(5);

        splitPaneAB.setDividerLocation(300);

        charlieText.setBorder(BorderFactory.createRaisedBevelBorder());
        charlieText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        charlieText.setLineWrap(true);

        splitPane.setBackground(new Color(100, 100, 200));
        splitPane.setSize(1200, 600);
        splitPane.setDividerLocation(600);
        splitPane.setDividerSize(5);

        splitPane.setTopComponent(panelAlice);
        splitPane.setBottomComponent(panelBob);

        splitPaneAB.setTopComponent(splitPane);
        splitPaneAB.setBottomComponent(panelCharlie);
        splitPaneAB.setDividerSize(5);

        splitPaneABC.setTopComponent(splitPaneAB);
        splitPaneABC.setBottomComponent(splitPaneCT);
        splitPaneABC.setDividerSize(5);

        add(splitPaneABC);

        setVisible(true);
        setResizable(false);
    }

    //Boolean function to check if alice's text box is empty
    public boolean  isAliceTextEmpty(){
        if(aliceText.getText().isEmpty()) {
            return true;
        }
        // System.out.println();
        return false;
    }

    //Function to generate new keys for Bob
    public void keyGeneration(ActionEvent e){
        bob.keyGeneration();
        console.setText("New Public Key E: "  + bob.getPublicKey().get(0).toString() + "\n" + "New Public Key N: " + bob.getPublicKey().get(1).toString() + "\n" + "New Private Key D: " +  bob.getPrivateKey());
        
    }

    //Function to begin Encrypting Alice's text
    public void setEncrypt(ActionEvent e){
        //Checks if Alice's text box is empty
        if (!isAliceTextEmpty()) {
            //Alice Steps
            alice.setPlainText(aliceText.getText());
            alice.setBobKey(bob.getPublicKey());
            alice.binaryTextConversion();
            alice.encryptMessage();
            aliceText.setText(alice.getCipherText().toString());

            //Bobs Steps
            //System.out.println("Alice's Text being sent to Bob: " + alice.getCipherText());
            //System.out.println("Alice's Text being sent to Bob: " + alice.getCipherText());
            // bob.clearOldCipher();
            bob.setCipherText(alice.getCipherText());
            bobText.setText(alice.getCipherText().toString());

            //Charlie Steps
            charlie.setPublicKey(bob.getPublicKey());
            charlie.setCipherText(alice.getCipherText());
            charlieText.setText("Intercepted Public Keys: \n" + "E: " + charlie.getPublicKey().get(0) + "\n" + "N: " +charlie.getPublicKey().get(1) + "\nIntercepted Cipher Text: \n" + charlie.getCipherText());

            console.setText("Alice gets Bobs public keys! \nCharlie intercepted communications and has gathered bobs public keys! \nAlice's message has been encrypted! \nAlice has sent her encrypted message to Bob! \nCharlie has intercepted communications and has stored the encrypted message!");
        }
        else {
            console.setText("Alice currently has no message to encrypt. \nPlease type in her field!");
        }

    }

    //Function to Decrypt the cipher that Bob has at that time
    public void setDecrypt(ActionEvent e){
        //System.out.println("setdecrypt is working!\n");
        if (!aliceText.getText().isEmpty()) {
            //Bob sets his text and begins decrypting
            bobText.setText(bob.decryptMessage().toString());
            //Charlie attempts to run decryption if possible based off of the keys he gets
            charlieText.setText(charlie.decryptionVerification());
            console.setText("Using his keys, Bob is able to decrypt the cipher text sent by Charlie! \nCharlie attempts to decrypt the message himself with the information he has! \nThis either works or fails based on the message in his box!");
        }
        else{
            console.setText("Alice currently has no text to encrypt. \nplease type in her field before you decrypt!");
        }
    }

    // Outputs the current keys that Bob has
    public void getOutputKeys(ActionEvent e){
        console.setText("Public Key E: "  + bob.getPublicKey().get(0).toString() + "\n" + "Public Key N: " + bob.getPublicKey().get(1).toString() + "\n" + "Private Key D: " +  bob.getPrivateKey());
    }

    // Function to open a new pane that allows the user to enter custom PQ values
    public void addPQ(ActionEvent e){
        // System.out.println("it working!");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AddPQ create = new AddPQ();
                create.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // System.out.println(create.P);
                        //Checks if the input values are null or not
                        if(!(create.P == null|| create.Q ==null)){
                            bob.customGeneration(create.P, create.Q);
                            console.setText("Bob has generated a private key and public key using\n P: "+create.P + "\n" + "Q: " + create.Q);
                        }

                        super.windowClosed(e);

                    }
                });
            }
        });
    }




}
