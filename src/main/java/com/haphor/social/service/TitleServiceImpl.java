package com.haphor.social.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.TitleDAO;
import com.haphor.social.dto.TitleDTO;
import com.haphor.social.dto.response.AllTitlesResponseDTO;
import com.haphor.social.dto.response.TitleResponseDTO;
import com.haphor.social.model.Title;
import com.haphor.social.util.converter.entity.ConvertEntity;

@Service
public class TitleServiceImpl implements TitleService {
	
	@Autowired
	private TitleDAO titleDao;
	
	@Autowired
	private ConvertEntity convertEntity;

	@Override
	public AllTitlesResponseDTO getTitles() {
		
		Set<Title> titles = new HashSet<>(titleDao.findAll());
		
		Set<TitleDTO> titlesDto = convertEntity.toTitleDTOs(titles);
		
		return new AllTitlesResponseDTO(titlesDto, true, "Titles successfully fetched", HttpStatus.OK);
	}

	@Override
	public TitleResponseDTO getTitle(int titleId) {
		
		Optional<Title> maybeTitle = titleDao.findById(titleId);
		
		if(maybeTitle.isPresent()) {
			
			TitleDTO titleDto = convertEntity.toTitleDTO(maybeTitle.get());
			
			return new TitleResponseDTO(true, "Title successfully fetched", HttpStatus.OK, titleDto);
		}
		return new TitleResponseDTO(true, "Title not found", HttpStatus.NOT_FOUND, new TitleDTO());
	}

}
