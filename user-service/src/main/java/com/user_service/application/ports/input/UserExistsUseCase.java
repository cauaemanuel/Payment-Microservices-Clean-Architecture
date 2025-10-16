package com.user_service.application.ports.input;

import java.util.UUID;

public interface UserExistsUseCase {

    boolean execute(String email);
}
