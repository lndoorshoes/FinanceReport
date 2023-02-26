package lndoor.groups;

import lndoor.stock.Items;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndustryInfos {
	private String stockListSortType;
	private List<Items> groups;
	private int totalCount;
	private int page;
	private int pageSize;
}
