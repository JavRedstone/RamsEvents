package com.jav.ramsevents.model;
import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Events students value object
 * 
 * @author Javier Huang
 */
@Data
@NoArgsConstructor
public class EventsStudentsVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String firstName = null;
	private String lastName = null;
	private String email = null;
}
