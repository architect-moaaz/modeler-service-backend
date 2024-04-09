package io.intelliflow.service;

import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ExecutionInfo;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.intelliflow.dao.FileStoreDao;
import io.intelliflow.dto.FileStoreDto;
import io.intelliflow.model.FileData;
import io.intelliflow.model.FileStore;
import io.intelliflow.support.FileOperations;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class FileStoreServiceTest {

    @Inject
    FileStoreService service;

    @BeforeEach
    public void setup() {
        FileStoreDao mockDao = Mockito.mock(FileStoreDao.class);
        FileStore fileStore = new FileStore();
        Mockito.when(mockDao.insert(fileStore, 100)).thenReturn(fileStore);

        fileStore.setId(UUID.randomUUID());
        fileStore.setName("testFile.txt");
        fileStore.setStatus("Pending");
        fileStore.setOrderkey(1);
        fileStore.setPartition(ByteBuffer.wrap("Hello World".getBytes(StandardCharsets.UTF_8)));
        List<FileStore> list = new ArrayList<FileStore>();
        list.add(fileStore);
        PagingIterable<FileStore> newFile = new PagingIterable<FileStore>() {
            @NonNull
            @Override
            public ColumnDefinitions getColumnDefinitions() {
                return null;
            }

            @NonNull
            @Override
            public List<ExecutionInfo> getExecutionInfos() {
                return null;
            }

            @Override
            public boolean isFullyFetched() {
                return false;
            }

            @Override
            public int getAvailableWithoutFetching() {
                return 0;
            }

            @Override
            public boolean wasApplied() {
                return false;
            }

            @Override
            public Iterator<FileStore> iterator() {
                return list.listIterator();
            }
        };
        Mockito.when(mockDao.findByName("testFile.txt")).thenReturn(newFile);

    }

    @Test
    void savePartition() {
            // File file = File.createTempFile("testFile", ".txt");
            File file = FileOperations.createFile("testFile", "txt");
            FileOperations.writeToFile(file, "This is test content");
            Path path = Paths.get(file.toURI());
            UUID id = UUID.randomUUID();
            FileStoreDto fileStoreDto = new FileStoreDto("testFile.txt", 0, null,id, "Pending");

            FileStoreDto response = service.savePartition(path, "testFile.txt");
            assertEquals(response.getName(), fileStoreDto.getName());
    }

    @Test
    void savePartitionException() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                FileStoreDto response = service.savePartition(null, "test");
            }
        });
    }

    @Test
    void getFiles() {
        service.getFiles("testFile.txt");
    }
}