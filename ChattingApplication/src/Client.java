import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Client implements ActionListener {

    JTextField text ;

    static JPanel a1;

    static Box vertical = Box.createVerticalBox();

    static DataOutputStream dout;

    static JFrame frame = new JFrame();


    Client(){

        frame.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        frame.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter(){
                                  public void mouseClicked(MouseEvent me){
                                      System.exit(0);

                                  }
                              }
        );


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/elon.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(355,20,35,30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,22,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel moreOption = new JLabel(i15);
        moreOption.setBounds(410,22,10,25);
        p1.add(moreOption);

        JLabel name = new JLabel("Elon Musk");
        name.setBounds(110,11,130,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,21));
        p1.add(name);

        JLabel status = new JLabel("online");
        status.setBounds(110,37,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,15));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        frame.add(a1);

        text = new JTextField();
        text.setBounds(5,653,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,17));
        frame.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,653,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,22));
        frame.add(send);



        frame.setSize(450,700);
        frame.setLocation(900,50);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setUndecorated(true);
        frame.setVisible(true);


    }

    public void actionPerformed(ActionEvent ae){

        try{
            String msgOutput = text.getText();

            JPanel p2 = formatLabel(msgOutput);

            a1.setLayout(new BorderLayout());

            JPanel rightSide = new JPanel(new BorderLayout());
            rightSide.add(p2, BorderLayout.LINE_END);
            vertical.add(rightSide);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(msgOutput);

            text.setText("");

            frame.repaint();
            frame.invalidate();
            frame.validate();

        } catch(Exception e){
            e.printStackTrace();
        }


    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel tim = new JLabel(sdf.format(cal.getTime()));
        tim.setText(sdf.format(cal.getTime()));

        panel.add(tim);

        return panel;
    }


    public static void main(String[] args) {
        new Client();

        try{
            Socket s = new Socket("127.0.0.1", 2000);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while(true){
                a1.setLayout(new BorderLayout());

                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);



                frame.validate();


            }


        }catch(Exception e){
            e.printStackTrace();

        }

    }
}
