package com.gbourquet.yaph.serveur.metier.generated;

import com.gbourquet.yaph.serveur.metier.BaseDto;
import java.io.Serializable;

public class Account extends BaseDto implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.id
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.nom
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private String nom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.prenom
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private String prenom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.login
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private String login;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.password
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.dateActivation
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private String dateActivation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.dateDesactivation
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private String dateDesactivation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.id
     *
     * @return the value of account.id
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.id
     *
     * @param id the value for account.id
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.nom
     *
     * @return the value of account.nom
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public String getNom() {
        return nom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.nom
     *
     * @param nom the value for account.nom
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public void setNom(String nom) {
        this.nom = nom == null ? null : nom.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.prenom
     *
     * @return the value of account.prenom
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.prenom
     *
     * @param prenom the value for account.prenom
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom == null ? null : prenom.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.login
     *
     * @return the value of account.login
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public String getLogin() {
        return login;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.login
     *
     * @param login the value for account.login
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public void setLogin(String login) {
        this.login = login == null ? null : login.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.password
     *
     * @return the value of account.password
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.password
     *
     * @param password the value for account.password
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.dateActivation
     *
     * @return the value of account.dateActivation
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public String getDateActivation() {
        return dateActivation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.dateActivation
     *
     * @param dateActivation the value for account.dateActivation
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public void setDateActivation(String dateActivation) {
        this.dateActivation = dateActivation == null ? null : dateActivation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.dateDesactivation
     *
     * @return the value of account.dateDesactivation
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public String getDateDesactivation() {
        return dateDesactivation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.dateDesactivation
     *
     * @param dateDesactivation the value for account.dateDesactivation
     *
     * @mbggenerated Mon Jan 05 14:44:06 CET 2015
     */
    public void setDateDesactivation(String dateDesactivation) {
        this.dateDesactivation = dateDesactivation == null ? null : dateDesactivation.trim();
    }
}