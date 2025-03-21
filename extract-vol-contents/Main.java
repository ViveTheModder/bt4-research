package cmd;
//Proof of Concept: VOL File Contents Extractor (ADX Only)
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		File src=null;
		Scanner sc = new Scanner(System.in);
		while (src==null)
		{
			System.out.println("Enter a valid path to a VOL file (AFS file without metadata and header data):");
			String path = sc.nextLine();
			File tmp = new File(path);
			if (tmp.isFile())
			{
				String fileName = tmp.getName().toLowerCase();
				if (fileName.endsWith(".vol") || fileName.endsWith(".afs")) src=tmp; 
			}
		}
		sc.close();
		//let's see how much this is gonna fuck my PC UP --> UPDATE: 25% CPU usage lmao
		RandomAccessFile vol = new RandomAccessFile(src,"r");
		int fileCnt=0;
		long volLen = vol.length();
		long start = System.currentTimeMillis();
		while (vol.getFilePointer()<volLen)
		{
			//ADX detection attempt 1
			if (vol.readUnsignedShort()==0x8000)
			{
				System.out.println("ADX Detection Attempt 1 - SUCCESS (Position: "+vol.getFilePointer()+")");
				short copyrightOffset = vol.readShort();
				//ADX detection attempt 2
				int partOfHeader = vol.readInt();
				if (partOfHeader==51512321 || partOfHeader==51512322)
				{
					System.out.println("ADX Detection Attempt 2 - SUCCESS (Position: "+vol.getFilePointer()+")");
					vol.seek(vol.getFilePointer()-8+copyrightOffset);
					//ADX detection attempt 3
					if (vol.readInt()==0x29435249)
					{
						System.out.println("ADX Detection Attempt 3 - SUCCESS (Position: "+vol.getFilePointer()+")");
						fileCnt++;
						vol.seek(vol.getFilePointer()-copyrightOffset+8);
						int totalSamples = vol.readInt();
						int numBlocks = totalSamples/32; //32 is the number of samples per block
						int estimatedFileSize = (numBlocks*18)+copyrightOffset+4; //18 is the number of bytes per block
						//round the file size to the nearest multiple of 32 (just for generosity)
						if (estimatedFileSize%32!=0) estimatedFileSize = (estimatedFileSize+32-(estimatedFileSize%32));
						byte[] adxBytes = new byte[estimatedFileSize];
						vol.seek(vol.getFilePointer()-16);
						vol.read(adxBytes);
						String adxFolderPath = src.getAbsoluteFile().getParent()+"/"+src.getName()+" RESULTS/";
						File adxFolder = new File(adxFolderPath);
						if (!adxFolder.exists()) adxFolder.mkdir();
						System.out.println("Making "+fileCnt+".adx...");
						RandomAccessFile adx = new RandomAccessFile(adxFolderPath+fileCnt+".adx","rw");
						adx.write(adxBytes);
						adx.close();
					}
				}
			}
		}
		vol.close();
		long end = System.currentTimeMillis();
		System.out.println("Time: "+((end-start)/1000.0)+" s");
	}
}