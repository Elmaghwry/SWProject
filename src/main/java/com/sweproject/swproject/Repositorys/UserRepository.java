package com.sweproject.swproject.Repositorys;

import com.sweproject.swproject.Entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {
}
