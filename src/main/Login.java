/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package main;

/**
*
* @author Ritesh Kumar Gupta
*/
import java.math.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login implements ActionListener
{

	JFrame frame;
	JPanel panel;
	BufferedImage myPicture;
	JLabel picLabel;
	JButton login_b;
	JButton register_b;
	JLabel nick_l;
	JLabel footer;
	JTextField nick_t;
	JLabel pass_l;
	JPasswordField pass_t;
	String proxY;
	dbConnection dbc;
	PreparedStatement query = null;

	public Login()  throws IOException                                          //Constructor of Login Class
	{
		//Creating Instants of the Inbuilt Swing class/ components
		frame=new JFrame("Highly Confidential Security System");
		panel=new JPanel();
		login_b=new JButton("Sign in");
		register_b=new JButton("New User?Enter Confidential Data.");
		myPicture = ImageIO.read(new File("src/main/images/logo.png"));
		picLabel= new JLabel(new ImageIcon(myPicture));

		nick_l=new JLabel("Username:");
		nick_t=new JTextField();

		pass_l=new JLabel("Password:");
		pass_t=new JPasswordField();

		footer=new JLabel();

		//Frame
		frame.setSize(360,530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		frame.setResizable(false);

		//panel
		panel.setBackground(Color.BLACK);
		panel.add(login_b);
		panel.add(register_b);
		panel.add(nick_t);
		panel.add(picLabel);
		panel.add(nick_l);
		panel.add(pass_l);
		panel.add(pass_t);
		panel.add(footer);
		panel.setLayout(null);

		//Logo
		picLabel.setBounds(20, 30, 300, 190);

		//Text Field Label
		nick_l.setForeground(Color.WHITE);
		nick_l.setBounds(20, 240, 200, 30);

		//Text Field
		nick_t.setBounds(90, 245, 200, 24);
		nick_t.setBackground(Color.DARK_GRAY);
		nick_t.setForeground(Color.WHITE);
		nick_t.setToolTipText("User");

		/*Password Field*/
		pass_l.setForeground(Color.WHITE);
		pass_l.setBounds(20, 285, 200, 10);

		//Text Field
		pass_t.setBounds(90, 285, 200, 24);
		pass_t.setBackground(Color.DARK_GRAY);
		pass_t.setForeground(Color.WHITE);
		pass_t.setToolTipText("PassWord");

		//Confirm Button
		login_b.setBounds(155, 325, 80,25);
		login_b.setBackground(Color.darkGray);
		login_b.setForeground(Color.WHITE);
		login_b.addActionListener(this);

		//Register Button
		register_b.setBounds(60, 380, 235,25);
		register_b.setBackground(Color.darkGray);
		register_b.setForeground(Color.WHITE);
		register_b.addActionListener(this);

		//Footer(Descriptions || Terms and Conditions and all)
		footer.setForeground(Color.WHITE);
		footer.setText("<html><p align=justify>Highly Confidential Security System (H.C.S.S.) is a project by Ritesh Kumar Gupta.This is a java application for storing and retrieving confidential data in single credentials.</p>");
		footer.setBounds(45,390,275,128);
	}
	/*This function checks database , whether the user name and password entered are correct or not*/
	boolean userAuthenticate(String UserID,String UserPWD)
	{
		dbc=new dbConnection();
		Connection link=dbc.getdbconnectionString();//Get a connection from database
		try
		{
			BlowFishCipher BF=new BlowFishCipher();
			query=link.prepareStatement("SELECT user,pass "+ "FROM images " + "WHERE user=?");
			query.setString(1,UserID);
			ResultSet rs;
			rs =query.executeQuery();
			if (!rs.next()) 
			{
				JOptionPane.showMessageDialog(frame,"Oops!The Username you entered does not exist in our database.");
				return false;
			} 
			else
			{
				String db_fetched_pwd=rs.getString("pass");
				if(db_fetched_pwd.equals(BF.md5(UserPWD)))
				{
					JOptionPane.showMessageDialog(frame,"Great!!You are an authenticated User.\nThe data is stored in encrypted form.\nThe secret key is required to decode the data.\n\t\tPress OK to Proceed Further.");
					return true;
				}
				else
				{
					JOptionPane.showMessageDialog(frame,"Incorrect Password.\nPlease enter the correct password!!");
				}

			}
		}
		catch (Exception ex)
		{
			System.out.println("The Following Error Occured while Checking User Handle and Password from the database: "+ex.toString());
		}
		return false;
	}
	public void actionPerformed(ActionEvent e)   
	{
		String name="";
		String pwd="";
		char pwd_array[]=pass_t.getPassword();
		pwd=String.copyValueOf(pwd_array);
		name=nick_t.getText();
		if((e.getSource()==login_b))
		{
			if(name.equals("") || pwd.equals(""))
			{
				JOptionPane.showMessageDialog(frame,"Either UserName or Password is empty.Please re-enter Correct username and password.");
			}
			else
			{
				/*When Authentication is successful, redirect the user to dataaccess page*/
				if(userAuthenticate(name,pwd))
				{
					try
					{
						frame.dispose();
						dataAccess Access=new dataAccess(name);
						Access.fetchdata();

					}
					catch (Exception ex)
					{
						System.out.println("Following Error occured while retrieving the data: "+ex.toString()); 
					}
				}
			}
		}
		try
		{
			if((e.getSource()==register_b))
			{
				showdataEntryForm();
				frame.dispose();

			}
		}
		catch (IOException te)
		{
			System.out.println(te.toString());
		}   

	}
	public void showdataEntryForm()throws IOException
	{
		enterData En=new enterData();
		System.out.println("New Data Entry");
	}
}

