package bourra.usersservice.services;

import bourra.usersservice.dto.Region;
import bourra.usersservice.dto.requests.UserReq;
import bourra.usersservice.dto.responses.UserRes;
import bourra.usersservice.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService extends Service<User, UUID, UserReq, UserRes> {




    Region getRegionUser(UUID id);

    UserRes getByEmail(Integer id);
    UserRes update(User user);
    boolean delete(Integer id);
    User auth(String email, String password);
}
