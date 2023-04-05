import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigInteger;

public class AddPQ extends JFrame {

    public BigInteger P;
    public BigInteger Q;

    private JLabel labelP = new JLabel("P: ");
    private JLabel labelQ = new JLabel("Q: ");

    private JTextField pText = new JTextField();
    private JTextField qText = new JTextField();

    private JButton submit = new JButton("Submit!");

    private JPanel panel = new JPanel();
    private JLabel errorMessage = new JLabel();

    //constructor function to add all the components to the JFrame.
    public AddPQ() {
        // System.out.println("added pq");
        setTitle("RSA Simulator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(50, 50, 50));

        labelP.setForeground(Color.lightGray);
        labelQ.setForeground(Color.lightGray);
        errorMessage.setForeground(Color.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setBackground(new Color(50,50,50));
        GridLayout grid = new GridLayout(3,2,40,100);
        panel.setLayout(grid);
        panel.add(labelP);
        panel.add(pText);
        panel.add(labelQ);
        panel.add(qText);
        panel.add(errorMessage);
        panel.add(submit);
        submit.addActionListener(this::submitListener);
        add(panel);
        setVisible(true);
    }

    //event listener for the submit button in order to determine whether p and q follow the correct formatting.
    public void submitListener(ActionEvent e){
        String tempP = pText.getText();
        String tempQ = qText.getText();

        // Checks if P and Q are ints and larger than 127 so ASCII issues never happen
        if ((intCheck(tempP) && intCheck(tempQ)) && (isProbablyPrime(tempP) && isProbablyPrime(tempQ)) && (Integer.parseInt(tempP) * Integer.parseInt(tempQ)) > 127)
        {
            P = BigInteger.valueOf(Integer.parseInt(tempP));
            Q = BigInteger.valueOf(Integer.parseInt(tempQ));
            dispose();
        }
        else
        {
            errorMessage.setText("Incorrect Values!");
        }
    }

    //Function to determine whether the textfield contains only numbers.
    public static boolean intCheck(String text)
    {
        try {
            Integer.parseInt(text);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    //checks if the passed variable is a probable prime.
    public boolean isProbablyPrime(String temp)
    {
        return BigInteger.valueOf(Integer.parseInt(temp)).isProbablePrime(5);
    }
}
