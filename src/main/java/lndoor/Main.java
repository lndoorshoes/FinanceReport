package lndoor;

import lndoor.groups.IndustryInfos;
import lndoor.stock.StockInfos;
import lndoor.stock.ThemeInfos;
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
        StockInfos kospiHigh52Week = StockUtils.setStockInfos(StockUtils.HIGH_KOSPI);
        StockInfos kospiLow52Week = StockUtils.setStockInfos(StockUtils.LOW_KOSPI);
        StockInfos kosdaqHigh52Week = StockUtils.setStockInfos(StockUtils.HIGH_KOSDAQ);
        StockInfos kosdaqLow52Week = StockUtils.setStockInfos(StockUtils.LOW_KOSDAQ);

        // Contents
        List<StockInfos> allStockInfos = new ArrayList<>();
        allStockInfos.add(kospiHigh52Week);
        allStockInfos.add(kospiLow52Week);
        allStockInfos.add(kosdaqHigh52Week);
        allStockInfos.add(kosdaqLow52Week);
        List<String[]> contentsHighLowSheet = PoiCSVUtils.makeStockCSVContents(allStockInfos);

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


        IndustryInfos industryInfos = IndustryUtils.setIndustryInfos(IndustryUtils.INDUSTRY);
        List<String[]> contentsIndustryCSV = PoiCSVUtils.makeIndustryCSVContents(industryInfos);

        List<String[]> industrySheet = Stream.concat(initialIndustryCSV.stream(), contentsIndustryCSV.stream()).collect(Collectors.toList());

        resultCSV.add(industrySheet);





        // 테마
        // https://m.stock.naver.com/api/stocks/theme?page=1

        List<String[]> initialThemeCSV = new ArrayList<>();
        String[] themeTopHeadline = {LocalDateTime.now().toString() + " 테마별 현황"};
        String[] themeType = {"이름", "상승률"};
        initialThemeCSV.add(themeTopHeadline);
        initialThemeCSV.add(themeType);

        ThemeInfos themeInfos = ThemeUtils.setThemeInfos(ThemeUtils.THEME);

        List<String[]> contentsThemeCSV = PoiCSVUtils.makeThemeCSVContents(themeInfos);

        List<String[]> themeSheet = Stream.concat(initialThemeCSV.stream(), contentsThemeCSV.stream()).collect(Collectors.toList());

        resultCSV.add(themeSheet);




        PoiCSVUtils.writeCSV(resultCSV);

        // CSV beautify
        // merge some cells
        // 서체, coloring




    }
}
