package ua.kusakabe.filter;

import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.kusakabe.dto.AuthRR;
import ua.kusakabe.service.TokenCacheService;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private TokenCacheService tokenCacheService;
    @Autowired
    private RestTemplate restTemplate;

    public AuthFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            String token = null;

            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token ?
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Authorization header not present");
                }
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.contains("Bearer")) {
                    token = authHeader.substring(7);
                }
                //Check if token is present in cache
                if(tokenCacheService.isTokenCached(token)){
                    //If yes -> grant access with that token
                    return chain.filter(exchange);
                } else {
                    //If no -> make request to auth-service for validation
                    AuthRR req = new AuthRR();
                    req.setToken(token);
                    String resBody = restTemplate.postForObject("http://localhost:8080/api/v1/auth/validate", req, String.class);
                    if(resBody != null && !resBody.isEmpty()) {
                        if(resBody.equals("OK")){  //If response is 200 status code -> grant access;
                            tokenCacheService.cacheToken(token);
                            return chain.filter(exchange);
                        } else {    //Or else validation failed
                            throw new RuntimeException("Token validation failed!");
                        }
                    } else {
                        throw new RuntimeException("Invalid validation request!");
                    }
                }
            }
            return null;
        }));
    }

    public static class Config {}

}
