package domain.model;

import java.util.UUID;

public final class AuthorizationId {

	private final String value;
	
	public AuthorizationId() {
		this.value = UUID.randomUUID().toString();
	}
	
	public AuthorizationId(
			final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
