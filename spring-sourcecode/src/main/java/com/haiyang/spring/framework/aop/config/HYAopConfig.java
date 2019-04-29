package com.haiyang.spring.framework.aop.config;

import lombok.Data;

/**
 * @ClassName: HYAopConfig
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 15:02
 * @Version: 1.0
 */
@Data
public class HYAopConfig {
    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
}
