package es.televoip.microserv.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.televoip.microserv.entity.Region;
import es.televoip.microserv.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/regions")
@RequiredArgsConstructor
public class RegionController {

	private final RegionService regionService;

	@PostMapping
	public ResponseEntity<Region> createRegion(@Valid @RequestBody Region region) {
		log.info("Creating Region: {}", region);

		// al CREAR se comprueba el name, si ya existe se devuelve una excepción
		Region regionCreate = regionService.createRegion(region);
		return ResponseEntity.status(HttpStatus.CREATED).body(regionCreate);
	}

}
