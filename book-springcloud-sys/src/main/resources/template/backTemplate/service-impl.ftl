package ${basePackage}.service.impl;

import java.util.List;
import java.util.Map;

import ${basePackage}.dao.mapper.${modelNameUpperCamel}Mapper;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.elling.common.base.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by ${author} on ${date}.
 */
@Service
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {

    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;
    
	@Override
	public List<Map<String,Object>> getByCondition(${modelNameUpperCamel} ${modelNameLowerCamel}) {
		return ${modelNameLowerCamel}Mapper.getByCondition(${modelNameLowerCamel});
	}
}
