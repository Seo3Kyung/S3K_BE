package com.s3k.backend.security.service;

import com.s3k.backend.member.entity.Member;
import com.s3k.backend.member.enums.Role;
import com.s3k.backend.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

  private final MemberService memberService;

  public static final String ID_TOKEN_CLAIM_NAME = "sub";

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    OidcUser oidcUser = super.loadUser(userRequest);

    String snsName = userRequest.getClientRegistration().getRegistrationId();
    String snsId = userRequest.getIdToken().getClaims().get(ID_TOKEN_CLAIM_NAME).toString();

    try {
      if (!memberService.existsMemberBySnsId(snsId)) {
        memberService.createPendingMember(snsId, snsName);
      }

      Member member = memberService.getMemberDetailBySnsId(snsId);

      List<GrantedAuthority> auths = List.of(
          new SimpleGrantedAuthority(Role.getEnum(member.getRole()).name()));

      return new DefaultOidcUser(auths,
          oidcUser.getIdToken(),
          oidcUser.getUserInfo(),
          ID_TOKEN_CLAIM_NAME);

    } catch (Exception e) {
      throw new OAuth2AuthenticationException(
          new OAuth2Error("LOGIN_PROCESSING_ERROR"), e.getMessage(), e.getCause()
      );
    }
  }
}
