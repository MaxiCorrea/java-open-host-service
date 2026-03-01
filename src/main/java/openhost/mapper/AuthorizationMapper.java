package openhost.mapper;

import domain.model.Authorization;
import openhost.dto.AuthorizationResponseDTO;

public class AuthorizationMapper {

	public static AuthorizationResponseDTO toDTO(
			final Authorization authorization) {
		AuthorizationResponseDTO dto = new AuthorizationResponseDTO();
		dto.authorizationId = authorization.getId().getValue();
		dto.affiliateId = authorization.getAffiliateId();
		dto.status = authorization.getStatus().toString();
		if(authorization.getApprovedAmount() != null) {
			dto.approvedAmount = authorization.getApprovedAmount().getAmount();
			dto.currency = authorization.getApprovedAmount().getCurrency();
		}
		return dto;
	}
	
}
