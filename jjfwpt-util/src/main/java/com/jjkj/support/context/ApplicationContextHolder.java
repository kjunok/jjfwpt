/**
 *
 */
package com.jjkj.support.context;

import java.util.Map;

import com.jjkj.util.InstanceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;



/**
 *
 * @author WangJun
 * @version 2017年12月6日 上午11:53:31
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static final Logger logger = LogManager.getLogger();
    static ApplicationContext applicationContext;
    private static final Map<String, Object> SERVICE_FACTORY = InstanceUtil.newHashMap();
   // private static final EnableMotan ENABLE_MOTAN = new EnableMotan();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> t) {
        return applicationContext.getBean(t);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> t) {
        return applicationContext.getBeansOfType(t);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T getService(Class<T> cls) {
        String clsName = cls.getName();
        T v = (T)SERVICE_FACTORY.get(clsName);
        if (v == null) {
            synchronized (clsName) {
                v = (T)SERVICE_FACTORY.get(clsName);
                if (v == null) {
                    logger.info("*****Autowire {}*****", cls);
                    //if (ENABLE_DUBBO.matches(null, null)) {
                     //  v = DubboContext.getService(cls);
                    //}
//                    else if (ENABLE_MOTAN.matches(null, null)) {
//                        v = MotanContext.getService(cls);
//                    }
                   // else {
                        v = ApplicationContextHolder.getBean(cls);
                  //  }
                    logger.info("*****{} Autowired*****", cls);
                    SERVICE_FACTORY.put(clsName, v);
                }
            }
        }
        return v;
    }
}
