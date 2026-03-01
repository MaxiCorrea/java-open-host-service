package application.usecase;

import domain.model.Authorization;
import domain.port.AuthorizationOutputPort;

public class CreateAuthorizationUseCase {

	private final AuthorizationOutputPort authorizationOutputPort;
	
	public CreateAuthorizationUseCase(
			final AuthorizationOutputPort authorizationOutputPort) {
		this.authorizationOutputPort = authorizationOutputPort;
	}
	
	public Authorization execute(
			final String affiliateId) {
		Authorization authorization = new Authorization(affiliateId);
		authorizationOutputPort.save(authorization);
		return authorization;
	}
	
}
