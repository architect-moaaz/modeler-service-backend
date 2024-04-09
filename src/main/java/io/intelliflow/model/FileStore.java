package io.intelliflow.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

@Entity
@NamingStrategy(convention = NamingConvention.SNAKE_CASE_INSENSITIVE)
@PropertyStrategy(mutable = false)
public class FileStore {

    @PartitionKey
    private String name;

    @ClusteringColumn
    private int orderkey;

    private ByteBuffer partition;

    private UUID id;

    private String status;

    public FileStore() {
    }

    public FileStore(String name, int orderkey, ByteBuffer partition, UUID id, String status) {
        this.name = name;
        this.orderkey = orderkey;
        this.partition = partition;
        this.id = id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderkey() {
        return orderkey;
    }

    public void setOrderkey(int orderkey) {
        this.orderkey = orderkey;
    }

    public ByteBuffer getPartition() {
        return partition;
    }

    public void setPartition(ByteBuffer partition) {
        this.partition = partition;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileStore fileStore = (FileStore) o;
        return orderkey == fileStore.orderkey && Objects.equals(name, fileStore.name) && Objects.equals(partition, fileStore.partition) && Objects.equals(id, fileStore.id) && Objects.equals(status, fileStore.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, orderkey, partition, id, status);
    }
}

