package qlch.dao;

import java.util.List;

/**
 *
 * @author PC
 */
abstract public class MainDAO<EntityType, KeyType> {

    abstract public void insert(EntityType entity);

    abstract public void update(EntityType entity);

    abstract public void delete(KeyType id);

    abstract public List<EntityType> selectAll();

    abstract public EntityType selectById(KeyType id);

    abstract protected List<EntityType> selectBySql(String sql, Object... args);
}
