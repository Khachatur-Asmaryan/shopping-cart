package com.shoppingcart.services.interfaces;

import com.shoppingcart.exceptions.BadRequestException;
import com.shoppingcart.exceptions.NotFoundException;
import com.shoppingcart.models.Authority;

import java.util.List;

public interface AuthorityService {

    Authority getById(int id) throws NotFoundException;

    List<Authority> getAll();

    void save(Authority authority) throws BadRequestException;

    void update(Authority authority);

    void delete(int id);
}
