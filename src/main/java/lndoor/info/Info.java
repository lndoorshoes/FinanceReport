package lndoor.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Info {
	String stockListSortType;
	private int totalCount;
	private int page;
	private int pageSize;
}
