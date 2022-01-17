import java.math.BigDecimal;
import java.math.RoundingMode;

public class HelloWorld {
    public static void main(String[] args){
        BigDecimal bd =new BigDecimal("2.0");
        System.out.println(bd.setScale(1, RoundingMode.FLOOR).toString());
        System.out.println(bd.setScale(1, RoundingMode.CEILING).toString());
        System.out.println(bd.setScale(0, RoundingMode.DOWN).toString());
        System.out.println(bd.setScale(1, RoundingMode.HALF_DOWN).toString());
        System.out.println(bd.setScale(1, RoundingMode.HALF_UP).toString());
        System.out.println(bd.setScale(1, RoundingMode.HALF_EVEN).toString());
       // System.out.println(bd.setScale(0, RoundingMode.UNNECESSARY).toString());
       // System.out.println("Hello Ananth!");
        //System.out.println("hii java");
        //System.out.println("hii java");
    }
}
