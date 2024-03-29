package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService(); //클래스레벨에 올라가기때문에 딱 하나만 가짐

    //2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance; //인스턴스 참조를 꺼내는건 이방법밖에 없음, 항상 같은 인스턴스만 반환(1개의 인스턴스만 존재해서)
    }

    //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService(){

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }


}
