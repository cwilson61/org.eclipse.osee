@echo off

set api=%test%
REM ------------------------------------------------------------------------------
REM Create folders
REM ------------------------------------------------------------------------------
rmdir /s /q %api%GPP\%NAME%

REM ------------------------------------------------------------------------------
REM Remove Files
REM ------------------------------------------------------------------------------
del /s /q %speaker_b%\*
del /s /q %speaker_b%\*

exit /B %errors%