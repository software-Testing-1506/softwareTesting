import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
class doc {
	String name;
	String text;
	int entercount =1,wordcount = 0,symbolcount=0;
	boolean[] temp ={};
	public doc(){}
	public doc(String n,String t,boolean[] para){
		name = n;
		text = t;
		temp = para;
		cal();
	}
	public String  mes(){
		String message= "name:"+name+"\n";
		if(temp[2] ==true)
			message +="\tsymbolcount:"+symbolcount;
		
		if(temp[1] == true)
			message +="\twordcount:"+wordcount;
		
		if(temp[0]==true)
			message +="\tentercount:"+entercount;
		
		return message;
	}
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
		return !(isChinese(c)||isword(c));
	}
	private void cal(){
		boolean isaword = false;
			//多少行是指的多少回车吗
			//单词数 ，如果是汉语呢，那应该是一个字是一个词咯？
		for(int i =0;i<text.length();++i){
			char temp = text.charAt(i);
			if(temp=='\n'){
				entercount +=1;
			}
			else if(isword(temp)&&!isnum(temp)){
				isaword = true;
			}
			else if(isChinese(temp)){
				wordcount+=1;
				if(isaword)
					wordcount +=1;
				isaword = false;
			}
			else if(issymbol(temp))
			{
				if(isaword)
					wordcount +=1;
				isaword = false;
				if(issymbol(temp)){
					symbolcount +=1;
				}
			}
		}
		wordcount = isaword?wordcount+1:wordcount;
	}

}

class outdoc {
	String name;
	ArrayList children =new ArrayList();
	public void clear(){
		children.clear();
	}
}

public class newlei {
	/**
	 * @param args
	 */
	//final char[] para1= {'c','w','l'}; 
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
	public static void writeFileByChars(File file,String mes) throws IOException{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
		bufferedWriter.write(mes);
		bufferedWriter.flush();// 清空缓冲区  
        bufferedWriter.close();// 关闭输出流  
	}
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		boolean  isout = false;
		boolean[] para= {true,true,true};
		ArrayList parachar = new ArrayList();
		ArrayList<doc> docarr = new ArrayList<doc>();
		outdoc outdocarr = new outdoc();
		for(int i =0;i<args.length;i++){
			if(args[i].charAt(0)=='-'){
				if(args[i].length()!=2)
					throw new Exception("a para after a -");
				if(args[i].charAt(1)=='o'){	//参数是-o
					isout= true;
					if(i-1<0)				//first param can't be -o
						throw new Exception("-o as the first para");
					if(i+1>=args.length)
						throw new Exception("-o as the last para");
					if(args[i+1].charAt(0)=='-'||args[i-1].charAt(0)=='-')//the para before 
															// or after must be filename
						throw new Exception("no filename after||before -o");
					if(docarr.size()==0)				//has to have at least one filename message
						throw new Exception("no file message to write!");
					outdoc tempo  =new outdoc();
					for(int index =0;index<docarr.size();index++){	//
						tempo.children.add(docarr.get(index));
					}
					docarr.clear();
					outdocarr = tempo;
				}
				else{						//参数不是-o
					parachar.add(args[i].charAt(1));
				}
			}
			else{
				if(isout){
					outdocarr.name = args[i];		//类型转换吗
					isout = false;	
					File file = new File(args[i]);
					if(!file.exists()){
						createFile(args[i])	;			//unsure
					}
					String s="";
					for(int index =0;index<outdocarr.children.size();index++)
						s += ((doc) (outdocarr.children.get(index))).mes()+"\n";
					writeFileByChars(file,s);										//write into the file

					//code outputfile code here
					
					
					outdocarr.clear();
				}
				else{
					doc tempd = new doc();
					if(parachar.size() ==0){
						 tempd= new doc(args[i],readFileByChars(args[i]),para);	
					}
					else{
						for(int index=0;index<parachar.size();index++){
						boolean[] x = getpara(parachar);
						parachar.clear();
						tempd= new doc(args[i],readFileByChars(args[i]),x);	//set an obj and clear the parachar
																//print the result	
					}
						
				}
					docarr.add(tempd);
					System.out.println(tempd.mes());
			}	
		}
		//createFile("fucknigs");
		//String stream = readFileByChars("javaforcednote");
		//doc d = new doc("javaforcednote",stream,new boolean[]{true,false});
		//System.out.println(d.mes());
	}

}
	private static boolean[] getpara(ArrayList parachar) throws Exception{
		// TODO Auto-generated method stub
		boolean[] t ={false,false,false};
		for(int index=0;index<parachar.size();index++){
			switch ((char)(parachar.get(index))){
			case 'c':
				t[0] =true;
				break;
			case 'w':
				t[1]=true;
				break;
			case 'l':
				t[2] =true;
				break;
			default:
				throw new Exception("not exist such para");
			}
		}
		return t;
		}
	}
