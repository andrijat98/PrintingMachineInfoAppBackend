package io.github.andrijat98.printingmachineinfoapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "image_model")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String ImageURL;
	private String name;
	private String type;
	
	public Image(String name, String type, String url) {
		super();
		this.name = name;
		this.type = type;
		this.ImageURL = url;
	}
	
	
}
