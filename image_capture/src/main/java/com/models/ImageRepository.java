package com.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by alex.bichovsky on 2/13/2017.
 */
@Transactional
public interface ImageRepository extends CrudRepository<ImageModel, Long>{

    public ImageModel findByName(String name);
}
