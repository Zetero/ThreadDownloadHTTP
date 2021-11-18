package url;

//Управление файлами и каталогами
import java.io.File;
//Буферизированные потоки ввода и вывода. Для оптимизации
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
//Побайтовая запись файла
import java.io.FileOutputStream;
//Вылавливание ошибок
import java.io.IOException;
//Поддержка URL
import java.net.URL;

import com.sun.tools.javac.Main;

//Поддержка HTTP
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
			//Создаем экзмеляр класса URL с ссылкой
			URL url = new URL(link);
			//Открываем соединение url 
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			//Выясняем размер будущего файла через встроенную функцию класса HttpURLConnection
			double fileSize = (double)http.getContentLength();
			//Открываем входной поток из нашего соединения
			BufferedInputStream in = new BufferedInputStream(http.getInputStream());
			//Открываем выходной поток из файла
			FileOutputStream fos = new FileOutputStream(out);
			//Создаем выходной буфферизированный поток с размером 512
			BufferedOutputStream bout = new BufferedOutputStream(fos, speed);
			//Буффер из массива байтов размер
			byte[] buffer = new byte[speed];
			//
			double downloaded  = 0.00;
			//Кол-во прочитанных битов
			int read = 0;
			//Процент скачанного
			double percentDownloaded = 0.00;
			System.out.println("Start download the " + (fileCount + 1) + " file.");
			//Читаем биты
			while((read = in.read(buffer, 0, speed)) >= 0)
			{
				//Записываем в выходной поток буффер с отступом 0 и размером read
				bout.write(buffer, 0 , read);
				downloaded += read;;
			}
			//Закрываем все
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
