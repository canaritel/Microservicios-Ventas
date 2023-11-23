package es.televoip.microserv.service;

import java.util.Optional;

import es.televoip.microserv.entity.Region;

public interface RegionService {

	public Region getRegion(Long id);

	public Optional<Region> getRegionByName(String name);

	public Region createRegion(Region region);

	public Region updateRegion(Region region);

	public void deleteRegion(Region region);

}
