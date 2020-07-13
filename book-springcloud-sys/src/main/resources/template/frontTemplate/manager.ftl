<template>
    <div id="itemManager-container">
        <div class="form-container">
            <el-form ref="form" :rules="rules" :model="form" label-width="100px">
            	<#list colsEntityNoKey as result>
            	<#if result_index%2=0>
            	<el-row :gutter="24">
            	</#if>
            		<#if result.dealType=='time'><#--如果是时间则打印对应时间空间/下拉控件等-->
            		<el-col :span="12">
                        <el-form-item label="${result.comment}" prop="${result.colunm}">
			                <el-date-picker v-model="form.${result.colunm}" type="date" placeholder="选择日期" size="small" :disabled="isDetail"></el-date-picker>
			            </el-form-item>
                    </el-col>
                    <#elseif result.dealType=='select'>
                    <el-col :span="12">
                    	<el-form-item label="${result.comment}" prop="${result.colunm}">
                            <el-select v-model="form.${result.colunm}" placeholder="请选择" style="width:100%" size="small" :disabled="isDetail">
                                <el-option
                                    v-for="it in ${result.colunm}Arr"
                                    :key="it.value"
                                    :label="it.label"
                                    :value="it.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <#else>
                    <el-col :span="12">
                        <el-form-item label="${result.comment}" prop="${result.colunm}">
                            <el-input v-model="form.${result.colunm}" size="small" :disabled="isDetail"></el-input>
                        </el-form-item>
                    </el-col>	
                    </#if>    
            	<#if result_index%2=1>
            	</el-row>
            	</#if>
            	<#if (result_index+1)=(colsEntityNoKey?size)&&((colsEntityNoKey?size)%2!=0)>
            	</el-row>
            	</#if>
            	</#list>
            </el-form>
        </div>
        
        <div class="button-container">
            <el-row justify="center">
                <el-col :span="2" :offset="5">
                    <el-button type="primary" icon="el-icon-s-order" @click="saveOrUpdate" v-if="!isDetail">保存</el-button>
                </el-col>
                <el-col :span="2" :offset="2">
                    <el-button type="primary" icon="el-icon-delete-solid" @click="resetForm" v-if="!isDetail">重置</el-button>
                </el-col>
                <el-col :span="2" :offset="!isDetail?2:9">
                    <el-button type="primary" icon="el-icon-circle-close" @click="close">关闭</el-button>
                </el-col>
            </el-row>
        </div>
    </div>
</template>
<script>
import {${sign}Api} from "@/service/sys-api";
export default {
    data(){
        return {
            form:{
            	<#list colsEntity as result>
            	${result.colunm}:'',
            	</#list>
            },
            <#list colsEntity as result>
            <#if result.dealType == 'select'>
            ${result.colunm}Arr:[],
            </#if>
        	</#list>
        	type:'detail',//处理类型，新增add、修改update、查看详情detail
            isDetail:false,
            rules: {//校验表单
            	<#list colsEntityNoKey as result>
            	${result.colunm}:[{required:true,message:'${result.comment}不能为空',trigger: 'blur'}],
            	</#list>
            }
        }
    },
    props:{
    	checkrow:{
    		default: ()=>{
    			return '';
    		}
    	}
    },
    components:{
    
    },
    mounted(){
 		this.queryById();
    },
    methods:{
        //重置
        resetForm() {
            this.$refs["form"].resetFields();
        },
        //关闭
        close() {
            this.$emit('closechild');
        },
        //根据ID查询记录
        queryById(){
            this.type= this.checkrow.type;
            let selObj = this.checkrow.selArr;
            if(this.type == 'detail'){
                this.isDetail = true;
            }

            if(this.type=='update'||this.type=='detail'){
                let id = selObj.${primaryKey};
                let param = new URLSearchParams();
                param.append("id",id);
                
                //let param = {
                //    ${primaryKey}:id
                //};
                ${sign}Api.detail(param).then((res)=>{
                    if(res.code == "0"){
                        this.form = res.data;
                    }else{
                        this.$alert('获取信息失败，请联系管理员处理','提示信息');
                    }
                });
            }
            
        },

        //新增或者更新
        saveOrUpdate() {
        this.$refs['form'].validate((valid) =>{
                if(valid){
		        	let param = this.form;
		            let loading = this.$loading({lock:true,text:'保存中....',background:'rgba(0,0,0,0.5)'});
		            let url = '';
		            if(this.type == 'update'){
		                ${sign}Api.update(param).then((res)=>{
			    			if(res.code == "0"){
			        			this.$alert('更新成功','提示信息',{
			        				confirmButtonText:'确定',
			        				callback: action => {
			        					this.$emit('closechild');
			        				}
			        			})
			        		}else{
			        			this.$alert('提交失败，请联系管理员处理','提示信息');
			        		}
			        		loading.close();
			        	});	
		            }else{
		                ${sign}Api.save(param).then((res)=>{
			    			if(res.code == "0"){
			        			this.$alert('保存成功','提示信息',{
			        				confirmButtonText:'确定',
			        				callback: action => {
			        					this.$emit('closechild');
			        				}
			        			})
			        		}else{
			        			this.$alert('提交失败，请联系管理员处理','提示信息');
			        		}
			        		loading.close();
			        	});	
		            }
		        }
            })
        },
    }
}
</script>
<style lang="scss" scoped>
@import 'src/components/page/style/normal'; 
</style>