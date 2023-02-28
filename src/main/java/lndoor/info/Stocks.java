package lndoor.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stocks {
	public String stockEndType;
	public String itemCode;
	public String reutersCode;
	public String stockName;
	public String sosok;
	public String closePrice;
	public String compareToPreviousClosePrice;
	public CompareToPreviousPrice compareToPreviousPrice;
	public String fluctuationsRatio;
	public String accumulatedTradingVolume;
	public String accumulatedTradingValue;
	public String accumulatedTradingValueKrwHangeul;
	public Date localTradedAt;
	public String marketValue;
	public String marketValueHangeul;
	public String nav;
	public String threeMonthEarningRate;
	public String marketStatus;
	public TradeStopType tradeStopType;
	public StockExchangeType stockExchangeType;
}
