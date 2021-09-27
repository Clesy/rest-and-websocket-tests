package dao;

import domain.spin.SpinDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.SqlQueries.USERS_SPIN;

public class SpinDao {
    private final Connection connection;

    public SpinDao(Connection connection) {
        this.connection = connection;
    }

    public List<SpinDb> getSpinReward(int userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(USERS_SPIN)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<SpinDb> userSpinDbList = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int amount = resultSet.getInt("amount");
                    long spinDate = resultSet.getLong("spin_date");

                    userSpinDbList.add(new SpinDb(id, amount, spinDate));
                }
                return userSpinDbList;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
}
