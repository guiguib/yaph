package com.gbourquet.yaph.serveur.metier.generated;

import com.gbourquet.yaph.serveur.metier.BaseDto;
import java.io.Serializable;

public class PasswordField extends BaseDto implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordField.id
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordField.idCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private Integer idCard;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordField.type
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordField.libelle
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private String libelle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordField.value
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table passwordField
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordField.id
     *
     * @return the value of passwordField.id
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordField.id
     *
     * @param id the value for passwordField.id
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordField.idCard
     *
     * @return the value of passwordField.idCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public Integer getIdCard() {
        return idCard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordField.idCard
     *
     * @param idCard the value for passwordField.idCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setIdCard(Integer idCard) {
        this.idCard = idCard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordField.type
     *
     * @return the value of passwordField.type
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordField.type
     *
     * @param type the value for passwordField.type
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordField.libelle
     *
     * @return the value of passwordField.libelle
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordField.libelle
     *
     * @param libelle the value for passwordField.libelle
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle == null ? null : libelle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordField.value
     *
     * @return the value of passwordField.value
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordField.value
     *
     * @param value the value for passwordField.value
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}