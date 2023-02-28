package lndoor;

import lndoor.info.IndustryInfo;
import lndoor.info.StockInfo;
import lndoor.info.ThemeInfo;
import lndoor.utils.IndustryUtils;
import lndoor.utils.PoiCSVUtils;
import lndoor.utils.StockUtils;
import lndoor.utils.ThemeUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        // Head
        List<String[]> initialHighLowCSV = new ArrayList<>();
        String[] StockTopHeadline = {LocalDateTime.now().toString() + " 52주 신고가/신저가"};
        String[] StockCatecoryType = {"KOSPI", "", "KOSDAQ", ""};
        String[] StockSortType = {"신고가", "신저가", "신고가", "신저가"};
        initialHighLowCSV.add(StockTopHeadline);
        initialHighLowCSV.add(StockCatecoryType);
        initialHighLowCSV.add(StockSortType);

        // Contents Input Setting
        StockInfo kospiHigh52Week = StockUtils.setStockInfo(StockUtils.HIGH_KOSPI);
        StockInfo kospiLow52Week = StockUtils.setStockInfo(StockUtils.LOW_KOSPI);
        StockInfo kosdaqHigh52Week = StockUtils.setStockInfo(StockUtils.HIGH_KOSDAQ);
        StockInfo kosdaqLow52Week = StockUtils.setStockInfo(StockUtils.LOW_KOSDAQ);

        // Contents
        List<StockInfo> allStockInfo = new ArrayList<>();
        allStockInfo.add(kospiHigh52Week);
        allStockInfo.add(kospiLow52Week);
        allStockInfo.add(kosdaqHigh52Week);
        allStockInfo.add(kosdaqLow52Week);
        List<String[]> contentsHighLowSheet = PoiCSVUtils.makeStockCSVContents(allStockInfo);

        List<String[]> highLowSheet = Stream.concat(initialHighLowCSV.stream(), contentsHighLowSheet.stream()).collect(Collectors.toList());
        List<List<String[]>> resultCSV = new ArrayList<>();
        resultCSV.add(highLowSheet);


        // sheet 분리해서 제공하자
        // 업종
        List<String[]> initialIndustryCSV = new ArrayList<>();
        String[] industryTopHeadline = {LocalDateTime.now().toString() + " 업종별 현황"};
        String[] industryType = {"이름", "상승률"};
        initialIndustryCSV.add(industryTopHeadline);
        initialIndustryCSV.add(industryType);


        IndustryInfo industryInfo = IndustryUtils.setIndustryInfo(IndustryUtils.INDUSTRY);
        List<String[]> contentsIndustryCSV = PoiCSVUtils.makeIndustryCSVContents(industryInfo);

        List<String[]> industrySheet = Stream.concat(initialIndustryCSV.stream(), contentsIndustryCSV.stream()).collect(Collectors.toList());

        resultCSV.add(industrySheet);





        // 테마
        // https://m.stock.naver.com/api/stocks/theme?page=1

        List<String[]> initialThemeCSV = new ArrayList<>();
        String[] themeTopHeadline = {LocalDateTime.now().toString() + " 테마별 현황"};
        String[] themeType = {"이름", "상승률"};
        initialThemeCSV.add(themeTopHeadline);
        initialThemeCSV.add(themeType);

        ThemeInfo themeInfo = ThemeUtils.setThemeInfo(ThemeUtils.THEME);

        List<String[]> contentsThemeCSV = PoiCSVUtils.makeThemeCSVContents(themeInfo);

        List<String[]> themeSheet = Stream.concat(initialThemeCSV.stream(), contentsThemeCSV.stream()).collect(Collectors.toList());

        resultCSV.add(themeSheet);




        PoiCSVUtils.writeCSV(resultCSV);

        // CSV beautify
        // merge some cells
        // 서체, coloring




    }
}
