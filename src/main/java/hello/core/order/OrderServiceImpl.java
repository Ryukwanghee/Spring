package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    // 여기에 autowired 쓰는건 비추, 이쪽에서 set도 만들고 사용하는곳에서 new로 생성자도 만들어야가능
    // 그럴바엔 그냥 여기 set에 autowired쓰자
   /* private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;*/

    
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // @Autowired(required = false) 하면 필수가 아니라는 말
    /*
    @Autowired //수정자 의존관계       private final 에서 final을 빼고 사용
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired             //수정자 의존관계       private final 에서 final을 빼고 사용
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }*/


    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //  이 객체 하나만 바꿔주면 됨, 이러면 dip, ocp 위반 그래서 AppConfig를 사용해서 구현과 역할을 분리
    //  private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
