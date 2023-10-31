import java.sql.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * @authors Soham,Yogesh,Tanmay,Vaibhav
 */

public class QuizClient2 extends JFrame implements ActionListener {

    static JLabel l1;
    static JFrame f;
    static ButtonGroup cb;
    static JRadioButton a, b, c, d;
    static JButton sbmt, get;
    static int flag = 1;
    static String rm, sm;
    static String qno, ans, temp;

    static Connection con;
    static Statement stmt;
    static ResultSet rs;

    static Socket sock;
    static PrintWriter pw;
    static BufferedReader keyread;
    static InputStream istream;
    static BufferedReader rread;

    QuizClient2() {
        try {

            System.out.println("into constructor");

            if (flag == 1) {
                f.setTitle("Quiz Client");
                f.setSize(500, 500);
                f.setLayout(new FlowLayout());
                f.setVisible(true);
                l1 = new JLabel("loading...");
                cb = new ButtonGroup();
                a = new JRadioButton("loading...");
                b = new JRadioButton("loading...");
                c = new JRadioButton("loading...");
                d = new JRadioButton("loading...");
                sbmt = new JButton("Submit");
                get = new JButton("get");
                cb.add(a);
                cb.add(b);
                cb.add(c);
                cb.add(d);
                f.add(l1);
                f.add(a);
                f.add(b);
                f.add(c);
                f.add(d);
                f.add(sbmt);
                f.add(get);
                get.addActionListener(this);
                sbmt.addActionListener(this);
            }
            // l1.setText("ans");
            if (flag == 2) {
                f.removeAll();
                l1.setText("loading...");
                a.setText("loading...");
                b.setText("loading...");
                c.setText("loading...");
                d.setText("loading...");
            }

            System.out.println("out of while");
            
            System.out.println("adding done");
            
            // For closing frame window

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });

            // sbmt.addActionListener(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == sbmt) {
                ans = cb.getSelection().getActionCommand();
                stmt.executeUpdate("insert into student values('" + qno + "','" + temp + "','" + ans + "')");
                System.out.println("Answer Submitted...");
                flag = 2;
                System.out.println("done remove");
                update();
            } else if (ae.getSource() == get) {
                System.out.println("btn click");
                con = DriverManager.getConnection("jdbc:odbc:AJPmicro");
                rs = stmt.executeQuery("select * from queans where qno='" + qno + "'");
                System.out.println("stmt done");
                while (rs.next() == true) {
                    l1.setText(rs.getString("que"));
                    a.setText(rs.getString("a"));
                    b.setText(rs.getString("b"));
                    c.setText(rs.getString("c"));
                    d.setText(rs.getString("d"));
                    a.setActionCommand(a.getText());
                    b.setActionCommand(b.getText());
                    c.setActionCommand(c.getText());
                    d.setActionCommand(d.getText());
                    temp = (rs.getString("ans"));
                }

            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws Exception {
        f = new JFrame();
        QuizClient2 qc = new QuizClient2();
        f.setVisible(true);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("Driver Loaded");
            con = DriverManager.getConnection("jdbc:odbc:AJPmicro");
            System.out.println("Connection Established...");
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println("Exception");
        }
        sock = new Socket("127.0.0.1", 3000);
        OutputStream ostream = sock.getOutputStream();
        pw = new PrintWriter(ostream, true);
        keyread = new BufferedReader(new InputStreamReader(System.in));
        istream = sock.getInputStream();
        rread = new BufferedReader(new InputStreamReader(istream));
        System.out.println("Waiting..........");
        while (true) {
            if ((rm = rread.readLine()) != null) {
                qno = rm;
                System.out.println(qno);
                flag = 2;
                new QuizClient();
            }
        }
    }

    public void update() {
        try {
                System.out.println("into update");
                l1.setText("loading...");
                a.setText("loading...");
                b.setText("loading...");
                c.setText("loading...");
                d.setText("loading...");
        } catch (Exception e) {
        }
    }
}
