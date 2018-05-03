package com.sweproject.swproject.Repositorys;

import com.sweproject.swproject.Entities.ProductEntity;
import com.sweproject.swproject.Entities.StoreEntity;
import org.springframework.data.repository.CrudRepository;

public interface StoreReopsitory extends CrudRepository<StoreEntity,Integer> {
}
