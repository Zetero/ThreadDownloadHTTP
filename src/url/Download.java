package url;

//���������� ������� � ����������
import java.io.File;
//���������������� ������ ����� � ������. ��� �����������
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
//���������� ������ �����
import java.io.FileOutputStream;
//������������ ������
import java.io.IOException;
//��������� URL
import java.net.URL;

import com.sun.tools.javac.Main;

//��������� HTTP
import java.net.HttpURLConnection;

public class Download implements Runnable
{

	String link;
	File out;
	int speed;
	int fileCount;

	public Download(String link, File out, int speed, int fileCount)
	{
		this.link = link;
		this.out = out;
		this.speed = speed;
		this.fileCount = fileCount;
	}


	@Override
	public void run() 
	{
		try
		{
			//������� �������� ������ URL � �������
			URL url = new URL(link);
			//��������� ���������� url 
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			//�������� ������ �������� ����� ����� ���������� ������� ������ HttpURLConnection
			double fileSize = (double)http.getContentLength();
			//��������� ������� ����� �� ������ ����������
			BufferedInputStream in = new BufferedInputStream(http.getInputStream());
			//��������� �������� ����� �� �����
			FileOutputStream fos = new FileOutputStream(out);
			//������� �������� ����������������� ����� � �������� 512
			BufferedOutputStream bout = new BufferedOutputStream(fos, speed);
			//������ �� ������� ������ ������
			byte[] buffer = new byte[speed];
			//
			double downloaded  = 0.00;
			//���-�� ����������� �����
			int read = 0;
			//������� ����������
			double percentDownloaded = 0.00;
			System.out.println("Start download the " + (fileCount + 1) + " file.");
			//������ ����
			while((read = in.read(buffer, 0, speed)) >= 0)
			{
				//���������� � �������� ����� ������ � �������� 0 � �������� read
				bout.write(buffer, 0 , read);
				downloaded += read;;
			}
			//��������� ���
			bout.close();
			in.close();
			System.out.println("Download the "+ (fileCount + 1) + " file complete!");

		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

}
