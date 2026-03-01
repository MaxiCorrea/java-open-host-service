package openhost.api;

import application.usecase.CreateAuthorizationUseCase;
import application.usecase.GetAuthorizationUseCase;
import openhost.dto.AuthorizationResponseDTO;
import openhost.mapper.AuthorizationMapper;

// @RestController
// @RequestMapping("/v1/authorizations")
public class AuthorizationController {

	private final CreateAuthorizationUseCase createAuthorizationUseCase;
	private final GetAuthorizationUseCase getAuthorizationUseCase;
	
	public AuthorizationController(
			final CreateAuthorizationUseCase createAuthorizationUseCase,
			final GetAuthorizationUseCase getAuthorizationUseCase) {
		this.createAuthorizationUseCase = createAuthorizationUseCase;
		this.getAuthorizationUseCase = getAuthorizationUseCase;
	}
	
	public AuthorizationResponseDTO create(
			final String affiliateId) {
		return AuthorizationMapper.toDTO(createAuthorizationUseCase.execute(affiliateId));
	}
	
	public AuthorizationResponseDTO get(
			final String affiliateId) {
		return AuthorizationMapper.toDTO(getAuthorizationUseCase.execute(affiliateId));
	}
	
}
