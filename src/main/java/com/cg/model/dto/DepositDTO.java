package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepositDTO implements Validator {
    private Long id;

//    @NotBlank
//    @Pattern(regexp = "^[0-9]*$", message = "Customer ID only number")
    private Long customerId;

//    @DecimalMin(value = "100", message = "Số tiền tối thiểu là 100")
//    @DecimalMax(value = "10000", message = "Số tiền tối đa là 10.000")
    private BigDecimal transactionAmount;

    public Deposit toDeposit(Customer customer) {
        return new Deposit()
                .setId(id)
                .setCustomer(customer)
                .setTransactionAmount(transactionAmount);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DepositDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DepositDTO depositDTO = (DepositDTO) target;

        BigDecimal transactionAmount = depositDTO.getTransactionAmount();

//        ValidationUtils.rejectIfEmpty(errors, "transactionAmount", "transactionAmount.empty");

        if (transactionAmount != null) {
            if (transactionAmount.toString().length() > 7){
                errors.rejectValue("transactionAmount", "transactionAmount.length", "Số tiền tối đa là 1.000.000");
            }

            if (!transactionAmount.toString().matches("(^$|[0-9]*$)")){
                errors.rejectValue("transactionAmount", "transactionAmount.matches", "Số tiền giao dịch chỉ chấp nhận ký tự số");
            }
        } else {
            errors.rejectValue("transactionAmount", "transactionAmount.null", "Vui lòng nhập số tiền gửi");
        }
    }
}
