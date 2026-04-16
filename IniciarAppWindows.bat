@echo off
SET APP_HOME=%~dp0
SET JAVA_FX_HOME=%APP_HOME%lib\javafx-sdk-17.0.18
SET CLASSPATH=%APP_HOME%out\production\PracticaFinal;%APP_HOME%lib\sqlite-jdbc-3.41.2.2.jar
SET MODULE_PATH=%JAVA_FX_HOME%\lib

echo Iniciando la aplicación...
java --module-path "%MODULE_PATH%" --add-modules javafx.controls,javafx.fxml,javafx.graphics -classpath "%CLASSPATH%" Aplicacion.Main

pause
