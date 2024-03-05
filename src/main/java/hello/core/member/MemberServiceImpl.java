package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 기능을 실행하는 부분만 책임지면 된다.
@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; //dip 지킴 , appconfig의 생성자를 통해서 주입하는 것, 생성자 주입

    @Autowired //ac.getBean(MemberRepository.class) 처럼 동작한다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }


    //테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}