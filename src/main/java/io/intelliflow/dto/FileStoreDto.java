package io.intelliflow.dto;

import java.nio.ByteBuffer;
import java.util.UUID;

public class FileStoreDto {

    private String name;

    //shares the count of partitions, difference from model
    private int orderKeyCount;

    private ByteBuffer partition;

    private UUID id;

    private String status;

    public FileStoreDto() {
    }

    public FileStoreDto(String name, int orderKeyCount, ByteBuffer partition, UUID id, String status) {
        this.name = name;
        this.orderKeyCount = orderKeyCount;
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

    public int getOrderKeyCount() {
        return orderKeyCount;
    }

    public void setOrderKeyCount(int orderKeyCount) {
        this.orderKeyCount = orderKeyCount;
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
}

