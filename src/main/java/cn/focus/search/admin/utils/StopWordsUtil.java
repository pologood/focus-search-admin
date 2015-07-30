package cn.focus.search.admin.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.focus.search.admin.model.StopWords;

@Service
public class StopWordsUtil {

	public List<StopWords> getStopList(int type, String stops, String editor, int status)
	{
		List<StopWords> stopList = new LinkedList<StopWords>();
		try{
			String[] stop = stops.split(",");
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
	
	public static void main(String[] args)
	{
		Date now=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowt = sdf.format(now);
		System.out.println(nowt);
		try {
			now = sdf.parse(nowt);
			System.out.println(now.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
