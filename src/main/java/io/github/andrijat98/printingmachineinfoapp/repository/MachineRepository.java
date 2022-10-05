package io.github.andrijat98.printingmachineinfoapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.andrijat98.printingmachineinfoapp.models.PrintingMachine;

public interface MachineRepository extends JpaRepository<PrintingMachine, Long>{

	void deletePrintingMachineById(Long id);

	Optional<PrintingMachine> findPrintingMachineById(Long id);
	
}
