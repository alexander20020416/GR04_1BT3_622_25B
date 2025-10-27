# Usamos Tomcat 9 con JDK 21
FROM tomcat:9.0.109-jdk21



# Limpiamos aplicaciones por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiamos el WAR generado por Maven
COPY target/GR04_1BT3_622_25B.war /usr/local/tomcat/webapps/GR04_1BT3_622_25B.war

# Exponemos el puerto 8080 para la webapp
EXPOSE 8080

# Comando por defecto para iniciar Tomcat
CMD ["catalina.sh", "run"]