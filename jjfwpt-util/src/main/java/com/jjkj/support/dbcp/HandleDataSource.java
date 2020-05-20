package com.jjkj.support.dbcp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jjkj.util.DataUtil;
import com.jjkj.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author WangJun
 * @version 2016年5月20日 下午3:18:04
 */
public class HandleDataSource {
    private static final Logger logger = LogManager.getLogger();
    /**  */
    static Map<String, List<String>> METHODTYPE = new HashMap<String, List<String>>();
    // 数据源名称线程池
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void putDataSource(String datasource) {
        holder.set(datasource);
    }

    public static String getDataSource() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }

    public static void setDataSource(String service, String method) {
        logger.info(service + "." + method + "=>start...");
        if (DataUtil.isNotEmpty(PropertiesUtil.getString("druid.reader.url"))) {
            try {
                L: for (Entry<String, List<String>> entry : METHODTYPE.entrySet()) {
                    for (String type : entry.getValue()) {
                        if (method.startsWith(type)) {
                            logger.info(service + "." + method + "=>" + entry.getKey());
                            putDataSource(entry.getKey());
                            break L;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
                putDataSource("write");
            }
        }
    }
}
