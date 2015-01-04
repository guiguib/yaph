package com.gbourquet.yaph.serveur.util;

import java.io.Serializable;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gbourquet.yaph.serveur.service.Service;

/**
 * Class BeanFactory. Classe utilitaires pour créer les beans Spring (singleton)
 * @author guillaume
 */
public final class BeanFactory extends ClassPathXmlApplicationContext {

    /**
     * Instance singleton de la classe.
     */
    private static BeanFactory instance;

    /**
     * Constructeur.
     */
    private BeanFactory() {
        super("serveurContext.xml");
    }

    /**
     * methode d'accès à l'instance singleton.
     * @return l'instance singleton de la classe
     */
    public static BeanFactory getInstance() {
        if (instance == null) {
            instance = new BeanFactory();
        }
        return instance;
    }

    /**
     * Methode pour instancier le service voulu.
     * @param name
     *            nom du service
     * @return Service
     */
    public Service getService(final String name) {

        return (Service) getBean(name);
    }
    
    /**
     * Methode pour instancier l'objet metier voulu.
     * @param name
     *            nom de l'objet metier
     * @return Serializable
     */
    public Serializable getMetier(final String name) {

        return (Serializable) getBean(name);
    }

}
