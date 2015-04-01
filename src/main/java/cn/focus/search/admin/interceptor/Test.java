package cn.focus.search.admin.interceptor;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Test {

	  public static void main(String[] args) {
	        //标题行
	        String title[]={"单词","索引分词","搜索分词"};
	        //内容
	        String context[][]={{"徐汇龙庭(鑫龙苑)","徐汇龙庭 徐汇 徐 汇龙 龙庭 庭 鑫龙苑 鑫龙 鑫 龙苑 苑 ","徐汇龙庭 鑫龙苑 "},
	                            {"界龙阳光苑","界龙阳光苑 界龙 龙阳 阳光苑 阳光 苑","界龙阳光苑 "},
	                            {"舜龙公寓","舜龙公寓 舜龙 舜 龙公 公寓 ","舜龙公寓 "},
	                            {"西郊龙柏","西郊龙柏 西郊 郊 龙柏 柏 ","西郊龙柏"}
	                            };
	        //操作执行
	        try { 
	            //t.xls为要新建的文件名
	            WritableWorkbook book= Workbook.createWorkbook(new File("D:\\data\\t.xls")); 
	            //生成名为“第一页”的工作表，参数0表示这是第一页 
	            WritableSheet sheet=book.createSheet("第一页",0); 
	            
	            //写入内容
	            for(int i=0;i<3;i++)    //title
	                sheet.addCell(new Label(i,0,title[i])); 
	            for(int i=0;i<4;i++)    //context
	            {
	                for(int j=0;j<3;j++)
	                {
	                    sheet.addCell(new Label(j,i+1,context[i][j])); 
	                }
	            }
	            
	            //写入数据
	            book.write(); 
	            //关闭文件
	            book.close(); 
	            System.out.println("finished");
	        }
	        catch(Exception e) { } 
	    }

}
