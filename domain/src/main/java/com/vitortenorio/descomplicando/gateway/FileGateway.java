package com.vitortenorio.descomplicando.gateway;

import com.vitortenorio.descomplicando.enums.FileType;

public interface FileGateway {
    void process(FileType fileType);
}
