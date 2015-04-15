package cn.focus.search.admin.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA. User: Bo-long Date: 2011-6-9 Time: 22:32:06 To
 * change this template use File | Settings | File Templates.
 */
@Service
public class ExcelUtil {
	private Log logger = LogFactory.getLog(ExcelUtil.class);
	private Map<String, String> excelTemplateMap;
	private static final ExcelUtil instance = new ExcelUtil();

	private ExcelUtil() {
	}

	public static ExcelUtil getInstance() {
		return instance;
	}

	/**
	 * @param request
	 * @param response
	 * @param exportName
	 *            ：生成下载的文件名
	 * @param templateName
	 *            ：位于指定的路径下的模板文件名
	 * @param dataMap
	 *            ：需要导出的数据
	 * @throws Exception
	 */
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, String exportName,
			String templateName, Map<String, Object> dataMap) throws Exception {
		
		response.reset();
		response.setContentType("application/vnd.ms-excel");
//	    response.setContentType("application/xls;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ exportName + "\"");

		String templateFullPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "/templates/" + templateName;
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFullPath));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is,
					dataMap);
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(
							"ExcelUtil -> exportExcel() -> is.close() error!",
							e);
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error(
							"ExcelUtil -> exportExcel() -> os.close() error!",
							e);
				}
			}
		}
	}

	/**
	 * @param request
	 * @param templatePathName
	 *            ：位于指定的路径下的模板文件名
	 * @param dataMap
	 *            ：需要导出的数据
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel(HttpServletRequest request,
			String templatePathName, Map<String, Object> dataMap)
			throws Exception {
		String templateFullPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ templatePathName;
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFullPath));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is,
					dataMap);
			return workbook;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(
							"ExcelUtil -> exportExcel() -> is.close() error!",
							e);
				}
			}
		}
	}

	/**
	 * @param request
	 * @param exportName
	 *            ：生成下载的文件名
	 * @param templateName
	 *            ：位于指定的路径下的模板文件名
	 * @param dataMap
	 *            ：需要导出的数据
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel(HttpServletRequest request,
			String exportName, String templateName, Map<String, Object> dataMap)
			throws Exception {
		String templateFullPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "templates/" + templateName;
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFullPath));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is,
					dataMap);
			return workbook;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(
							"ExcelUtil -> exportExcel() -> is.close() error!",
							e);
				}
			}
		}
	}

	public void setExcelTemplateMap(Map<String, String> excelTemplateMap) {
		this.excelTemplateMap = excelTemplateMap;
	}

	public static void main(String[] args) throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("学生表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("单词");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("索引分词");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("搜索分词");
		cell.setCellStyle(style);

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		List list = getStudent();

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			Student stu = (Student) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue((double) stu.getId());
			row.createCell((short) 1).setCellValue(stu.getName());
			row.createCell((short) 2).setCellValue((double) stu.getAge());
			cell = row.createCell((short) 3);
			cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu
					.getBirth()));
		}
		// 第六步，将文件存到指定位置
		try {
			FileOutputStream fout = new FileOutputStream("E:/students.xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<Student> getStudent() throws Exception {
		List list = new ArrayList();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

		Student user1 = new Student(1, "张三", 16, df.parse("1997-03-12"));
		Student user2 = new Student(2, "李四", 17, df.parse("1996-08-12"));
		Student user3 = new Student(3, "王五", 26, df.parse("1985-11-12"));
		list.add(user1);
		list.add(user2);
		list.add(user3);

		return list;
	}
}
