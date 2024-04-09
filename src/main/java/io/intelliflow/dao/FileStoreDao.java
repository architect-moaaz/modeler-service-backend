package io.intelliflow.dao;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import io.intelliflow.model.FileStore;

@Dao
public interface FileStoreDao {

    @Insert(ttl = ":ttl")
    FileStore insert(FileStore fileStore, int ttl);

    @Select(customWhereClause = "name = :searchString", orderBy = {"orderkey ASC"})
    PagingIterable<FileStore> findByName(String searchString);

    @Delete(entityClass = FileStore.class, customWhereClause = "name = :fileName")
    Boolean deleteFile(String fileName);
}
