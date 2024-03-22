package bourra.regionsservice.services;

import bourra.regionsservice.dto.requests.RegionReq;
import bourra.regionsservice.dto.responses.RegionRes;
import bourra.regionsservice.entities.Region;

import java.util.UUID;

public interface RegionService extends Service<Region, Integer, RegionReq, RegionRes> {

    RegionRes update(Region user);
    boolean delete(Integer id);
}
