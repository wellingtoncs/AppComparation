package com.wcs.compare.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wcs.compare.entity.AreaEntity;

@Repository
public interface AreaRepository extends CrudRepository<AreaEntity, Long>  {

	/**
	 * 
	 * Find the area by ID.
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param id
	 * @return AreaEntity
	 */
	AreaEntity findById(long id);
}