package io.github.andrijat98.printingmachineinfoapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.andrijat98.printingmachineinfoapp.exceptions.MachineNotFoundException;
import io.github.andrijat98.printingmachineinfoapp.models.PrintingMachine;
import io.github.andrijat98.printingmachineinfoapp.repository.MachineRepository;

@Service
public class MachineService {
	
	@Autowired
	private final MachineRepository machineRepo;
	
	public MachineService(MachineRepository machineRepo) {
		this.machineRepo = machineRepo;
	}
	
	public PrintingMachine addMachine(PrintingMachine machine) {
		machine.setDateAdded(LocalDateTime.now().format(DateTimeFormatter.ofPattern("\"dd-MM-yyyy\"")));
		return machineRepo.save(machine);
	}
	
	public List<PrintingMachine> findAllMachines() {
		return machineRepo.findAll();
	}
	
	public PrintingMachine updateMachine(PrintingMachine machine) {
		return machineRepo.save(machine);
	}
	
	public PrintingMachine findPritningMachineById(Long id) {
		return machineRepo.findPrintingMachineById(id)
				.orElseThrow(() -> new MachineNotFoundException("Machine with ID" + id + " was not found."));
	}
	
	@Transactional
	public void deleteMachine(Long id) {
		machineRepo.deletePrintingMachineById(id);
	}
	
}
