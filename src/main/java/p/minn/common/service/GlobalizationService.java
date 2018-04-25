package p.minn.common.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import p.minn.common.entity.Globalization;
import p.minn.common.entity.IdEntity;
import p.minn.common.repository.GlobalizationDao;
import p.minn.common.utils.Page;
import p.minn.common.utils.UtilCommon;
import p.minn.vo.MyUserDetails;

/**
 * @author minn
 * @QQ:3942986006
 *
 */
@Service
public class GlobalizationService {

	
	@Autowired
	private GlobalizationDao globalizationDao;
	
	
	
	/**
	 * 分页查询
	 * @param lang
	 * @return
	 * @throws Exception 
	 */
	public Page query(String messageBody,String lang) throws Exception{
		
		Page page=(Page) UtilCommon.gson2T(messageBody, Page.class);
		Map<String,String> condition=UtilCommon.getCondition(page);
		List<Map<String,Object>> list=globalizationDao.query(lang,null,condition);
	    page.setResult(list);
	
		return page;
	} 
	
	public void update(MyUserDetails user,String messageBody,String lang){
		Globalization globalization=(Globalization) UtilCommon.gson2T(messageBody,Globalization.class);
		globalization.setUpdateid(user.getId());
		globalizationDao.update(globalization);
	}
	
	public void delete(String messageBody){
		IdEntity idEntity=(IdEntity) UtilCommon.gson2T(messageBody,IdEntity.class);
		globalizationDao.delete(idEntity);
	}

	public void save(MyUserDetails user,String messageBody) {
		// TODO Auto-generated method stub
		Globalization globalization=(Globalization) UtilCommon.gson2T(messageBody,Globalization.class);
		globalization.setCreateid(user.getId());
		globalizationDao.save(globalization);
	}

}
