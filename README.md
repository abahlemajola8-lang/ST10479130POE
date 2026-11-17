# QuickChat (PROG5121 PoE)

Console messaging app: registration, login, send messages, store in `messages.json`,
longest message, search by recipient, delete by hash, read JSON, message report.

## Run in NetBeans
File > Open Project > select the QuickChat folder > Run (F6).

## Run the tests
Right-click the project > Test (Alt+F6), or `mvn test`.

## GitHub (required by the rubric)
```
git init
git add .
git commit -m "QuickChat PoE"
git branch -M main
git remote add origin https://github.com/<your-username>/QuickChat.git
git push -u origin main
```
The workflow in `.github/workflows/TestJava.yml` runs the unit tests automatically on every push.
