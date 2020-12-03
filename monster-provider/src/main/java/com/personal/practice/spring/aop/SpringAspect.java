package com.personal.practice.spring.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect//切面 默认五个连接点 对应5个增强
@Component
public class SpringAspect {
    @Before("pointcut()")
    public void  before(){
        System.out.println("before aspect");
    }

    @After("pointcut()")
    public void after(){
        System.out.println("after aspect");

    }

    /**
     * 用args()将传递给target的参数同样也传递给advice
     * @param point
     * @param test
     */
    @Around("pointcut()&&args(test)")
    public void around(ProceedingJoinPoint point,int test){
        System.out.println("around before point"+test);
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("around after point"+test);
    }

    /**
     * 切点
     * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
     *  这里问号表示当前项可以有也可以没有，其中各项的语义如下：
     * modifiers-pattern：方法的可见性，如public，protected；
     * ret-type-pattern：方法的返回值类型，如int，void等；
     * declaring-type-pattern：方法所在类的全路径名，如com.spring.Aspect；
     * name-pattern：方法名类型，如buisinessService()；
     * param-pattern：方法的参数类型，如java.lang.String；
     * throws-pattern：方法抛出的异常类型，如java.lang.Exception；
     *
     * *通配符，该通配符主要用于匹配单个单词，或者是以某个词为前缀或后缀的单词。
     * .通配符，该通配符表示0个或多个项，主要用于declaring-type-pattern和param-pattern中，如果用于declaring-type-pattern中，则表示匹配当前包及其子包，如果用于param-pattern中，则表示匹配0个或多个参数。
     *
     */
    @Pointcut("execution(public * com.personal.practice.spring.aop..*.aopTest(..))")
    //方法名为pointcut的签名，可以在@Before等注解中使用
    public void pointcut(){
        System.out.println("Pointcut");
    }



    @AfterReturning("pointcut()")
    public void afterReturn(){
        System.out.println("afterReturn");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }
}
