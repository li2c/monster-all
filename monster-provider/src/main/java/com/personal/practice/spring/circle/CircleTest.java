package com.personal.practice.spring.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

public class CircleTest {

}
//
//@Component
//class A{
//    @Autowired
//    private B b;
//}
//
//@Component
//class B{
//    @Autowired
//    private A a;
//}

//@Component
//class C{
//   C(D d){
//
//   }
//}
//
//@Component
//class D{
//    D(C c){
//
//    }
//}

@Service
@Scope("prototype")
class E{
    @Autowired
    private F f;

}

@Service
@Scope("prototype")
class F{
    @Autowired
    private E e;

}

@Service
@Scope("prototype")
 class A1 {
    @Autowired
    private B1 b1;
}

@Service
@Scope("prototype")
 class B1 {
    @Autowired
    public C1 c1;
}

@Service
@Scope("prototype")
 class C1 {
    @Autowired  public A1 a1;
}

