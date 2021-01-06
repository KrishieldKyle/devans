package com.haphor.social.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.TechnologyDAO;
import com.haphor.social.dto.TechnologyDTO;
import com.haphor.social.dto.response.AllTechnologiesResponseDTO;
import com.haphor.social.dto.response.TechnologyResponseDTO;
import com.haphor.social.model.Technology;
import com.haphor.social.util.converter.entity.ConvertEntity;

@Service
public class TechnologyServiceImpl implements TechnologyService {
	
	@Autowired
	private TechnologyDAO technologyDao;
	
	@Autowired
	private ConvertEntity convertEntity;

	@Override
	public AllTechnologiesResponseDTO getTechnologies() {
		
		Set<Technology> technologies = new HashSet<>(technologyDao.findAll());
		
		Set<TechnologyDTO> technologyDtos = convertEntity.toTechnologyDTOs(technologies);
		
		return new AllTechnologiesResponseDTO(technologyDtos, true, "Technologies successfully fetched", HttpStatus.OK);
	}

	@Override
	public TechnologyResponseDTO getTechnology(int technologyId) {
		
		Optional<Technology> maybeTechnology = technologyDao.findById(technologyId);
		
		if(maybeTechnology.isPresent()) {
			TechnologyDTO technologyDto = convertEntity.toTechnologyDTO(maybeTechnology.get());
			
			return new TechnologyResponseDTO(true, "Technology successfully fetched", HttpStatus.OK, technologyDto);
		}
		
		
		
		return new TechnologyResponseDTO(false, "Technology not found", HttpStatus.NOT_FOUND, new TechnologyDTO());
	}

}
