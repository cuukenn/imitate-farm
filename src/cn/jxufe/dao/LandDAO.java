package cn.jxufe.dao;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.jxufe.entity.Land;
public interface LandDAO extends PagingAndSortingRepository<Land, Long> {
}