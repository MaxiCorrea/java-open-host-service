package domain.port;

import java.util.Optional;

import domain.model.Authorization;
import domain.model.AuthorizationId;

public interface AuthorizationOutputPort {

	void save (Authorization authorization);
	
	Optional<Authorization> findById(AuthorizationId id);
	
}
