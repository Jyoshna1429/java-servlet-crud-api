package in.strikes.Service;

import in.strikes.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    // Use Map to store user details.
    private Map<Integer, User> userDB;

    public UserService() {
        userDB = new HashMap<>();
    }

    public User createUser(User user) {
        userDB.put(user.getId(), user);
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userDB.values());
    }

    public User getUserById(Integer id) {
        return userDB.get(id);
    }

    public User updateUser(User user)
    {
        User existingUser = userDB.get(user.getId());
        if(existingUser == null)
        {
            return null;
        }
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());

        return existingUser;
    }

    public User deleteUser(Integer id)
    {
        User user = userDB.get(id);
        if(user == null)
        {
            return null;
        }
        userDB.remove(id);
        return user;
    }
}