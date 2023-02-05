FROM gradle:jdk11

WORKDIR /app

ADD --chown=gradle:gradle /bin/app/ .

ENTRYPOINT ["gradle"]

CMD ["build", "bootRun"]
