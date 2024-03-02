package hello.core.singleton;

public class StatefulService { //컨트롤 시프트 t 하면 테스트

   // private int price; //상태를 유지하는 필드

    public int order(String name, int price){
        System.out.println("name = " + name + "price" + price);
        // this.price = price; //여기가 문제
        return price;
    }

    /*public int getPrice() {
    //    return price;

    }*/
}
