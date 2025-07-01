package com.rutik.ems.service;

import com.rutik.ems.dto.LoginRequest;
import com.rutik.ems.dto.RegisterRequest;
import com.rutik.ems.model.Admin;

public interface AdminService {
    String register(RegisterRequest req);
    Admin login(LoginRequest req);
}
