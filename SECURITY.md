# Security Architecture – JWT & RSA

## Overview
The platform uses stateless authentication based on JWT tokens signed with an asymmetric RSA key pair.

- Membership service: authentication & JWT generation
- Product & Order services: JWT validation

## Authentication Flow
1. Client sends credentials to POST /api/v1/auth/login
2. Membership validates email/password
3. JWT token is generated and signed using RSA private key (RS256)
4. Client includes the token in `Authorization: Bearer <token>`
5. Product and Order validate the token using the RSA public key

## JWT Configuration
- Algorithm: RS256
- Expiration: 1 hour

### JWT Payload
```json
{
  "sub": "userId",
  "email": "user@email.com",
  "roles": ["ROLE_USER"],
  "iat": <issued_at>,
  "exp": <expires_at>
}
