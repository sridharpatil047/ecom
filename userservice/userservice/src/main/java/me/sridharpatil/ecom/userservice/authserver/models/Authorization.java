package me.sridharpatil.ecom.userservice.authserver.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter @Setter
@Entity @Table(name = "`authorization`")
public class Authorization {
    @Id
    @Column
    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    @Column(columnDefinition = "TEXT")
    private String authorizedScopes;
    @Column(columnDefinition = "TEXT")
    private String attributes;
    @Column(columnDefinition = "TEXT")
    private String state;

    @Column(columnDefinition = "TEXT")
    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;

    @Column(columnDefinition = "TEXT")
    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    @Column(columnDefinition = "TEXT")
    private String accessTokenMetadata;
    private String accessTokenType;
    @Column(columnDefinition = "TEXT")
    private String accessTokenScopes;

    @Column(columnDefinition = "TEXT")
    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    @Column(columnDefinition = "TEXT")
    private String refreshTokenMetadata;

    @Column(columnDefinition = "TEXT")
    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    @Column(columnDefinition = "TEXT")
    private String oidcIdTokenMetadata;
    @Column(columnDefinition = "TEXT")
    private String oidcIdTokenClaims;

    @Column(columnDefinition = "TEXT")
    private String userCodeValue;
    private Instant userCodeIssuedAt;
    private Instant userCodeExpiresAt;
    @Column(columnDefinition = "TEXT")
    private String userCodeMetadata;

    @Column(columnDefinition = "TEXT")
    private String deviceCodeValue;
    private Instant deviceCodeIssuedAt;
    private Instant deviceCodeExpiresAt;
    @Column(columnDefinition = "TEXT")
    private String deviceCodeMetadata;
}
