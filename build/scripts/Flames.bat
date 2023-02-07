@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Flames startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and FLAMES_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Flames-7.jar;%APP_HOME%\lib\JDA-5.0.0-beta.3.jar;%APP_HOME%\lib\google-cloud-dialogflow-4.5.0.jar;%APP_HOME%\lib\google-cloud-language-2.1.6.jar;%APP_HOME%\lib\bugsnag-3.6.2.jar;%APP_HOME%\lib\protobuf-java-3.19.3.jar;%APP_HOME%\lib\protobuf-java-util-3.19.3.jar;%APP_HOME%\lib\proto-google-cloud-dialogflow-v2-4.5.0.jar;%APP_HOME%\lib\proto-google-cloud-dialogflow-v2beta1-0.103.0.jar;%APP_HOME%\lib\auto-value-annotations-1.9.jar;%APP_HOME%\lib\proto-google-cloud-language-v1-2.1.6.jar;%APP_HOME%\lib\proto-google-cloud-language-v1beta2-0.88.6.jar;%APP_HOME%\lib\google-auth-library-credentials-1.4.0.jar;%APP_HOME%\lib\google-auth-library-oauth2-http-1.4.0.jar;%APP_HOME%\lib\guava-31.0.1-jre.jar;%APP_HOME%\lib\google-http-client-1.41.2.jar;%APP_HOME%\lib\google-http-client-gson-1.41.2.jar;%APP_HOME%\lib\grpc-alts-1.44.0.jar;%APP_HOME%\lib\grpc-api-1.44.0.jar;%APP_HOME%\lib\grpc-auth-1.44.0.jar;%APP_HOME%\lib\grpc-context-1.44.0.jar;%APP_HOME%\lib\grpc-core-1.44.0.jar;%APP_HOME%\lib\grpc-grpclb-1.44.0.jar;%APP_HOME%\lib\grpc-netty-shaded-1.44.0.jar;%APP_HOME%\lib\grpc-protobuf-1.44.0.jar;%APP_HOME%\lib\grpc-protobuf-lite-1.44.0.jar;%APP_HOME%\lib\grpc-services-1.44.0.jar;%APP_HOME%\lib\grpc-stub-1.44.0.jar;%APP_HOME%\lib\api-common-2.1.3.jar;%APP_HOME%\lib\gax-2.11.0.jar;%APP_HOME%\lib\gax-grpc-2.11.0.jar;%APP_HOME%\lib\proto-google-common-protos-2.7.2.jar;%APP_HOME%\lib\trove4j-3.0.3.jar;%APP_HOME%\lib\jackson-annotations-2.14.1.jar;%APP_HOME%\lib\jackson-databind-2.14.1.jar;%APP_HOME%\lib\jackson-core-2.14.1.jar;%APP_HOME%\lib\slf4j-api-1.7.36.jar;%APP_HOME%\lib\nv-websocket-client-2.14.jar;%APP_HOME%\lib\okhttp-4.10.0.jar;%APP_HOME%\lib\opus-java-1.1.1.jar;%APP_HOME%\lib\commons-collections4-4.4.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\checker-qual-3.21.1.jar;%APP_HOME%\lib\error_prone_annotations-2.11.0.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\httpclient-4.5.13.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-codec-1.15.jar;%APP_HOME%\lib\httpcore-4.4.15.jar;%APP_HOME%\lib\opencensus-contrib-http-util-0.31.0.jar;%APP_HOME%\lib\opencensus-api-0.31.0.jar;%APP_HOME%\lib\conscrypt-openjdk-uber-2.5.1.jar;%APP_HOME%\lib\perfmark-api-0.23.0.jar;%APP_HOME%\lib\annotations-4.1.1.4.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.20.jar;%APP_HOME%\lib\grpc-xds-1.44.0.jar;%APP_HOME%\lib\gson-2.8.9.jar;%APP_HOME%\lib\re2j-1.5.jar;%APP_HOME%\lib\bcpkix-jdk15on-1.67.jar;%APP_HOME%\lib\bcprov-jdk15on-1.67.jar;%APP_HOME%\lib\opencensus-proto-0.2.0.jar;%APP_HOME%\lib\threetenbp-1.5.2.jar;%APP_HOME%\lib\okio-jvm-3.0.0.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.5.31.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.5.31.jar;%APP_HOME%\lib\kotlin-stdlib-1.6.20.jar;%APP_HOME%\lib\opus-java-api-1.1.1.jar;%APP_HOME%\lib\opus-java-natives-1.1.1.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.6.20.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\jna-4.4.0.jar


@rem Execute Flames
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %FLAMES_OPTS%  -classpath "%CLASSPATH%" com.severalcircles.flames.Flames %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable FLAMES_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%FLAMES_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
