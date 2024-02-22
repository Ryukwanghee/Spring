package hello.core.member;

// 기능을 실행하는 부분만 책임지면 된다.
public class MemberServiceImpl implements MemberService {

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    private final MemberRepository memberRepository; //dip 지킴 , appconfig의 생성자를 통해서 주입하는 것, 생성자 주입

    public void join(Member member) {
        memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}