package com.sxk.bruin.springbootdatasource.aspectj;

import com.sxk.bruin.springbootdatasource.annotation.UseFirstDataSource;
import com.sxk.bruin.springbootdatasource.constant.DataSourceNames;
import com.sxk.bruin.springbootdatasource.util.DataSourceNameUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author suxingkang
 */
@Aspect
@Component
public class DynamicDataSourceAop {


	@Pointcut("execution(* com.sxk.bruin.springbootdatasource.controller.*.*(..))")
	public void pointCut() {}

	@Before("pointCut()")
	public void beforeAdvice(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		UseFirstDataSource firstDataSource = signature.getMethod().getAnnotation(UseFirstDataSource.class);
		if (Objects.isNull(firstDataSource)){
			DataSourceNameUtil.setDataSourceName(DataSourceNames.SECOND);
		}else {
			DataSourceNameUtil.setDataSourceName(DataSourceNames.FIRST);
		}
	}

}
