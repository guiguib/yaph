package com.gbourquet.yaph.dao.generated;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PasswordCardMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int countByExample(PasswordCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int deleteByExample(PasswordCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int insert(PasswordCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int insertSelective(PasswordCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    List<PasswordCard> selectByExample(PasswordCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    PasswordCard selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int updateByExampleSelective(@Param("record") PasswordCard record, @Param("example") PasswordCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int updateByExample(@Param("record") PasswordCard record, @Param("example") PasswordCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int updateByPrimaryKeySelective(PasswordCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    int updateByPrimaryKey(PasswordCard record);
}