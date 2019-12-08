FROM mysql:8.0

Maintainer samo

ENV MYSQL_ROOT_PASSWORD=${DEV_GIGETY_PW} \
    MYSQL_USER=${DEV_GIGETY_USR} \
    MYSQL_PASSWORD=${DEV_GIGETY_PW} \
    MYSQL_DATABASE=gigety_um
ADD schema.sql /docker-entrypoint-initdb.d

