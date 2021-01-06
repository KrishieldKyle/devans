package com.haphor.social.service;

import com.haphor.social.dto.response.AllTitlesResponseDTO;
import com.haphor.social.dto.response.TitleResponseDTO;

public interface TitleService {
	
	public AllTitlesResponseDTO getTitles();
	
	public TitleResponseDTO getTitle(int titleId);
}
