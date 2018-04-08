import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class javarestoreTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPrint1() {
		String s = "asdf";
		String filename = "output.txt";
		ArrayList<String> as = new ArrayList<String>();
		as.add(s);
		ArrayList<outfile> ao = new ArrayList<outfile>();
		ao.add(new outfile(new File(filename),s));
		javarestore.print(as,ao);
		Assert.assertEquals("output to files but the content of files not match", javarestore.readFileByChars(filename), s);
	}
	@Test
	public void testPrint2() {
		String s = "asdf";
		String filename = "output.txt";
		ArrayList<String> as = new ArrayList<String>();
		as.add(s);
		ArrayList<outfile> ao = new ArrayList<outfile>();
		ao.add(new outfile(new File(filename),s));
		javarestore.print(as,ao);
		Assert.assertEquals("output to files but the content of files not match", javarestore.readFileByChars(filename), s);
	}
	@Test
	public void testPrint3() {
		String s = "asdf";			//simulate two files output
		String filename = "output.txt";
		ArrayList<String> as = new ArrayList<String>();
		ArrayList<outfile> ao = new ArrayList<outfile>();
		as.add(s);
		ao.add(new outfile(new File(filename),s));

		String s1 = "xzvbfwetyt";
		String filename1 = "output.tt";
		as.add(s1);
		ao.add(new outfile(new File(filename1),s1));
		javarestore.print(as,ao);
		Assert.assertEquals("output to files but the content of files not match", javarestore.readFileByChars(filename), s);
		Assert.assertEquals("output to files but the content of files not match", javarestore.readFileByChars(filename1), s1);
	}

	@Test
	public void testPrint4() {
		String s = "asdf";			//simulate two files output
		String filename = "output.txt";
		ArrayList<String> as = new ArrayList<String>();
		ArrayList<outfile> ao = new ArrayList<outfile>();
		as.add(s);
		ao.add(new outfile(new File(filename),s));

		String s1 = "xzvbfwetyt";
		String filename1 = "output.tt";
		as.add(s1);
		ao.add(new outfile(new File(filename1),s1));
		
		String s2 = "safgsfghtr";
		as.add(s2);
		String filename2 = "junittest";
		ao.add(new outfile(new File(filename2),s2));
		javarestore.print(as,ao);
		Assert.assertEquals("output to files but the content of files not match", javarestore.readFileByChars(filename), s);
		Assert.assertEquals("output to files but the content of files not match", javarestore.readFileByChars(filename1), s1);	
		Assert.assertEquals("output to files but the content of files not match", javarestore.readFileByChars(filename2), s2);
	}

	@Test
	public void testfileopen() {
		String filename = "output.txt";
		String testcontent = "it's a tricking content test checking if the function is right";
		try {
			javarestore.writeFileByChars(new File(filename), testcontent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals("not fit traversal dir filenames",javarestore.readFileByChars(filename),testcontent);
		
	}
	@Test
	public void testTraversalDirWithSuffix1() {
		String filesuffix = ".cpp";
		String[] filenames = {"asd.cpp"};
		int count=0;
		Assert.assertEquals("not fit ames'num",javarestore.TraversalDir(filesuffix).length,filenames.length);
		for(String s:javarestore.TraversalDir(filesuffix)) {
			Assert.assertEquals("not fit traversal dir filenames",s,filenames[count]);
			count+=1;
		}
	}
	@Test
	public void testTraversalDirWithSuffix2() {
		String filesuffix = ".c";
		String[] filenames = {"a.c","x.c"};
		int count=0;
		Assert.assertEquals("not fit ames'num",javarestore.TraversalDir(filesuffix).length,filenames.length);
		for(String s:javarestore.TraversalDir(filesuffix)) {
			Assert.assertEquals("not fit traversal dir filenames",s,filenames[count]);
			count+=1;
		}
	}
	@Test
	public void testTraversalDirWithSuffix3() {
		String filesuffix = ".tt";
		String[] filenames = {"output.tt"};
		int count=0;
		Assert.assertEquals("not fit ames'num",javarestore.TraversalDir(filesuffix).length,filenames.length);
		for(String s:javarestore.TraversalDir(filesuffix)) {
			Assert.assertEquals("not fit traversal dir filenames",s,filenames[count]);
			count+=1;
		}
	}
	@Test
	public void testTraversalDirWithSuffix4() {
		String filesuffix = ".txt";
		String[] filenames = {"output.txt"};
		int count=0;
		Assert.assertEquals("not fit ames'num",javarestore.TraversalDir(filesuffix).length,filenames.length);
		for(String s:javarestore.TraversalDir(filesuffix)) {
			Assert.assertEquals("not fit traversal dir filenames",s,filenames[count]);
			count+=1;
		}
	}
	@Test
	public void testTraversalDirWithSuffix5() {
		String filesuffix = ".classpath";
		String[] filenames = {".classpath"};
		int count=0;
		Assert.assertEquals("not fit ames'num",javarestore.TraversalDir(filesuffix).length,filenames.length);
		for(String s:javarestore.TraversalDir(filesuffix)) {
			Assert.assertEquals("not fit traversal dir filenames",s,filenames[count]);
			count+=1;
		}
	}
	@Test
	public void testTraversalDir() {			//ide explain * as all the filenames but the 
		String[] filenames = {".classpath",".project","a.c","asd.cpp","checkword","javaforcednote","junittest","output","output.tt","output.txt","stoplist","text","x.c"};
		int count=0;
		Assert.assertEquals("not fit traversal dir filenames'num",javarestore.TraversalDir().length,javarestore.TraversalDir().length);
		for(String s:javarestore.TraversalDir()) {
			Assert.assertEquals("not fit traversal dir filenames",s,filenames[count]);
			count+=1;
		}
	}
	@Test
	public void testIput() {
		String[] onefile = {"javaforcednote"};
		
	}
}
