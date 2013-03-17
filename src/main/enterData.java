/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

/**
* @author Ritesh Kumar Gupta
*/
package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JTabbedPane;
import java.io.File;
import java.sql.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class enterData extends WindowAdapter implements ActionListener
{
	JFrame frame;
	JPanel panel;
	BufferedImage myPicture,body_picture;
	JLabel header,body_bg,con_lab,con_no_lab;
	JButton logoff,menu2,menu3,menu4,menu5;
	Color customColor;
	Image con_yes,con_no;
	JButton submit_b;
	JTabbedPane jtp;
	JPanel tab1panel,tab2panel,tab3panel,tab4panel,tab5panel,tab6panel;

	/*The  Form Elements For  DataEntry*/
	JTextField username,email,name,creditcardpin,cvv,bankaccno,netbank,pancardno,passport,dl,gmail,facebook;
	JPasswordField password,confirmpassword;
	JLabel username_label,email_label,password_label,confirmpassword_label,instruction1,name_label,creditcardpin_label,cvv_label,bankaccno_label,netbank_label,pancardno_label,passport_label,dl_label,gmail_label,facebook_label;

	final JFileChooser imageUploader;
	JButton imageuploader_b;

	JButton audiouploader_b;/*audio Uploader Button*/
	JButton videouploader_b;/*Video Uploader Button*/
	JButton playPreview;
	JButton playPreview_video;
	String preview_audio_string="";
	String preview_video_string="";

	dbConnection dbc;
	PreparedStatement query = null;
	String user_id="NA";
        BlowFishCipher BF;
	public enterData()throws IOException
	{
		dbc=new dbConnection();
                BF=new BlowFishCipher();
                BF.init();
		frame=new JFrame("Enter Confidential data - Highly Confidential Security System");
		panel=new JPanel();
		tab1panel = new JPanel();
		tab2panel = new JPanel();
		tab3panel = new JPanel();
		tab4panel = new JPanel();
		tab5panel = new JPanel();
                tab6panel = new JPanel();
		myPicture = ImageIO.read(new File("src/main/images/header.png"));
		body_picture=ImageIO.read(new File("src/main/images/chat_img.png"));
		customColor= new Color(60,0,0);
		header=new JLabel(new ImageIcon(myPicture));
		body_bg=new JLabel(new ImageIcon(body_picture));
		logoff=new JButton("LogOff");
		menu2=new JButton("Author");
                menu3=new JButton("About");
		menu4=new JButton("Help");
		menu5=new JButton("Connection(Green 'y')");
		submit_b=new JButton("Submit");
		con_yes=Toolkit.getDefaultToolkit().createImage("src/main/images/green.gif");
		con_no=Toolkit.getDefaultToolkit().createImage("src/main/images/green.gif");
		con_lab=new JLabel(new ImageIcon(con_yes));
		con_no_lab=new JLabel(new ImageIcon(con_no));
		jtp = new JTabbedPane();
		imageUploader = new JFileChooser();
		imageuploader_b=new JButton("Click here to Upload image in an Image Locker.");
		audiouploader_b=new JButton("Click here to Upload Audio in an Audio Locker.");
		videouploader_b=new JButton("Click here to Upload Video in Video Locker.");
		playPreview=new JButton("Preview");
		playPreview_video=new JButton("Video Preview");

		//Frame
		frame.setSize(800, 700);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);                                                       //Add Panel to the Frame
		frame.addWindowListener(this);

		//Panel
		panel.setBackground(Color.BLACK);
		panel.add(header);
		panel.add(con_lab);
		panel.add(con_no_lab);
		panel.add(logoff);
		panel.add(menu2);
		panel.add(menu3);
		panel.add(menu4);
		panel.add(menu5);
		panel.add(jtp);
		panel.setLayout(null);

		//Header
		header.setBackground(Color.PINK);
		header.setBounds(0,0,780,100);

		//Tabbed Panels
		jtp.setBounds(50,145,700,450);
		jtp.setBackground(Color.DARK_GRAY);
		jtp.setForeground(Color.WHITE);

		//Message Sending Button
		submit_b.setBounds(635,625, 100, 40);
		submit_b.addActionListener(this);
		submit_b.setForeground(Color.WHITE);
		submit_b.setBackground(Color.GRAY);

		//imageuploader_b Button
		imageuploader_b.setBounds(635,625, 100, 40);
		imageuploader_b.addActionListener(this);
		imageuploader_b.setForeground(Color.WHITE);
		imageuploader_b.setBackground(Color.GRAY);

		//audiouploader_b Button Configurations
		audiouploader_b.setBounds(635,625, 100, 40);
		audiouploader_b.addActionListener(this);
		audiouploader_b.setForeground(Color.WHITE);
		audiouploader_b.setBackground(Color.GRAY);

		//Videouploader_b Button Configurations
		videouploader_b.setBounds(635,625, 100, 40);
		videouploader_b.addActionListener(this);
		videouploader_b.setForeground(Color.WHITE);
		videouploader_b.setBackground(Color.GRAY);

		//Config of button that play loaded audio/video files 
		playPreview.setBounds(135,225, 100, 40);
		playPreview.addActionListener(this);
		playPreview.setForeground(Color.WHITE);
		playPreview.setBackground(Color.GRAY);

		//Config of button that play loaded video file 
		playPreview_video.setBounds(135,225, 100, 40);
		playPreview_video.addActionListener(this);
		playPreview_video.setForeground(Color.WHITE);
		playPreview_video.setBackground(Color.GRAY);


		//Menubutton
		logoff.setBounds(0, 100,130,25);
		logoff.setBackground(customColor);
		logoff.setForeground(Color.WHITE);
		logoff.addActionListener(this);
		menu2.setBounds(130, 100,130,25);
		menu2.setBackground(customColor);
		menu2.setForeground(Color.WHITE);
                menu2.addActionListener(this);
		menu3.setBounds(260, 100,130,25);
		menu3.setBackground(customColor);
		menu3.setForeground(Color.WHITE);
                menu3.addActionListener(this);
		menu4.setBounds(390, 100,130,25);
		menu4.setBackground(customColor);
		menu4.setForeground(Color.WHITE);
                menu4.addActionListener(this);
		menu5.setBounds(520, 100,190,25);
		menu5.setBackground(customColor);
		menu5.setForeground(Color.WHITE);
                menu5.addActionListener(this);
		//End of Menus Buttons

		//Connection Lights
                con_lab.setBounds(715, 100, 50,40);
		con_no_lab.setBounds(715, 100, 50,40);

		//Image Uploader for Image Locker


		/*Tab 1 Contents here*/
		username=new JTextField(30);
		username_label=new JLabel("Username");
		password=new JPasswordField(30);
		password_label=new JLabel("Password");
		confirmpassword=new JPasswordField(30);
		confirmpassword_label=new JLabel("Confirm Password");
		email=new JTextField(30);
		email_label=new JLabel("Email");
		instruction1=new JLabel("*The username and password that you entered above will be used as  Login Credentials when you access the data.");
		tab1panel.add(username_label);
		tab1panel.add(username);
		tab1panel.add(password_label);
		tab1panel.add(password);
		tab1panel.add(confirmpassword_label);
		tab1panel.add(confirmpassword);
		tab1panel.add(email_label);
		tab1panel.add(email);
		tab1panel.add(instruction1);
		tab1panel.setBackground(Color.DARK_GRAY);
		tab1panel.setForeground(Color.WHITE);
		tab1panel.setLayout(null);

		/*Note Tabbed Pane Coordinates is 50,145,700,450
		* so i am setting position of the form element accordingly
		*/
		username_label.setBounds(60,35, 100,24);
		username_label.setForeground(Color.WHITE);
		username.setBounds(185,35, 200,24);

		email_label.setBounds(60,65, 100,24);
		email_label.setForeground(Color.WHITE);
		email.setBounds(185,65, 200,24);

		password_label.setBounds(60,95, 100,24);
		password_label.setForeground(Color.WHITE);
		password.setBounds(185,95, 200,24);

		confirmpassword_label.setBounds(60,125, 120,24);
		confirmpassword_label.setForeground(Color.WHITE);
		confirmpassword.setBounds(185,125, 200,24);

		instruction1.setBounds(20,200,650,50);
		instruction1.setForeground(Color.WHITE);
		/*End Tab1 Contents*/

		/**********Tab 2 :Personal Locker****************/
		name=new JTextField(30);
		name_label=new JLabel("Name");
		name_label.setForeground(Color.WHITE);

		creditcardpin=new JTextField(30);
		creditcardpin_label=new JLabel("Credit Card PinCode");
		creditcardpin_label.setForeground(Color.WHITE);

		cvv=new JTextField(30);
		cvv_label=new JLabel("CVV Number");
		cvv_label.setForeground(Color.WHITE);

		bankaccno=new JTextField(30);
		bankaccno_label=new JLabel("Bank Account Number");
		bankaccno_label.setForeground(Color.WHITE);

		netbank=new JTextField(30);
		netbank_label=new JLabel("NetBanking Password");
		netbank_label.setForeground(Color.WHITE);

		pancardno=new JTextField(30);
		pancardno_label=new JLabel("PanCard Number");
		pancardno_label.setForeground(Color.WHITE);

		passport=new JTextField(30);
		passport_label=new JLabel("Passport Number");
		passport_label.setForeground(Color.WHITE);

		dl=new JTextField(30);
		dl_label=new JLabel("Driving License Number");
		dl_label.setForeground(Color.WHITE);

		gmail=new JTextField(30);
		gmail_label=new JLabel("Gmail Password");
		gmail_label.setForeground(Color.WHITE);

		facebook=new JTextField(30);
		facebook_label=new JLabel("Facebook Password");
		facebook_label.setForeground(Color.WHITE);

		tab2panel.add(name_label);
		tab2panel.setBackground(Color.DARK_GRAY);
		tab2panel.add(name);
		tab2panel.add(creditcardpin_label);
		tab2panel.add(creditcardpin);
		tab2panel.add(cvv_label);
		tab2panel.add(cvv);
		tab2panel.add(bankaccno_label);
		tab2panel.add(bankaccno);
		tab2panel.add(netbank_label);
		tab2panel.add(netbank);
		tab2panel.add(pancardno_label);
		tab2panel.add(pancardno);
		tab2panel.add(passport_label);
		tab2panel.add(passport);
		tab2panel.add(dl_label);
		tab2panel.add(dl);
		tab2panel.add(gmail_label);
		tab2panel.add(gmail);
		tab2panel.add(facebook_label);
		tab2panel.add(facebook);
		tab2panel.setLayout(null);

		/*Note Tabbed Pane Coordinates is 50,145,700,450
		* so i am setting position of the form element accordingly
		*/
		name_label.setBounds(60,35, 150,24);
		name.setBounds(225,35, 200,24);

		creditcardpin_label.setBounds(60,65, 150,24);
		creditcardpin.setBounds(225,65, 200,24);

		cvv_label.setBounds(60,95, 150,24);
		cvv.setBounds(225,95, 200,24);

		bankaccno_label.setBounds(60,125, 150,24);
		bankaccno.setBounds(225,125, 200,24);

		netbank_label.setBounds(60,155, 150,24);
		netbank.setBounds(225,155, 200,24);

		pancardno_label.setBounds(60,185, 150,24);
		pancardno.setBounds(225,185, 200,24);

		passport_label.setBounds(60,215, 150,24);
		passport.setBounds(225,215, 200,24);

		dl_label.setBounds(60,245, 150,24);
		dl.setBounds(225,245, 200,24);

		gmail_label.setBounds(60,275, 150,24);
		gmail.setBounds(225,275, 200,24);

		facebook_label.setBounds(60,305, 150,24);
		facebook.setBounds(225,305, 200,24);
		/*****************End Tab 2************/

		/**********Tab 3: Note Tab 3 is for image Locker***********/
		tab3panel.add(imageuploader_b);
		tab3panel.setBackground(Color.DARK_GRAY);
		tab3panel.setLayout(null);
		imageuploader_b.setBounds(180,35, 300,50);

		/**************End Tab 3***********/

		/**************Tab 4: Note Tab 4 is for Audio Locker*************/
		tab4panel.setBackground(Color.DARK_GRAY);
		tab4panel.add(audiouploader_b);
		tab4panel.add(playPreview);
		tab4panel.setLayout(null);
		audiouploader_b.setBounds(180,35, 300,50);
		playPreview.setVisible(false);
		playPreview.setBounds(180,315, 300,50);

		/************End Tab 4*******************************************/

		/******************Tab 5:****************************/
		tab5panel.add(videouploader_b);
		tab5panel.setBackground(Color.DARK_GRAY);
		tab5panel.add(playPreview_video);
		tab5panel.setLayout(null);
		videouploader_b.setBounds(180,35, 300,50);
		playPreview_video.setVisible(false);
		playPreview_video.setBounds(180,315, 300,50);
		/*End Tab 5*/
                
                /*Tab Panel 6*/
                tab6panel.add(submit_b);
		tab6panel.setBackground(Color.DARK_GRAY);
		tab6panel.setLayout(null);
                submit_b.setBounds(180,35, 300,50);
                /*End Tab Panel 6*/

		//Tabbeed Panel
		jtp.addTab("User Credentials",tab1panel);
		jtp.addTab("Personal Locker", tab2panel);
		jtp.addTab("Image Locker", tab3panel);
		jtp.addTab("Audio Locker", tab4panel);
		jtp.addTab("Video Locker", tab5panel);
                jtp.addTab("Submit data", tab6panel);

		jtp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				insertdataintodatabase( jtp.getSelectedIndex());
			}
		});


	}
        /*Some Field like username,password,cnfrm password are mandatory field.so unless it is completely filled,we won't populate database*/
        String ismandatoryfield_completed()
        {
            String ErrorMsg="";
            
            /*User name must be mandatorily filled by the user*/
            if(username.getText().equals(""))
            {
                ErrorMsg=ErrorMsg+"UserName Should not be empty\n";
            }
            else//When the username supplied is not empty,check whether already same nick name is occupied by another User.
            {
                Connection link=dbc.getdbconnectionString();
                try
                {
                    query=link.prepareStatement("SELECT user "+ "FROM images " + "WHERE user=?");
                    query.setString(1,username.getText());
                    ResultSet rs;
                    rs =query.executeQuery();
                    if (rs.next()) 
                    {
                        ErrorMsg=ErrorMsg+"UserName already exist in our database.Select different username.\n";
                    } 
                }
                catch(Exception ex)
                {
                    System.out.println("Following Error Occured while Checking Duplicate UserName "+ex.toString());
                }
            }
            
            char pwd_array[]=password.getPassword();
            String db_password=String.copyValueOf(pwd_array);
            
            pwd_array=confirmpassword.getPassword();
            String Confirm_password=String.copyValueOf(pwd_array);
            
            if((db_password.equals(Confirm_password)) && (db_password.length()>0 && Confirm_password.length()>0))
            {}
            else
            {
                ErrorMsg=ErrorMsg+"Enter password and Confirm password did not match.\n";
            }
            return ErrorMsg;
            
        }
	/*This function insert the data into database.It is called when ever we click the tab button*/
        boolean insertdataintodatabase(int tab_clicked)
	{
                /*7 means insert the informations into database*/
		if(tab_clicked==7)
		{
			String db_user="NA",db_password="",db_name="NA",db_email="NA",db_credit="NA",db_cvv="NA",db_bankaccount="NA",db_netbank="NA",db_pancard="NA",db_passport="NA",db_dl="NA",db_gmail="NA",db_facebook="NA";
			String fetchedata[]=new String[14];
			for(int i=0;i<fetchedata.length;++i)
				fetchedata[i]="NA";
			/*Collect the value from from and assign it to above variables*/
			db_user=fetchedata[0]=username.getText();
			char pwd_array[]=password.getPassword();
			db_password=fetchedata[1]=String.copyValueOf(pwd_array);
			db_name=fetchedata[2]=name.getText();db_email=fetchedata[3]=email.getText();
			db_credit=fetchedata[4]=creditcardpin.getText();db_cvv=fetchedata[5]=cvv.getText();db_bankaccount=fetchedata[6]=bankaccno.getText();
			db_netbank=fetchedata[7]=netbank.getText();db_pancard=fetchedata[8]=pancardno.getText();db_passport=fetchedata[9]=passport.getText();
			db_dl=fetchedata[10]=dl.getText();db_gmail=fetchedata[11]=gmail.getText();db_facebook=fetchedata[12]=facebook.getText();
			user_id=db_user;
			try
			{
				fetchedata=BF.Encrypt(fetchedata);
			}
			catch(Exception ex)
			{
				System.out.println("Following Error Occured while Encrypting the data: "+ex.toString());
                                return false;
                        }
			Connection link=dbc.getdbconnectionString();
			try
			{
				query=link.prepareStatement("Insert into images (user,pass,name,email,credit,cvv,bankaccount,netbank,pancard,passport,dl,gmail,facebook) "+" values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
				query.setString(1,db_user);//Do not encrypt User name
				/*Insert MD5 Hash of password. Encrypt rest of the entities with blowFish Cipher*/
				query.setString(2,BF.md5(db_password));query.setString(3,fetchedata[2]);query.setString(4,fetchedata[3]); query.setString(5,fetchedata[4]); query.setString(6,fetchedata[5]); 
				query.setString(7,fetchedata[6]); query.setString(8,fetchedata[7]); query.setString(9,fetchedata[8]);query.setString(10,fetchedata[9]); 
				query.setString(11,fetchedata[10]);query.setString(12,fetchedata[11]);query.setString(13,fetchedata[12]);
				int s =query.executeUpdate();
				if(s>0)
					System.out.println("The Information is successfully Update into database");
				else
					System.out.println("Oops!!an Internal Error has Occured while inserting the information into to the database.");
			}
			catch(Exception ex)
			{
				System.out.println("The Following Error Has Occured while Inserting Personal Information into database: "+ex.toString());
                                return false;
                        }
		}
                return true;
	}
	public void actionPerformed(ActionEvent e)
	{
		// When Submit button is pressed ,whold data is to be inserted into database.Some data might be misleading or InComplete.I am checking those misleading/InComplete data before inserting into database.*/
		if(e.getSource()==submit_b)
		{
                    String errmsg=ismandatoryfield_completed();
                    if(errmsg.length()>0)
                    {
                        JOptionPane.showMessageDialog(frame,"The data cannot be inserted into database due to following Errors :\n"+errmsg+"\nPlease fix them first (by clicking on User Credentials tab)before proceeding further\n");
                    }
                    else
                    {
                        if(insertdataintodatabase(7))//7 is just an indication of "Insert the data in forms into database"
                        {
                             File file = new File("");
                            System.out.println();
                            JOptionPane.showMessageDialog(frame,"Data is Successfully stored in a database.\nPlease note: The Secret key is generated in following text file: "+file.getAbsoluteFile()+"\\secretkey\\"+username.getText()+"_secretkey.txt\nThe Secret Key in the text file will be used later for decoding the data.\nPlease Store it and Keep it safe!!");
                        }
                        else
                        {
                            
                        }
                    }
                    
		}
		if (e.getSource()==logoff)
		{
			frame.dispose();
			try
			{
				File tmp=new File(preview_audio_string);
                                tmp.delete();
                                tmp=new File(preview_video_string);
                                tmp.delete();
                                System.out.println("Closing Windows... Thank You!");
                                System.exit(1);
				System.exit(1);
			}
			catch(Exception oe)
			{
			}
		}
                if(e.getSource()==menu2)
                {
                    JOptionPane.showMessageDialog(frame,"This Project is developed by Ritesh Kumar Gupta.");
                }
                if(e.getSource()==menu3)
                {
                    JOptionPane.showMessageDialog(frame,"Highly Confidential Security System is a java application that stores user sign-in\n information and all others credientals data in a database.Data in a database are \nstored in encrypted form.It uses BlowFish Cipher,which is impossible\n to Crack.It also user MD5 Hash in order to provide an extra-level Security.\nUser can lock any sort of data-Personal,audio,video,images,etc.\n");
                }
		//When Image Uploader Button is Click -> Upload Image
		if(e.getSource()==imageuploader_b)
		{
                    String errmsg=ismandatoryfield_completed();
                    if(errmsg.length()>0)
                        JOptionPane.showMessageDialog(frame,"Error Messages:\n"+errmsg+"\nPlease fix them first (by clicking on User Credentials tab)before Uploading image.\n");
                    else
			uploadImagetoLocker();       
                }
		if(e.getSource()==audiouploader_b)
		{
                    String errmsg=ismandatoryfield_completed();
                    if(errmsg.length()>0)
                        JOptionPane.showMessageDialog(frame,"Error Messages:\n"+errmsg+"\nPlease fix them first (by clicking on User Credentials tab)before Uploading Audio.\n");
                    else
			uploadAudiotoLocker(1);       
		}
		if(e.getSource()==videouploader_b)
		{
                    String errmsg=ismandatoryfield_completed();
                    if(errmsg.length()>0)
                        JOptionPane.showMessageDialog(frame,"Error Messages:\n"+errmsg+"\nPlease fix them first (by clicking on User Credentials tab)before Uploading Video.\n");
                    else
			uploadAudiotoLocker(2);       
		}
		if(e.getSource()==playPreview)
		{
			try
			{
				startAudioFilePreview();
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		if(e.getSource()==playPreview_video )
		{
			try
			{
				startVideoFilePreview();
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}

	}
	public void uploadImagetoLocker()
	{
                user_id=username.getText();
		Connection link=dbc.getdbconnectionString();
		imageUploader.setDialogTitle("Specify an Image file you want to save in Image Locker");
		int userSelection = imageUploader.showSaveDialog(frame);
                char ImageFileExtensionName[]=new char[3] ;
                String OriginalImageFilePath="";/*We will be showing Preview of Original File.Not Encrypted File.Encrypted File can't be displyead as preview since the header in Encrypted file can't be determined*/
		if (userSelection == JFileChooser.APPROVE_OPTION) 
		{
			try
			{
				File fileToSave;                //File Object for image file 
				query=link.prepareStatement("SELECT savedimages "+ "FROM mediafile " + "WHERE user=?");
				query.setString(1,user_id);
				ResultSet rs;
				rs =query.executeQuery();
				fileToSave =imageUploader.getSelectedFile();/*So the original file is stored in fileToSave*/
                                FileInputStream fis=new FileInputStream(fileToSave);
                                String Uploaded_Image_FileName=fileToSave.getPath();/*Get File Path(Address),we will use it for fetching Image File Extension*/
                                OriginalImageFilePath=Uploaded_Image_FileName;
                                int Idx=0;
                                /*Extract Extension Name from the image file name*/
                                for(int i=Uploaded_Image_FileName.length()-1;i>=0;i--,Idx++)
                                {
                                    if(Uploaded_Image_FileName.charAt(i)=='.')/*Extension Name is Separated by '.'*/
                                        break;
                                    ImageFileExtensionName[Idx]=Uploaded_Image_FileName.charAt(i);
                               }
                                String ImageExtension = new StringBuilder(new String(ImageFileExtensionName)).reverse().toString();
                                /*Extract Extension Name from the image file that i had uploaded*/
                                /*Encrypt the original File:fileToSave*/
                                BF.ImageEncrypt(fileToSave,ImageExtension,user_id);
                                fis.close();
                               
                                fis=new FileInputStream(new File("temp_encrypted/encrypted_"+user_id+"."+ImageExtension));
                                fileToSave=new File("temp_encrypted/encrypted_"+user_id+"."+ImageExtension);
                                
                                 /*
                                 * NOW WE ARE TRYING TO  STORE ENCRYPTED FILE IN A DATABASE
                                 * WE HAVE AlREADY STORED(in last 2 step) THE ENCRYPTED FILE IN A TEMPORARY FOLDER NAMED:temp_encrypted
                                 * THE NAME OF ENCRYPTED IMAGE FILE THAT WE STORED IN TEMP FOLDER IS OF PATTERN:encrypted_<user_id>.<ImageExtension>
                                 */
                                //If image is not in database,Cool Insert one
				if (!rs.next()) 
				{
					System.out.println("Image is not in database,so new image is being inserted.");
					query=link.prepareStatement("Insert into mediafile (user,savedimages,imagefiletype) "+" values (?,?,?)");
					query.setString(1,user_id); 
					query.setBinaryStream(2, (InputStream)fis, (int)(fileToSave.length()));
                                        query.setString(3,ImageExtension);

				} 
				else
				{
					System.out.println("Image already  in database,so the old image is replace with the current one.");
					query=link.prepareStatement("Update mediafile set savedimages=(?),imagefiletype=(?)"+" WHERE user=(?)");
					query.setBinaryStream(1, (InputStream)fis, (int)(fileToSave.length()));
                                        query.setString(2,ImageExtension);
					query.setString(3,user_id); 
				}
				int s =query.executeUpdate();
                                
                                if(s>0)
                                    System.out.println("Following file : " +"image_locker_files"+fileToSave.getAbsolutePath()+" is successfully Uploaded to Image Locker.");
				else
                                    System.out.println("Oops!!an Internal Error has Occured in saving file to the image Locker.");
                                fis.close();
                                fileToSave.delete();/*As i had already finished storing the encrypted file into database,so let's delete it from the temporary folder*/
                        }
			catch (Exception ex)
			{
				System.out.println("Following Error Occured while Uploading Image to Image Locker : "+ex);
			}
			if(true)
			{
				try
				{
					query=link.prepareStatement("SELECT savedimages "+ "FROM mediafile " + "WHERE user=?");
					query.setString(1,user_id);
					ResultSet rs;
					rs =query.executeQuery();
					
					if (!rs.next()) 
					{
						System.out.println("No such file stored.");
					} 
					else 
					{

						BufferedImage buffimg = null;
                                                buffimg= ImageIO.read(new File(OriginalImageFilePath));
						int Type = buffimg.getType() == 0? BufferedImage.TYPE_INT_ARGB :buffimg.getType();
						BufferedImage resizeImageJpg = resizeImage(buffimg, Type);
						JLabel preview=new JLabel(new ImageIcon(resizeImageJpg ));
						tab3panel.add(preview);
						preview.setBounds(125,100, 400,300);
                                        }

				}
				catch (Exception ex)
				{
					System.out.println("Following Error Occured in displaying Preview : "+ex);
				}
                        }

		}//End of Action Performed
		dbc.closeConnection(link);
	}
	public void uploadAudiotoLocker(int mediatype)
	{
                user_id=username.getText();
		/*
		* Note as there is not much difference b/w audio and video storing in database, so i am using the same funtion for both with a petty modifications
		mediatype=1 => AUDIO
		mediatype=2 => VIDEO
		*/
		String media_filename="";
		Connection link=dbc.getdbconnectionString();//Get a connection from database
		if(mediatype==1)
		{
			imageUploader.setDialogTitle("Specify an audio file you want to save in Audio Locker");//Tile of the file selection dialog box
		}
		else if(mediatype==2)
		{
			imageUploader.setDialogTitle("Specify a video file you want to save in Video Locker");//Tile of the file selection dialog box
		}
		{}
		int userSelection = imageUploader.showSaveDialog(frame);//This is upload audio/video button
		if (userSelection == JFileChooser.APPROVE_OPTION) //If user have selected the audio|video file(or, double clicked audio|video file)
		{
			try
			{
				File fileToSave;                //File Object for audio file 
				/*Make a query to the database and check if user has already uploaded some audio file earler.Note that i am only allowing user to upload atmax one audio file.So if user has already uploaded one,i am replacing it.*/
				if(mediatype==1)
				{
					query=link.prepareStatement("SELECT savedaudio "+ "FROM mediafile " + "WHERE user=?");
				}
				else if(mediatype==2)
				{
					query=link.prepareStatement("SELECT savedvideo "+ "FROM mediafile " + "WHERE user=?");
				}
				else
				{
				}
				query.setString(1,user_id);
				ResultSet rs;
				rs =query.executeQuery();
				fileToSave =imageUploader.getSelectedFile();
				media_filename=makefileName(fileToSave.getName());
				FileInputStream fis=new FileInputStream(fileToSave);

				//If user has not uploaded any audio in database,Cool Insert one
				if (!rs.next()) 
				{
					if(mediatype==1)
					{
						query=link.prepareStatement("Insert into mediafile (user,savedaudio,audiofilename) "+" values (?,?,?)");
						query.setString(1,user_id); 
						query.setBinaryStream(2, (InputStream)fis, (int)(fileToSave.length()));
						query.setString(3,media_filename);
						playPreview.setVisible(true);
					}
					else if(mediatype==2)
					{
						query=link.prepareStatement("Insert into mediafile (user,savedvideo,videofilename) "+" values (?,?,?)");
						query.setString(1,user_id); 
						query.setBinaryStream(2, (InputStream)fis, (int)(fileToSave.length()));
						query.setString(3,media_filename);
						playPreview_video.setVisible(true);
					}
					else
					{}


				} 
				else
				{
					if(mediatype==1)
					{
						query=link.prepareStatement("Update mediafile set savedaudio=?,audiofilename=?"+" WHERE user=?");
						query.setBinaryStream(1, (InputStream)fis, (int)(fileToSave.length()));
						query.setString(2, media_filename);
						query.setString(3,user_id); 
						playPreview.setVisible(true);
					}
					else if(mediatype==2)
					{
						query=link.prepareStatement("Update mediafile set savedvideo=?,videofilename=?"+" WHERE user=?");
						query.setBinaryStream(1, (InputStream)fis, (int)(fileToSave.length()));
						query.setString(2, media_filename);
						query.setString(3,user_id); 
						playPreview_video.setVisible(true);
					}
					else{}

				}
				int s =query.executeUpdate();
				if(s>0)
				{
					System.out.println("Following file : "+fileToSave.getAbsolutePath()+" is successfully Uploaded.");
				}
				else
				{
					System.out.println("Oops!!an Internal Error has Occured in saving file to the database.");
				}
			}
			catch (Exception ex)
			{
				System.out.println("Following Error Occured while Uploading file to Locker : "+ex);
			}
			if(true)
			{
				try
				{
					if(mediatype==1)
					{
						query=link.prepareStatement("SELECT savedaudio,audiofilename "+ "FROM mediafile " + "WHERE user=?");
					}
					else if(mediatype==2)
					{
						query=link.prepareStatement("SELECT savedvideo,videofilename "+ "FROM mediafile " + "WHERE user=?");
					}
					else{}
					query.setString(1,user_id);
					ResultSet rs;
					rs =query.executeQuery();
					if (!rs.next()) 
					{
						System.out.println("No such file stored.");
					} 
					else 
					{

						Blob blob;
						blob=null;
						String retrievedfilename="";
						if(mediatype==1)
						{
							retrievedfilename=rs.getString("audiofilename");
							blob = rs.getBlob("savedaudio");
						}
						else if(mediatype==2)
						{
							retrievedfilename=rs.getString("videofilename");
							blob = rs.getBlob("savedvideo");
						}
						else{}
						
						/*DOWNLOAD AUDIO IN DATABASE TO A PARTICULAR LOCATION*/
						byte[] content = blob.getBytes(1, (int) blob.length());
						String newStr= retrievedfilename;
						OutputStream os = new FileOutputStream(new File("Downloads/"+newStr));
						os.write(content);
						os.close();
                                                File fhandle = new File("Downloads/"+newStr);
						String dirPath = fhandle.getParentFile().getAbsolutePath();

						String previewaudiofilelocation=dirPath+"\\"+newStr;

						if(mediatype==1)
						{
							preview_audio_string=previewaudiofilelocation;
							playPreview.setVisible(true);
						}
						else if(mediatype==2)
						{
							preview_video_string=previewaudiofilelocation;
							playPreview_video.setVisible(true);
						}
						else{}

					}

				}
				catch (Exception ex)
				{
					System.out.println("Following Error Occured in displaying Preview : "+ex);
				}

			}

		}//End of Action Performed
		dbc.closeConnection(link);
	}
	/*The User can upload any size image.So we need an image of a fixed width/height for displaying image preview.So image Resizing is required which is done by function below*/
	void startAudioFilePreview()throws IOException, InterruptedException 
	{
		String myCommand = "C:\\Program Files\\Windows Media Player\\wmplayer "+preview_audio_string+"";
		System.out.println("Audio Cmd is "+myCommand);
		Runtime.getRuntime().exec(myCommand);
                
	}
	void startVideoFilePreview()throws IOException, InterruptedException 
	{
		String myCommand = "C:\\Program Files\\Windows Media Player\\wmplayer "+preview_video_string+"";
		System.out.println("Video Cmd is"+myCommand);
		Runtime.getRuntime().exec(myCommand);
               
	}
	private static BufferedImage resizeImage(BufferedImage originalImage, int type)
	{
		BufferedImage resizedImage = new BufferedImage( 400,300,type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 400,300, null);
		g.dispose();
		return resizedImage;
	}
	/*Note this function is useful , when i play the audio and video file which */
	public String makefileName(String oldfileName)
	{
		StringBuilder validfileName = new StringBuilder(oldfileName);
		for(int i=0;i<oldfileName.length();i++)
		{

			if(oldfileName.charAt(i)<='Z' && oldfileName.charAt(i)>='A') 
			{

			}
			else if (oldfileName.charAt(i)<='z' && oldfileName.charAt(i)>='a')
			{
			}
			else if(oldfileName.charAt(i)<='9' && oldfileName.charAt(i)>='0' )
			{
			}
			else if(oldfileName.charAt(i)=='.')
			{
			}
			else
			{
				validfileName.setCharAt(i,'x');
			}
		}
		return  validfileName.toString();
	}
	public void windowClosing(WindowEvent w)
	{
		try
		{
                        File tmp=new File(preview_audio_string);
                        tmp.delete();
                        tmp=new File(preview_video_string);
                        tmp.delete();
			System.exit(1);
		}
		catch(Exception oe){}
	}//End of windowClosing
}//End of enterData Class