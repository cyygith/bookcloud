package com.elling.book.sys.tool.model;

import javax.persistence.*;

@Table(name = "tool_gen_code")
public class ToolGenCode {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//让通用Mapper在执行insert操作之后将数据库自动生成的主键值会写到实体类对象中
    private Integer id;

    /**
     * 执行的表
     */
    @Column(name = "RUN_TABLE")
    private String runTable;

    /**
     * 数据库连接URL
     */
    @Column(name = "JDBC_URL")
    private String jdbcUrl;

    /**
     * 数据库连接用户名
     */
    @Column(name = "JDBC_USERNAME")
    private String jdbcUsername;

    /**
     * 数据库连接密码
     */
    @Column(name = "JDBC_PASSWORD")
    private String jdbcPassword;

    /**
     * 数据库驱动类
     */
    @Column(name = "JDBC_DRIVER_CLASS_NAME")
    private String jdbcDriverClassName;

    /**
     * 数据库schema
     */
    @Column(name = "JDBC_SCHEMA")
    private String jdbcSchema;

    /**
     * java代码存放的路径
     */
    @Column(name = "JAVA_PATH")
    private String javaPath;

    /**
     * 资源文件存放的路径
     */
    @Column(name = "RESOURCES_PATH")
    private String resourcesPath;

    /**
     * 模板存放的路径
     */
    @Column(name = "TEMPLATE_FILE_PATH")
    private String templateFilePath;

    /**
     * 基础包名
     */
    @Column(name = "BASE_MODEL")
    private String baseModel;

    /**
     * 基础包类路径
     */
    @Column(name = "BASE_PACKAGE")
    private String basePackage;

    /**
     * model类路径
     */
    @Column(name = "MODEL_PACKAGE")
    private String modelPackage;

    /**
     * mapper类路径
     */
    @Column(name = "MAPPER_PACKAGE")
    private String mapperPackage;

    /**
     * service类路径
     */
    @Column(name = "SERVICE_PACKAGE")
    private String servicePackage;

    /**
     * service实Impl类路径
     */
    @Column(name = "SERVICE_IMPL_PACKAGE")
    private String serviceImplPackage;

    /**
     * controller类路径
     */
    @Column(name = "CONTROLLER_PACKAGE")
    private String controllerPackage;
    
    /**
     * page页路径
     */
    @Column(name = "PAGE_PATH")
    private String pagePath;
    /**
     * 作者
     */
    @Column(name = "AUTHOR")
    private String author;

    /**
     * 时间格式化
     */
    @Column(name = "DATE_FORMAT")
    private String dateFormat;

    /**
     * 执行次数
     */
    @Column(name = "RUN_COUNT")
    private Integer runCount;

    /**
     * 执行类
     */
    @Column(name = "RUN_CLASS")
    private String runClass;

    /**
     * 执行时间
     */
    @Column(name = "RUN_TIME")
    private String runTime;
    
    /**
     * 项目地址
     */
    @Column(name = "PROJECT_PATH")
    private String projectPath;
    
    
    @Transient
    private String tableName;
    
    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取执行的表
     *
     * @return RUN_TABLE - 执行的表
     */
    public String getRunTable() {
        return runTable;
    }

    /**
     * 设置执行的表
     *
     * @param runTable 执行的表
     */
    public void setRunTable(String runTable) {
        this.runTable = runTable;
    }

    /**
     * 获取数据库连接URL
     *
     * @return JDBC_URL - 数据库连接URL
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * 设置数据库连接URL
     *
     * @param jdbcUrl 数据库连接URL
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * 获取数据库连接用户名
     *
     * @return JDBC_USERNAME - 数据库连接用户名
     */
    public String getJdbcUsername() {
        return jdbcUsername;
    }

    /**
     * 设置数据库连接用户名
     *
     * @param jdbcUsername 数据库连接用户名
     */
    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    /**
     * 获取数据库连接密码
     *
     * @return JDBC_PASSWORD - 数据库连接密码
     */
    public String getJdbcPassword() {
        return jdbcPassword;
    }

