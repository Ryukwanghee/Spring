package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        @Autowired(required = false)
        public void setNoBean1(Member noBean1) { //Member는 스프링이 관리하는 빈이 아니라 그냥 자바 (의존성주입 불가 @Autowired)
            // 그래서 호출이 안됨
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2); //호출은 되는데 그냥 null로
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) { //스프링 빈이 없으면 Optional.empty
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
