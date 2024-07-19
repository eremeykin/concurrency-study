package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

@Listing("3.10")
class UsingThreadLocal {

    private static ThreadLocal<Integer> numHolder = new ThreadLocal<>();

    public static void setNum(int num) {
        numHolder.set(num);
    }


    public static Integer getNum() {
        return numHolder.get();
    }


}
