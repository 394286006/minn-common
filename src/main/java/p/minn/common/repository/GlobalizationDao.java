package p.minn.common.repository;

import org.apache.ibatis.annotations.Param;

import p.minn.common.annotation.LogAnnotation;
import p.minn.common.baseDao.BaseDao;
import p.minn.common.entity.Globalization;
import p.minn.common.entity.IdEntity;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 
 */
public interface GlobalizationDao extends BaseDao<Globalization,IdEntity>{

	@LogAnnotation(resourceKey="common_del")
	public void deleteByTableId(@Param("tableid") String tableid,@Param("tablename") String tablename);
	
	//@LogAnnotation(resourceKey="common_del")
    public void updateByTable(Globalization globalization);
}