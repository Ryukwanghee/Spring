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

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()
    // 컨테이너를 사용하면 싱글톤을 보장한댔는데 여기서 코드들 보면 보장하는 것 같지가 않은 느낌이다.
    // 자바 코드상에 bean이 따로 계속 생성되어서 다 따로 인스턴스가 생성될 것 같은 느낌임
    // 결론은 같은 인스턴스가 생성됨. 동일한 것을 확인가능 ConfigurationSingletonTest 여기서 확인해봄


    //call AppConfig.memberService
    //"call AppConfig.memberRepository"
    //"call AppConfig.memberRepository"
    //"call AppConfig.orderService"
    //"call AppConfig.memberRepository"
    //위에처럼 호출횟수가 되어야 할 것 같은데

    //실제론
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.orderService 이렇게 호출됨


   /* public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); // 두번째 impl이 repository를 주입받음. appconfig에서 repository를 생성하는게 첫번째
    }*/

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository()); // 두번째 impl이 repository를 주입받음. appconfig에서 repository를 생성하는게 첫번째
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
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
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
        // return new FixDiscountPolicy();
    }
}
