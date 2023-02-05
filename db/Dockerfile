FROM postgres:13-alpine

COPY cert /var/lib/postgresql

RUN chown 0:70 /var/lib/postgresql/server.key && chmod 640 /var/lib/postgresql/server.key
RUN chown 0:70 /var/lib/postgresql/server.crt && chmod 640 /var/lib/postgresql/server.crt

ENTRYPOINT ["docker-entrypoint.sh"]

CMD [ "-c", "ssl=on" , \
    "-c", "ssl_cert_file=/var/lib/postgresql/server.crt", \
    "-c", "ssl_key_file=/var/lib/postgresql/server.key"]
