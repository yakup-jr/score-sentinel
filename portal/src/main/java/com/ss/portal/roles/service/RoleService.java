package com.ss.portal.roles.service;

import com.ss.portal.roles.entity.RoleEntity;
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

    public RoleModel save(RoleEntity role) {
        if (roleRepository.findByName(role.getName()).isEmpty())
            return RoleModel.toModel(roleRepository.save(role));
        throw new RoleNotFoundException(role.getName());
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
        if (this.findById(id) != null) throw new RoleNotFoundException(id);
        roleRepository.deleteById(id);
    }

    public void deleteByName(RoleEnum name) throws RoleNotFoundException {
        if (this.findByName(name) == null) throw new RoleNotFoundException(name);
        roleRepository.deleteByName(name);
    }
}
