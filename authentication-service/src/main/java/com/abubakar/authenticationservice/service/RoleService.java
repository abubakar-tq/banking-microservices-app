package com.abubakar.authenticationservice.service;

import com.abubakar.authenticationservice.dto.PageResponseDTO;
import com.abubakar.authenticationservice.dto.RoleRequestDTO;
import com.abubakar.authenticationservice.dto.RoleResponseDTO;

public interface RoleService {

    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);
    RoleResponseDTO updateRole(Long id, RoleRequestDTO roleRequestDTO);
    void deleteRoleById(Long id);
    RoleResponseDTO getRoleById(Long id);
    RoleResponseDTO getRoleByName(String name);
    PageResponseDTO<RoleResponseDTO> getAllRoles(int page, int size);
    PageResponseDTO<RoleResponseDTO> searchRoles(String query, int page, int size);
}
