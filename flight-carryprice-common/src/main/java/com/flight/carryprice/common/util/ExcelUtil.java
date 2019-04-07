package com.flight.carryprice.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("deprecation")
public class ExcelUtil {

    private final static Logger LOGGER = Logger.getLogger(ExcelUtil.class);

    /**
     * 根据文件类型读取文件
     *
     * @param file :支持xls,xlsx
     * @return ArrayList<String[]>
     * @author juln
     */
    public static ArrayList<String[]> readFileByWay(String file) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        Workbook workbook = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            if (file.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (file.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
            int totalCells = 0;// 列
            if (lastRowIndex >= 1 && sheet.getRow(0) != null) {
                totalCells = sheet.getRow(0).getPhysicalNumberOfCells();// 获取列

                for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
                    Row currentRow = sheet.getRow(rowIndex);// 当前行
                    String[] cellStrs = new String[totalCells];
                    for (int c = 0; c < totalCells; c++) {
                        cellStrs[c] = getCellValue(currentRow.getCell(c), true);
                    }
                    list.add(cellStrs);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
        return list;
    }

    /**
     * 根据文件类型读取文件
     *
     * @param file          :支持xls,xlsx
     * @param firstRowIndex :从第几行开始读取
     * @return ArrayList<String[]>
     * @author juln
     */
    public static ArrayList<String[]> readFileByWay(String file, int firstRowIndex) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        Workbook workbook = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            if (file.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (file.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowIndex = sheet.getLastRowNum();
            int totalCells = 0;// 列
            if (lastRowIndex >= 1 && sheet.getRow(0) != null) {
                totalCells = sheet.getRow(firstRowIndex).getPhysicalNumberOfCells();// 获取列

                for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
                    Row currentRow = sheet.getRow(rowIndex);// 当前行
                    String[] cellStrs = new String[totalCells];
                    for (int c = 0; c < totalCells; c++) {
                        cellStrs[c] = getCellValue(currentRow.getCell(c), true);
                    }
                    list.add(cellStrs);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
        return list;
    }

    /**
     * 根据文件类型读取文件
     *
     * @param file          :支持xls,xlsx
     * @param firstRowIndex :从第几行开始读取
     * @return ArrayList<String[]>
     * @author juln
     */
    public static ArrayList<String[]> readFileByWay(MultipartFile file, int firstRowIndex) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        Workbook workbook = null;
        InputStream is = null;
        try {
            is = file.getInputStream();
            CommonsMultipartFile c_file = (CommonsMultipartFile) file;
            String fileName = c_file.getFileItem().getName();
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowIndex = sheet.getLastRowNum();
            int totalCells = 0;// 列
            if (lastRowIndex >= 1 && sheet.getRow(0) != null) {
                totalCells = sheet.getRow(firstRowIndex - 1).getPhysicalNumberOfCells();// 获取列

                for (int rowIndex = firstRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
                    Row currentRow = sheet.getRow(rowIndex);// 当前行
                    String[] cellStrs = new String[totalCells];
                    for (int c = 0; c < totalCells; c++) {
                        cellStrs[c] = getCellValue(currentRow.getCell(c), true);
                    }
                    list.add(cellStrs);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
        return list;
    }

    /**
     * 取单元格的值
     *
     * @param cell       单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    private static String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).trim();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue()).trim();
        } else {
            return String.valueOf(cell.getStringCellValue()).trim();
        }
    }

    /**
     * 创建excel
     *
     * @param filename
     * @param title
     * @param date
     * @param secondTitles
     * @param list
     * @return
     */
    public static HSSFWorkbook createExcel(String filename, String title, String[] secondTitles,
                                           List<LinkedList<String>> list) {

        HSSFWorkbook book = new HSSFWorkbook();
        LOGGER.info("list size:" + list.size());
        int totle = list.size();// 获取List集合的size
        int mus = 65530;// 每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        for (int m = 0; m < avg + 1; m++) {
            HSSFSheet sheet = book.createSheet(title + m);
            sheet.setDisplayGridlines(true);
            // 定义各种样式
            HSSFCellStyle firStyle = createMyCellStyle(book, 18, true, true, true, null);// 大标题
            // 第一行样式
            // HSSFCellStyle secStyle = createMyCellStyle(book, 12, true, false,
            // true, null);// 中标题
            // 第二、三行样式
            HSSFCellStyle thirStyle = createMyCellStyle(book, 10, true, true, true, null);// 小标题
            // 第四行样式
            HSSFCellStyle contentStyle = createMyCellStyle(book, 10, false, true, true, "宋体");// 内容

            HSSFRow row = null;
            // 第一行
            int index = 0;
            @SuppressWarnings("unused")
            SimpleDateFormat ss = new SimpleDateFormat("yyyy年MM月dd日");
            row = sheet.createRow(index);
            row.setHeight((short) 600);
            createCellAndSetStrVal(row, 0, firStyle, title);
            sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 0, (short) (secondTitles.length - 1)));
            LOGGER.info("生成大标题");
            index++;

            // 第三行
            row = sheet.createRow(index);
            row.setHeight((short) 1200);
            for (int i = 0; i < secondTitles.length; i++) {

                createCellAndSetStrVal(row, i, thirStyle, secondTitles[i]);
            }
            index++;

            List<LinkedList<String>> newlist = new ArrayList<LinkedList<String>>();
            int num = m * mus;
            for (int x = num; x < num + mus; x++) {
                if (x >= list.size()) {
                    break;
                }
                newlist.add(list.get(x));
            }
            for (LinkedList<String> set : newlist) {
                row = sheet.createRow(index);
                row.setHeight((short) 400);
                createCellAndSetNumberVal(row, 0, contentStyle, String.valueOf(index - 2));
                Iterator it = set.iterator();
                int j = 1;
                while (it.hasNext()) {
                    String content = (String) it.next();

                    if (content != null) {
                        createCellAndSetStrVal(row, j++, contentStyle, content);
                    } else {
                        createCellAndSetStrVal(row, j++, contentStyle, "");
                    }
                }
                index++;
            }
            for (int i = 0; i < secondTitles.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        return book;
    }

    /**
     * 创建excel
     *
     * @param filename
     * @param title
     * @param date
     * @param secondTitles
     * @param list
     * @param request
     * @param response
     * @return
     */
    public static HSSFWorkbook createExcel(String filename, String title, String[] secondTitles,
                                           List<LinkedList<String>> list, HttpServletRequest request, HttpServletResponse response) {

        HSSFWorkbook book = new HSSFWorkbook();
        LOGGER.info("list size:" + list.size());
        int totle = list.size();// 获取List集合的size
        int mus = 65530;// 每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        for (int m = 0; m < avg + 1; m++) {
            HSSFSheet sheet = book.createSheet(title + m);
            sheet.setDisplayGridlines(true);
            // 定义各种样式
            HSSFCellStyle firStyle = createMyCellStyle(book, 18, true, true, true, null);// 大标题
            // 第一行样式
            // HSSFCellStyle secStyle = createMyCellStyle(book, 12, true, false,
            // true, null);// 中标题
            // 第二、三行样式
            HSSFCellStyle thirStyle = createMyCellStyle(book, 10, true, true, true, null);// 小标题
            // 第四行样式
            HSSFCellStyle contentStyle = createMyCellStyle(book, 10, false, true, true, "宋体");// 内容

            HSSFRow row = null;
            // 第一行
            int index = 0;
            @SuppressWarnings("unused")
            SimpleDateFormat ss = new SimpleDateFormat("yyyy年MM月dd日");
            row = sheet.createRow(index);
            row.setHeight((short) 600);
            createCellAndSetStrVal(row, 0, firStyle, title);
            sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 0, (short) (secondTitles.length - 1)));
            LOGGER.info("生成大标题");
            index++;

            // 第三行
            row = sheet.createRow(index);
            row.setHeight((short) 1200);
            for (int i = 0; i < secondTitles.length; i++) {

                createCellAndSetStrVal(row, i, thirStyle, secondTitles[i]);
            }
            index++;

            List<LinkedList<String>> newlist = new ArrayList<LinkedList<String>>();
            int num = m * mus;
            for (int x = num; x < num + mus; x++) {
                if (x >= list.size()) {
                    break;
                }
                newlist.add(list.get(x));
            }
            for (LinkedList<String> set : newlist) {
                row = sheet.createRow(index);
                row.setHeight((short) 400);
                createCellAndSetNumberVal(row, 0, contentStyle, String.valueOf(index - 2));
                Iterator it = set.iterator();
                int j = 1;
                while (it.hasNext()) {
                    String content = (String) it.next();

                    if (content != null) {
                        createCellAndSetStrVal(row, j++, contentStyle, content);
                    } else {
                        createCellAndSetStrVal(row, j++, contentStyle, "");
                    }
                }
                index++;
            }
            for (int i = 0; i < secondTitles.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        export(filename, book, request, response);
        return book;
    }

    /**
     * 创建单元格式样
     *
     * @param workbook
     * @param fontHeight字号
     * @param isBold是否粗体
     * @param isAlignCenter是否水平居中
     * @param isVerticalCenter是否垂直居中
     * @param fontName字体名称
     * @return
     */
    private static HSSFCellStyle createMyCellStyle(HSSFWorkbook workbook, int fontHeight, boolean isBold,
                                                   boolean isAlignCenter, boolean isVerticalCenter, String fontName) {
        // 设置字体和样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        if (isAlignCenter) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
        }
        if (isVerticalCenter) {
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        }
        HSSFFont font = workbook.createFont();
        if (fontName != null) {
            font.setFontName(fontName);
        } else {
            font.setFontName("微软雅黑");
        }
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) fontHeight);
        if (isBold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        style.setFont(font);
        return style;
    }

    /**
     * 创建单元格并写入数字
     *
     * @param row行
     * @param num列
     * @param style单元格式样
     * @param value单元格内容
     */
    private static void createCellAndSetNumberVal(HSSFRow row, int num, HSSFCellStyle style, String value) {
        HSSFCell cell = row.createCell(num);
        if (style != null) {
            cell.setCellStyle(style);
        }
        if (StringUtils.isNotBlank(value)) {
            cell.setCellValue(Double.parseDouble(value));
        }
    }

    /**
     * 创建单元格并写入文本内容
     *
     * @param row行
     * @param num列
     * @param style单元格式样
     * @param value单元格内容
     */
    private static void createCellAndSetStrVal(HSSFRow row, int num, HSSFCellStyle style, String value) {
        // HSSFCell cell = row.createCell((short) num);
        HSSFCell cell = row.createCell(num);
        if (style != null) {
            cell.setCellStyle(style);
        }
        if (StringUtils.isNotEmpty(value)) {
            HSSFRichTextString richstr = new HSSFRichTextString(value);
            cell.setCellValue(richstr);
        }
    }

    public static void export(String filename, HSSFWorkbook book, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            // 输出流导出
            OutputStream os;
            String agent = request.getHeader("User-Agent").toLowerCase();
            if (agent.indexOf("firefox") > -1) {
                filename = new String(filename.getBytes(), "ISO8859-1");// firefox浏览器
            } else if (agent.indexOf("msie") > -1) {
                filename = URLEncoder.encode(filename, "UTF-8");// IE浏览器
            } else if (agent.indexOf("chrome") > -1) {
                filename = URLEncoder.encode(filename, "UTF-8");// chrome谷歌
            } else {
                filename = URLEncoder.encode(filename, "UTF-8");// 其他（包括360）
            }
            response.reset();
            response.setContentType("application/msexcel");
            response.setHeader("Content-Disposition", "attachment;" + " filename=" + filename);
            os = response.getOutputStream();

            book.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }


    /**
     * 设置颜色
     *
     * @param differentStyle
     * @return
     */
    public static HSSFCellStyle setDifferentStyle(HSSFCellStyle differentStyle) {
        differentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        differentStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        return differentStyle;
    }


    /**
     * 创建FD运价比较Excel
     *
     * @param title
     * @param secondTitles
     * @param databaseList
     * @param redisList
     * @param booleanList
     * @return
     */
    public static HSSFWorkbook createFdDifferenceExcel(String title, String[] secondTitles, List<LinkedList<String>> databaseList,
                                                       List<LinkedList<String>> redisList, List<LinkedList<Boolean>> booleanList) {
        HSSFWorkbook book = new HSSFWorkbook();
        LOGGER.info("differrenceList size:" + databaseList.size() + redisList.size());
        int totle = databaseList.size() + redisList.size();// 获取List集合的size
        int mus = 65520;// 每个工作表格最多存储条数据（注：excel表格一个工作表可以存储65536条）
        //这里的avg是需要有几个表单，每个表单中的数量为65530.
        int avg = totle / mus;
        //超过一个表单最大量，进行创建新的表单
        System.out.println("avg + 1" + avg + 1);
        for (int sheetPage = 0; sheetPage < avg + 1; sheetPage++) {
            System.out.println("sheetPage" + sheetPage);
            HSSFSheet sheet = book.createSheet(title + sheetPage);
            sheet.setDisplayGridlines(true);
            // 定义各种样式
            HSSFCellStyle firStyle = createMyCellStyle(book, 18, true, true, true, null);// 大标题
            // 第一行样式
            HSSFCellStyle thirStyle = createMyCellStyle(book, 10, true, true, true, null);// 小标题
            // 第四行样式--这里就是展示数据的样式
            HSSFCellStyle contentStyle = createMyCellStyle(book, 10, false, true, true, "宋体");// 内容
            //不同的样式，设置颜色
            HSSFCellStyle differentStyle = setDifferentStyle(createMyCellStyle(book, 10, false, true, true, "宋体"));// 不同的内容
            HSSFRow row = null;
            // 第一行
            //这里的index是表明单元格逻辑上的第几行
            int index = 0;
            @SuppressWarnings("unused")
            SimpleDateFormat ss = new SimpleDateFormat("yyyy年MM月dd日");
            row = sheet.createRow(index);
            row.setHeight((short) 600);
            createCellAndSetStrVal(row, 0, firStyle, title);
            sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 0, (short) (secondTitles.length - 1)));
            LOGGER.info("生成大标题");
            index++;

            // 第三行-表头
            row = sheet.createRow(index);
            row.setHeight((short) 1200);
            for (int i = 0; i < secondTitles.length; i++) {

                createCellAndSetStrVal(row, i, thirStyle, secondTitles[i]);
            }
            index++;
            // 计算每个sheet写入的内容
            List<LinkedList<String>> newlist = new ArrayList<>();
            //循环总体的表单数量*每个表单中的最大数量,一个表单中的数量大于65536的时候阻断。
            //每页中的最大数量
            int end = (sheetPage + 1) * mus / 2;

            if (end > databaseList.size()) {
                end = databaseList.size();
            }

            int start = (sheetPage) * mus / 2;
            int rowStart = 0;
            // 循环每一行数据
            for (int i = start; i < end; i++) {
                LinkedList<String> colDatabase = databaseList.get(i);
                LinkedList<String> colRedis = redisList.get(i);
                LinkedList<Boolean> colBoolean = booleanList.get(i);

                //创建database的数据
                //在表格中的第index行进行创建单元格
                row = sheet.createRow(index);
                row.setHeight((short) 400);
                //循环每一列进行塞值和样式
                createCellAndSetStrVal(row, 0, contentStyle, String.valueOf(i + 1));
                createCellAndSetStrVal(row, 1, contentStyle, "数据库");
                for (int j = 0; j < colDatabase.size(); j++) {
                    String contentDatabase = colDatabase.get(j) == null ? "" : colDatabase.get(j);
                    //这里的j+1分别是对应的行创建
                    //row是创建一个单元行，第二个参数是表明要创建的列数，然后往每个列中进行塞值和样式
                    if (colBoolean.get(j) != null && colBoolean.get(j)) {
                        createCellAndSetStrVal(row, j + 2, contentStyle, contentDatabase);
                    } else {
                        createCellAndSetStrVal(row, j + 2, differentStyle, contentDatabase);
                    }
                }
                index++;
                //创建redis的数据
                row = sheet.createRow(index);
                row.setHeight((short) 400);
                createCellAndSetStrVal(row, 1, contentStyle, "缓存");
                for (int j = 0; j < colRedis.size(); j++) {
                    String contentRedis = colRedis.get(j) == null ? "" : colRedis.get(j);
                    //这里的j+1分别是对应的行创建
                    //row是创建一个单元行，第二个参数是表明要创建的列数，然后往每个列中进行塞值和样式
                    if (colBoolean.get(j) != null && colBoolean.get(j)) {
                        createCellAndSetStrVal(row, j + 2, contentStyle, contentRedis);
                    } else {
                        createCellAndSetStrVal(row, j + 2, differentStyle, contentRedis);
                    }
                }
                index++;
                //合并第二行
                sheet.addMergedRegion(new CellRangeAddress((rowStart + 1) * 2, (rowStart + 1) * 2 + 1, 0, 0));
                //合并第二行
                sheet.addMergedRegion(new CellRangeAddress((rowStart + 1) * 2, (rowStart + 1) * 2 + 1, 15, 15));
                rowStart++;
            }
        }
        return book;
    }

