package bourra.usersservice.clients;

import bourra.usersservice.dto.HttpResponse;
import bourra.usersservice.dto.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "REGIONS-SERVICE")
public interface RegionRestClient {

    @GetMapping("/tourism/api/v1/regions/{id}")
    HttpResponse findRegionById(@PathVariable int id);

    @GetMapping("/tourism/api/v1/regions")
    HttpResponse findAllRegions();
}
