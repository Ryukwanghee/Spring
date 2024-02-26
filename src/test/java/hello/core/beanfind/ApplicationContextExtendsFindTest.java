package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 중복오류가 발생한다")
    void findBeanByParentTypeDuplicate() {
        //assertThrows메서드는 NoUniqueBeanDefinitionException이 발생하는지 확인, 이 예외는 스프링 애플리케이션 컨텍스트에서 특정 타입에 해당하는 빈이 중복되어 등록되어 있을 때 발생
        //람다 표현식인 () -> ac.getBean(DiscountPolicy.class)는 특정 코드 블록을 실행했을 때 예외가 발생하는지 확인하는데 사용
        //DiscountPolicy 타입의 빈을 가져오는데, 이 때 중복된 빈이 존재하는지 확인
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 빈 이름을 지정한다")
    void findBeanByParentTypeBeanName() {
        // rateDiscountPolicy이름의 빈을 찾아서 DiscountPolicy 타입으로 가져오는 역할,
        // 즉 "rateDiscountPolicy"라는 이름의 빈을 가져와서 이를 DiscountPolicy 타입의 변수 rateDiscountPolicy에 할당하는 역할
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        //rateDiscountPolicy 객체가 RateDiscountPolicy 클래스의 인스턴스인지를 확인하고자 할 때 사용
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() {
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        //isInstanceOf 메서드는 주어진 객체가 특정 클래스의 인스턴스인지를 확인합니다. 따라서 bean 객체가 RateDiscountPolicy 클래스의 인스턴스인지를 확인하여 검증합니다.
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
