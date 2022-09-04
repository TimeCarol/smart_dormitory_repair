package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
* ${table.comment!} 服务实现类
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
    public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

<#--  @Override-->
<#--  public DataResult<List getAll${table.entityName}(${table.entityName} ${table.entityName?uncap_first}){-->
<#--  DataResult result =DataResult.success();-->

<#--  return result;-->
<#--  }-->
<#--                         -->
<#--   @Override-->
<#--  public DataResult add(@Valid @RequestBody ${table.entityName} ${table.entityName?uncap_first}) {-->
<#--  DataResult result =DataResult.success();-->

<#--  return result;-->
<#--  }-->
<#--   @Override-->
<#--  public DataResult update(@Valid @RequestBody ${table.entityName} ${table.entityName?uncap_first}) {-->
<#--  DataResult result =DataResult.success();-->

<#--  return result;-->
<#--  }-->

<#--   @Override-->
<#--  public DataResult remove(@NotBlank(message = "{required}") @PathVariable String ids) {-->
<#--  DataResult result =DataResult.success();-->

<#--  return result;-->

    }
</#if>
