package models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



public abstract class AbstractDomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
}