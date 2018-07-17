package Decryptor;


import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;


public class DecryptOperations extends javax.swing.JFrame {
    
    String filename, encryptedfile, keyFile, publickey;
    String ip = null;
    
    public DecryptOperations() {
        //System.out.println("Preparing for Encrypt/Decrypt");
    }
    

    public void encrypt(String inputFile) {                                         
        
        System.out.println(inputFile);
        File inputedFile = new File(inputFile);
        File encryptedFile = new File(inputFile+".encrypted");
        publickey = generateKey();
        
        
        keyFile = keyFileName(inputFile);
        
        try
        {    
            FileWriter fw;    
            fw = new FileWriter(keyFile);
            fw.write(publickey);    
            fw.close();    
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        ip = getIPAddress();
        
        String privateKey = getPrivateKey(ip, publickey);
        
        
    }                                        

    
    public void decrypt(String enc_filename, String ip) throws KeyException, FileNotFoundException, FileNotFoundError {                                         
        // TODO add your handling code here:
        
        System.out.println(enc_filename);
        File existing_encryptedfile;
        
        existing_encryptedfile = new File(enc_filename);        
        
        File decryptedFile = new File(enc_filename.replace(".encrypted", "")+"");
        
        String DecryptionKey = null;
        String keyFileName = getKeyFileName(enc_filename);
        
        
        try 
        {
            
            String publickey_dec = readFile(keyFileName);
            DecryptionKey = getPrivateKey(ip, publickey_dec);
            System.out.println(DecryptionKey);
        } 
        catch (IOException ex) {
            Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Decrypt.decryptfile(DecryptionKey, existing_encryptedfile, decryptedFile);
        }
        catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            
            ex.printStackTrace();
        }
        catch (KeyException e)
        {
            throw new KeyException("IP address wrong", e);
        }
    }                                        

    /**
     * @param args the command line arguments
     */
    
    /**
     *
     * @param file
     * @return 
     * @throws java.io.IOException
     */
    
    public static String readFile(String file) throws IOException, FileNotFoundError {
           BufferedReader reader = new BufferedReader(new FileReader (file));
           String line = null;
           StringBuilder stringBuilder = new StringBuilder();
           //String ls = System.getProperty("line.separator");
   
           try {
              while((line = reader.readLine()) != null) {
                  stringBuilder.append(line);
                  //stringBuilder.append(ls);
              }
              //System.out.println(stringBuilder.toString());
              //System.out.println(stringBuilder.toString().length());
              return stringBuilder.toString();
           } 
           catch (FileNotFoundException e)
            {
                throw new FileNotFoundError("No File", e);
            }
           
           finally {
                reader.close();
           }
    }

    public static String getIPAddress()
    {
        /*String ipaddr = "";
        try {
		InetAddress addr = InetAddress.getLocalHost();
		ipaddr = addr.getHostAddress();
		System.out.println("" + ipaddr);
        }
        catch (Exception ex) {
                 ex.printStackTrace();
	}*/
        
        URL ipAddress;
        String ip = "";
        try 
        {
            ipAddress = new URL("http://myexternalip.com/raw");

            BufferedReader in = new BufferedReader(new InputStreamReader(ipAddress.openStream()));

            ip = in.readLine();
            System.out.println(ip);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return ip;
    }
    
    
    
    public static String generateKey()
    {
            String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
            String key = "";
            Random random = new Random();
            int randomLen = 16;
            for (int i = 0; i < randomLen; i++) {
                char c = alphabet.charAt(random.nextInt(alphabet.length()));
                key+=c;
            }

            System.out.println(key.toUpperCase());
            
            return key.toUpperCase();
    }
    
    public static String getPrivateKey(String ip, String publickey)
    {
        String key = "";
        String newIP = ip;
        
        for (int i = 0; i<(16-ip.length()); i++)
        {
        	newIP += '-';
        	
        }
        
        for (int i = 0; i < 16; i++) {
        	int val = (int)(newIP.charAt(i))+(int)(publickey.charAt(i));
                if (val > 127)
                    val = 127;
        	key += (char)val;
        }
        
        System.out.println(key);
        return key;
    }

    
    public static String getfilepath(String filename)
    {
	String file = "";
	for (int i = 0; i < filename.length(); i++)
        {
            char character = filename.charAt(i); 
            int ascii = (int) character;
            //System.out.println(ascii);
            if (ascii==92) 
            {
                file += character;
                file += character;
            }
            else
            {
                file += filename.charAt(i);
                //System.out.print(file);
            }
        }
        return file;
    }
        
    public static String keyFileName(String input)
    {
            String keyfile = "";
            for (int i = 0; i<input.length(); i++)
            {
               char character = input.charAt(i);
               if (character == '.')
               {
                  keyfile += "_key";
                  //System.out.println("character");
               }
               //System.out.println(character);
               keyfile += character; 
            }
            
            return keyfile;

    }
        
    
    public static String getKeyFileName(String input)
    {
            String keyfile = "";
            for (int i = 0; i<input.length(); i++)
            {
               char character = input.charAt(i);
               if (character == '.')
               {
                    keyfile += "_key.txt";
                    //System.out.println("character");
                    break;
               }
               else {
                    //System.out.println(character);
                    keyfile += character; 
               }
            }
            
            return keyfile;
    }
        
    // Variables declaration                      
    //private javax.swing.JButton jButton1;
    //private javax.swing.JButton jButton2;
    //private javax.swing.JButton jButton3;
    //private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}

