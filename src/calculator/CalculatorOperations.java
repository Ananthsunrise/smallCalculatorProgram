package calculator;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorOperations implements Operations{

    public BigDecimal adder (BigDecimal firstnumber, BigDecimal secondnumber) {
        return firstnumber.add(secondnumber);
    }
    public BigDecimal subtraction (BigDecimal firstnumber, BigDecimal secondnumber) {
        return firstnumber.subtract(secondnumber);
    }
    public BigDecimal multiplier (BigDecimal firstnumber, BigDecimal secondnumber){
        return firstnumber.multiply(secondnumber);
    }
    public BigDecimal division (BigDecimal firstnumber, BigDecimal secondnumber) {
        return firstnumber.divide(secondnumber,8,RoundingMode.UP);
    }
}
