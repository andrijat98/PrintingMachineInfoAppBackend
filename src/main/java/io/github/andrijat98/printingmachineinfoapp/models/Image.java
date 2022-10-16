package io.github.andrijat98.printingmachineinfoapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String ImageURL;
	
	public Image(String url) {
		this.ImageURL = url;
	}
	
	
}
