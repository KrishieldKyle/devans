package com.haphor.social.service;

import com.haphor.social.dto.response.AllTechnologiesResponseDTO;
import com.haphor.social.dto.response.TechnologyResponseDTO;

public interface TechnologyService {
	
	public AllTechnologiesResponseDTO getTechnologies();
	
	public TechnologyResponseDTO getTechnology(int technologyId);
}
