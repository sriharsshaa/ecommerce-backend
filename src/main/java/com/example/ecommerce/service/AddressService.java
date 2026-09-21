package com.example.ecommerce.service;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // Get all addresses of a user
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    // Add a new address
    public Address addAddress(Address address) {

        // If this is the user's first address,
        // automatically make it the default address
        List<Address> existingAddresses =
                addressRepository.findByUserId(address.getUserId());

        if (existingAddresses.isEmpty()) {
            address.setDefault(true);
        }

        // If the new address is set as default,
        // remove default from existing addresses
        if (address.isDefault()) {
            for (Address existingAddress : existingAddresses) {
                existingAddress.setDefault(false);
            }

            addressRepository.saveAll(existingAddresses);
        }

        return addressRepository.save(address);
    }

    // Update an existing address
    public Address updateAddress(Long id, Address updatedAddress) {

        Address existingAddress =
                addressRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Address not found"));

        existingAddress.setFullName(updatedAddress.getFullName());
        existingAddress.setPhone(updatedAddress.getPhone());
        existingAddress.setAddressLine(updatedAddress.getAddressLine());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPincode(updatedAddress.getPincode());

        if (updatedAddress.isDefault()) {

            List<Address> userAddresses =
                    addressRepository.findByUserId(
                            existingAddress.getUserId()
                    );

            for (Address address : userAddresses) {
                address.setDefault(false);
            }

            addressRepository.saveAll(userAddresses);

            existingAddress.setDefault(true);
        }

        return addressRepository.save(existingAddress);
    }

    // Delete an address
    public void deleteAddress(Long id) {

        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Address not found");
        }

        addressRepository.deleteById(id);
    }

    // Set an address as the default address
    public Address setDefaultAddress(Long id) {

        Address selectedAddress =
                addressRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Address not found"));

        List<Address> userAddresses =
                addressRepository.findByUserId(
                        selectedAddress.getUserId()
                );

        // Make every address non-default
        for (Address address : userAddresses) {
            address.setDefault(false);
        }

        addressRepository.saveAll(userAddresses);

        // Make selected address default
        selectedAddress.setDefault(true);

        return addressRepository.save(selectedAddress);
    }
}