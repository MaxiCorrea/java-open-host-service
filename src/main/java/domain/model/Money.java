package domain.model;

import java.math.BigDecimal;

public final class Money {

	private final BigDecimal amount;
	private final String currency;
	
	public Money(
			final BigDecimal amount,
			final String currency) {
		this.amount = amount;
		this.currency = currency;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
}
