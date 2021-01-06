package com.haphor.social.util.converter.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.haphor.social.dto.TechnologyDTO;
import com.haphor.social.dto.TitleDTO;
import com.haphor.social.model.Technology;
import com.haphor.social.model.Title;

@Component
public class ConvertDTO {
	
	/**
	 * This method will convert the given set of Technology DTO into a set of Technology Entities
	 * @param technologyDTOs
	 * @return
	 */
	public Set<Technology> toTechnologyEntities(Set<TechnologyDTO> technologyDTOs){
		
		Set<Technology> newTechnologies = new HashSet<>();
		
		
		for(TechnologyDTO technologyDTO : technologyDTOs) {
			
			newTechnologies.add(new Technology(technologyDTO.getTechnologyId(), technologyDTO.getName()));
		}
		return newTechnologies;
		
	}
	
	
	/**
	 * This method will convert the given set of Title DTO into a set of Technology Entities
	 * @param titleDTOs
	 * @return
	 */
	public Set<Title> toTitleEntities(Set<TitleDTO> titleDTOs){
		
		Set<Title> newTitles = new HashSet<>();
		
		
		for(TitleDTO titleDTO : titleDTOs) {
			
			newTitles.add(new Title(titleDTO.getTitleId(), titleDTO.getName()));
		}
		return newTitles;
		
	}

}
