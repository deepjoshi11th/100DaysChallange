package test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFileChooser;

public class Tester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//System.out.println("Hello, World");	
		//String[] commands = {"cmd.exe","/c","D:","&& cd","D:\\Deep", "&& dir"};
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(null);
		String path = jfc.getSelectedFile().getAbsolutePath();
		path = path+"\\";
		File f = new File(path+"Comp\\");
		if(!f.exists()) {
			f.mkdir();
		}
		if(f.exists()) {
			System.out.println("Comp dir created");
		}
		
		String[] commands = {"cmd.exe","/c","D: && cd "+path+" && dir"};
		ProcessBuilder pb = new ProcessBuilder(commands);
		Process p = Runtime.getRuntime().exec(commands);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while((line=br.readLine())!=null) {
			if(line.contains("IMG-")) {
				line = line.substring(line.lastIndexOf("IMG-"));
				//System.out.print(line+" ");
				//System.out.println(Integer.parseInt(line.substring(8, 10))+" "+Integer.parseInt(line.substring(10, 12)));
				Date d = new Date(Integer.parseInt(line.substring(4, 8))-1900,Integer.parseInt(line.substring(8, 10))-1,Integer.parseInt(line.substring(10,12)));
				Date e = new Date();
				float compressionRatio;
				if(validDuration(d,e)) {
					compressionRatio = 0.15f;
					try {
						compressFile(path+line,path+"Comp\\"+line,compressionRatio);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Files.copy(new File(path+line).toPath(), new File(path+"Comp\\"+line).toPath());
				}
			}
		}
	}

	private static void compressFile(String string, String string2, float compressionRatio) throws Exception {
		// TODO Auto-generated method stub
		File input = new File(string);
		BufferedImage image =  ImageIO.read(input);
		File compressedImage = new File(string2);
		OutputStream os = new FileOutputStream(compressedImage);
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = writers.next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);
		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(compressionRatio);
		writer.write(null,new IIOImage(image, null, null),param);
		os.close();
		ios.close();
		writer.dispose();
	}

	private static boolean validDuration(Date d, Date e) {
		long duration = e.getTime() - d.getTime();
		System.out.println(e+" "+d);
		duration = duration/(1000*60*60*24);
		
		if(duration>60) {
			return true;
		} else {
			return false;
		}
	}
}
