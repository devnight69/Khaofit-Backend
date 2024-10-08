package com.khaofit.khaofitservice.repository;

import com.khaofit.khaofitservice.model.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is a address repository class .
 *
 * @author kousik manik
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  List<Address> findByUserMobileNumber(String mobileNumber);

}
