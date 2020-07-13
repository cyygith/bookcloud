<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Title</title>
    <style>
        body{
            
        }
        .red{
            color: red;
        }
        table{
        	width:100%;
        	border-collapse:collapse;
        	border-style:solid;
        	border-width:0.5px;
        	border-color:#000
        }
        table tr td{
        	border-style:solid;
        	border-width:0.5px;
        	padding:9px 9px;
        	border-color:#000
        }
        table thead tr th{
        	padding:9px 9px;
        	text-align:center;
        	border-style:solid;
        	border-width:0.5px;
        	border-color:#000
        }
    </style>
</head>
<body>
<div class="red">
    你好，建党节快乐,
    <table style="width:100%">
    	<tr>
    		<td>理由</td>
    		<td>理由中顶啊四代机反杀了对焦方式拉开鸡蛋沙拉；开飞机来撒房间数量肯定就飞拉萨看到警方阿里；数控刀具法理上看到</td>
    	</tr>
    </table>
    <table>
        <thead>
            <tr>
                <#list titleList as title>
                  <th>${title}</th>
                </#list>
            </tr>
        </thead>
        <#list dataList as dl>
                <tr>
                    <#list dl as d><td>${d}</td></#list>
                </tr>
        </#list>
        
    </table>
</div>
</body>
</html>