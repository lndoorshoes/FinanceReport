package lndoor.utils;

import lndoor.groups.IndustryInfos;
import lndoor.stock.Items;
import lndoor.stock.StockInfos;
import lndoor.stock.ThemeInfos;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PoiCSVUtils {
	private static final String REPORT_PATH = "./";
	private static final String REPORT_NAME = "output.xlsx";
	private static final String[] REPORT_CONTENT = {"52w_High_Low", "Industry", "Theme"};

	public static void writeCSV(List<List<String[]>> dataList) {
		// 빈 Workbook 생성
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Center style
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);

		// 빈 Sheet를 생성
		for(int i = 0; i < REPORT_CONTENT.length; i++) {
			XSSFSheet sheet = workbook.createSheet(REPORT_CONTENT[i]);
			List<String[]> row_content = dataList.get(i);

			int maxColumnWidth = 0;
			for(int j = 0; j < row_content.size(); j++) {
				Row row = sheet.createRow(j);
				String[] cell_content = row_content.get(j);
				for(int k = 0; k < cell_content.length; k++) {
					Cell cell = row.createCell(k);
					cell.setCellStyle(style);
					cell.setCellValue(cell_content[k]);
					if(j == row_content.size() - 1) {
						sheet.autoSizeColumn(k);
						maxColumnWidth = Math.max(maxColumnWidth, sheet.getColumnWidth(k));
						sheet.setColumnWidth(k, maxColumnWidth);
					}
				}
			}
		}

		List<Integer> row_size = new ArrayList<>();
		for(int i = 0; i < dataList.size(); i++) {
			row_size.add(dataList.get(i).size());
		}
		workbook = beautifyCSV(workbook, row_size);
		try {
			FileOutputStream out = new FileOutputStream(new File(REPORT_PATH, REPORT_NAME));
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String[]> makeStockCSVContents(List<StockInfos> allStockInfos) {
		List<String[]> result = new ArrayList<>();

		int maxSize = 0;
		int columnSize = allStockInfos.size();
		for (int i = 0; i < allStockInfos.size(); i++) {
			maxSize = Math.max(maxSize, allStockInfos.get(i).getTotalCount());
		}

		for (int i = 0; i < maxSize; i++) {
			String[] rowData = new String[columnSize];
			for (int j = 0; j < allStockInfos.size(); j++) {
				if (i >= allStockInfos.get(j).getStocks().size()) {
					rowData[j] = "";
				} else {
					rowData[j] = allStockInfos.get(j).getStocks().get(i).getStockName();
				}
			}
			result.add(rowData);
		}

		return result;
	}

	public static List<String[]> makeIndustryCSVContents(IndustryInfos industryInfos) {
		List<String[]> result = new ArrayList<>();
		List<Items> groups = industryInfos.getGroups();
		int groupSize = groups.size();

		for (int i = 0; i < groupSize; i++) {
			String[] rowData = new String[2];
			rowData[0] = groups.get(i).getName();
			rowData[1] = groups.get(i).getChangeRate();
			result.add(rowData);
		}

		return result;
	}

	public static List<String[]> makeThemeCSVContents(ThemeInfos themeInfos) {
		List<String[]> result = new ArrayList<>();
		List<Items> groups = themeInfos.getGroups();
		int groupSize = groups.size();

		for (int i = 0; i < groupSize; i++) {
			String[] rowData = new String[2];
			rowData[0] = groups.get(i).getName();
			rowData[1] = groups.get(i).getChangeRate();
			result.add(rowData);
		}

		return result;
	}

	public static XSSFWorkbook beautifyCSV(XSSFWorkbook workbook, List<Integer> row_size) {
		// sheet1
		CellStyle headline = workbook.createCellStyle();
		headline.setAlignment(HorizontalAlignment.CENTER);
		headline.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headline.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headline.setBorderBottom(BorderStyle.THIN);
		headline.setBorderTop(BorderStyle.THIN);
		headline.setBorderRight(BorderStyle.THIN);
		headline.setBorderLeft(BorderStyle.THIN);

		CellStyle border_right = workbook.createCellStyle();
		border_right.setAlignment(HorizontalAlignment.CENTER);
		border_right.setBorderRight(BorderStyle.THIN);

		CellStyle border_bottom = workbook.createCellStyle();
		border_bottom.setAlignment(HorizontalAlignment.CENTER);
		border_bottom.setBorderBottom(BorderStyle.THIN);

		CellStyle border_right_bottom = workbook.createCellStyle();
		border_right_bottom.setAlignment(HorizontalAlignment.CENTER);
		border_right_bottom.setBorderRight(BorderStyle.THIN);
		border_right_bottom.setBorderBottom(BorderStyle.THIN);

		Sheet sheet1 = workbook.getSheetAt(0);
		for(int i = 1; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				sheet1.getRow(i).getCell(j).setCellStyle(headline);
			}
		}
		sheet1.addMergedRegion(CellRangeAddress.valueOf("A2:B2"));
		sheet1.addMergedRegion(CellRangeAddress.valueOf("C2:D2"));

		for(int i = 3; i < row_size.get(0); i++) {
			sheet1.getRow(i).getCell(1).setCellStyle(border_right);
			sheet1.getRow(i).getCell(3).setCellStyle(border_right);
		}
		for(int i = 0; i < 4; i++) {
			if(i % 2 == 0) {
				sheet1.getRow(row_size.get(0) - 1).getCell(i).setCellStyle(border_bottom);
			} else {
				sheet1.getRow(row_size.get(0) - 1).getCell(i).setCellStyle(border_right_bottom);
			}
		}

		// sheet2, 3
		for(int n = 1; n < 3; n++) {
			Sheet sheetn = workbook.getSheetAt(n);
			for (int i = 1; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					sheetn.getRow(i).getCell(j).setCellStyle(headline);
				}
			}

			for (int i = 2; i < row_size.get(n); i++) {
				sheetn.getRow(i).getCell(0).setCellStyle(border_right);
				sheetn.getRow(i).getCell(1).setCellStyle(border_right);
			}
			for (int i = 0; i < 2; i++) {
				sheetn.getRow(row_size.get(n) - 1).getCell(i).setCellStyle(border_right_bottom);
			}
		}

		return workbook;
	}
}
