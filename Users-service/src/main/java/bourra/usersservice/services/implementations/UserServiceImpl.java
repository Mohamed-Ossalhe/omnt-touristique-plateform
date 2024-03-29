package bourra.usersservice.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import bourra.usersservice.clients.RegionRestClient;
import bourra.usersservice.dto.HttpResponse;
import bourra.usersservice.dto.Region;
import bourra.usersservice.dto.requests.UserReq;
import bourra.usersservice.dto.responses.UserRes;
import bourra.usersservice.entities.User;
import bourra.usersservice.enums.Role;
import bourra.usersservice.exceptions.BadRequestException;
import bourra.usersservice.exceptions.EntityAlreadyExistsException;
import bourra.usersservice.exceptions.NotFoundException;
import bourra.usersservice.repositories.UserRepository;
import bourra.usersservice.services.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RegionRestClient regionRestClient;
    private final ModelMapper mapper;

    @Override
    public UserRes getById(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User Not Exist with the given Id : " + id));
        var reg = regionRestClient.findRegionById(user.getRegId()).getData().get("response");
        Region region = mapper.map(reg, Region.class);
        log.info("\n\nRegion : " +region.getId());
        user.setRegion(region);
        return mapper.map(user, UserRes.class);

    }

    @Override
    public Region getRegionUser(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User Not Exist with the given Id : " + id));
        var reg = regionRestClient.findRegionById(user.getRegId()).getData().get("response");
        return mapper.map(reg, Region.class);

    }


    @Override
    public Page<UserRes> getAllPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                user -> mapper.map(user, UserRes.class)
        );
    }

    @Override
    public List<UserRes> getAll() {
        //Region region = regionRestClient.findRegionById(user.getRegId());
        //log.info("\n\nRegion : "+region.getId());
        //user.setRegion(region);
        return repository.findAll().stream().map(
                user -> mapper.map(user, UserRes.class)
        ).toList();
    }

    @Override
    public UserRes create(UserReq request) {
        User existingUserEmail = repository.findByEmail(request.getEmail());

        if (Objects.nonNull(existingUserEmail)) {
            throw new EntityAlreadyExistsException("User already exists with the given Email.");
        }

        User user = mapper.map(request, User.class);
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = repository.save(user);

        return mapper.map(savedUser, UserRes.class);
    }

    @Override
    public UserRes getByEmail(Integer id) {
        return null;
    }

    @Override
    public UserRes update(User user) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public User auth(String email, String password) {
        User user = repository.findByEmail(email);
        if(Objects.isNull(user)){
            throw new NotFoundException("No User Found with this Email");
        }
        else if (!password.equals(user.getPassword())) {
            throw new BadRequestException("Incorrect Password");
        }

        return user;

    }

}
