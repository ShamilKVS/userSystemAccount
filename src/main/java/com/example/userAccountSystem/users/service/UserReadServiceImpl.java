package com.example.userAccountSystem.users.service;

import com.example.userAccountSystem.users.data.ProductQuantity;
import com.example.userAccountSystem.users.data.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserReadServiceImpl implements UserReadService{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserReadServiceImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDto getUserById(final Long userId){

        UserMapper mapper = new UserMapper(this);
        String sql = mapper.schema();
        sql += " where id = ?";

        return jdbcTemplate.queryForObject(sql,mapper,userId);
    }

    @Override
    public List<UserDto> getAllUsers(){

        UserMapper mapper = new UserMapper(this);
        String sql = mapper.schema();


        return jdbcTemplate.query(sql,mapper);
    }

    private static final class UserMapper implements RowMapper<UserDto> {


        private final String schema;

        private final UserReadServiceImpl userReadService;
        UserMapper(final UserReadServiceImpl userReadService){
            this.userReadService = userReadService;
            String sql = "select * from users ";
            this.schema = sql;
        }
        public String schema() {
            return this.schema;
        }

        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final String firstName = rs.getString("first_name");
            final String lastName = rs.getString("last_name");
            final String fullName = rs.getString("display_name");
            final BigDecimal balanace = rs.getBigDecimal("balance");
            List<ProductQuantity> productQuantities = this.userReadService.getUserProducts(id);
            return new UserDto(id,firstName,lastName,fullName,balanace,productQuantities);
        }
    }

    public List<ProductQuantity> getUserProducts(final Long userId){
        UserProductMapper mapper = new UserProductMapper();
        return this.jdbcTemplate.query(mapper.schema(),mapper,userId);
    }

    private static final class UserProductMapper implements RowMapper<ProductQuantity>{
        private final String schema;
        UserProductMapper(){
            String sql = "select product_id,sum(quantity) as productquantity from user_product_quantities where user_id = ?" +
                    " group by product_id";
            this.schema = sql;
        }
        public String schema() {
            return this.schema;
        }

        @Override
        public ProductQuantity mapRow(ResultSet rs, int rowNum) throws SQLException {

            final Long productId = rs.getLong("product_id");
            final  Integer productQuantity = rs.getInt("productquantity");

            return new ProductQuantity(productId,productQuantity);
        }
    }
}
