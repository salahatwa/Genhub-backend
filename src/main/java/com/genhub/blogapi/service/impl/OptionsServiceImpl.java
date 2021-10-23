
package com.genhub.blogapi.service.impl;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genhub.blogapi.service.OptionsService;

/**
 * @author ssatwa Oct 16, 2021
 */
@Service
public class OptionsServiceImpl implements OptionsService {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public void initSettings(Resource resource) {
		Session session = entityManager.unwrap(Session.class);
		session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
	}

}
