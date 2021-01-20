package com.mole.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.mole.entity.User;
import com.mole.repository.UserRepository;

import org.reactivestreams.Publisher;

import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AuthenticationProviderUserPassword implements AuthenticationProvider { 

    @Inject
    private final UserRepository userRepo;

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            Optional<User> user = userRepo.findByEmailAndPasswordAndEnabled(authenticationRequest.getIdentity().toString(), Aes256.encrypt(authenticationRequest.getSecret().toString()), true);
            if (user.isPresent()) {
                User user_ = user.get();
                List<String> roles;
                if (user_.getAdmin()) {
                    roles = Arrays.asList("ADMIN");
                } else {
                    roles = Arrays.asList("USER");
                }
                emitter.onNext(new UserDetails((String) authenticationRequest.getIdentity(), roles));
                emitter.onComplete();
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
        }, BackpressureStrategy.ERROR);
    }
}