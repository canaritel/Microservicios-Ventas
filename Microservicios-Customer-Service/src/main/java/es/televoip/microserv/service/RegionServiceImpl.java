package es.televoip.microserv.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.apache.commons.lang3.StringUtils;
import es.televoip.microserv.entity.Region;
import es.televoip.microserv.repository.RegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // ideal para inyectar objetos por constructor
@Transactional
@Validated
public class RegionServiceImpl implements RegionService {

	private final RegionRepository regionRepository;

	@Override
	public Region getRegion(Long id) {
		return regionRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Region Id: La Región con ID " + id + " no se encuentra"));
	}

	@Override
	public Optional<Region> getRegionByName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Region name: El nombre de la región no puede ser nulo o vacío.");
		}

		String strippedName = StringUtils.stripAccents(name.trim()); // quitamos tildes y espacios en blanco
		return regionRepository.findByNameIgnoreCase(strippedName);
	}

	@Override
	public Region createRegion(Region region) {
		checkREgionNumberAvailability(region.getName());

		return regionRepository.save(region);
	}

	@Override
	public Region updateRegion(Region region) {
		Region regionDB = this.getRegion(region.getId());

		regionDB.setName(region.getName());
		return regionRepository.save(regionDB);
	}

	@Override
	public void deleteRegion(Region region) {
		Optional<Region> customerOptional = regionRepository.findById(region.getId());
		if (!customerOptional.isPresent()) {
			throw new EntityNotFoundException("RegionId: Region with ID " + region.getId() + " not found");
		}

		regionRepository.delete(customerOptional.get());
	}

	private void checkREgionNumberAvailability(String nameRegion) {
		String strippedName = StringUtils.stripAccents(nameRegion).trim();
		regionRepository.findByNameIgnoreCase(strippedName).ifPresent(invoice -> {
			throw new DataIntegrityViolationException(
					"Region name: La Región con nombre " + strippedName + " ya existe.");
		});
	}

}
