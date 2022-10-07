package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

/**
* ${table.comment}查询
*/
${table.entityName}Dto query(${table.entityName}Vo vo);

/**
* ${table.comment}分页查询
*/
IPage
<${table.entityName}Dto> pageList(${table.entityName}Vo vo);

    /**
    * ${table.comment}新增
    */
    ${table.entityName}Dto add(${table.entityName}Vo vo);

    /**
    * ${table.comment}修改
    */
    ${table.entityName}Dto edit(${table.entityName}Vo vo);

    /**
    * ${table.comment}删除(单个条目)
    */
    ${table.entityName}Dto del(${table.entityName}Vo vo);
    }

</#if>

