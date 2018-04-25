package p.minn.common.baseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import p.minn.common.annotation.LogAnnotation;
import p.minn.common.utils.Page;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @omment 
 */
public interface BaseDao<T,T1> {

	@LogAnnotation(resourceKey="common_edit")
	public abstract  void update(T t) ;

	@LogAnnotation(resourceKey="common_del")
	public abstract void delete(T1 t);
	
	@LogAnnotation(resourceKey="common_add")
	public abstract void save(T t);
	
	public T findEntityById(T1 t);
	
	public abstract int getTotal(@Param("lang")  String lang,@Param("condition")  Map<String,String> condition);
	
	public abstract List<Map<String, Object>> query(@Param("lang")  String lang, @Param("page") Page page,
			@Param("condition") Map<String,String> condition);
}
