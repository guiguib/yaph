package com.gbourquet.yaph.dao.generated;

import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.metier.generated.PasswordFieldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PasswordFieldMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int countByExample(PasswordFieldExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int deleteByExample(PasswordFieldExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int insert(PasswordField record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int insertSelective(PasswordField record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    List<PasswordField> selectByExample(PasswordFieldExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    PasswordField selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int updateByExampleSelective(@Param("record") PasswordField record, @Param("example") PasswordFieldExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int updateByExample(@Param("record") PasswordField record, @Param("example") PasswordFieldExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int updateByPrimaryKeySelective(PasswordField record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordField
     *
     * @mbggenerated Thu Jan 22 16:44:01 CET 2015
     */
    int updateByPrimaryKey(PasswordField record);
}