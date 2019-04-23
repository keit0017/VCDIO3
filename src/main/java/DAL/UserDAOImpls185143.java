package DAL;

import DAL.DTO.IUserDTO;
import DAL.DTO.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpls185143 implements IUserDAO {

    private Connection createConnection() throws SQLException {

        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185143?"
                + "user=s185143&password=hYfJLbbto4EHaStuKJn1m");

    }

    @Override
    public void createUser(IUserDTO user) throws DALException {

        try(Connection connection = createConnection()){

            PreparedStatement statement = connection.prepareStatement("INSERT INTO usersdb VALUES (?,?,?);");
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getIni());
            statement.execute();

            PreparedStatement statementRoles = connection.prepareStatement("INSERT INTO rolesdb VALUES (?,?);");

            for (int i = 0; i < user.getRoles().size(); i++){
                statementRoles.setInt(1, user.getUserId());
                statementRoles.setString(2, user.getRoles().get(i));
                statementRoles.execute();
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public IUserDTO getUser(int userId) throws DALException {

        try (Connection connection = createConnection()){
            IUserDTO user = new UserDTO();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM usersdb WHERE ID = ?;");
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                user.setUserId(resultSet.getInt("ID"));
                user.setUserName(resultSet.getString("name"));
                user.setIni(resultSet.getString("ini"));
            }

            PreparedStatement statementRoles = connection.prepareStatement("SELECT * FROM rolesdb WHERE ID = ?;");
            statementRoles.setInt(1, userId);
            ResultSet resultSetRoles = statementRoles.executeQuery();
            List<String> roles = new ArrayList<>();

            while(resultSetRoles.next()) {
                roles.add(resultSetRoles.getString("role"));
            }
            user.setRoles(roles);

            return user;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }



    @Override
    public List<IUserDTO> getUserList() throws DALException {

        try(Connection connection = createConnection()){
            List<IUserDTO> userlist = new ArrayList<>();
            List<String> roles = new ArrayList<>();
            IUserDTO user = new UserDTO();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM usersdb;");
            PreparedStatement statementRoles = connection.prepareStatement("SELECT * FROM rolesdb;");
            ResultSet resultSet = statement.executeQuery();
            ResultSet resultSetRoles = statementRoles.executeQuery();

            while(resultSet.next()){
                roles.clear();
                user.setUserId(resultSet.getInt("ID"));
                user.setUserName(resultSet.getString("name"));
                user.setIni(resultSet.getString("ini"));

                resultSetRoles.first();
                do {
                    if (user.getUserId() == resultSetRoles.getInt(1)){
                        roles.add(resultSetRoles.getString(2));
                    }
                } while (resultSetRoles.next());

                user.setRoles(roles);

                userlist.add(user);
            }
            return userlist;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }



    @Override
    public void updateUser(IUserDTO user) throws DALException {

        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE usersdb SET name = ?, ini = ? WHERE ID = ?;");
            PreparedStatement statementRoles = connection.prepareStatement("INSERT INTO rolesdb VALUES (?,?);");
            PreparedStatement statementDeleteRoles = connection.prepareStatement("DELETE FROM rolesdb WHERE ID = ?;");

            statementDeleteRoles.setInt(1, user.getUserId());
            statementDeleteRoles.execute();

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getIni());
            statement.setInt(3, user.getUserId());
            statement.executeUpdate();

            for (int i = 0; i < user.getRoles().size(); i++){
                statementRoles.setInt(1, user.getUserId());
                statementRoles.setString(2, user.getRoles().get(i));
                statementRoles.execute();
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {

        try(Connection connection = createConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM usersdb WHERE ID = ?;");
            PreparedStatement statementRoles = connection.prepareStatement("DELETE FROM rolesdb WHERE ID = ?;");

            statement.setInt(1,userId);
            statementRoles.setInt(1,userId);

            statement.execute();
            statementRoles.execute();

        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}