package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
<#--import org.apache.shiro.authz.annotation.Logical;-->
<#--import org.apache.shiro.authz.annotation.RequiresPermissions;-->
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import com.timecarol.smart_dormitory_repair.util.SimpleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@Slf4j
@RequestMapping("/${table.entityName}")
@Api(description = "${table.comment}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
    public class ${table.controllerName} extends ${superControllerClass} {
<#else>
    public class ${table.controllerName} {
</#if>

@Autowired
${table.serviceName} ${table.serviceName?substring(1)?uncap_first};

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
@PostMapping("/query")
<#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:list")-->
@ApiOperation("${table.comment}查询单个")
public SimpleResponse
<${table.entityName}Dto> query(@Valid @RequestBody ${table.entityName}Vo vo){
    return new SimpleResponse<>(${table.entityName?uncap_first}Service.query(vo));
    }

    @PostMapping("/pageList")
    <#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:list")-->
    @ApiOperation("${table.comment}分页查询")
    public SimpleResponse
    <IPage
    <${table.entityName}Dto>> pageList(@Valid @RequestBody ${table.entityName}Vo vo){
        return new SimpleResponse<>(${table.entityName?uncap_first}Service.pageList(vo));
        }

        @PostMapping("/add")
        <#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:add")-->
        @ApiOperation("${table.comment}新增")
        public SimpleResponse
        <${table.entityName}Dto> add(@Valid @RequestBody ${table.entityName}Vo vo) {
            return new SimpleResponse<>(${table.entityName?uncap_first}Service.add(vo));
            }

            @PostMapping("/edit")
            <#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:update")-->
            @ApiOperation("${table.comment}修改")
            public SimpleResponse
            <${table.entityName}Dto> edit(@Valid @RequestBody ${table.entityName}Vo vo) {
                return new SimpleResponse<>(${table.entityName?uncap_first}Service.edit(vo));
                }


                @PostMapping(value = "/del")
                <#--    @RequiresPermissions("sys:${table.entityName?uncap_first}:delete")-->
                @ApiOperation("${table.comment}删除(单个条目)")
                public SimpleResponse
                <${table.entityName}Dto> del(@Valid @RequestBody ${table.entityName}Vo vo) {
                    return new SimpleResponse<>(${table.entityName?uncap_first}Service.del(vo));
                    }
                    }
</#if>