    /**
     * 创建配送信息Excel
     *
     * @param title
     * @param secondTitles
     * @param list
     * @return
     */
    public static HSSFWorkbook createDistributionExcel(String title, String[] secondTitles,
                                                       List<LinkedList<String>> list, List<Map<String, Object>> companyList) {

        HSSFWorkbook book = new HSSFWorkbook();
        LOGGER.info("list size:" + list.size());
        int totle = list.size();// 获取List集合的size
        int mus = 65530;// 每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        for (int m = 0; m < avg + 1; m++) {
            HSSFSheet sheet = book.createSheet(title + m);
            sheet.setDisplayGridlines(true);
            // 定义各种样式
            HSSFCellStyle firStyle = createMyCellStyle(book, 18, true, true, true, null);// 大标题
            // 第一行样式
            HSSFCellStyle thirStyle = createMyCellStyle(book, 10, true, true, true, null);// 小标题
            // 第四行样式
            HSSFCellStyle contentStyle = createMyCellStyle(book, 10, false, true, true, "宋体");// 内容

            HSSFRow row = null;
            // 第一行
            int index = 0;
            @SuppressWarnings("unused")
            SimpleDateFormat ss = new SimpleDateFormat("yyyy年MM月dd日");
            row = sheet.createRow(index);
            row.setHeight((short) 600);
            createCellAndSetStrVal(row, 0, firStyle, title);
            sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 0, (short) (secondTitles.length - 1)));
            LOGGER.info("生成大标题");
            index++;

            // 第三行-表头
            row = sheet.createRow(index);
            row.setHeight((short) 1200);
            for (int i = 0; i < secondTitles.length; i++) {

                createCellAndSetStrVal(row, i, thirStyle, secondTitles[i]);
            }
            index++;
            // 计算每个sheet写入的内容
            List<LinkedList<String>> newlist = new ArrayList<LinkedList<String>>();
            int num = m * mus;
            for (int x = num; x < num + mus; x++) {
                if (x >= list.size()) {
                    break;
                }
                newlist.add(list.get(x));
            }
            // 写内容
            int start = 2;// 合并开始行号
            int end = 2;// 合并结束行号
            int seq = 1;// 序号
            for (int i = 0; i < newlist.size(); i++) {
                LinkedList<String> set = newlist.get(i);
                row = sheet.createRow(index);
                row.setHeight((short) 400);

                for (int j = 0; j < set.size(); j++) {
                    String content = set.get(j) == null ? "" : set.get(j);
                    createCellAndSetStrVal(row, j + 1, contentStyle, content);
                }
                // 合并判断
                boolean flag = false;
                if (i == newlist.size() - 1) {// 最后一个
                    flag = true;
                } else {
                    String distributionId = set.get(5);
                    String nextDistributionId = newlist.get(i + 1).get(5);
                    if (!distributionId.equals(nextDistributionId)) {
                        flag = true;
                    }
                }
                // 合并
                if (flag) {
                    if (start != end) {
                        // 合并序号
                        for (int j = 0; j < set.size() + 1; j++) {// 0为合并序号，set对应从1开始
                            if (j != 1 && j != 2 && j != 3 && j != 4 && j != 5) {
                                sheet.addMergedRegion(new CellRangeAddress(start, end, j, j));
                            }
                        }
                    }
                    // 序号
                    HSSFRow seqrow = sheet.getRow(start);
                    createCellAndSetNumberVal(seqrow, 0, contentStyle, String.valueOf(seq++));
                    // 数据初始化，下次合并做准备
                    start = index + 1;
                    end = index + 1;
                } else {
                    end++;
                }
                index++;
            }
            // 附表：快递公司信息
            row = sheet.createRow(index);
            row.setHeight((short) 600);
            createCellAndSetStrVal(row, 0, firStyle, "附表：快递公司信息");
            createCellAndSetStrVal(row, 4, firStyle, "附表：快递公司信息");
            sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 0, (short) 4));
            index++;
            // 快递公司列表
            for (int i = 0; i < companyList.size(); i++) {
                Map<String, Object> map = companyList.get(i);
                row = sheet.createRow(index);
                row.setHeight((short) 400);

                createCellAndSetStrVal(row, 0, contentStyle, String.valueOf(map.get("id")));
                String express_company_name = String.valueOf(map.get("express_company_name"));
                createCellAndSetStrVal(row, 1, contentStyle, express_company_name);
                createCellAndSetStrVal(row, 2, contentStyle, express_company_name);
                createCellAndSetStrVal(row, 3, contentStyle, express_company_name);
                createCellAndSetStrVal(row, 4, contentStyle, express_company_name);
                sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 1, (short) 4));

                index++;
            }
            // 自动设宽
            for (int i = 0; i < secondTitles.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        return book;
    }

    public static void downFile(String path, String filename, HttpServletRequest request,
                                HttpServletResponse response) {
        try {

            File file = new File(path);// path
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            String agent = request.getHeader("User-Agent").toLowerCase();
            if (agent.indexOf("firefox") > -1) {
                filename = new String(filename.getBytes(), "ISO8859-1");// firefox浏览器
            } else if (agent.indexOf("msie") > -1) {
                filename = URLEncoder.encode(filename, "UTF-8");// IE浏览器
            } else if (agent.indexOf("chrome") > -1) {
                filename = URLEncoder.encode(filename, "UTF-8");// chrome谷歌
            } else {
                filename = URLEncoder.encode(filename, "UTF-8");// 其他（包括360）
            }
            response.reset();
            // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);// 输出文件
            os.flush();
            os.close();

        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }

    /**
     * 创建并导出excel
     *
     * @param filename
     * @param title
     * @param secondTitles
     * @param list
     * @param request
     * @param response
     * @return
     */
    public static HSSFWorkbook createExcelToExport(String filename, String title, String[] secondTitles, String[] secondDes,
                                                   List<LinkedList<String>> list, HttpServletRequest request, HttpServletResponse response) {

        HSSFWorkbook book = new HSSFWorkbook();
        LOGGER.info("list size:" + list.size());
        int totle = list.size();// 获取List集合的size
        int mus = 65530;// 每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        for (int m = 0; m < avg + 1; m++) {
            HSSFSheet sheet = book.createSheet(title + m);
            sheet.setDisplayGridlines(true);
            // 定义各种样式
            HSSFCellStyle firStyle = createMyCellStyle(book, 18, true, true, true, null);// 大标题
            // 第一行样式
            // HSSFCellStyle secStyle = createMyCellStyle(book, 12, true, false,
            // true, null);// 中标题
            // 第二、三行样式
            HSSFCellStyle secondStyle = createMyCellStyle(book, 10, true, true, true, null);// 小标题
            HSSFCellStyle thirStyle = createMyCellStyle(book, 8, false, true, true, null);// 小标题
            // 第四行样式
            HSSFCellStyle contentStyle = createMyCellStyle(book, 10, false, true, true, "宋体");// 内容

            HSSFRow row = null;
            // 第一行
            int index = 0;
            @SuppressWarnings("unused")
            SimpleDateFormat ss = new SimpleDateFormat("yyyy年MM月dd日");
            row = sheet.createRow(index);
            row.setHeight((short) 600);
            createCellAndSetStrVal(row, 0, firStyle, title);
            sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 0, (short) (secondTitles.length - 1)));
            LOGGER.info("生成大标题");
            index++;

            // 第二行
            row = sheet.createRow(index);
            row.setHeight((short) 1200);
            for (int i = 0; i < secondTitles.length; i++) {

                createCellAndSetStrVal(row, i, secondStyle, secondTitles[i]);
            }
            index++;

            // 第三行
            row = sheet.createRow(index);
            row.setHeight((short) 1200);
            for (int i = 0; i < secondDes.length; i++) {
                thirStyle.setWrapText(true);
                createCellAndSetStrVal(row, i, thirStyle, secondDes[i]);
            }
            index++;


            List<LinkedList<String>> newlist = new ArrayList<LinkedList<String>>();
            int num = m * mus;
            for (int x = num; x < num + mus; x++) {
                if (x >= list.size()) {
                    break;
                }
                newlist.add(list.get(x));
            }
            LOGGER.info(JacksonUtil.obj2json(newlist));
            for (LinkedList<String> set : newlist) {
                row = sheet.createRow(index);
                row.setHeight((short) 400);
                //createCellAndSetNumberVal(row, 0, contentStyle, String.valueOf(index - 2));
                Iterator it = set.iterator();
                int j = 0;
                while (it.hasNext()) {
                    String content = (String) it.next();

                    if (content != null) {
                        createCellAndSetStrVal(row, j++, contentStyle, content);
                    } else {
                        createCellAndSetStrVal(row, j++, contentStyle, "");
                    }
                }
                index++;
            }
            for (int i = 0; i < secondTitles.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        export(filename, book, request, response);
        return book;
    }

    /**
     * 通用导出
     *
     * @param title
     * @param secondTitles
     * @param list
     * @return
     */
    public static HSSFWorkbook createExcel(String title, String[] secondTitles, List<LinkedList<String>> list) {
        HSSFWorkbook book = new HSSFWorkbook();
        int totle = list.size();// 获取List集合的size
        int mus = 65530;// 每个工作表格最多存储2条数据（注：excel表格一个工作表可以存储65536条）
        int avg = totle / mus;
        for (int m = 0; m < avg + 1; m++) {
            HSSFSheet sheet = book.createSheet(title + m);
            sheet.setDisplayGridlines(true);
            // 定义各种样式
            HSSFCellStyle firStyle = createMyCellStyle(book, 18, true, true, true, null);// 大标题
            // 第一行样式
            HSSFCellStyle thirStyle = createMyCellStyle(book, 10, true, true, true, null);// 小标题
            // 第四行样式
            HSSFCellStyle contentStyle = createMyCellStyle(book, 10, false, true, true, "宋体");// 内容

            HSSFRow row = null;
            // 第一行
            int index = 0;
            @SuppressWarnings("unused")
            SimpleDateFormat ss = new SimpleDateFormat("yyyy年MM月dd日");
            row = sheet.createRow(index);
            row.setHeight((short) 600);
            createCellAndSetStrVal(row, 0, firStyle, title);
            sheet.addMergedRegion(new CellRangeAddress(index, index, (short) 0, (short) (secondTitles.length - 1)));
            index++;

            // 第三行-表头
            row = sheet.createRow(index);
            row.setHeight((short) 1200);
            for (int i = 0; i < secondTitles.length; i++) {
                createCellAndSetStrVal(row, i, thirStyle, secondTitles[i]);
            }
            index++;
            // 计算每个sheet写入的内容
            List<List<String>> newlist = new ArrayList<>();
            int num = m * mus;
            for (int x = num; x < num + mus; x++) {
                if (x >= list.size()) {
                    break;
                }
                newlist.add(list.get(x));
            }
            // 写内容
            int start = 2;// 合并开始行号
            int end = 2;// 合并结束行号
            int seq = 1;// 序号
            for (int i = 0; i < newlist.size(); i++) {
                List<String> set = newlist.get(i);
                row = sheet.createRow(index);
                row.setHeight((short) 400);

                for (int j = 0; j < set.size(); j++) {
                    String content = set.get(j) == null ? "" : set.get(j);
                    createCellAndSetStrVal(row, j + 1, contentStyle, content);
                }
                // 合并判断
                HSSFRow seqrow = sheet.getRow(start++);
                createCellAndSetNumberVal(seqrow, 0, contentStyle, String.valueOf(seq++));
                index++;
            }
            // 自动设宽
            for (int i = 0; i < secondTitles.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        return book;
    }
}
