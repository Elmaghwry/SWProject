package com.sweproject.swproject.Repositorys;

import com.sweproject.swproject.Entities.ProductEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<ProductEntity,Integer> {
    /*@Modifying(clearAutomatically = true)
    @Query("UPDATE ProductEntity p SET p.num_of_views = :num_of_views WHERE p.id = :PId")
    int updateAddress(@Param("PId") int PId, @Param("num_of_views") int num_of_views);*/
}
