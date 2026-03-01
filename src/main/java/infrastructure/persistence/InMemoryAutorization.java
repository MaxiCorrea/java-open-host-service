package infrastructure.persistence;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import domain.model.Authorization;
import domain.model.AuthorizationId;
import domain.port.AuthorizationOutputPort;

public class InMemoryAutorization implements AuthorizationOutputPort {

	private final ConcurrentHashMap<AuthorizationId, Authorization> database;
	
	public InMemoryAutorization() {
		database = new ConcurrentHashMap<>();
	}
	
	@Override
	public void save(
			final Authorization authorization) {
		database.put(authorization.getId(), authorization);
	}

	@Override
	public Optional<Authorization> findById(
			final AuthorizationId id) {
		return Optional.ofNullable(database.get(id));
	}

}
