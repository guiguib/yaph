package com.gbourquet.yaph.dao;

import org.apache.ibatis.session.SqlSession;

import com.gbourquet.yaph.dao.generated.AccountMapper;
import com.gbourquet.yaph.dao.generated.PasswordCardMapper;
import com.gbourquet.yaph.dao.generated.PasswordFieldMapper;

public class DaoFactory {
	/**
	 * SqlSession.
	 */
	private SqlSession sqlSession;

	/**
	 * Setter de sqlSession.
	 * 
	 * @param sqlSession
	 *            SqlSession
	 */
	public final void setSqlSession(final SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * Getter de sqlSession.
	 * 
	 * @return sqlSession
	 */
	public final SqlSession getSqlSession() {
		return sqlSession;
	}

	public final AccountMapper getPersonneDAO() {
		try {
			AccountMapper mapper = sqlSession.getMapper(AccountMapper.class);
			return mapper;
		} finally {
			sqlSession.close();
		}
	}
	
	public final PasswordCardMapper getPasswordDAO() {
		try {
			PasswordCardMapper mapper = sqlSession.getMapper(PasswordCardMapper.class);
			return mapper;
		} finally {
			sqlSession.close();
		}
	}
	
	public final PasswordFieldMapper getPasswordFieldDAO() {
		try {
			PasswordFieldMapper mapper = sqlSession.getMapper(PasswordFieldMapper.class);
			return mapper;
		} finally {
			sqlSession.close();
		}
	}
}
