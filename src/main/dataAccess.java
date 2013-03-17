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
public class dataAccess extends WindowAdapter implements ActionListener
{
	JFrame frame;
	JPanel panel;
	BufferedImage myPicture,body_picture;
	JLabel header,body_bg,con_lab,con_no_lab,info_lab;
	JButton logoff,menu2,menu3,menu4,menu5;
	Color customColor;
	Image con_yes,con_no;
	JTabbedPane jtp;
	JPanel tab1panel,tab2panel,tab3panel,tab4panel,tab5panel;
	/*The  Form Elements For  DataEntry*/
	JLabel username_label,email_label,password_label,confirmpassword_label,instruction1,name_label,creditcardpin_label,cvv_label,bankaccno_label,netbank_label,pancardno_label,passport_label,dl_label,gmail_label,facebook_label;
	JLabel username_data,email_data,password_data,confirmpassword_data,name_data,creditcardpin_data,cvv_data,bankaccno_data,netbank_data,pancardno_data,passport_data,dl_data,gmail_data,facebook_data;
	JButton playPreview,playPreview_video,decode_but;
	String preview_audio_string="",preview_video_string="";
	dbConnection dbc;
	PreparedStatement query = null;
	String user_id="NA";
	int has_uploaded_audio=0,has_uploaded_video=0;
	JTextField secretkeybox;
        File DecrytedFileHandler=null;
	//uid => User ID
	public dataAccess(String uid)throws IOException
	{
		user_id=uid;
		dbc=new dbConnection();
		frame=new JFrame("Data Access - Highly Confidential Security System");
		panel=new JPanel();
		tab1panel = new JPanel();
		tab2panel = new JPanel();
		tab3panel = new JPanel();
		tab4panel = new JPanel();
		tab5panel = new JPanel();
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
		decode_but=new JButton("Decode Data");
		con_yes=Toolkit.getDefaultToolkit().createImage("src/main/images/green.gif");
		con_no=Toolkit.getDefaultToolkit().createImage("src/main/images/red.gif");
		con_lab=new JLabel(new ImageIcon(con_yes));
		con_no_lab=new JLabel(new ImageIcon(con_no));
		info_lab=new JLabel("All the data are stored in encrypted form in our database.Please Enter the Secret Key below to decode them.");
		secretkeybox=new JTextField(256);
		jtp = new JTabbedPane();

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

		//Secret Key Box
		secretkeybox.setBounds(50,175,300,24);

		//Tabbed Panels
		jtp.setBounds(50,145,700,450);
		jtp.setBackground(Color.DARK_GRAY);
		jtp.setForeground(Color.WHITE);

		decode_but.setForeground(Color.WHITE);
		decode_but.setBackground(Color.GRAY);
		decode_but.setBounds(355,175,150,24);
		decode_but.addActionListener(this);

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
		//End of Menus Buttons
		//Connection Lights
		 con_no_lab.setBounds(715, 100, 50,40);
                 con_lab.setBounds(715, 100, 50,40);
		//Image Uploader for Image Locker
		/*Tab 1 Contents here*/
		username_label=new JLabel("Username");
		email_label=new JLabel("Email");
		tab1panel.add(username_label);
		tab1panel.add(email_label);
		tab1panel.setBackground(Color.DARK_GRAY);
		tab1panel.setForeground(Color.WHITE);
		tab1panel.add(info_lab);
		tab1panel.add(secretkeybox);
		tab1panel.add(decode_but);
		tab1panel.setLayout(null);
		/*Note Tabbed Pane Coordinates is 50,145,700,450
		* so i am setting position of the form element accordingly
		*/
		username_label.setBounds(60,35, 400,24);
		username_label.setForeground(Color.WHITE);
		email_label.setBounds(60,65, 400,24);
		info_lab.setBounds(50,150, 650,24);
		info_lab.setForeground(Color.WHITE);
		info_lab.setBackground(Color.GRAY);
		email_label.setForeground(Color.WHITE);


		/*End Tab1 Contents*/
		/**********Tab 2 :Personal Locker****************/
		name_label=new JLabel("Name");
		name_label.setForeground(Color.WHITE);
		creditcardpin_label=new JLabel("Credit Card PinCode");
		creditcardpin_label.setForeground(Color.WHITE);
		cvv_label=new JLabel("CVV Number");
		cvv_label.setForeground(Color.WHITE);
		bankaccno_label=new JLabel("Bank Account Number");
		bankaccno_label.setForeground(Color.WHITE);
		netbank_label=new JLabel("NetBanking Password");
		netbank_label.setForeground(Color.WHITE);
		pancardno_label=new JLabel("PanCard Number");
		pancardno_label.setForeground(Color.WHITE);
		passport_label=new JLabel("Passport Number");
		passport_label.setForeground(Color.WHITE);
		dl_label=new JLabel("Driving License Number");
		dl_label.setForeground(Color.WHITE);
		gmail_label=new JLabel("Gmail Password");
		gmail_label.setForeground(Color.WHITE);
		facebook_label=new JLabel("Facebook Password");
		facebook_label.setForeground(Color.WHITE);
		tab2panel.add(name_label);
		tab2panel.setBackground(Color.DARK_GRAY);
		tab2panel.add(creditcardpin_label);
		tab2panel.add(cvv_label);
		tab2panel.add(bankaccno_label);
		tab2panel.add(netbank_label);
		tab2panel.add(pancardno_label);
		tab2panel.add(passport_label);
		tab2panel.add(dl_label);
		tab2panel.add(gmail_label);
		tab2panel.add(facebook_label);
		tab2panel.setLayout(null);
		/*Note Tabbed Pane Coordinates is 50,145,700,450
		* so i am setting position of the form element accordingly
		*/
		name_label.setBounds(60,35, 400,24);
		creditcardpin_label.setBounds(60,65,400,24);
		cvv_label.setBounds(60,95,400,24);
		bankaccno_label.setBounds(60,125,400,24);
		netbank_label.setBounds(60,155,400,24);
		pancardno_label.setBounds(60,185,400,24);
		passport_label.setBounds(60,215,400,24);
		dl_label.setBounds(60,245,400,24);
		gmail_label.setBounds(60,275,400,24);
		facebook_label.setBounds(60,305,400,24);
		/*****************End Tab 2************/
		/**********Tab 3: Note Tab 3 is for image Locker***********/
		tab3panel.setBackground(Color.DARK_GRAY);
		tab3panel.setLayout(null);
		/**************End Tab 3***********/
		/**************Tab 4: Note Tab 4 is for Audio Locker*************/
		tab4panel.setBackground(Color.DARK_GRAY);
		tab4panel.add(playPreview);
		tab4panel.setLayout(null);
		playPreview.setBounds(180,315, 300,50);
		/************End Tab 4*******************************************/
		/******************Tab 5:****************************/
		tab5panel.setBackground(Color.DARK_GRAY);
		tab5panel.add(playPreview_video);
		tab5panel.setLayout(null);
		playPreview_video.setBounds(180,315, 300,50);
		/*End Tab 5*/
		//Tabbeed Panel
		jtp.addTab("User Credentials",tab1panel);
		jtp.addTab("Personal Locker", tab2panel);
		jtp.addTab("Image Locker", tab3panel);
		jtp.addTab("Audio Locker", tab4panel);
		jtp.addTab("Video Locker", tab5panel);


	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==decode_but)
		{
			fetchdata();
		}
		if(e.getSource()==playPreview)
		{
			retreivemediafiles(1);//Media type 1 is for audio
			try
			{
				if(has_uploaded_audio==1)//If user has not uploaded any audio,then why should i play audio preview!!!
					startAudioFilePreview();
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		if(e.getSource()==playPreview_video)
		{
			retreivemediafiles(2);//Media Type 2 is for Video
			try
			{
				if(has_uploaded_video==1)//If user has not uploaded any video,then why should i play video preview!!!
					startVideoFilePreview();
			}
			catch (Exception ex)
			{
				System.out.println(ex.toString());
			}
		}
		if (e.getSource()==logoff)
		{
			frame.dispose();
			try
			{   DecrytedFileHandler.delete();
                            File tmp=new File(preview_audio_string);
                            tmp.delete();
                            tmp=new File(preview_video_string);
                            tmp.delete();
                            System.out.println("Closing Windows... Thank You!");
                            System.exit(1);
                        }
                            catch(Exception oe)
			{}
		}
		if(e.getSource()==menu2)
		{
			JOptionPane.showMessageDialog(frame,"This Project is developed by Ritesh Kumar Gupta.");
		}
		if(e.getSource()==menu3)
		{
			JOptionPane.showMessageDialog(frame,"Highly Confidential Security System is a java application that stores user sign-in\n information and all others credientals data in a database.Data in a database are \nstored in encrypted form.It uses BlowFish Cipher,which is impossible\n to Crack.It also user MD5 Hash in order to provide an extra-level Security.\nUser can lock any sort of data-Personal,audio,video,images,etc.\n");
		}
	}

	public void fetchdata()
	{
		/*initially Assume that user has not uploaded any audio or video in database.We check db(database) after some tym and will set these two variable accordingly*/
		has_uploaded_audio=0;
		has_uploaded_video=0;
		Connection link=dbc.getdbconnectionString();//Get a connection from database
		try
		{
                        panel.add(con_lab);/*Database Successfully Connected.Display green blinking icon.*/
			query=link.prepareStatement("SELECT user,name,email,credit,cvv,bankaccount,netbank,pancard,passport,dl,gmail,facebook "+ "FROM images " + "WHERE user=?");
			query.setString(1,user_id);
			ResultSet rs =query.executeQuery();
			if(rs.next())
			{
				String fetched_data[]=new String[13];
				fetched_data[12]="";/*Note Fetched data 12 is for receiving the error message while decrypting*/
				String db_user=fetched_data[0]=rs.getString("user");
				String db_name=fetched_data[1]=rs.getString("name");
				String db_email=fetched_data[2]=rs.getString("email");
				String db_credit=fetched_data[3]=rs.getString("credit");
				String db_cvv=fetched_data[4]=rs.getString("cvv");
				String db_bankaccount=fetched_data[5]=rs.getString("bankaccount");
				String db_netbank=fetched_data[6]=rs.getString("netbank");
				String db_pancard=fetched_data[7]=rs.getString("pancard");
				String db_passport=fetched_data[8]=rs.getString("passport");
				String db_dl=fetched_data[9]=rs.getString("dl");
				String db_gmail=fetched_data[10]=rs.getString("gmail");
				String db_facebook=fetched_data[11]=rs.getString("facebook");

				if(secretkeybox.getText().length()>0)
				{
					BlowFishCipher BF=new BlowFishCipher();
					fetched_data=BF.decrypt(fetched_data,secretkeybox.getText(),frame);
					if(fetched_data[12].length()>0)
					{
						JOptionPane.showMessageDialog(frame,fetched_data[12]);
					}
				}

				/*Printing the information fetched from database*/
				/*Note:Variable Spacing  is delibrately done for formatting Purpose*/
				name_label.setText("Name:                                     "+fetched_data[1]);
				cvv_label.setText("CVV Number:                       "+fetched_data[4]);
				bankaccno_label.setText("Back a/c no:                         "+fetched_data[5]);
				netbank_label.setText("NetBamking Password:    "+fetched_data[6]);
				pancardno_label.setText("PanCard Number:              " +fetched_data[7]);
				passport_label.setText("PassPort Number:             "+fetched_data[8]);
				dl_label.setText("Driving License Number:  "+fetched_data[9]);
				gmail_label.setText("Gmail Password:                "+fetched_data[10]);
				facebook_label.setText("Facebook Password:        "+fetched_data[11]);
				creditcardpin_label.setText("CreditCard PinCode:          "+fetched_data[3]);
				username_label.setText("UserName:   "+db_user);
				email_label.setText("Email:             "+fetched_data[2]);
			}
			else
			{
				System.out.println("Record not found in database.");
			}
		}
		catch (Exception ex)
		{
                        System.out.println("Error Occurs While Connecting with database: "+ex.toString());
			panel.add(con_no_lab);/*Database error.Display red blinking*/
		}

		/*Fetch Images,Audio and Videos*/
		ResultSet rs=null;
		try
		{
			query=link.prepareStatement("SELECT savedimages,savedaudio,audiofilename,savedvideo,videofilename,imagefiletype "+ "FROM mediafile " + "WHERE user=?");
			query.setString(1,user_id);
			rs =query.executeQuery();
			if(rs.next())
                        {}
			else
                            System.out.println("No images,audio and video exist in database for "+user_id);
			
		}
		catch(Exception ex)
		{
			System.out.println("The Following Error has occured while fetching image,audio and video from HCSS: "+ex.toString());
		}
		/*Now Display the image here*/
		BufferedImage buffimg = null;
		JLabel preview=null;
		try
		{

			String db_imagefiletype=rs.getString(6);
			if(db_imagefiletype.equals("NA"))/*That Means User Has Not Uploaded Any Image*/
			{
				System.out.println("User Has Not Uploaded Any Image.");
				preview=new JLabel("         User: "+user_id+" has not uploaded any image in Image Locker!!");  
				preview.setForeground(Color.WHITE);
				preview.setBackground(Color.RED);
				preview.setBounds(125,70, 400,300);
			}
			else if(secretkeybox.getText().length()==0)
			{
				preview=new JLabel("The Image is stored in Encrypted Form.Only Accessible Via PassWord.");
				preview.setForeground(Color.WHITE);
				preview.setBounds(125,0, 400,50);
			}
			else
			{

				Blob blob=null;
				blob=rs.getBlob(1);
				byte[] content = blob.getBytes(1, (int) blob.length());
				/*Fetch Encrypted Image From database and generate Encrypted Image to Temporary folder:temp_encrypted*/
				OutputStream os = new FileOutputStream(new File("temp_encrypted/encrypted_op"+user_id+"."+db_imagefiletype));
				os.write(content);
				os.close();
				/*So Till Now We have Fetched Encrypted image and produced Encrypted Image in Temp Folder*/
				/*The Image is Encrypted therefore unavailable. Thus we need to decrypt it.Image is higly Confidential,for Decryption, Secret key is need*/

				BlowFishCipher BF=new BlowFishCipher();
                                DecrytedFileHandler=new File("temp_encrypted/DecryptedImg_"+user_id+"."+db_imagefiletype);
				/*Lets Call Image Decrypt Function for Decrypting Image*/
				/*1st Arguement is path of the encrypted image.The Encryped image which is generated from the database*/
				if(BF.ImageDecrypt("temp_encrypted/encrypted_op"+user_id+"."+db_imagefiletype,user_id,db_imagefiletype,secretkeybox.getText()))
				{
					/*Image is Decrypted and We have produced the decryped image in a folder,so that we can show preview of the decrypted image*/
					buffimg= ImageIO.read(new File("temp_encrypted/DecryptedImg_"+user_id+"."+db_imagefiletype));
                                        int Type = buffimg.getType() == 0? BufferedImage.TYPE_INT_ARGB :buffimg.getType();
					BufferedImage resizeImageJpg = resizeImage(buffimg, Type);
					preview=new JLabel(new ImageIcon(resizeImageJpg ));
                                       
				}
				preview.setBounds(125,70, 400,300);
                                
			}
			tab3panel.add(preview);
			link.close();
		}
		catch (Exception ex)
		{
			System.out.println("Oops!Something Went Wrong in Fetching Images.Details: "+ex.toString());
		}
	}
	public void retreivemediafiles(int mediatype)
	{
		/*
		* Note as there is not much difference b/w audio and video storing in database, so i am using the same funtion for both with a petty modifications
		mediatype=1 => AUDIO
		mediatype=2 => VIDEO
		*/
		String media_filename="";
		Connection link=dbc.getdbconnectionString();//Get a connection from database
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
				if(mediatype==1)
					JOptionPane.showMessageDialog(frame,"User: "+user_id+" has not uploaded any audio file in Audio Locker");
				else
					JOptionPane.showMessageDialog(frame,"User: "+user_id+" has not uploaded any Video file in Video Locker");
			} 
			else 
			{

				Blob blob;
				blob=null;
				String retrievedfilename="";
				if(mediatype==1)
				{
					has_uploaded_audio=1;
					retrievedfilename=rs.getString("audiofilename");
					blob = rs.getBlob("savedaudio");
				}
				else if(mediatype==2)
				{   
					has_uploaded_video=1;
					retrievedfilename=rs.getString("videofilename");
					blob = rs.getBlob("savedvideo");
				}
				else{}
				System.out.println("The Retrieved FileName is "+retrievedfilename);
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

	/*The User can upload any size image.So we need an image of a fixed width/height for displaying image preview.So image Resizing is required which is done by function below*/
	void startAudioFilePreview()throws IOException, InterruptedException 
	{
		String myCommand = "C:\\Program Files\\Windows Media Player\\wmplayer "+preview_audio_string+"";
		Runtime.getRuntime().exec(myCommand);
	}
	void startVideoFilePreview()throws IOException, InterruptedException 
	{
		String myCommand = "C:\\Program Files\\Windows Media Player\\wmplayer "+preview_video_string+"";
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
	public void windowClosing(WindowEvent w)
	{
		
                        /*Before shutting down the windows, no need to keep Decrypted Image File in temporary folder.
                         So Delete it.*/
                        try
                        {
                            DecrytedFileHandler.delete();
                            File tmp=new File(preview_audio_string);
                            tmp.delete();
                            tmp=new File(preview_video_string);
                            tmp.delete();
                            System.out.println("Closing Windows... Thank You!");
                            System.exit(1);
                        }
                        catch (Exception ex)
                        {}
		
                
	}//End of windowClosing
}//End of enterData Class