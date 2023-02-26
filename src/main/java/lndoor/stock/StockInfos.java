package lndoor.stock;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInfos {
	private String stockListSortType;
	private String stockListCategoryType;
	private List<Stocks> stocks;
	private int totalCount;
	private int page;
	private int pageSize;

}
