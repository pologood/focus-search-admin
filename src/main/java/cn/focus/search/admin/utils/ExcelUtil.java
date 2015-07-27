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


}
