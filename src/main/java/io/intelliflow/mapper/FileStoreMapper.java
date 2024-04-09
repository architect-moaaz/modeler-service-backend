package io.intelliflow.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import io.intelliflow.dao.FileStoreDao;

@Mapper
public interface FileStoreMapper {

    @DaoFactory
    FileStoreDao fileStoreDao();
}
