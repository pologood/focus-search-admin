package cn.focus.search.admin.utils;

import cn.focus.search.admin.model.StopWords;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StopWordsUtil {

	public List<StopWords> getStopList(int type, String stops, String editor, int status)
	{
		List<StopWords> stopList = new LinkedList<StopWords>();
		try{
			String[] stop = stops.split("[, ， ]");
			for (int i = 0; i < stop.length; i++)
			{
				StopWords sw = new StopWords();
				sw.setName(stop[i]);
				sw.setType(type);
				sw.setStatus(status);
				sw.setEditor(editor);
				Date now=new Date();
				sw.setCreateTime(now);
				sw.setUpdateTime(now);
				stopList.add(sw);
			}
			
			return stopList;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return stopList;		
	}
	

	
	public String[] getParticiple(String par)
	{
		String[] stop = par.split("[, ， ]");
		return stop;
	}
	
	public static void main(String[] args)
	{
		char[] b = new char[10];
/*		Date now=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowt = sdf.format(now);
		System.out.println(nowt);
		try {
			now = sdf.parse(nowt);
			System.out.println(now.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		}*/
		List<String> list = new ArrayList<String>();	
		list.add("a");list.add("b");list.add("c");
		Iterator<String> it = list.iterator();
		while(it.hasNext())
		{	
			String str = (String)it.next();			
			it.remove();
			//System.out.println(str);
		}
		ListIterator<String> its = list.listIterator();
		
		its.add("d");
		its.hasPrevious();
		its.previous();
		its.set("e");
		while(its.hasNext())
		{	
			String str = (String)its.next();
			System.out.println(str);
		}
/*		String ss = "a,b c，d，";
		int len = ss.length();
		System.out.println(ss.substring(0, len-1));
		String[] stop = ss.split("[, ，]");
		for (int i = 0; i < stop.length; i++)
			System.out.println(stop[i]);*/
	}
}
