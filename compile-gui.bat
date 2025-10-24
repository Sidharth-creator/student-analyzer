@echo off
echo Compiling GUI application...
rem Create the classes directory if it doesn't exist
if not exist "classes" mkdir classes
javac -d classes -cp "lib/*" src/academic/ui/*.java src/academic/data/*.java src/academic/models/*.java
if %errorlevel% equ 0 (
    echo Compilation successful!
) else (
    echo Compilation failed.
)
pause
