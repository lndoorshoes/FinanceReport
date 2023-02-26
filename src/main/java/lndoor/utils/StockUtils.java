package lndoor.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lndoor.stock.StockInfos;
import lndoor.stock.Stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StockUtils {
	public static final String HIGH_KOSPI = "high52week/KOSPI/";
	public static final String LOW_KOSPI = "low52week/KOSPI/";
	public static final String HIGH_KOSDAQ = "high52week/KOSDAQ/";
	public static final String LOW_KOSDAQ = "low52week/KOSDAQ/";
	public static final String HOST = "https://m.stock.naver.com/api/stocks/";

	// KOSPI 신고가: https://m.stock.naver.com/api/stocks/high52week/KOSPI?page=1
	// KOSPI 신저가: https://m.stock.naver.com/api/stocks/low52week/KOSPI?page=1
	// KOSDAQ 신고가: https://m.stock.naver.com/api/stocks/high52week/KOSDAQ?page=1
	// KOSDAQ 신저: https://m.stock.naver.com/api/stocks/low52week/KOSDAQ?page=1


	public static StockInfos setStockInfos(String type) throws IOException {
		StockInfos result = new StockInfos();
		int currentPage = 1;

		URL url = new URL(HOST + type + "?page=" + currentPage);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();

		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestMethod("GET");

		BufferedReader br = null;
		StringBuffer sb = null;
		String responseData = "";
		String responseBody = "";

		br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		sb = new StringBuffer();
		while ((responseData = br.readLine()) != null) {
			sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
		}

		//메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
		responseBody = sb.toString();

		//http 요청 응답 코드 확인 실시
		String responseCode = String.valueOf(conn.getResponseCode());

		ObjectMapper mapper = new ObjectMapper();
		result = mapper.readValue(responseBody, StockInfos.class);

		int total = result.getTotalCount();
		int pageSize = result.getPageSize();

		while (pageSize * currentPage < total) {
			currentPage++;
			url = new URL(HOST + type + "?page=" + currentPage);
			conn = (HttpURLConnection)url.openConnection();

			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestMethod("GET");

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			sb = new StringBuffer();
			while ((responseData = br.readLine()) != null) {
				sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
			}

			//메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
			responseBody = sb.toString();

			StockInfos subResult = mapper.readValue(responseBody, StockInfos.class);
			List<Stocks> mergeResult = result.getStocks();
			result.setStocks(Stream.concat(result.getStocks().stream(), subResult.getStocks().stream())
							.collect(Collectors.toList()));
		}

		return result;
	}
}
