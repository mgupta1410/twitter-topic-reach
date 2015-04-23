import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class preprocessing
{
public static void main(String[] args) throws IOException
{
	Preprocessor p = new Preprocessor();
	File file = new File("final_test.txt");//write in this
	if (!file.exists()) {file.createNewFile();}
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	FileReader input = new FileReader("raw_test");
	BufferedReader br = new BufferedReader(input);

	String line;
	
	while((line = br.readLine()) != null)
	{	
		 //System.out.println(line);
		 String[] words = line.split(" ", 3);
		 if(words.length >=3)
{
		 String str3 = p.PreprocessLine(words[2]);
		 if(str3!=null && !str3.equalsIgnoreCase("") )
		{
			String str = words[0]+" "+str3+"\n";
			bw.write(str);	
		}
}		
	}
	bw.close();
	br.close();
}
}

class Preprocessor 
{
public static int tweetCountPoliticsFinal;
public static int tweetCountSportsFinal;
public static int tweetCountTechnologyFinal;
public static int tweetCountTotalFinal;
public Map <String , Integer> tweetPolitcs = new HashMap <String , Integer>();
public Map <String , Integer> tweetSports = new HashMap <String , Integer>();
public Map <String , Integer> tweetTechnology = new HashMap <String , Integer>();
public Map <String , Integer> stopword = new HashMap <String , Integer>();

public String removeLinks(String val)
{
	String res = new String("");
	StringTokenizer token = new StringTokenizer(val);
	while(token.hasMoreElements())
	{
		String str = (String) token.nextElement();
		CharSequence ch = "http://";
		CharSequence ch1 = "www.";
		CharSequence ch2 = "https://";
		CharSequence ch3 = "ssh://";
		CharSequence ch4 = "ftp://";
		if( !str.contains(ch) )
		{
			if( !str.contains(ch1) )
			{
				if( !str.contains(ch2) )
				{
					if( !str.contains(ch3) )
					{
						if( !str.contains(ch4) )	res = res + str + " ";
		
					}
				}
			}
		}

	}
	return res;
}

public String removeUsername(String val)
{
	String res = new String("");
	StringTokenizer token = new StringTokenizer(val);
	while(token.hasMoreElements())
	{
		String str = (String) token.nextElement();
		CharSequence ch = "@";
		if(!str.contains(ch))	res = res + str + " ";
	}
	return res;
}

public String removeStopwords(String val)
{
	String res = new String("");
	StringTokenizer token = new StringTokenizer(val);
	while(token.hasMoreElements())
	{
		String str = (String) token.nextElement();
		if(!(stopword.containsKey(str)))
		{
			res = res + str + " ";
		}
	}
	return res;
}

public String removeHash(String val)
{
	String res = new String("");
	StringTokenizer token = new StringTokenizer(val);
	while(token.hasMoreElements())
	{
		String str = (String) token.nextElement();
		if(str.startsWith("#"))
		{
			res = res + str.substring(1,(int)str.length()) + " ";
		}
		else
			res = res + str + " ";
	}
	return res;
}

public String removeEmoticons(String result)
{
	result = result.replaceAll(":[)]", "");
	result = result.replaceAll(";[)]", "");
	result = result.replaceAll(":-[)]", "");
	result = result.replaceAll(";-[)]", "");
	result = result.replaceAll(":d", "");
	result = result.replaceAll(";d", "");
	result = result.replaceAll("=[)]", "");
	result = result.replaceAll("\\^_\\^", "");
	result = result.replaceAll(":[(]", "");
	result = result.replaceAll(":-[(]", "");
	return result;
}

public String removeExtra(String val)
{
	String res = new String("");
	for(int i=0;i<val.length();i++)
	{
		char ch = val.charAt(i);
		int c = (int) ch;
		if((c>=65 && c<=90) || (c>=97 && c<=122) || (c==32))
		res = res + ch;
		}
	return res;
}
public String PreprocessLine(String line) throws IOException
{
	Preprocessor p = new Preprocessor();
	File stop = new File("stop.txt");
	FileReader st = new FileReader(stop);
	BufferedReader br1 = new BufferedReader(st);
	String word;
	while((word = br1.readLine()) != null)
	{
		p.stopword.put(word, 1);
	}
	br1.close();
	line = p.removeUsername(line);
	line = p.removeLinks(line);
	line = p.removeHash(line);
	line = p.removeEmoticons(line);
	line = p.removeExtra(line);
	line = p.removeStopwords(line);
	return line;
}

}


