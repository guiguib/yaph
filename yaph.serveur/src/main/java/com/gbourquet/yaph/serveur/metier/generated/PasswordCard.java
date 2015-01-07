package com.gbourquet.yaph.serveur.metier.generated;

import com.gbourquet.yaph.serveur.metier.BaseDto;
import java.io.Serializable;

public class PasswordCard extends BaseDto implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordCard.id
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordCard.adresse
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private String adresse;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordCard.user
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private String user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordCard.password
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordCard.account
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private Integer account;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column passwordCard.titre
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private String titre;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table passwordCard
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordCard.id
     *
     * @return the value of passwordCard.id
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordCard.id
     *
     * @param id the value for passwordCard.id
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordCard.adresse
     *
     * @return the value of passwordCard.adresse
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordCard.adresse
     *
     * @param adresse the value for passwordCard.adresse
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse == null ? null : adresse.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordCard.user
     *
     * @return the value of passwordCard.user
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getUser() {
        return user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordCard.user
     *
     * @param user the value for passwordCard.user
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordCard.password
     *
     * @return the value of passwordCard.password
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordCard.password
     *
     * @param password the value for passwordCard.password
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordCard.account
     *
     * @return the value of passwordCard.account
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public Integer getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordCard.account
     *
     * @param account the value for passwordCard.account
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setAccount(Integer account) {
        this.account = account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column passwordCard.titre
     *
     * @return the value of passwordCard.titre
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public String getTitre() {
        return titre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column passwordCard.titre
     *
     * @param titre the value for passwordCard.titre
     *
     * @mbggenerated Wed Jan 07 10:47:53 CET 2015
     */
    public void setTitre(String titre) {
        this.titre = titre == null ? null : titre.trim();
    }
}