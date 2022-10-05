package io.github.andrijat98.printingmachineinfoapp.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "machines")
public class PrintingMachine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	@NotEmpty(message = "Proizvođač polje ne sme biti prazno")
	private String manufacturer;
	@NotEmpty(message = "Model polje ne sme biti prazno")
	private String model;
	private String printingTechnique;
	@Min(1)
	private String printingUnits;
	private Boolean hasPerfector;
	private String maxFormat;
	private String minFormat;
	private String impressionsPerHour;
	private String length;
	private String height;
	private String width;
	@Column(columnDefinition = "LONGTEXT")
	private String description;
	private String dateAdded;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "machine_images",
		joinColumns = {
			@JoinColumn(name = "machine_id")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "image_id")
		}
	)
	private Set<Image> machineImages;
}
