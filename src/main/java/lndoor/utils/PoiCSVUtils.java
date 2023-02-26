package lndoor.utils;

import lndoor.groups.IndustryInfos;
import lndoor.stock.Items;
import lndoor.stock.StockInfos;
import lndoor.stock.ThemeInfos;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
					cell.setCellValue(cell_content[k]);
					if(j == row_content.size() - 1) {
						sheet.autoSizeColumn(k);
						maxColumnWidth = Math.max(maxColumnWidth, sheet.getColumnWidth(k));
						sheet.setColumnWidth(k, maxColumnWidth);
					}
				}
			}
		}
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
}
