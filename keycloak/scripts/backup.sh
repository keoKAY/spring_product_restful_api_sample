#!/bin/bash

echo "Backing up the keycloak as JSON "

docker exec -it ite-keycloak \
/opt/keycloak/bin/kc.sh export \
--realm ecommerce_realm \
--file /tmp/ecommerce_realm.json


echo "Copy it out....!"
docker cp ite-keycloak:/tmp/ecommerce_realm.json .