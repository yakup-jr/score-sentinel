package com.ss.portal.roles.service;

import com.ss.portal.roles.enums.RoleEnum;
import com.ss.portal.roles.exception.RoleNotFoundException;
import com.ss.portal.roles.model.RoleModel;
import com.ss.portal.roles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleModel findById(Long id) throws RoleNotFoundException {
        return RoleModel.toModel(
            roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException((id))));
    }

    public RoleModel findByName(RoleEnum name) throws RoleNotFoundException {
        return RoleModel.toModel(
            roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(name)));
    }

    public List<RoleModel> findAll() {
        return RoleModel.toModel(roleRepository.findAll());
    }

    public void deleteById(Long id) throws RoleNotFoundException {
        if (this.findById(id) != null) new RoleNotFoundException(id);
        roleRepository.deleteById(id);
    }

    public void deleteByName(RoleEnum name) throws RoleNotFoundException {
        if (this.findByName(name) != null) new RoleNotFoundException(name);
        roleRepository.deleteByName(name);
    }
}
