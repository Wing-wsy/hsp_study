package com.itheima.imports;

import com.itheima.beans.OtherBean;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wing
 * @create 2024/6/1
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        // 返回的数组封装的是需要被注册到Spring容器中的Bean的全限定名
        return new String[]{OtherBean.class.getName()};
    }
}
