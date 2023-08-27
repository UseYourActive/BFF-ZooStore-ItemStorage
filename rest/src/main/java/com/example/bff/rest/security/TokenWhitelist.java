package com.example.bff.rest.security;

public enum TokenWhitelist {
    GET(new String[]{
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/items",
            "/items/**",
            "/test",
            "/test/**",

            "/auth/login",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",


            "/shopping-cart/register"
    }),

    POST(new String[]{
            "/auth/login",
            "/auth/register",
            "/shopping-cart/register"
    }),

    PUT(new String[]{

    }),

    PATCH(new String[]{

    }),

    DELETE(new String[]{

    });

    public final String[] values;

    private TokenWhitelist(String[] values) {
        this.values = values;
    }
}
