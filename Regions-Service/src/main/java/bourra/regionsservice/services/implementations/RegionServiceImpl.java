package bourra.regionsservice.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import bourra.regionsservice.dto.requests.RegionReq;
import bourra.regionsservice.dto.responses.RegionRes;
import bourra.regionsservice.entities.Region;
import bourra.regionsservice.exceptions.NotFoundException;
import bourra.regionsservice.repositories.RegionRepository;
import bourra.regionsservice.services.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionServiceImpl implements RegionService {

    private final RegionRepository repository;
    private final ModelMapper mapper;

    @Override
    public RegionRes getById(Integer id) {
        Optional<Region> userOp = repository.findById(id);
        return userOp.map(
                user -> mapper.map(user, RegionRes.class)
        ).orElseThrow(() -> new NotFoundException("Region Not Exist with the given Id : " + id)
        );
    }

    @Override
    public Page<RegionRes> getAllPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                user -> mapper.map(user, RegionRes.class)
        );
    }

    @Override
    public List<RegionRes> getAll() {
        return repository.findAll()
                .stream()
                .map(
                        region -> mapper.map(region, RegionRes.class)
                ).toList();
    }

    @Override
    public RegionRes create(RegionReq request) {
        Region region = mapper.map(request, Region.class);

        return mapper.map(region, RegionRes.class);
    }

    @Override
    public RegionRes update(Region region) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }



}
