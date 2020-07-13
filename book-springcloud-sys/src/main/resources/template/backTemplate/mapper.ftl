package ${basePackage}.dao.mapper;

import java.util.List;
import java.util.Map;

import com.elling.common.base.MyMapper;
import ${basePackage}.model.${modelNameUpperCamel};

/**
 *
 * Created by ${author} on ${date}.
 */
public interface ${modelNameUpperCamel}Mapper extends MyMapper<${modelNameUpperCamel}> {
	/**
	 *	根据自定义条件查询
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> getByCondition(${modelNameUpperCamel} ${modelNameLowerCamel});
}