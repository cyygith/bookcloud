<template>
    <div id="itemlist-panel" class="itemlist-panel">
        <div class="black-panel"> &nbsp;</div>
        <div class="condition-panel">
                <el-form ref="form" :model="form" label-width="120px">
                	<#list colsEntityNoKey as result><#--循环输出变量 start-->
                	<#if result_index = 3>
                	<div v-if="queryMore">
                	</#if>
                	<#if result_index%3=0><#--一行输出三个 start-->
                	<el-row :gutter="24">
                	</#if>
                		<#if result.dealType=='time'><#--如果是时间则打印对应时间空间、下拉控件等-->
                		<el-col :span="7">
                            <el-form-item label="${result.comment}" prop="${result.colunm}">
				                <el-date-picker v-model="form.${result.colunm}" type="date" placeholder="选择日期" size="small"></el-date-picker>
				            </el-form-item>
                        </el-col>
                        <#elseif result.dealType=='select'><#--如果是下拉控件-->
                        <el-col :span="7">
                        	<el-form-item label="${result.comment}" prop="${result.colunm}">
	                            <el-select v-model="form.${result.colunm}" placeholder="请选择" style="width:100%" size="small">
	                                <el-option
	                                    v-for="it in ${result.colunm}Arr"
	                                    :key="it.value"
	                                    :label="it.label"
	                                    :value="it.value">
	                                </el-option>
	                            </el-select>
	                        </el-form-item>
                        </el-col>
                        <#else >
                        <el-col :span="7">
                            <el-form-item label="${result.comment}" prop="${result.colunm}">
                                <el-input v-model="form.${result.colunm}" size="small"></el-input>
                            </el-form-item>
                        </el-col>	
                        </#if>
                	<#if (result_index+1)%3=0>
                	</el-row>
                	</#if>
                	<#if (result_index+1)=(colsEntityNoKey?size)&&((colsEntityNoKey?size)%3!=0)>
                	</el-row>
                	</#if>
 					</#list>
 					</div>
                    <el-row justify="center">
                        <el-col :span="1" :offset="7">
                            <el-button type="primary" icon="el-icon-search" @click="queryList" size="small">查 询</el-button>
                        </el-col>
                        <el-col :span="1" :offset="1">
                            <el-button plain icon="el-icon-switch-button" size="small" @click="resetForm">重 置</el-button>
                        </el-col>
                        <el-col :span="1" :offset="1">
                            <el-button type="text" icon="queryMore==false?'el-icon-arrow-down':'el-icon-arrow-up'" size="small" @click="queryMore==true?queryMore=false:queryMore=true">更多查询</el-button>
                        </el-col>
                    </el-row>
                </el-form>
        </div>

        <div class="list-panel">
            <div class="list-button-p">
                <el-row>
		          <el-col :span="1">
		            <el-button type="primary" icon="el-icon-search" @click="add" size="small">新增</el-button>
		          </el-col>
		          <el-col :span="1" :offset="1">
		            <el-button type="primary" icon="el-icon-s-custom" @click="del" size="small">删除</el-button>
		          </el-col>
		        </el-row>
            </div>
            <div class="list-list">
                <el-table border style="width: 100%" :data="tableData" ref="multipleTable">
                        <el-table-column fixed type="selection" width="40"></el-table-column>
                        <#list colsEntityNoKey as result>
                        <el-table-column prop="${result.colunm}" label="${result.comment}"></el-table-column>
                        </#list>
                        <el-table-column fixed="right" label="操作" width="160">
				            <template slot-scope="scope">
				              <el-button @click="update(scope.row)" type="warning" size="mini">编辑</el-button>
				              <el-button @click="detail(scope.row)" type="primary" size="mini">查看</el-button>
				            </template>
				        </el-table-column>
                </el-table>
            </div>
            <div class="list-page">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.sync="page.currPage"
                    :page-sizes="[5,10,20,30,40]"
                    :page-size="page.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="page.totalCount">
                </el-pagination>
            </div>    
        </div>
        <div class="black-panel"> &nbsp;</div>
        <div class="popdialog-container">
            <el-dialog 
                :title="dialogTitle"
                width="60%"
                v-if='dialogVisible'
                :visible.sync="dialogVisible"
                :close-on-click-modal="false"
                :before-close="handleClose">
                <${modelNameMidCamel} :checkrow="checkRow" @closechild="closechild"></${modelNameMidCamel}>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import ${modelNameLowerCamel} from "@/components/page/${base_model}/${sign}/${modelNameLowerCamel}Manager"
