package model;

import com.google.inject.Singleton;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Singleton
@Entity
public class EmeraldAccount {

    public static final Long EMERALD_ACCOUNT_ID  = 1L;

    @Id
    private Long id = EMERALD_ACCOUNT_ID;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
