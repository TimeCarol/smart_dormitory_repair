package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
    public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

<#--    /**-->
<#--    * ${table.entityName!}详情-->
<#--    * @param id-->
<#--    * @return-->
<#--    */-->
<#--    get${table.entityName}(String ${table.entityName?uncap_first}Id);-->

<#--    /**-->
<#--    * ${table.entityName!}详情-->
<#--    * @param id-->
<#--    * @return-->
<#--    */-->
<#--    getAll${table.entityName}();-->

<#--    /**-->
<#--    * ${table.entityName!}新增-->
<#--    * @param param 根据需要进行传值-->
<#--    * @return-->
<#--    */-->
<#--    void add(${entity} param);-->

<#--    /**-->
<#--    * ${table.entityName!}修改-->
<#--    * @param param 根据需要进行传值-->
<#--    * @return-->
<#--    */-->
<#--    void modify(${entity} param);-->

<#--    /**-->
<#--    * ${table.entityName!}删除(单个条目)-->
<#--    * @param id-->
<#--    * @return-->
<#--    */-->
<#--    void remove(String ${table.entityName}Id);-->
    }

</#if>

