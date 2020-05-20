package com;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.*;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author jobob
 * @since 2018-09-12
 */
public class MybatisSqlGenerator {

    private final static String modulePath="/code-generator";
    private final static String xmlPath="/src/main/resources/mapper/";
    private final static String javaPath="/src/main/java/com/jjkj/";
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    /**
     * RUN THIS
     */
    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        gc.setOutputDir(projectPath + "/code-generator/src/main/java");
        gc.setAuthor("WangJun");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://114.116.117.206:3306/iwant?useUnicode=true&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("WANGJUN1Q2W3E13");
        mpg.setDataSource(dsc);

        String moduleName=scanner("模块名").trim();
        String UpModuleName=moduleName.substring(0,1).toUpperCase()+moduleName.substring(1,moduleName.length()).trim();
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("module");
        pc.setParent("com.jjkj");
        mpg.setPackageInfo(pc);

        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("API"+UpModuleName+"Service");
        gc.setServiceImplName(UpModuleName+"ServiceImpl");
        gc.setControllerName(UpModuleName+"Controller");
        mpg.setGlobalConfig(gc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("UpModuleName",UpModuleName);
                map.put("ModuleName",moduleName);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        //APIService
        //serviceImpl
        focList.add(new FileOutConfig("templates/p_mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/dao/mapper/xml/"+tableInfo.getMapperName()+ StringPool.DOT_XML;
                return path;
            }
        });
        focList.add(new FileOutConfig("templates/p_mapper.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/dao/mapper/"+tableInfo.getMapperName()+ StringPool.DOT_JAVA;
                return path;
            }
        });
        focList.add(new FileOutConfig("templates/p_dao.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/dao/"+UpModuleName+"InfoDao"+ StringPool.DOT_JAVA;
                return path;
            }
        });

        focList.add(new FileOutConfig("templates/p_daoImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/dao/impl/"+UpModuleName+"InfoDaoImpl"+ StringPool.DOT_JAVA;
                return path;
            }
        });

        focList.add(new FileOutConfig("templates/p_APIService.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/api/API"+UpModuleName+"Service"+ StringPool.DOT_JAVA;
                return path;
            }
        });
        focList.add(new FileOutConfig("templates/p_serviceImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/service/"+UpModuleName+"ServiceImpl"+ StringPool.DOT_JAVA;
                return path;
            }
        });
        focList.add(new FileOutConfig("templates/p_gridCloums.json.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/gridJson/"+UpModuleName+"GridCloums.json";
                return path;
            }
        });
        focList.add(new FileOutConfig("templates/p_pageview.vue.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/vuePage/"+UpModuleName+"info.vue";
                return path;
            }
        });
        focList.add(new FileOutConfig("templates/p_index_mapping.index.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String path= projectPath +modulePath+javaPath+ pc.getModuleName()
                        +"/dao/elasticsearch/indexMapping/"+moduleName+"info_index_mapping.index";
                return path;
            }
        });
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //templateConfig.setService();
        templateConfig.setServiceImpl(null);
        templateConfig.setService(null);
        templateConfig.setMapper(null);
        templateConfig.setXml(null);
        //templateConfig.setController();
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.want.generator.generator.common.BaseEntity");
        strategy.setEntityLombokModel(true);
       // strategy.setSuperControllerClass("com.want.generator.common.BaseController");
        strategy.setInclude(scanner("表名"));
        strategy.setEntitySerialVersionUID(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        //strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}
