package com.nhnacademy.inkbridge.batch.dto.keymanager;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: KeyResponseDto.
 *
 * @author choijaehun
 * @version 2024/02/27
 */

@Getter
public class KeyResponseDto {
    private Header header;
    private Body body;

    /**
     * key manager response body
     */
    @Getter
    @NoArgsConstructor
    public static class Body {
        private String secret;
    }


    /**
     * key manager response header
     */
    @Getter
    @NoArgsConstructor
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private boolean isSuccessful;
    }
}
