package com.ctrip.flight.faq.dal.entity;

import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Database(name = "dalservice4db_dalcluster")
@Table(name = "user")
public class DalUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.BIGINT)
    private Long id;

    @Column(name = "name")
    @Type(Types.VARCHAR)
    private String name;

    @Column(name = "age")
    @Type(Types.INTEGER)
    private Integer age;

    @Column(name = "datachange_lasttime")
    @Type(Types.TIMESTAMP)
    private Timestamp dataChangeLastTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Timestamp getDataChangeLastTime() {
        return dataChangeLastTime;
    }

    public void setDataChangeLastTime(Timestamp dataChangeLastTime) {
        this.dataChangeLastTime = dataChangeLastTime;
    }

    @Override
    public String toString() {
        return "DalUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dataChangeLastTime=" + dataChangeLastTime +
                '}';
    }
}
