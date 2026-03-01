package application.usecase;

import domain.model.Authorization;
import domain.model.AuthorizationId;
import domain.port.AuthorizationOutputPort;

public class GetAuthorizationUseCase {

	private final AuthorizationOutputPort authorizationOutputPort;
	
	public GetAuthorizationUseCase(
			final AuthorizationOutputPort authorizationOutputPort) {
		this.authorizationOutputPort = authorizationOutputPort;
	}
	
	public Authorization execute(
			final String affiliatedId) {
		return authorizationOutputPort.findById(new AuthorizationId(affiliatedId)).orElseThrow();
	}
	
}
