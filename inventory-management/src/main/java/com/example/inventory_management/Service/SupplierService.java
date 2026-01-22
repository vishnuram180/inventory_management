package com.example.inventory_management.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.SupplierRepository;
import com.example.inventory_management.entity.Supplier;

@Service
public class SupplierService {
	private SupplierRepository s_repo;

	public SupplierService(SupplierRepository s_repo) {
		this.s_repo = s_repo;
	}

	public List<Supplier> get() {

		return s_repo.findAll();
	}

	public Supplier add(Supplier supplier) {
		supplier.setStatus(true);
		return s_repo.save(supplier);
	}

	public Supplier update(Long id, Supplier supplier) {
		Supplier existingSupplier = s_repo.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
		existingSupplier.setSupplier_name(supplier.getSupplier_name());
		existingSupplier.setEmail(supplier.getEmail());
		existingSupplier.setStatus(supplier.isStatus());
		return s_repo.save(existingSupplier);
	}

	public void delete(Long id) {
		s_repo.deleteById(id);

	}

}
