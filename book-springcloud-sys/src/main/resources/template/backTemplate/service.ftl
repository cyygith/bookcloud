package ${basePackage}.service;
import java.util.List;
import java.util.Map;

import ${basePackage}.model.${modelNameUpperCamel};
import com.elling.common.base.Service;

/**
 *
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Service extends Service<${modelNameUpperCamel}> {
	
	/**
	 *	根据自定义条件查询
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getByCondition(${modelNameUpperCamel} ${modelNameLowerCamel});
}
