package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

   /* public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); // 두번째 impl이 repository를 주입받음. appconfig에서 repository를 생성하는게 첫번째
    }*/

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); // 두번째 impl이 repository를 주입받음. appconfig에서 repository를 생성하는게 첫번째
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }


    // 이렇게 생성자 주입을 통하면 DIP가 지켜짐.
    /*public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }*/

    // 원래 MemoryMemberRepository() 가 중복이었는데
    // MemoryMemberRepository() 중복을 이렇게 제거했음

    // 이렇게 생성자 주입을 통하면 DIP가 지켜짐.
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
        // return new FixDiscountPolicy();
    }
}
