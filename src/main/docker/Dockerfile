FROM jboss/wildfly:24.0.0.Final

ENV WILDFLY_USER admin
ENV WILDFLY_PASS admin

ENV JBOSS_CLI $JBOSS_HOME/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR $JBOSS_HOME/standalone/deployments/

RUN echo "Adding WildFly administrator"
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

# Configure Wildfly server
RUN echo "Starting WildFly server" && \
      bash -c '$JBOSS_HOME/bin/standalone.sh -c standalone.xml &' && \
      bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
      curl --location --output /tmp/postgresql-42.3.1.jar --url https://jdbc.postgresql.org/download/postgresql-42.3.1.jar && \
      $JBOSS_CLI --connect --command="module add --name=org.postgresql --resources=/tmp/postgresql-42.3.1.jar --dependencies=javax.api,javax.transaction.api" && \
      $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=postgresql:add(driver-name="postgresql",driver-module-name="org.postgresql",driver-class-name=org.postgresql.Driver)" && \
	  $JBOSS_CLI --connect --command=":shutdown" && \
      rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
      rm -f /tmp/*.jar
      
COPY maven/${project.build.finalName}.${project.packaging} ${JBOSS_HOME}/standalone/deployments/

EXPOSE 8080
EXPOSE 8009
EXPOSE 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]