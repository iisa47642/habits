# https://www.keycloak.org/server/containers
FROM quay.io/keycloak/keycloak:26.0.0
COPY keycloak-kafka-1.1.5-jar-with-dependencies.jar /opt/keycloak/providers/keycloak-kafka-1.1.5-jar-with-dependencies.jar
RUN /opt/keycloak/bin/kc.sh build

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]