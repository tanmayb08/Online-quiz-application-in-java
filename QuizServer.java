import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * @authors Soham,Yogesh,Tanmay,Vaibhav
 */

public class QuizServer extends Frame implements ActionListener {
    static boolean on = true;
    static int it = 0;

    static Label l1, l2, qnl, qul, al, bl, cl, dl, anl;
    static TextField qno, que, a, b, c, d, ans;
    Button send, win;
    Panel p1;

    String qno1, que1, a1, b1, c1, d1, ans1;

    static Connection con;
    static Statement stmt;
    static String sql, sm, rm;

    static ServerSocket servsock;
    static BufferedReader keyread;
    static Socket sock;
    static BufferedReader rread;
    static OutputStream ostream;
    static InputStream istream;

    static PrintWriter pw;

    QuizServer() {
        try {

            setTitle("Quiz Server");
            setSize(475, 500);
            setLayout(new FlowLayout());
            //l1 = new Label("WELCOME");
            qno = new TextField(30);
            que = new TextField(50);
            a = new TextField(55);
            b = new TextField(55);
            c = new TextField(55);
            d = new TextField(55);
            ans = new TextField(50);
            qnl = new Label("Enter question number : ");
            qul = new Label("Question : ");
            al = new Label("a : ");
            bl = new Label("b : ");
            cl = new Label("c : ");
            dl = new Label("d : ");
            anl = new Label("answer : ");
            send = new Button("Send");
            win = new Button("End");
            //add(l1);
            add(qnl);
            add(qno);
            add(qul);
            add(que);
            add(al);
            add(a);
            add(bl);
            add(b);
            add(cl);
            add(c);
            add(dl);
            add(d);
            add(anl);
            add(ans);
            add(send);
            add(win);
            send.addActionListener(this);
            win.addActionListener(this);
            System.out.println("Loading done");
        } catch (Exception e) {
            System.out.println(e);
        }
        // for closing java after closing window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println("Action perform");
        try {
            if (ae.getSource() == send) {
                qno1 = qno.getText();
                que1 = que.getText();
                a1 = a.getText();
                b1 = b.getText();
                c1 = c.getText();
                d1 = d.getText();
                ans1 = ans.getText();

                sql = "insert into queans values('" + qno1 + "','" + que1 + "','" + a1 + "','" + b1 + "','" + c1 + "','"
                        + d1 + "','" + ans1 + "')";
                stmt.executeUpdate(sql);
                System.out.println("stmt done");
                l2 = new Label("Query uploaded...");
                System.out.println("l2 set");
                add(l2);
                System.out.println("l2 add");
                pw.println(qno1);
                System.out.println("pw send");
                pw.flush();
                System.out.println("Action Done");
            } else if (ae.getSource() == win) {
                on = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    QuizServer(int a) {
        try {
            send.addActionListener(this);
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public static void main(String[] args) throws Exception {

        QuizServer qc = new QuizServer();
        qc.setVisible(true);
        // new QuizServer(2);

        //For database Connection
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        System.out.println("Driver Loaded");
        con = DriverManager.getConnection("jdbc:odbc:AJPmicro");
        System.out.println("Connection Established...");
        stmt = con.createStatement();

        //For Client server Connection
        servsock = new ServerSocket(3000);
        System.out.println("Server is Ready...");
        sock = servsock.accept();
        keyread = new BufferedReader(new InputStreamReader(System.in));
        ostream = sock.getOutputStream();
        pw = new PrintWriter(ostream, true);
        istream = sock.getInputStream();
        rread = new BufferedReader(new InputStreamReader(istream));

    }
}
