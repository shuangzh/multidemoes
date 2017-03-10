package com.shuangzh.dao.mybatis.typehandlers;

import com.shuangzh.dao.mybatis.domain.PhoneNumber;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 2017/3/9.
 */
public class PhoneTypeHandler extends BaseTypeHandler<PhoneNumber> {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PhoneNumber parameter, JdbcType jdbcType) throws SQLException {
        logger.info("transform PhoneNumber Object to column phone String");
        ps.setString(i, parameter.getAsString());
    }

    @Override
    public PhoneNumber getNullableResult(ResultSet rs, String columnName) throws SQLException {
        logger.info("getNullableResult(ResultSet rs, String columnName) -> transform column [{}]  String [{}] to PhoneNumber Object", columnName, rs.getString(columnName));
        return new PhoneNumber(rs.getString(columnName));
    }

    @Override
    public PhoneNumber getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        logger.info("getNullableResultt(ResultSet rs, int columnIndex) -> transform column [{}]  String [{}] to PhoneNumber Object", columnIndex, rs.getString(columnIndex));
        return new PhoneNumber(rs.getString(columnIndex));
    }

    @Override
    public PhoneNumber getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        logger.info("getNullableResult(CallableStatement cs, int columnIndex) -> transform column [{}]  String [{}] to PhoneNumber Object", columnIndex, cs.getString(columnIndex));
        return new PhoneNumber(cs.getString(columnIndex));
    }
}
