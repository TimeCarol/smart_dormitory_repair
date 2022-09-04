package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.ApiOperation;
<#--import org.apache.shiro.authz.annotation.Logical;-->
<#--import org.apache.shiro.authz.annotation.RequiresPermissions;-->
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
<#--import com.common.res.DataResult;-->
<#if restControllerStyle>
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>
/**
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
    @RestController
<#else>
    @Controller
</#if>
@RequestMapping("api")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
        public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

<#--    @ApiOperation(value = "${table.comment}分页列表", response = ${entity}.class)-->
<#--    @ApiImplicitParams({-->
<#--    @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),-->
<#--    @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),-->
<#--    @ApiImplicitParam(name = "sort", value = "排序方式排序[true:正序; false:倒序]", dataType = "Boolean"),-->
<#--    @ApiImplicitParam(name = "sortName", value = "排序字段,参照返回字段", dataType = "String")})-->
<#--    @PostMapping(value = "/page")-->
<#--    public  Object list(@Valid @RequestBody ${entity} param) {-->

<#--    Object data = ${table.serviceName?uncap_first}.page(param);-->
<#--    return RetJson.ok(data);-->
<#--    }-->
    @GetMapping("/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:list")-->
    @ApiOperation("${table.entityName}查询单个")
    public SimpleResponse&lt;${table.entityName?uncap_first}Dto&gt; get${table.entityName}(@RequestBody ${table.entityName} ${table.entityName?uncap_first}){
    return new SimpleResponse<>(${table.entityName?uncap_first}Service.get${table.entityName}(${table.entityName?uncap_first}));
    }

    @GetMapping("/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:list")-->
    @ApiOperation("${table.entityName}查询全部")
    public SimpleResponse&lt;${table.entityName?uncap_first}Dto&gt; getAll${table.entityName}(){
    return new SimpleResponse<>(${table.entityName?uncap_first}Service.getAll${table.entityName}());
    }

    @PostMapping("/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:add")-->
    @ApiOperation("${table.entityName}新增")
    public SimpleResponse&lt;${table.entityName?uncap_first}Dto&gt; add(@Valid @RequestBody ${table.entityName} ${table.entityName?uncap_first}) {
    return new SimpleResponse<>(${table.entityName?uncap_first}Service.add(${table.entityName?uncap_first}));
    }

    @PutMapping("/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:update")-->
    @ApiOperation("${table.entityName}修改")
    public SimpleResponse&lt;${table.entityName?uncap_first}Dto&gt; update(@Valid @RequestBody ${table.entityName} ${table.entityName?uncap_first}) {
    return new SimpleResponse<>(${table.entityName?uncap_first}Service.update(${table.entityName?uncap_first}));
    }


    @DeleteMapping(value = "/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/{ids}")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:delete")-->
    @ApiOperation("${table.entityName}删除(单个条目)")
    public SimpleResponse&lt;${table.entityName?uncap_first}Dto&gt; del(@NotBlank(message = "{required}") @PathVariable String ids) {
    return new SimpleResponse<>(${table.entityName?uncap_first}Service.del(ids));
    }
    }
</#if>
