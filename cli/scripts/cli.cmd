@REM Licensed to the Apache Software Foundation (ASF) under one or more
@REM contributor license agreements.  See the NOTICE file distributed with
@REM this work for additional information regarding copyright ownership.
@REM The ASF licenses this file to You under the Apache License, Version 2.0
@REM (the "License"); you may not use this file except in compliance with
@REM the License.  You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.

@echo off && rem

set SUCCESS_CODE=0
set ERROR_CODE=1

@REM check if java install
if "%JAVA_HOME%" == "" goto JAVA_HOME_ERROR
if not exist "%JAVA_HOME%\bin\java.exe" goto JAVA_HOME_ERROR

set jarFile=
for /f "delims=" %%t in ('dir /S /B cli-*.jar') do set jarFile=%%t

@REM check if jar file exist
if "%jarFile%" == "" (
  echo Error: cli.jar not exist. make sure it is placed in the current directory or subdirectory of this script
  exit /B %ERROR_CODE% 
)

set allparam=
:param
set str=%1
if "%str%"=="" (
    goto end
)
set allparam=%allparam% %str%
shift /0
goto param

:end
if "%allparam%"=="" (
    goto eof
)

rem remove left right blank
:intercept_left
if "%allparam:~0,1%"==" " set "allparam=%allparam:~1%"&goto intercept_left

:intercept_right
if "%allparam:~-1%"==" " set "allparam=%allparam:~0,-1%"&goto intercept_right

:eof

java -Dscript.name="cli.cmd" -jar %jarFile% %allparam%
exit /B %SUCCESS_CODE%

:JAVA_HOME_ERROR
echo.
echo Error: JAVA_HOME invalid in your environment. >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
exit /B %ERROR_CODE% 