import {${sign}Api} from "@/service/sys-api";
export default {
	name:'${modelNameLowerCamel}',
    data(){
        return {
            dialogVisible:false,
            visible:false,
            form:{
            	<#list colsEntityNoKey as result>
            	${result.colunm}:'',
            	</#list>
            },
            <#list colsEntity as result>
            <#if result.dealType == 'select'>
            ${result.colunm}Arr:[],
            </#if>
        	</#list>
            page:{
            	pageSize:5,
            	currPage:1,
            	totalPage:0
            },
            tableData: [],
            checkRow:{}, //选择的条数
            queryMore:false,  //查询更多
            dialogTitle:'新增'
        }
    },
    components:{
        '${modelNameMidCamel}' : ${modelNameLowerCamel}
    },
    mounted(){
        this.queryList();
    },
    methods:{
    	//改变每页条数
        handleSizeChange(val) {
            this.page.pageSize = val;
            this.queryList();
        },
        //改变当前页面
        handleCurrentChange(val) {
            this.page.currPage = val;
            this.queryList();
        },
        //关闭弹出窗口
        handleClose(done) {
            this.dialogVisible = false;
        },
        //关闭子窗口
        closechild(){
        	this.dialogVisible = false;
        	//this.queryList();
        },
        //重置
        resetForm(){
        	this.$refs['form'].resetFields();
        },
        //查询list数据
        queryList() {
	        let param = Object.assign({}, this.form);
	        param.size = this.page.pageSize;
	        param.page = this.page.currPage;
	        
	        let loading = this.$loading({lock:true,text:'处理中....',background:'rgba(0,0,0,0.5)',target: document.querySelector('.list-panel')});
	        ${sign}Api.list(param).then((res)=>{
                if(res.code == "0"){
                  this.tableData = res.data.list;
                  this.page.pageSize = res.data.pageSize;
                  this.page.totalCount = res.data.total;
                }else{
                  this.$message({showClose:true,message:'程序出现异常，请联系管理员处理'});
                }
                loading.close();
	        });
	    },
	    //新增
	    add() {
	      this.checkRow.selArr = this.$refs.multipleTable.selection;
	      this.dialogVisible = true;
	      this.checkRow.type='add';
	      this.dialogTitle = "新增";
	    },
	    //修改
	    update(row) {
	      this.checkRow.selArr = row;
	      this.checkRow.type='update';
	      this.dialogVisible = true;
	      this.dialogTitle = "修改";
	    },
	    //查看详情
	    detail(row){
	      this.checkRow.selArr = row;
	      this.checkRow.type='detail';
	      this.dialogVisible = true;
	      this.dialogTitle = "查看详情";
	    },
	    //删除
	    del(){
	      this.checkRow.type='del';
	      let ll = this.$refs.multipleTable.selection;
	      if(ll.length<1){
	          this.$message({ showClose: true, type: 'warning',  message: '请选择删除的行！', duration:2000 });
	      }
	      let idArr = ll.map((e,i)=>{
	        return e.${primaryKey};
	      });
	      let param = {
	        ids:idArr.join(",")
	      }
	
	      ${sign}Api.deleteByIds(param).then((res)=>{
                if(res.code == "0"){
                  this.$message({
                    showClose: true,
                    message: '删除成功！',
                    duration:2000
                  });
                  this.queryList();//关闭后刷新界面
                }else{
                  this.$message({showClose:true,message:'删除失败，程序出现异常，请联系管理员处理'});
                }
	        });
	    },
	    
    }
}
</script>
<style lang="scss" scoped>
@import 'src/components/page/style/normal'; 
</style>

