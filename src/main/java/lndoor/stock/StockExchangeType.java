package lndoor.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockExchangeType{
	private String code;
	private String zoneId;
	private String nationType;
	private int delayTime;
	private String startTime;
	private String endTime;
	private String closePriceSendTime;
	private String nameKor;
	private String nameEng;
	private String nationCode;
	private String nationName;
	private String name;
}