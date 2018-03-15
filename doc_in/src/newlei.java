import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class newlei {

	/**
	 * @param args
	 */
	public static boolean isChinese(char c) {  
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断  
    }  
	public static boolean isword(char c) {  
        return (c>='A'&&c<='Z')||(c>='a'&&c<='z')||(c>='0'&&c<='9');  
    }  
	public static boolean isnum(char c) {  
        return (c>='0'&&c<='9');  
    }
	public static boolean issymbol(char c){
		return !(isChinese(c)||isword(c)||(c=='\n')||(c ==' '));
	}
	public static boolean createFile(String filePath){
		boolean result = false;
		File file = new File(filePath);
		if(!file.exists()){
			try{
				result = file.createNewFile();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		else System.out.println("file exists");
		return result;
	}
	public static String readFileByChars(String filePath){
		File file = new File(filePath);
		if(!file.exists()||!file.isFile()){
			return null;
		}
		StringBuffer content = new StringBuffer();
		try{
			char[] temp = new char[1];				//这tm成了逐个字符读入了
			InputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");  
			while(inputStreamReader.read(temp)!=-1){
				content.append(new String(temp));
				temp = new char[1];
			}
			fileInputStream.close();
			inputStreamReader.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return content.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//createFile("fucknigs");
		boolean isaword = false;
		String stream = readFileByChars("javaforcednote");
		int entercount = 1,wordcount  = 0,symbolcount =0;		//多少行是指的多少回车吗
									//单词数 ，如果是汉语呢，那应该是一个字是一个词咯？
		for(int i =0;i<stream.length();i++){
			char temp = stream.charAt(i);
			if(temp=='\n')
				entercount +=1;
			if(isword(temp)){
				if(!isnum(temp)){
					isaword = true;
				}
			}
			if(isChinese(temp)){
				wordcount+=1;
				if(isaword)
					wordcount +=1;
				isaword = false;
			}
			else if(issymbol(temp)||(temp ==' '))
			{
				if(isaword)
					wordcount +=1;
				isaword = false;
				if(issymbol(temp)){
					symbolcount +=1;
				}
			}
				
		}
		if(isaword) wordcount +=1;

		System.out.println(stream);
		System.out.println("entercount: ");
		System.out.println(entercount);
		System.out.println("wordcount: ");
		System.out.println(wordcount);
		System.out.println("symbolcount: ");
		System.out.println(symbolcount);
		/*char c = 'a';
		boolean allzimu = true;
		while(c<='Z'){
			if(isChinese(c)){
				allzimu = false;
				break;
				}
		}
		System.out.println(allzimu);*/
	}

}
