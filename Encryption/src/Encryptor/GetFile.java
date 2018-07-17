package Encryptor;


public class GetFile {
    
    public String getfilepath(String filename)
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
    
    
}
