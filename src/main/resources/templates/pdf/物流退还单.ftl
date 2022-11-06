<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>物流退还单</title>
    <style>
        @page {
            size: A4;
            margin: 20pt;
        }

        * {
            margin: 0;
            padding: 0;
            font-family: SimSun, sans-serif;
        }

        table {
            border-collapse: separate;
            border-spacing: 0;
            border-top: 0.9pt solid black;
            border-left: 0.9pt solid black;
        }

        td {
            border: 0.9pt solid black;
            border-top: none;
            border-left: none;
        }
    </style>
</head>

<body>
<div>
    <div style="height:30pt;">
        <p style="font-weight:700;font-size:20pt;display:inline-block;line-height:30pt;width:180pt">CIEC | 热联集团 </p>
        <p style="font-weight:400;font-size:12pt;display:inline-block;float:right;line-height:30pt;text-align:left;">
            编号：${printNo}</p>
    </div>
    <div style="font-weight:700;font-size:18pt;text-align:center;line-height:80pt;height:80pt;position:relative;">
        <img width="70pt" height="70pt" style="float:left;"
             src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAsAAAANCAYAAAB/9ZQ7AAAAWElEQVQokWNkEnL7z4AH/P+PkGZB5sAAIyMjhkKwYkKmoSjGZzI6YMLhVMoVA92M6QyY05CdA2ITbTLIABYmfKEMVIBsOkGTUSKFWCcQZTIyYGFg/Ee0YgBwpCFyEuSP9gAAAABJRU5ErkJggg=="/>
        <span style="display:inline-block;">退还单（系统版）</span>
        <span style="position:absolute;font-size:12pt;top:20pt;transform:translateX(-130pt);margin-left:260pt;">${piece}</span>
    </div>
    <div height="14.25pt">
        <p style="font-weight:400;font-size:12pt;display:inline-block;">计划单号：${dispatchNo}</p>
        <p style="font-weight:400;font-size:12pt;float:right;text-align:left;">
            开单日期：${printDate?string("yyyy年MM月dd日")}</p>
    </div>
    <table>
        <tbody>
        <tr height="14.25pt">
            <td rowspan="1" colspan="2" valign="center" style="font-weight:400;font-size:12pt;text-align:center;">项目名称
            </td>
            <td rowspan="1" colspan="3" valign="center"
                style="font-weight:400;font-size:12pt;text-align:center;">${projectName}</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">项目地址</td>
            <td rowspan="1" colspan="3" valign="center"
                style="font-weight:400;font-size:12pt;text-align:center;">${projectAddress}</td>
        </tr>
        <tr height="14.25pt">
            <td rowspan="1" colspan="2" valign="center" style="font-weight:400;font-size:12pt;text-align:center;">客户名称
            </td>
            <td rowspan="1" colspan="3" valign="center"
                style="font-weight:400;font-size:12pt;text-align:center;">${authName}</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">项目授权签收人</td>
            <td rowspan="1" valign="center" style="font-weight:400;font-size:12pt;text-align:center;">${authName}</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">联系电话</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">${mobiles}</td>
        </tr>
        <tr height="14.25pt">
            <td rowspan="1" colspan="2" valign="center" style="font-weight:400;font-size:12pt;text-align:center;">使用区域
            </td>
            <td rowspan="1" colspan="3" valign="center"
                style="font-weight:400;font-size:12pt;text-align:center;">${projectArea}</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">司机姓名</td>
            <td rowspan="1" valign="center" style="font-weight:400;font-size:12pt;text-align:center;">${driver}</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">车牌号</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">${carNumber}</td>
        </tr>
        <tr height="14.25pt">
            <td rowspan="1" colspan="6" valign="center" style="font-weight:700;font-size:12pt;text-align:center;">物料明细
            </td>
            <td rowspan="1" colspan="3" valign="center" style="font-weight:700;font-size:12pt;text-align:center;">定损明细
            </td>
        </tr>
        <tr height="14.25pt">
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;white-space:nowrap;">序号</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;white-space:nowrap;">物料名称</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">规格</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">型号</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">数量</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">件数</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">型号</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;white-space:nowrap;">定损类型</td>
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">数量</td>
        </tr>
        <#list detailList as detail>
            <tr height="14.25pt">
                <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">${detail.rowIndex!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;">${detail.productName!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;white-space:nowrap;">${detail.productSpe!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;white-space:nowrap;">${detail.productModel!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;">${detail.productCount!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;">${detail.productSpeCount!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;">${detail.compensationModel!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;">${detail.compensationType!""}</td>
                <td valign="center"
                    style="font-weight:400;font-size:12pt;text-align:center;">${detail.compensationCount!""}</td>
            </tr>
        </#list>
        <tr height="36.0pt">
            <td valign="center" style="font-weight:400;font-size:12pt;text-align:center;">备注</td>
            <td rowspan="1" colspan="9" valign="center"
                style="font-weight:400;font-size:12pt;text-align:center;">${remarks}</td>
        </tr>
        </tbody>
    </table>
    <div height="14.25pt">
        <p style="font-weight:400;font-size:12pt;display:inline-block;width:30%;">签收人签字：</p>
        <p style="font-weight:400;font-size:12pt;display:inline-block;width:30%;">过磅复核人签字：</p>
        <p style="font-weight:400;font-size:12pt;display:inline-block;width:30%;">签收日期：${receiveDate!""}</p>
    </div>
    <div height="14.25pt">
        <p style="font-weight:400;font-size:12pt;display:inline-block;width:30%;">制单人：${nickName}</p>
        <p style="font-weight:400;font-size:12pt;display:inline-block;width:30%;">司机签字：</p>
        <p style="font-weight:400;font-size:12pt;display:inline-block;width:30%;">劳务签字：</p>
    </div>
</div>
</body>

</html>