package io.github.andrijat98.printingmachineinfoapp.resources;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.andrijat98.printingmachineinfoapp.models.Image;
import io.github.andrijat98.printingmachineinfoapp.models.PrintingMachine;
import io.github.andrijat98.printingmachineinfoapp.service.MachineService;
import io.github.andrijat98.printingmachineinfoapp.utils.FileUploadUtil;

@RestController
@RequestMapping("/machines")
public class PrintingMachineResource {
	
	private final MachineService machineService;
	
	public PrintingMachineResource(MachineService machineService) {
		this.machineService = machineService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<PrintingMachine>> getAllPrintingMachines () {
		List<PrintingMachine> machines = machineService.findAllMachines();
		return new ResponseEntity<>(machines, HttpStatus.OK);
	}
	
	@GetMapping("/machine/{id}")
	public ResponseEntity<PrintingMachine> getMachineById (@PathVariable Long id) {
		PrintingMachine machine = machineService.findPritningMachineById(id);
		return new ResponseEntity<>(machine, HttpStatus.OK);
	}
	
	@PostMapping(value = {"/add"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<PrintingMachine> addMachine(@RequestPart("machine") PrintingMachine machine,
			@RequestPart("imageFile") MultipartFile[] file) throws IOException {
		
		try {
			
			PrintingMachine addedMachine = machineService.addMachine(machine);
			Set<Image> images = uploadImage(file, addedMachine);
			addedMachine.setMachineImages(images);
			machineService.updateMachine(addedMachine);
			
			return new ResponseEntity<>(addedMachine, HttpStatus.CREATED);
			
		} catch (Exception e) {
			//make an exception message response
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<PrintingMachine> updateMachine(@RequestBody PrintingMachine machine) {
		PrintingMachine updatedMachine = machineService.updateMachine(machine);
		return new ResponseEntity<>(updatedMachine, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMachine(@PathVariable Long id) {
		machineService.deleteMachine(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public Set<Image> uploadImage(MultipartFile[] multipartFiles, PrintingMachine machine) throws IOException {
		Set<Image> imageModels = new HashSet<>();
		for (MultipartFile file: multipartFiles) {
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String uploadDir = "user-photos/" + machine.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, file);
			
			Image imageModel = new Image(
					file.getOriginalFilename().toString(),
					file.getContentType().toString(),
					"http://localhost:8080/" + uploadDir + "/" + fileName
					//file.getBytes()
					
					);
			imageModels.add(imageModel);
		}
		
		return imageModels;
	}
	
}
