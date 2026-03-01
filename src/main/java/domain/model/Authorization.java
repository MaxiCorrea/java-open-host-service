package domain.model;

public class Authorization {

	private final AuthorizationId id;
	private final String affiliateId;
	private AuthorizationStatus status;
	private Money approvedAmount;
	
	public Authorization(
			final String affiliateId) {
		this.id = new AuthorizationId();
		this.affiliateId = affiliateId;
		this.status = AuthorizationStatus.PENDING;
	}
	
	public void approve(
			final Money amount) {
		this.status = AuthorizationStatus.APPROVED;
		this.approvedAmount = amount;
	}
	
	public AuthorizationId getId() {
		return id;
	}
	
	public String getAffiliateId() {
		return affiliateId;
	}
	
	public AuthorizationStatus getStatus() {
		return status;
	}
	
	public Money getApprovedAmount() {
		return approvedAmount;
	}
	
}
