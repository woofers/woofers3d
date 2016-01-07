@echo off
git add -A
set /p message="Enter Commit Message: "
git commit -m %message%
git push
pause