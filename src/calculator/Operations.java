package calculator;
import java.math.BigDecimal;

public interface Operations{
  BigDecimal adder (BigDecimal firstnumber, BigDecimal secondnumber);
  BigDecimal subtraction (BigDecimal firstnumber, BigDecimal secondnumber);
  BigDecimal multiplier (BigDecimal firstnumber, BigDecimal secondnumber);
  BigDecimal division (BigDecimal firstnumber, BigDecimal secondnumber);
}



