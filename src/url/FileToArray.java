package url;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileToArray 
{
	public String[] fta()
	{
		String[] Links = null;
		Scanner in = new Scanner(System.in);
		//
		System.out.println("Enter the path of the file (txt) with links.");
		//
		String FilePath = in.nextLine();
		File file = new File(FilePath);
		int CountLines = 0;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			while(br.readLine() != null)
				CountLines++;
			br.close();
			
			br = new BufferedReader(new FileReader(file));
			Links = new String[CountLines];
			int i = 0;
			String line;
			while((line = br.readLine()) != null)
			{
				Links[i] = line;
				i++;
			}
			br.close();
			
		}
		catch(IOException ex)
		{
			ex.getStackTrace();
		}
		return Links;	
	}
}
