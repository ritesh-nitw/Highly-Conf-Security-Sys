/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package main;

/**
*
* @author Ritesh Kumar Gupta
*/
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;


import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class BlowFishCipher 
{
	String download_secret_key_data="";
	SecretKey secretkey;
	void init()
	{
		while(true)/*The Secret key is generated automatically.If negative value is genrated, I am discarding -ve secret key. I am not breaking the loop until i get a +ve Secret Key :)*/
		{
			try
			{
				secretkey = KeyGenerator.getInstance("Blowfish").generateKey();
				byte[] encoded = secretkey.getEncoded();
				download_secret_key_data= new BigInteger(encoded).toString(16);
				if(download_secret_key_data.charAt(0)=='-')
				{}
				else
					break;
			}
			catch (Exception ex)
			{
				System.out.println("Following Error has occured while Generating Secret Key: "+ex.toString());
			}
		}
	}
	String[] Encrypt(String inputText[]) throws Exception 
	{
		/*create a secret key.Note Secret key is Only known to the user
		I am never storing it in database.so,it gives a high level Security*/

		/*WRITE DOWN THE SECRET KEY INTO A TEXT FILE.NOTE THIS SECRET KEY IS REQUIRED FOR */
		File file = new File("secretkey/"+inputText[0]+"_secretkey.txt");
		if (!file.exists())
			file.createNewFile();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(download_secret_key_data);
		bw.close();


		/*What cipher are we using? Its a BlowFish Cipher*/
		Cipher cipher = Cipher.getInstance("Blowfish");

		/*So I have a secret key.Now Encrypt the data using that Secret Key*/
		cipher.init(Cipher.ENCRYPT_MODE, secretkey);

		/*inputText is a PlaneTextstring that is need to be encrypted with blow fish Cipher*/
		for(int i=0;i<inputText.length;i++)
		{
			byte[] encrypted = cipher.doFinal(inputText[i].getBytes());
			String encoded_encrypted_data= new BigInteger(encrypted).toString(16);
			inputText[i]=encoded_encrypted_data;
		}
		return inputText;
		// So my message is encrypted now with a secret key 

		/* UNTIL HERE I have encrypted the data using BlowFish Cipher.The Encrypted data i had kept in HexaDecimal Format. */
		/*Also , i had Converted the Secret key in Base-16(HexaDecimal) Format*/

	}
	String[] decrypt(String datas[],String seckeybox,JFrame frame)
	{
		for(int i=1;i<(datas.length-1);++i)
		{
			byte[] encoded2 = new BigInteger(seckeybox, 16).toByteArray();//As the secret key is in Hexa format , so revert it back to byte format
			SecretKey blowsecKey = new SecretKeySpec(encoded2, "Blowfish");
			try
			{

				Cipher cipher = Cipher.getInstance("Blowfish");
				cipher.init(Cipher.DECRYPT_MODE,blowsecKey);
				// decrypt message
				String encrypted_message_string = datas[i];
				byte[] encypted_msg_byte = new BigInteger(encrypted_message_string, 16).toByteArray();//As the Encrypted Msg is in Hexa format , so revert it back to byte format
				byte[] decrypted = cipher.doFinal(encypted_msg_byte);
				// and display the results
				datas[i]=new String(decrypted);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(frame,"Sorry,the Data Cannot be decrypted as the secret key entered is Incorrect.");
				break;
			}
		}
		return datas;
	}
	/*This Function is Used For Image Encryption*/
	void ImageEncrypt(File inputFile,String ImageExtension,String user_id)throws Exception 
	{
		/*Will use salt along with MD5*/
		/*I am encrypting image with AES cipher with password[even The password i will hash with md5+salt..Amazing Security:)] instead of BlowFish Cipher(I used BlowFish For encrypting Text)*/
		byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
		BufferedImage input = ImageIO.read(inputFile);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		/* The password will be the secret key of blowfish cipher*/
		/*So when ever the plane text is decoded with secret key entry while accessing ,image too will be decrypted as the password for decrypting image=secret key for decoding plane text*/
		char[] pwd=download_secret_key_data.toCharArray();/*PWD for Image Decryption is a secret Key*/
		PBEKeySpec pbeKeySpec = new PBEKeySpec(pwd);          
		/*PassWord Based Encryption*/
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
		SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
		FileOutputStream output = new FileOutputStream("temp_encrypted/encrypted_"+user_id+"."+ImageExtension);
		CipherOutputStream cos = new CipherOutputStream(output, pbeCipher);
		ImageIO.write(input,ImageExtension,cos);
		cos.close();          
		output.close();

	}
	boolean ImageDecrypt(String Encrypted_FileName_Path,String user_id,String imageExtension,String ImagePassWord)
	{  
		boolean DecryptionOK=true;
		File encryptedfileHandler=new File(Encrypted_FileName_Path);/*Let's assign the Handler for the Encrypted File*/
		FileInputStream output=null;
		CipherInputStream cis=null;
		FileOutputStream out=null;
		if(ImagePassWord.length()>0)
		{
			byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
			char[] pwd=ImagePassWord.toCharArray();/*Image PassWord is the PassWord Entered by user(in a TextBox) for Image Decryption*/
			PBEKeySpec pbeKeySpec = new PBEKeySpec(pwd);
			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);
			try
			{
				/*This Function is called only during dataAccess.As We had saved the image in encrypted form in a database
				* .The encrypted file in database is stored in form of Bytes in a datatype called Blob
				* So,we convert the bytes(fetched from the database) into ImageFile first(Lets say this image file 'en.x').This image file is in encryped form.
				* And temporarily,we had generated this image file en.x in a hard disk temporary folder.
				* In ImageDecrypt Function,the first parameter 'Encrypted_FileName_Path' is the path of the image File 'en.x'
				* We are going to Decrypt this(en.x) image file.
				*/

				SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
				SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
				Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
				pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
				output = new FileInputStream(Encrypted_FileName_Path);
				cis = new CipherInputStream(output, pbeCipher);
				BufferedImage input = ImageIO.read(cis);
				out = new FileOutputStream("temp_encrypted/DecryptedImg_"+user_id+"."+imageExtension);
				ImageIO.write(input,imageExtension, out);
				/*Close all the stream as I do not need it Further*/


			}
			catch (Exception ex)
			{
				DecryptionOK=false;
				System.out.println("Image Cannot be Decryped as the image password ie.Secret Key is Incorrect.Details: "+ex.toString());
			}
		}

		/*The encrypted File is decrypted successfully.In preview,We are going to display the 
		* decrypted File, not the encrypted one.So encryped file doesn't have any further use,so
		* let's delete it from the temporary folder.
		*/
		try
		{
			cis.close();
			out.close();
			output.close();
		}
		catch (Exception ex1)
		{
		}
		encryptedfileHandler.delete();
		return DecryptionOK;
	}
	public static String md5(String input) 
	{
		String md5 = null;
		if(null == input) 
			return null;
		try 
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return md5;
	}
}
