package lndoor.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThemeInfos {
	private String stockListSortType;
	private List<Items> groups;
	private int totalCount;
	private int page;
	private int pageSize;
}
