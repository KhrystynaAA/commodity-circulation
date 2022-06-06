package sample.services.user;

import sample.model.Role;
import sample.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> users();
    String getRoleName(int userId);
    Optional<User> getUser(String username, String password);
    Optional<User> add(String username, String password);
    List<User> users(int productId);
}
