package url;

import java.io.File;
import java.lang.Thread.State;
import java.util.Scanner;

public class Main 
{


	public static void main(String[] args) throws InterruptedException
	{
		FileToArray FTA = new FileToArray();
		String[] Links = FTA.fta();
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the path of the folder downloaded files.");
		String OutputFolderPath = in.nextLine();
		System.out.println("Enter the download speed (kb/s).");
		int Speed = in.nextInt() + 1024;
		System.out.println("Enter counts the threads.");
		int CountThreads = in.nextInt();
		Thread TA[] = new Thread[CountThreads];
		in.close();

		int CountDownloadedFiles = 0; 
		int CountAllFiles = Links.length;
		String Name;
		File Out;
		
		for(int i = 0; i < CountThreads; i++)
		{
			if(CountAllFiles != CountDownloadedFiles)
			{
				Name = "File" + (CountDownloadedFiles+1);
				Out = new File(OutputFolderPath + "\\" + Name + Links[CountDownloadedFiles].substring(Links[CountDownloadedFiles].length() - 4));
				TA[i] = new Thread(new Download(Links[CountDownloadedFiles], Out, Speed, CountDownloadedFiles));
				TA[i].start();
				CountDownloadedFiles++;
			}
		}
		
		while(CountAllFiles != CountDownloadedFiles)
		{
			for(int i = 0; i < CountThreads; i++)
			{
				if(TA[i].equals(null))
				{
					Name = "File" + (CountDownloadedFiles+1);
					Out = new File(OutputFolderPath + "\\" + Name + Links[CountDownloadedFiles].substring(Links[CountDownloadedFiles].length() - 4));
					TA[i] = new Thread(new Download(Links[CountDownloadedFiles], Out, Speed, CountDownloadedFiles));
					TA[i].start();
				}
				if(TA[i].getState().equals(State.TERMINATED))
				{
					Name = "File" + (CountDownloadedFiles+1);
					Out = new File(OutputFolderPath + "\\" + Name + Links[CountDownloadedFiles].substring(Links[CountDownloadedFiles].length() - 4));
					TA[i] = new Thread(new Download(Links[CountDownloadedFiles], Out, Speed, CountDownloadedFiles));
					TA[i].start();
					CountDownloadedFiles++;
				}
			}
		}
	}
}
