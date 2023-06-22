package com.shoppingcart.services.impl;

import com.shoppingcart.entities.Authority;
import com.shoppingcart.exceptions.BadRequestException;
import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.repositories.AuthorityRepository;
import com.shoppingcart.services.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority getById(int id) throws NotFoundException {
        return authorityRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find Authority with current ID: " + id));
    }

    @Override
    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Authority authority) throws BadRequestException {
        authorityRepository.save(authority);
    }

    @Transactional
    @Override
    public void update(Authority authority) {
        authorityRepository.save(authority);
    }

    @Transactional
    @Override
    public void delete(int id) {
        authorityRepository.deleteById(id);
    }
}
