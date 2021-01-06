package com.haphor.social.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.haphor.social.util.constants.NotificationAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Action {

	@Id
	@Enumerated(EnumType.STRING)
	private NotificationAction action;
	private String message;
}
