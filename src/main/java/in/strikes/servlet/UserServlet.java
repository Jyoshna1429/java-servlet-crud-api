package in.strikes.servlet;

import in.strikes.Model.User;
import in.strikes.Service.UserService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    // CREATE USER
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        // Check whether required fields are missing
        if (idParam == null || idParam.isBlank()
                || name == null || name.isBlank()
                || email == null || email.isBlank()
                || mobile == null || mobile.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"message\":\"Some fields are missing\"}"
            );

            return;
        }

        // Convert ID from String to Integer
        Integer id;

        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"message\":\"ID must be a valid integer\"}"
            );

            return;
        }

        // Create User object
        User user = new User(id, name, email, mobile);

        // Store the user
        User createdUser = userService.createUser(user);

        // Send successful response
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");

        response.getWriter().write(userToJson(createdUser));
    }


    // GET ALL USERS OR GET USER BY ID
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        String idParam = request.getParameter("id");

        // If ID is not provided, fetch all users
        if (idParam == null || idParam.isBlank()) {

            List<User> users = userService.getAllUsers();

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");

            response.getWriter().write(usersToJson(users));

            return;
        }

        // Convert ID from String to Integer
        Integer id;

        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"message\":\"ID must be a valid integer\"}"
            );

            return;
        }

        // Find user by ID
        User user = userService.getUserById(id);

        // If user does not exist
        if (user == null) {

            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"message\":\"User not found\"}"
            );

            return;
        }

        // If user exists
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        response.getWriter().write(userToJson(user));
    }


    // UPDATE USER (NOT IMPLEMENTED YET)
    @Override
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        // Check whether required fields are missing
        if (idParam == null || idParam.isBlank()
                || name == null || name.isBlank()
                || email == null || email.isBlank()
                || mobile == null || mobile.isBlank()) {
            response.setStatus(400);
            response.setContentType("application/json");
            response.getWriter().write(
              "{\"message\":\" Some fields are missing.\" "
            );
        }
        Integer id;
        try
        {
          id = Integer.parseInt(idParam);
        }
        catch (NumberFormatException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(
                  "{\"message\" : \" Id must be a valid Integer.\"}"
            );
            return;
        }
        User Updateduser = new User(id,name,email,mobile);
        User res = userService.updateUser(Updateduser);

        if(res == null) {
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"message\":\"User not found.\"}"
            );
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(
                userToJson(res)
        );
    }


    // DELETE USER (NOT IMPLEMENTED YET)
    @Override
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String idParam = request.getParameter("id");
        if(idParam == null) {
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"message\":\"Id must required.\"}"
            );
        }
        Integer id;
        try
        {
            id = Integer.parseInt(idParam);
        }
        catch (NumberFormatException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\" : \" Id must be a valid Integer.\"}"
            );
            return;
        }
        User deletedUser = userService.deleteUser(id);
        if(deletedUser == null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"message\":\"User not found.\"}"
            );
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/josn");
        response.getWriter().write(userToJson(deletedUser));
    }


    // CONVERT ONE USER INTO JSON
    private String userToJson(User user) {

        return "{\n" +
                "  \"id\": " + user.getId() + ",\n" +
                "  \"name\": \"" + escapeJson(user.getName()) + "\",\n" +
                "  \"email\": \"" + escapeJson(user.getEmail()) + "\",\n" +
                "  \"mobile\": \"" + escapeJson(user.getMobile()) + "\"\n" +
                "}";
    }


    // CONVERT MULTIPLE USERS INTO A JSON ARRAY
    private String usersToJson(List<User> users) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        for (int i = 0; i < users.size(); i++) {

            stringBuilder.append(userToJson(users.get(i)));

            if (i < users.size() - 1) {
                stringBuilder.append(",");
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }


    // HANDLE SPECIAL CHARACTERS IN JSON STRINGS
    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}