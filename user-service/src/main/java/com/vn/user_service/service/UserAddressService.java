// UserAddressService.java
package com.vn.user_service.service;

import com.vn.user_service.domain.UserAddress;
import com.vn.user_service.domain.UserProfile;
import com.vn.user_service.dto.UserAddressDTO;
import com.vn.user_service.repository.UserAddressRepository;
import com.vn.user_service.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserProfileRepository userProfileRepository;

    public UserAddressService(UserAddressRepository userAddressRepository,
            UserProfileRepository userProfileRepository) {
        this.userAddressRepository = userAddressRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserAddress> findAll() {
        return userAddressRepository.findAll();
    }

    public List<UserAddress> findByUserId(Long userId) {
        return userAddressRepository.findByUserId(userId);
    }

    public UserAddress create(UserAddressDTO dto) {
        UserProfile user = userProfileRepository.findById(dto.userId).orElseThrow();
        UserAddress addr = new UserAddress();
        addr.setUser(user);
        addr.setProvince(dto.province);
        addr.setDistrict(dto.district);
        addr.setWard(dto.ward);
        addr.setStreet(dto.street);
        addr.setIsDefault(dto.isDefault);
        addr.setPhone(dto.phone);
        addr.setReceiverName(dto.receiverName);
        return userAddressRepository.save(addr);
    }

    public UserAddress update(Long id, UserAddressDTO dto) {
        UserAddress addr = userAddressRepository.findById(id).orElseThrow();
        addr.setProvince(dto.province);
        addr.setDistrict(dto.district);
        addr.setWard(dto.ward);
        addr.setStreet(dto.street);
        addr.setIsDefault(dto.isDefault);
        addr.setPhone(dto.phone);
        addr.setReceiverName(dto.receiverName);
        return userAddressRepository.save(addr);
    }

    public void delete(Long id) {
        userAddressRepository.deleteById(id);
    }
}
