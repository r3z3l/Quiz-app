package git.Quiz_app;

import git.Quiz_app.Util.jbtn;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class admin extends JFrame implements ActionListener {



ArrayList<JToggleButton> list = new ArrayList<>();
JLabel head,h2,qn;
JPanel header,bottom,body, left;
JScrollPane scroll;
jbtn clear, add;
JButton lnk;
JToggleButton x;
JTextField drop;


admin(){
    setTitle("admin panel");
    this.setLayout(new BorderLayout());
    getContentPane().setBackground(Color.decode("#f4f4f4"));

    //header panel
    header = new JPanel();
    header.setPreferredSize(new Dimension(100,100));
    header.setLayout(new MigLayout("fillx","[grow, left]",""));
    header.setBackground(Color.decode("#f4f4f4"));
    this.add(header,BorderLayout.NORTH);
    // head on header panel
    head = new JLabel("Admin",JLabel.CENTER);
    head.setFont(new Font("Osward", Font.BOLD, 38));
    header.add(head,"pushx,grow,wrap");
    h2 = new JLabel("question-set");
    h2.setFont(new Font("Osward",Font.PLAIN,15));
    header.add(h2,"align center");




    body = new JPanel();
    body.setPreferredSize(new Dimension(700,600));
//    body.setBackground(Color.decode("#333"));
    this.add(body,BorderLayout.CENTER);
    body.setLayout(new GridLayout(0,1,0,0));

    //SQL QUERY GOES HERE
    ResultSet rs;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/quiz_app", "root", "root");
        Statement s = c.createStatement();
        String query = "SELECT * FROM question;";
        rs = s.executeQuery(query);
        int i=1;
        while(rs.next()){
            x = new JToggleButton(i + ". " + rs.getString(2));
            x.setBackground(Color.white);
            x.setBorderPainted(false);
            x.setFocusPainted(false);
            x.setFont(new Font("Raleway", Font.BOLD, 15));
            x.setBounds(0,0,500,5);
            body.add(x);
            i++;
        }
        x.addActionListener((ActionListener) body);
        s.close();
        c.close();
    }
    catch(Exception exec){
        System.out.println("End of table reached");
    }
    scroll = new JScrollPane(body);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    this.add(scroll);

    //bottom layout
    bottom= new JPanel();
    bottom.setPreferredSize(new Dimension(100,100));
    bottom.setLayout(new MigLayout("fillx","[:70, grow, center][grow, left]",""));
    qn = new JLabel("Type question number to be removed:");
    qn.setFont(new Font("Raleway", Font.PLAIN, 15));
    bottom.add(qn,"align right");
    drop = new JTextField();
    bottom.add(drop,"wmin 50, wmax 50, wrap");

    bottom.setBackground(Color.decode("#f4f4f4"));
    this.add(bottom, BorderLayout.SOUTH);
    clear = new jbtn("REMOVE",Color.decode("#9A9483"), Color.decode("#C7BEA2"));
    add = new jbtn("ADD",Color.decode("#9A9483"), Color.decode("#C7BEA2"));
    bottom.add(clear,"span,wmin 100, wmax 100,align center,split2");
    bottom.add(add,"wmin 100, wmax 100,wrap 2px");
    clear.addActionListener(this);
    add.addActionListener(this);

    lnk = new JButton("Return to Login? Click here");
    lnk.setFont(new Font("Raleway", Font.BOLD, 10));
    lnk.setCursor(new Cursor(Cursor.HAND_CURSOR));
    lnk.setFocusPainted(false);
    lnk.setForeground(Color.decode("#193498"));
    lnk.setBackground(Color.decode("#f4f4f4"));
    lnk.setBorderPainted(false);
    lnk.addActionListener(this);
    bottom.add(lnk,"span,align center,wrap");




    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setSize(700,600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
}

    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getSource()==add)
    {   this.dispose();
        admin_add add = new admin_add();

    }

    if(e.getSource()==lnk){
        this.dispose();
        Main m = new Main();
    }
    if(e.getSource()==clear) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/quiz_app", "root", "root");
            Statement s = c.createStatement();

            String query = "SELECT COUNT(*) FROM question;";
            ResultSet rs = s.executeQuery(query);
            rs.next();
            int maxQuestion = rs.getInt(1);

            query = "DELETE FROM question WHERE question_no=" + drop.getText() + ";";
            s.executeUpdate(query);

            query = "UPDATE question SET question_no=" + drop.getText() + " WHERE question_no=" + maxQuestion + " ;";
            s.executeUpdate(query);

            query = "ALTER TABLE question AUTO_INCREMENT=1;";
            s.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException em) {
            System.out.println(em);
        }
        JOptionPane.showMessageDialog(null,"Question has been deleted!!!","Success", JOptionPane.OK_OPTION);
        this.dispose();
        admin ad = new admin();
    }
    }
}
