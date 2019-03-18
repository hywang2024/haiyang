package com.haiyang.poi;



import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * ExcelUtil工具类实现功能:
 *   导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.
 *   导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象.
 *   需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能:
 *   1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 *   2.列名称可以通过注解配置.
 *   3.导出到哪一列可以通过注解配置.
 *   4.鼠标移动到该列时提示信息可以通过注解配置.
 *   5.用注解设置只能下拉选择不能随意填写功能.
 *   6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 */
public class ExcelUtil<T> {
	Class<T> clazz;

	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	public List<T> importExcel(String sheetName, InputStream input) throws Exception {
		List<T> list = new ArrayList<T>();
		Workbook book = Workbook.getWorkbook(input);
		Sheet sheet = null;
		if (!sheetName.trim().equals("")) {
			sheet = book.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
		}
		if (sheet == null) {
			sheet = book.getSheet(0);// 如果传入的sheet名不存在则默认指向第1个sheet.
		}
		int rows = sheet.getRows();// 得到数据的行数
		// 有数据时才处理
		if (rows > 0) {
			// 得到类的所有field
			Field[] allFields = clazz.getDeclaredFields();
			// 定义一个map用于存放列的序号和field
			Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
			for (Field field : allFields) {
				// 将有注解的field存放到map中.
				if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
					ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
					int col = getExcelCol(attr.column());// 获得列号
					field.setAccessible(true);// 设置类的私有字段属性可访问.
					fieldsMap.put(col, field);
				}
			}
			//从第2行开始取数据,默认第一行是表头
			for (int i = 1; i < rows; i++) {
				Cell[] cells = sheet.getRow(i);// 得到一行中的所有单元格对象.
				T entity = null;
				for (int j = 0; j < cells.length; j++) {
					String c = cells[j].getContents();// 单元格中的内容.
					if (c.equals("")) {
						continue;
					}
					entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
					Field field = fieldsMap.get(j);// 从map中得到对应列的field.
					// 取得类型,并根据对象类型设置值.
					Class<?> fieldType = field.getType();
					if ((Integer.TYPE == fieldType)	|| (Integer.class == fieldType)) {
						field.set(entity, Integer.parseInt(c));
					} else if (String.class == fieldType) {
						field.set(entity, String.valueOf(c));
					} else if ((Long.TYPE == fieldType)	|| (Long.class == fieldType)) {
						field.set(entity, Long.valueOf(c));
					} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
						field.set(entity, Float.valueOf(c));
					} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
						field.set(entity, Short.valueOf(c));
					} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
						field.set(entity, Double.valueOf(c));
					} else if (Character.TYPE == fieldType) {
						if ((c != null) && (c.length() > 0)) {
							field.set(entity, Character.valueOf(c.charAt(0)));
						}
					} //else if
				}
				if (entity != null) {
					list.add(entity);
				}
			} //for
		} //if
		return list;
	}

	/**
	 * 对list数据源将其里面的数据导入到excel表单
	 * @param sheetName  工作表的名称
	 * @param sheetSize  每个sheet中数据的行数,此数值必须小于65536
	 * @param output  java输出流
	 */
	public boolean exportExcel(List<T> list, String sheetName, int sheetSize, OutputStream output) {
		Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
		List<Field> fields = new ArrayList<Field>();
		// 得到所有field并存放到一个list中.
		for (Field field : allFields) {
			if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
				fields.add(field);
			}
		}

		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
		// excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
		if (sheetSize > 65536 || sheetSize < 1) {
			sheetSize = 65536;
		}
		double sheetNo = Math.ceil(list.size() / sheetSize);// 取出一共有多少个sheet.
		for (int index = 0; index <= sheetNo; index++) {
			HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
			workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
			HSSFRow row;
			HSSFCell cell;// 产生单元格
			row = sheet.createRow(0);// 产生一行
			// 写入各个字段的列头名称
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
				int col = getExcelCol(attr.column());// 获得列号
				cell = row.createCell(col);// 创建列
				//cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
				cell.setCellType(CellType.STRING);
				cell.setCellValue(attr.name());// 写入列名
				//如果设置了提示信息则鼠标放上去提示.
				if (!attr.prompt().trim().equals("")) {
					//这里默认设了2-101列提示
					setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);
				}
				//如果设置了combo属性则本列只能选择不能输入
				if (attr.combo().length > 0) {
					//这里默认设了2-101列只能选择不能输入
					setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);
				}
			}
			int startNo = index * sheetSize;
			int endNo = Math.min(startNo + sheetSize, list.size());
			//写入各条记录,每条记录对应excel表中的一行
			for (int i = startNo; i < endNo; i++) {
				row = sheet.createRow(i + 1 - startNo);
				//得到导出对象
				T vo = (T) list.get(i); 
				for (int j = 0; j < fields.size(); j++) {
					//获得field
					Field field = fields.get(j);
					//设置实体类私有属性可访问
					field.setAccessible(true);
					ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
					try {
						//根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
						if (attr.isExport()) {
							//创建cell
							cell = row.createCell(getExcelCol(attr.column()));
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellType(CellType.STRING);
							//如果数据存在就填入,不存在填入空格
							cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} //for
			} //for
		} //for
		try {
			output.flush();
			workbook.write(output);
			output.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output is closed ");
			return false;
		}

	}

	/**
	 * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
	 * 
	 * @param col
	 */
	public static int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

	/**
	 * 设置单元格上提示
	 * @param sheet 要设置的sheet
	 * @param promptTitle  标题
	 * @param promptContent  内容
	 * @param firstRow  开始行
	 * @param endRow    结束行
	 * @param firstCol  开始列
	 * @param endCol    结束列
	 * @return 设置好的sheet
	 */
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
		String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
		// 构造constraint对象
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

	/**
	 * 设置某些列的值只能输入预制的数据,显示下拉框.
	 * @param sheet   要设置的sheet
	 * @param textlist   下拉框显示的内容
	 * @param firstRow   开始行
	 * @param endRow     结束行
	 * @param firstCol   开始列
	 * @param endCol     结束列
	 * @return 设置好的sheet
	 */
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet,String[] textlist,
		int firstRow, int endRow, int firstCol,	int endCol) {
		// 加载下拉列表内容
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 数据有效性对象
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	//判断从Excel文件中解析出来数据的格式
	public static String getValue(HSSFCell cell) {
		String value = "";
		// 简单的查检列类型
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字
			long dd = (long) cell.getNumericCellValue();
			value = dd + "";
			break;
		case HSSFCell.CELL_TYPE_BLANK:// 空白
			value = "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA:// 公式型 2
			value = String.valueOf(cell.getCellFormula());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			break;
		}
		return value;
	}
	
	public static String getExcelVersion(String filePath) {
		if (filePath.matches("^.+\\.(?i)(xlsx)$")) {
			return "2007";
		}else if (filePath.matches("^.+\\.(?i)(xls)$")) {
			return "2003";
		} 
		return "";
	}

	/**
	 * 判断日期格式 true:是   false:否
	 * @param str
	 * @return
	 */
	public boolean isDateFormat(String str){
		boolean result = true;
		Pattern pattern = Pattern.compile("[0-9]{2}-[0-9]{1,2}-[0-9]{1,2}");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			result = false;
		}
		return result;
	}
	
	/**
	 * 包含日期类型文件导入
	 * @param sheetName
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public List<T> importExcelHasDate(String sheetName, InputStream input) throws Exception {
		List<T> list = new ArrayList<T>();
		try {
			Workbook book = Workbook.getWorkbook(input);
			Sheet sheet = null;
			if (!sheetName.trim().equals("")) {
				//如果指定sheet名,则取指定sheet中的内容
				sheet = book.getSheet(sheetName);
			}
			if (sheet == null) {
				//如果传入的sheet名不存在则默认指向第1个sheet
				sheet = book.getSheet(0);
			}
			int rows = sheet.getRows();//得到数据的行数
			//有数据时才处理
			if (rows > 0) {
				//得到类的所有field
				Field[] allFields = clazz.getDeclaredFields();
				//定义一个map用于存放列的序号和field
				Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
				for (Field field : allFields) {
					// 将有注解的field存放到map中.
					if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
						ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
						int col = getExcelCol(attr.column());// 获得列号
						field.setAccessible(true);//设置类的私有字段属性可访问
						fieldsMap.put(col, field);
					}
				}
				//从第2行开始取数据,默认第一行是表头
				for (int i = 1; i < rows; i++) {
					Cell[] cells = sheet.getRow(i);// 得到一行中的所有单元格对象.
					T entity = null;
					for (int j = 0; j < cells.length; j++) {
						Object value = cells[j].getContents();// 单元格中的内容.
						if (value.equals("")) {
							continue;
						}
						if(value instanceof String){
							if(isDateFormat(value.toString())){
								SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd", Locale.CHINA);
								Date d = sf.parse(value.toString());
								SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
								System.out.println(sf1.format(d));
								value=sf1.format(d);
							}
						}
						String c = value.toString();
						//如果不存在实例则新建
						entity = (entity == null ? clazz.newInstance() : entity);
						//从map中得到对应列的field
						Field field = fieldsMap.get(j);
						//取得类型,并根据对象类型设置值.
						Class<?> fieldType = field.getType();
						if ((Integer.TYPE == fieldType)	|| (Integer.class == fieldType)) {
							field.set(entity, Integer.parseInt(c));
						} else if (String.class == fieldType) {
							field.set(entity, String.valueOf(c));
						} else if ((Long.TYPE == fieldType)	|| (Long.class == fieldType)) {
							field.set(entity, Long.valueOf(c));
						} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
							field.set(entity, Float.valueOf(c));
						} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
							field.set(entity, Short.valueOf(c));
						} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
							field.set(entity, Double.valueOf(c));
						} else if (Character.TYPE == fieldType) {
							if ((c != null) && (c.length() > 0)) {
								field.set(entity, Character.valueOf(c.charAt(0)));
							}
						} //else if
					} //for
					if (entity != null) {
						list.add(entity);
					}
				} //for
			} //if
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return list;
	}
}
