package br.com.dwerp.hibernate.generico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.dwerp.dao.generico.DAOGenerico;


public class DAOGenericoHibernate<E> implements DAOGenerico<E>, Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	protected EntityManager manager;
	private Class classeEntidade;
	
	public DAOGenericoHibernate(Class classeEntidade){
		this.classeEntidade = classeEntidade;
	}

	@Override
	public E salvar(E e) {
		manager.persist(e);
		return e;
	}
	
	
	@Override
	public E alterar(E e) {
		return manager.merge(e);
	}

	@Override
	public boolean excluir(Integer id) {
		E e = consultar(id);
		manager.remove(e);
		return true;
	}

	
	@Override
	public E consultar(Integer id) {
		return (E) manager.find(classeEntidade, id);
	}

	
	@Override
	public List<E> consultar() {
		return manager.createQuery("from "+classeEntidade.getSimpleName()).getResultList();
	}
	
	@Override
	public List<E> consultar_ativos() {		
		return manager.createQuery("from "+classeEntidade.getSimpleName()+" where situacao = true").getResultList();
	}	
	
}
