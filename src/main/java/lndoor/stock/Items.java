package lndoor.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Items {
	public int no;
	public String name;
	public int totalCount;
	public String changeRate;
	public int riseCount;
	public int fallCount;
	public int steadyCount;
}