    /**
     * 设置数据库连接密码
     *
     * @param jdbcPassword 数据库连接密码
     */
    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    /**
     * 获取数据库驱动类
     *
     * @return JDBC_DRIVER_CLASS_NAME - 数据库驱动类
     */
    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }

    /**
     * 设置数据库驱动类
     *
     * @param jdbcDriverClassName 数据库驱动类
     */
    public void setJdbcDriverClassName(String jdbcDriverClassName) {
        this.jdbcDriverClassName = jdbcDriverClassName;
    }

    /**
     * 获取数据库schema
     *
     * @return JDBC_SCHEMA - 数据库schema
     */
    public String getJdbcSchema() {
        return jdbcSchema;
    }

    /**
     * 设置数据库schema
     *
     * @param jdbcSchema 数据库schema
     */
    public void setJdbcSchema(String jdbcSchema) {
        this.jdbcSchema = jdbcSchema;
    }

    /**
     * 获取java代码存放的路径
     *
     * @return JAVA_PATH - java代码存放的路径
     */
    public String getJavaPath() {
        return javaPath;
    }

    /**
     * 设置java代码存放的路径
     *
     * @param javaPath java代码存放的路径
     */
    public void setJavaPath(String javaPath) {
        this.javaPath = javaPath;
    }

    /**
     * 获取资源文件存放的路径
     *
     * @return RESOURCES_PATH - 资源文件存放的路径
     */
    public String getResourcesPath() {
        return resourcesPath;
    }

    /**
     * 设置资源文件存放的路径
     *
     * @param resourcesPath 资源文件存放的路径
     */
    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    /**
     * 获取模板存放的路径
     *
     * @return TEMPLATE_FILE_PATH - 模板存放的路径
     */
    public String getTemplateFilePath() {
        return templateFilePath;
    }

    /**
     * 设置模板存放的路径
     *
     * @param templateFilePath 模板存放的路径
     */
    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    /**
     * 获取基础包名
     *
     * @return BASE_MODEL - 基础包名
     */
    public String getBaseModel() {
        return baseModel;
    }

    /**
     * 设置基础包名
     *
     * @param baseModel 基础包名
     */
    public void setBaseModel(String baseModel) {
        this.baseModel = baseModel;
    }

    /**
     * 获取基础包类路径
     *
     * @return BASE_PACKAGE - 基础包类路径
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * 设置基础包类路径
     *
     * @param basePackage 基础包类路径
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * 获取model类路径
     *
     * @return MODEL_PACKAGE - model类路径
     */
    public String getModelPackage() {
        return modelPackage;
    }

    /**
     * 设置model类路径
     *
     * @param modelPackage model类路径
     */
    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    /**
     * 获取mapper类路径
     *
     * @return MAPPER_PACKAGE - mapper类路径
     */
    public String getMapperPackage() {
        return mapperPackage;
    }

    /**
     * 设置mapper类路径
     *
     * @param mapperPackage mapper类路径
     */
    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
    }

    /**
     * 获取service类路径
     *
     * @return SERVICE_PACKAGE - service类路径
     */
    public String getServicePackage() {
        return servicePackage;
    }

    /**
     * 设置service类路径
     *
     * @param servicePackage service类路径
     */
    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    /**
     * 获取service实Impl类路径
     *
     * @return SERVICE_IMPL_PACKAGE - service实Impl类路径
     */
    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    /**
     * 设置service实Impl类路径
     *
     * @param serviceImplPackage service实Impl类路径
     */
    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    /**
     * 获取controller类路径
     *
     * @return CONTROLLER_PACKAGE - controller类路径
     */
    public String getControllerPackage() {
        return controllerPackage;
    }

    /**
     * 设置controller类路径
     *
     * @param controllerPackage controller类路径
     */
    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    /**
     * 获取作者
     *
     * @return AUTHOR - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取时间格式化
     *
     * @return DATE_FORMAT - 时间格式化
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * 设置时间格式化
     *
     * @param dateFormat 时间格式化
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 获取执行次数
     *
     * @return RUN_COUNT - 执行次数
     */
    public Integer getRunCount() {
        return runCount;
    }

    /**
     * 设置执行次数
     *
     * @param runCount 执行次数
     */
    public void setRunCount(Integer runCount) {
        this.runCount = runCount;
    }

    /**
     * 获取执行类
     *
     * @return RUN_CLASS - 执行类
     */
    public String getRunClass() {
        return runClass;
    }

    /**
     * 设置执行类
     *
     * @param runClass 执行类
     */
    public void setRunClass(String runClass) {
        this.runClass = runClass;
    }

    /**
     * 获取执行时间
     *
     * @return RUN_TIME - 执行时间
     */
    public String getRunTime() {
        return runTime;
    }

    /**
     * 设置执行时间
     *
     * @param runTime 执行时间
     */
    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }
    
	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
    
    
}