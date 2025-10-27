# Etapa 1: Compilar con Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Crear y entrar en el directorio de trabajo
WORKDIR /app

# Copiar el contenido del proyecto
COPY . .

#Empaquetar la apliacion (WAR)
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar en Tomcat 9 (JSP con javax)
FROM tomcat:9.0-jdk21-temurin

# Limpiar apps por defecto
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia del WAR generado desde la etapa de compilación
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Puerto de Tomcat
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]
