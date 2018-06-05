package cn.jxufe.dao;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.jxufe.view.LandView;
public interface LandViewDAO extends PagingAndSortingRepository<LandView, Long> {
	public LandView findByLandId(int landId);
}