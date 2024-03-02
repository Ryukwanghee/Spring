package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // 무상태로 int userPriceA 추가
        //ThreadA : A사용자 10000원 주문
        int userPriceA= statefulService1.order("userA", 10000);
        //ThreadB : B사용자 10000원 주문
        int userPriceB = statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문금액 조회
        //int price = statefulService1.getPrice();

        //System.out.println("price = " + price);
        System.out.println("price = " + userPriceA);
        System.out.println("price = " + userPriceB);


        //중간에 20000원 으로 바꿔버려서 price 2만원나옴
        //StatefulService로 똑같은거니까 price값이 바꿔짐

        //Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
        Assertions.assertThat(userPriceA).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}